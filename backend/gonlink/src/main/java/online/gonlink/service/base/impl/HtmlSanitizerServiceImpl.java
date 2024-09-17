package online.gonlink.service.base.impl;
import online.gonlink.exception.ResourceException;
import online.gonlink.exception.enumdef.ExceptionEnum;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.stereotype.Component;

@Component
public class HtmlSanitizerServiceImpl {

    public String sanitize(String html) {
        return Jsoup.clean(html, Safelist.basic());
    }

    public void sanitizeStrict(String html) {
        String clean = Jsoup.clean(html, Safelist.none());
        if(!html.equals(clean))
            throw new ResourceException(ExceptionEnum.HTML_SANITIZER, null);
    }

}
