package br.com.conductor.util;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

public class JsonUtil {

    public static Object createResponsePersonId(String body) {
        return new ResponsePersonId(body);
    }

    public static Object createResponseError(String body) {
        return new ResponseError(body);
    }

    public static Object createResponseMessage(String body) {
        return new ResponseSuccess(body);
    }

    @Data
    @AllArgsConstructor
    private static class ResponsePersonId implements Serializable {

        private String personId;
    }

    @Data
    @AllArgsConstructor
    private static class ResponseError implements Serializable{

        private String error;
    }

    @Data
    @AllArgsConstructor
    private static class ResponseSuccess implements Serializable{

        private String message;
    }
}
