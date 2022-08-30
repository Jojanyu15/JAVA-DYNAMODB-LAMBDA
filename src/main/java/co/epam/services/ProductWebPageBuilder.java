package co.epam.services;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.Objects;

public class ProductWebPageBuilder {
    private final String webPageHTML;

    public ProductWebPageBuilder(String webPageHTML) {
        this.webPageHTML = webPageHTML;
    }

    public String parseProductInsideWebPage(String productCard){
        Document doc = Jsoup.parse(this.webPageHTML);
        Objects.requireNonNull(doc.getElementById("products-container")).append(productCard);
        return doc.html();
    }

    public String updateProductInsideWebPage(String oldProductId, String newProductCard) {
        Document doc = Jsoup.parse(this.webPageHTML);
        Objects.requireNonNull(doc.getElementById(oldProductId))
                .replaceWith(Objects.requireNonNull(Jsoup.parseBodyFragment(newProductCard).getElementById(oldProductId)));
        return doc.html();
    }

}
