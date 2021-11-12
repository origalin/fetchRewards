package com.excercise.fetchrewards.common.dao;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PayerBalance {
    private Long id;
    private String payer;
    private Long points;
    private Long timestamp;
}
