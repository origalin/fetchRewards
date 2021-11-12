package com.excercise.fetchrewards.common.web;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class PayerBalanceVO implements Serializable {
    private String payer;
    private Long points;
}
