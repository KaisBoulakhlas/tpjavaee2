package montp.locale;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

@ApplicationScoped
public class Messages {
    
    private ResourceBundle resourceBundle;

    @PostConstruct
    public void init() {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        resourceBundle = ResourceBundle.getBundle("montp.locale.messages", Locale.getDefault(), loader);
    }

    public Locale getLocale() {
        return resourceBundle.getLocale();
    }

    public String get(String key) {
        if (resourceBundle != null) {
            try {
                return resourceBundle.getString(key);
            } catch (MissingResourceException mre) {
            }
        }
        return "___";
    }

    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }
    
}
