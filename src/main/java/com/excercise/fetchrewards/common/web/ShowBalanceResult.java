package com.excercise.fetchrewards.common.web;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@ToString
public class ShowBalanceResult implements Serializable {
    List<PayerBalanceVO> balanceList;
}
