package com.excercise.fetchrewards.common.web;

import com.excercise.fetchrewards.common.error.ErrorCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class BaseResponse<E> implements Serializable {
    private Boolean success;
    private Integer errorCode = 0;
    private String errorMsg = "";
    private Long serverTime = System.currentTimeMillis();
    private E data;

    private BaseResponse(){

    }

    public static <E> BaseResponse<E> success(E data){
        BaseResponse<E> response = new BaseResponse<>();
        response.data = data;
        response.success = true;
        return response;
    }

    public static <E> BaseResponse<E> fail(ErrorCode errorCode){
        BaseResponse<E> response = new BaseResponse<>();
        response.success = false;
        response.errorCode = errorCode.getCode();
        response.errorMsg = errorCode.getDesc();
        return response;
    }
}
