package co.epam.services.dynamo;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface RecordMapperService<T> {
    T getNewImage() throws JsonProcessingException;
    T getOldImage() throws JsonProcessingException;
}
