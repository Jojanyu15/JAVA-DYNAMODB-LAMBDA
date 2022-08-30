package co.epam.services.dynamo;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemUtils;
import com.amazonaws.services.dynamodbv2.model.Record;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.function.Function;

public class DynamoRecordMapperService<T>  implements RecordMapperService<T>{

    private final Record databaseRecord;
    private final Class<T> typeParameterClass;

    public DynamoRecordMapperService(Record databaseRecord, Class<T> typeParameterClass) {
        this.databaseRecord = databaseRecord;
        this.typeParameterClass = typeParameterClass;
    }

    public T getNewImage() throws JsonProcessingException {
        return mapRecordToObject(dynamoRecord -> ItemUtils.toItem(dynamoRecord.getDynamodb().getNewImage()), typeParameterClass);
    }

    public T getOldImage() throws JsonProcessingException {
        return mapRecordToObject(dynamoRecord -> ItemUtils.toItem(dynamoRecord.getDynamodb().getOldImage()), typeParameterClass);
    }

    public T mapRecordToObject(Function<Record, Item> function, Class<T> clazz) throws JsonProcessingException {
        return new ObjectMapper().readValue(mapRecordToItem(function).toJSON(), clazz);
    }

    private Item mapRecordToItem(Function<Record, Item> function) {
        return function.apply(this.databaseRecord);
    }

}
