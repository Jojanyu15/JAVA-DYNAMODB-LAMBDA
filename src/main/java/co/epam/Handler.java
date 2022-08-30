package co.epam;

import co.epam.model.Product;
import co.epam.services.dynamo.DynamoRecordMapperService;
import co.epam.services.dynamo.RecordMapperService;
import co.epam.services.s3.S3WebPageSender;
import com.amazonaws.services.dynamodbv2.model.Record;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.DynamodbEvent;
import com.amazonaws.services.lambda.runtime.events.transformers.v1.DynamodbEventTransformer;

import java.io.IOException;

public class Handler {
    private static final String EVENT_MODIFY = "MODIFY";
    private static final String EVENT_INSERT = "INSERT";

    public void handleRequest(DynamodbEvent event, Context context) {
        DynamodbEventTransformer.toRecordsV1(event).forEach(dynamoRecord -> {
            try {
                processRecord(dynamoRecord);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void processRecord(Record eventRecord) throws IOException {
        RecordMapperService<Product> dynamoEventMapper = new DynamoRecordMapperService<>(eventRecord, Product.class);
        if (eventRecord.getEventName().equals(EVENT_MODIFY)) {
            S3WebPageSender.createItem(dynamoEventMapper.getNewImage());
        } else if (eventRecord.getEventName().equals(EVENT_INSERT)) {
            S3WebPageSender.updateItem(dynamoEventMapper.getOldImage(),dynamoEventMapper.getNewImage());
        }
    }
}
