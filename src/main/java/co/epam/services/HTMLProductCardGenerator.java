package co.epam.services;

import co.epam.model.Product;

public class HTMLProductCardGenerator {

    private static final String CARD_TEMPLATE = "<div id=\"%d\" class=\"col-sm-6 card mb-4 box-shadow\">\n" +
            "          <div class=\"card-header\">\n" +
            "            <h4 class=\"my-0 font-weight-normal\">%s id: %d</h4>\n" +
            "          </div>\n" +
            "          <div class=\"card-body\">\n" +
            "            <h1 class=\"card-title pricing-card-title\">\n" +
            "              $ %s \n" +
            "            </h1>\n" +
            "            <h1>\n" +
            "              <small class=\"text-muted\">category: %s</small>\n" +
            "            </h1>\n" +
            "          </div>\n" +
            "        </div>\n" ;

    public static String generateHTMLCard(Product product) {
        return String.format(CARD_TEMPLATE,product.getId(),product.getName(),product.getId(), product.getPrice(),product.getCategory());
    }
}
