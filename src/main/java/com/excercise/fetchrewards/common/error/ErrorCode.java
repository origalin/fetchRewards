package com.excercise.fetchrewards.common.error;

import lombok.Getter;

@Getter
public enum ErrorCode {
    BALANCE_NOT_ENOUGH(100001, "balance_not_enough"),
    ;

    private final Integer code;
    private final String desc;

    ErrorCode(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
