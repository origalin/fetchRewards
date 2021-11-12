package com.excercise.fetchrewards.common.web;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SpendBalanceRequest extends BaseRequest {
    Long points;
}
