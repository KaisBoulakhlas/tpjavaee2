package montp.tools;

import javax.enterprise.inject.spi.CDI;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormatSymbols;
import java.text.Normalizer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public abstract class Tools {

    public static Date getDate(LocalDateTime dateTime) {
        return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDateTime getLocalDateTime(Date dateToConvert) {
        return LocalDateTime.ofInstant(
                dateToConvert.toInstant(), ZoneId.systemDefault());
    }

    public static Date getDate(LocalDate date) {
        return Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDate getLocalDate(Date dateToConvert) {
        return LocalDate.ofInstant(
                dateToConvert.toInstant(), ZoneId.systemDefault());
    }

    public static String getMonthName(int month) {
        return new DateFormatSymbols().getMonths()[month - 1];
    }

    public static int getNumberOfDaysInMonth(int month, int year) {
        Calendar monthStart = new GregorianCalendar(year, month, 1);
        return monthStart.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public static String stripAccents(String s) {
        s = Normalizer.normalize(s, Normalizer.Form.NFD);
        return s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
    }

    @SuppressWarnings("unchecked")
    public static <T> T lookupEJB(Class<T> ejbClass, String jndiName) {
        try {
            return (T) new InitialContext().lookup("java:comp/env/" + jndiName);
        } catch (NamingException e) {
            throw new IllegalArgumentException(
                String.format("Cannot find EJB class %s in JNDI %s", ejbClass, jndiName), e);
        }
    }

    public static <T> T lookupBean(Class<T> clazz) {
        try {
            return CDI.current().select(clazz).get();
        } catch (RuntimeException ex) {
            throw new IllegalArgumentException(
                String.format("Cannot find CDI bean %s", clazz), ex);
        }
    }

    public static String digestSHA256Hex(String text) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(text.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (int i = 0; i < hash.length; i++) {
                hexString.append(Integer.toString((hash[i] & 0xff) + 0x100, 16).substring(1));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException ex) {
            return text;
        }
    }

}
