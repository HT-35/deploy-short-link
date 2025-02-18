package com.example.short_link.shared.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter // All lombok annotations
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RestResponse<T> {

    private int statusCode;
    private String error;

    /**
     *
     *
     * */
    private Object message;
    private T data;


//    public  T getData() {
//        return data;
//    }
//
//    public void setData(T data) {
//        this.data = data;
//    }
}