package online.gonlink.util;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.stereotype.Component;

@Component
public class HtmlSanitizer {

    public String sanitize(String html) {
        return Jsoup.clean(html, Safelist.basic());
    }

    public String sanitizeStrict(String html) {
        return Jsoup.clean(html, Safelist.none());
    }

}