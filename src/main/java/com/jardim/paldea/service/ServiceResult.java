package com.jardim.paldea.service;

public record ServiceResult<T>(ResultType type, String message, T data) {

    public static <T> ServiceResult<T> success(String message, T data) {
        return new ServiceResult<>(ResultType.SUCCESS, message, data);
    }

    public static <T> ServiceResult<T> badRequest(String message) {
        return new ServiceResult<>(ResultType.BAD_REQUEST, message, null);
    }

    public static <T> ServiceResult<T> notFound(String message) {
        return new ServiceResult<>(ResultType.NOT_FOUND, message, null);
    }

    public boolean isSuccess() {
        return type == ResultType.SUCCESS;
    }

    public enum ResultType {
        SUCCESS,
        BAD_REQUEST,
        NOT_FOUND
    }
}
