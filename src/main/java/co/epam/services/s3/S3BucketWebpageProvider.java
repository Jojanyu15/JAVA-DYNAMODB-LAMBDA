package co.epam.services.s3;

import co.epam.config.s3.S3ClientConfig;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;

import java.io.*;
import java.net.URISyntaxException;
import java.util.Objects;

public class S3BucketWebpageProvider {

    private static final String S3_BUCKET_NAME = "orders-bucket-sqs-processed";
    private static final String WEB_PAGE_FILENAME = "index.html";
    private static final AmazonS3 s3Client = new S3ClientConfig().getS3Client();

    public void uploadHTMLWebPage(String webPageStreamHTML) {
        InputStream webPageInputStream = mapToInputStream(webPageStreamHTML);
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType("HTML");
        s3Client.putObject(S3_BUCKET_NAME, WEB_PAGE_FILENAME, webPageInputStream, metadata);
    }

    public static String getHTMLStringWebPage() throws IOException {
        validateWebPage();
        try {
            return new String(s3Client.getObject(S3_BUCKET_NAME, WEB_PAGE_FILENAME).getObjectContent().readAllBytes());
        } catch (IOException e) {
            throw new IOException(e.getCause());
        }
    }

    private static void validateWebPage() {
        if (isWebPageNotPresent()) {
            uploadWebPageTemplate();
        }
    }

    private static void uploadWebPageTemplate() {
        ClassLoader classLoader = S3BucketWebpageProvider.class.getClassLoader();
        try {
            File webPageFile = new File(Objects.requireNonNull(classLoader.getResource(WEB_PAGE_FILENAME)).toURI());
            s3Client.putObject(S3_BUCKET_NAME, WEB_PAGE_FILENAME, webPageFile);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private InputStream mapToInputStream(String text) {
        return new ByteArrayInputStream(text.getBytes());
    }

    private static boolean isWebPageNotPresent() {
        return !s3Client.doesObjectExist(S3_BUCKET_NAME, WEB_PAGE_FILENAME);
    }

}
