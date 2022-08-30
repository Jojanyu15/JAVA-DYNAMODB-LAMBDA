package co.epam.services.s3;

import co.epam.model.Product;
import co.epam.services.HTMLProductCardGenerator;
import co.epam.services.ProductWebPageBuilder;

import java.io.IOException;

public class S3WebPageSender {

    private static final S3BucketWebpageProvider s3BucketProviderService = new S3BucketWebpageProvider();
    private static ProductWebPageBuilder webPageBuilder;

    public S3WebPageSender() throws IOException {
        loadWebPage();
    }


    public static void createItem(Product newItem)  {
        String webPage = webPageBuilder.parseProductInsideWebPage(HTMLProductCardGenerator.generateHTMLCard(newItem));
        s3BucketProviderService.uploadHTMLWebPage(webPage);
    }

    public static void updateItem(Product oldItem, Product newItem)  {
        String webPage =  webPageBuilder.updateProductInsideWebPage(
                        String.valueOf(oldItem.getId()),
                        HTMLProductCardGenerator.generateHTMLCard(newItem));
        s3BucketProviderService.uploadHTMLWebPage(webPage);
    }

    private void loadWebPage() throws IOException {
        this.webPageBuilder= new ProductWebPageBuilder(S3BucketWebpageProvider.getHTMLStringWebPage());
    }
}
