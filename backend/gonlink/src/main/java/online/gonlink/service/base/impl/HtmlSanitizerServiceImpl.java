package online.gonlink.service.base.impl;

import online.gonlink.exception.ResourceException;
import online.gonlink.exception.enumdef.ExceptionEnum;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.stereotype.Component;

@Component
public class HtmlSanitizerServiceImpl {

    public String sanitize(String html) {
        Safelist safelist = Safelist.basic()
                .addProtocols("a", "href", "ftp", "http", "https", "mailto")
                .addProtocols("img", "src", "http", "https")
                .addAttributes("a", "target")
                .addEnforcedAttribute("a", "rel", "nofollow")
                .addTags("br", "p", "div", "span")
                .preserveRelativeLinks(true);

        return Jsoup.clean(html, safelist);
    }

    public void sanitizeStrict(String html) {
        Safelist safelist = Safelist.none()
                .addTags("a")
                .addAttributes("a", "href")
                .addProtocols("a", "href", "http", "https", "mailto")
                .preserveRelativeLinks(true);

        String clean = Jsoup.clean(html, safelist);
        if (!html.equals(clean)) {
            throw new ResourceException(ExceptionEnum.HTML_SANITIZER, null);
        }
    }
}