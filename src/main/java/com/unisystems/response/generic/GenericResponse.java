package com.unisystems.response.generic;

import java.util.List;

public class GenericResponse<T> {
    private T data;
    private List<Error> errors;

    public GenericResponse(T data) {
        this.data = data;
    }

    public GenericResponse(List<Error> errors) {
        this.errors = errors;
    }

    public GenericResponse() {
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public List<Error> getErrors() {
        return errors;
    }

    public void setErrors(List<Error> errors) {
        this.errors = errors;
    }

}
