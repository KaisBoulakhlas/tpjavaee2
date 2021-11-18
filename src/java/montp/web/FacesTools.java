package montp.web;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.event.PhaseId;
import javax.imageio.ImageIO;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Locale;

@Named
@Singleton
public class FacesTools {

    public static String getBaseUrl() {
        return getBaseUrl(getRequest());
    }

    public static String getBaseUrl(HttpServletRequest request) {
        String scheme = request.getScheme() + "://";
        String serverName = request.getServerName();
        String serverPort = (request.getServerPort() == 80) ? "" : ":" + request.getServerPort();
        String contextPath = request.getContextPath();
        return scheme + serverName + serverPort + contextPath;
    }

    public static ServletContext getServletContext() {
        return (ServletContext) getExternalContext().getContext();
    }

    public static ExternalContext getExternalContext() {
        return FacesContext
                .getCurrentInstance().getExternalContext();
    }

    public static HttpServletResponse getResponse() {
        return (HttpServletResponse) getExternalContext().getResponse();
    }

    public static HttpServletRequest getRequest() {
        return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
    }

    /*public static boolean isMobileDevice() {
        return isMobileDevice(getRequest());
    }

    public static boolean isMobileDevice(HttpServletRequest request) {
        UAgentInfo agentInfo = new UAgentInfo(request.getHeader("User-Agent"), null);
        return agentInfo.detectMobileQuick() || agentInfo.detectTierTablet();
    }*/

    public static void addMessage(FacesMessage.Severity severity,
            String msg, Object... args) {
        FacesContext
                .getCurrentInstance()
                .addMessage(null,
                        new FacesMessage(severity, String.format(msg, args), ""));
    }

    public static void addFlashMessage(FacesMessage.Severity severity,
            String msg, Object... args) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Flash flash = facesContext.getExternalContext().getFlash();
        flash.setKeepMessages(true);
        flash.setRedirect(true);
        facesContext.addMessage(null, new FacesMessage(severity, String.format(msg, args), ""));
    }

    public static void redirect(String url) {
        ExternalContext ec = getExternalContext();
        try {
            ec.redirect(ec.getRequestContextPath() + "/app/" + url + ".xhtml");
        } catch (IOException ex) {
        }
    }
    
    public boolean caseInsensitiveFilter(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim();
        if (filterText == null || filterText.equals("")) {
            return true;
        }
        if (value == null) {
            return false;
        }
        String name = value.toString().toUpperCase();
        filterText = filterText.toUpperCase();
        return name.contains(filterText);
    }

    /*
    exemple : 
    <p:graphicImage value="#{bean.getImage}">
        <f:param name="height" value="200"/>
    </p:graphicImage>
     */
    public static StreamedContent sendImage(byte[] data)
            throws IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if (facesContext.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            return DefaultStreamedContent.builder().build();
        }
        int width = -1;
        int height = -1;
        ExternalContext externalContext = facesContext.getExternalContext();
        String swidth = externalContext.getRequestParameterMap().get("width");
        if (swidth != null) {
            width = Integer.parseInt(swidth);
        }
        String sheight = externalContext.getRequestParameterMap().get("height");
        if (sheight != null) {
            height = Integer.parseInt(sheight);
        }
        final ByteArrayInputStream bis;
        if ((height != -1) || (width != -1)) {
            BufferedImage bim = ImageIO.read(new ByteArrayInputStream(data));
            Image scaled = bim.getScaledInstance(width, height,
                    Image.SCALE_SMOOTH);
            BufferedImage scaledBim = new BufferedImage(
                    scaled.getWidth(null), scaled.getHeight(null),
                    BufferedImage.TYPE_INT_RGB);
            scaledBim.createGraphics().drawImage(scaled, 0, 0, null);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(scaledBim, "jpeg", os);
            bis = new ByteArrayInputStream(os.toByteArray());
        } else {
            bis = new ByteArrayInputStream(data);
        }
        return DefaultStreamedContent.builder().contentType("image/jpeg").name("image.jpeg").stream(() -> bis).build();
    }

    public static String getClientIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

}
