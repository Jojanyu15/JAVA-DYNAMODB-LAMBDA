package co.epam.config.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

public class S3ClientConfig {
     public AmazonS3 getS3Client(){
        return AmazonS3ClientBuilder.standard().build();
    }
}
