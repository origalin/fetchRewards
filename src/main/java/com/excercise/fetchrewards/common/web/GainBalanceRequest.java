package com.excercise.fetchrewards.common.web;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class GainBalanceRequest extends BaseRequest {
    private String payer;
    private Long point;
    private Long timestamp;
}
