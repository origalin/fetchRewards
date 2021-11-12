package com.excercise.fetchrewards.repository;

import com.excercise.fetchrewards.common.dao.PayerBalance;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Simple repository implementation based on memory storage, deepcopy needed for a safe code
 * Thread safety is not guaranteed in this repo
 */
@Component
public class PayerBalanceRepository {

    List<PayerBalance> data = new ArrayList<>();

    public Boolean hasAny() {
        return data.size() > 0;
    }

    public List<PayerBalance> getAllPositive() {
        return data.stream().filter(payerBalance -> payerBalance.getPoints() > 0).collect(Collectors.toList());
    }

    public PayerBalance getEarliestPositive() {
        return data.stream().filter(payerBalance -> payerBalance.getPoints() > 0).min(Comparator.comparing(PayerBalance::getTimestamp)).orElse(null);
    }

    public void insertAll(List<PayerBalance> balanceList) {
        for (PayerBalance balance : balanceList) {
            balance.setId((long) data.size());
            data.add(balance);
        }
    }

    public void updateAll(List<PayerBalance> balanceList) {
        for (PayerBalance balance : balanceList) {
            PayerBalance origin = data.stream().filter(payerBalance -> Objects.equals(payerBalance.getId(), balance.getId())).findFirst().orElse(null);
            if (origin == null) {
                throw new RuntimeException();
            }
            origin.setPayer(balance.getPayer());
            origin.setTimestamp(balance.getTimestamp());
            origin.setPoints(balance.getPoints());
        }
    }
}
