package co.epam.services.s3;

import co.epam.model.Product;
import co.epam.services.HTMLProductCardGenerator;
import co.epam.services.ProductWebPageBuilder;

import java.io.IOException;

public class S3WebPageSender {

    private static final S3BucketWebpageProvider s3BucketProviderService = new S3BucketWebpageProvider();

    private S3WebPageSender(){}

    public static void createItem(Product newItem) throws IOException {
        String webPage = loadWebPageBuilder().parseProductInsideWebPage(HTMLProductCardGenerator.generateHTMLCard(newItem));
        s3BucketProviderService.uploadHTMLWebPage(webPage);
    }

    public static void updateItem(Product oldItem, Product newItem) throws IOException {
        String webPage =  loadWebPageBuilder().updateProductInsideWebPage(
                        String.valueOf(oldItem.getId()),
                        HTMLProductCardGenerator.generateHTMLCard(newItem));
        s3BucketProviderService.uploadHTMLWebPage(webPage);
    }

    private static ProductWebPageBuilder loadWebPageBuilder() throws IOException {
        return new ProductWebPageBuilder(S3BucketWebpageProvider.getHTMLStringWebPage());
    }
}
