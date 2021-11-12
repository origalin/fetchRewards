package com.excercise.fetchrewards.core.service;

import com.excercise.fetchrewards.common.dao.PayerBalance;
import com.excercise.fetchrewards.common.dao.Transaction;
import com.excercise.fetchrewards.common.web.*;
import com.excercise.fetchrewards.repository.PayerBalanceRepository;
import com.excercise.fetchrewards.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * There is an assumption: all the transactions happened before spending should be recorded.
 * If not, record new transaction afterwards may lead to different or invalid balance result
 * Example:     Alex:1000 11:10
 * Ella:1000 11:20
 * spend 1000
 * result:
 * Alex:0
 * Ella:1000
 * If there is actually a transaction arrives after spending
 * Alex:-500 11:15
 * the current balance is invalid
 * And if it arrived at the beginning, we can have current balance
 * Alex:0
 * Ella:500
 */
@Component
public class BalanceService {

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private PayerBalanceRepository payerBalanceRepository;

    public ShowBalanceResult showBalance(ShowBalanceRequest request) {
        //just get all, show them
        if (!payerBalanceRepository.hasAny()) {
            buildPayerBalance();
        }
        List<PayerBalance> payerBalanceList = payerBalanceRepository.getAllPositive();
        ShowBalanceResult result = new ShowBalanceResult();
        List<PayerBalanceVO> voList = payerBalanceList.stream().map(payerBalance -> {
            PayerBalanceVO balanceVO = new PayerBalanceVO();
            balanceVO.setPayer(payerBalance.getPayer());
            balanceVO.setPoints(payerBalance.getPoints());
            return balanceVO;
        }).collect(Collectors.toList());
        result.setBalanceList(voList);
        return result;
    }

    public GainBalanceResult gainBalance(GainBalanceRequest request) {
        //suppose there is no duplication or different transactions with same timestamp
        //just insert transactions
        Transaction transaction = new Transaction();
        transaction.setPayer(request.getPayer());
        transaction.setPoints(request.getPoint());
        transaction.setTimestamp(request.getTimestamp());
        transactionRepository.insert(transaction);
        return new GainBalanceResult();
    }

    public SpendBalanceResult spendBalance(SpendBalanceRequest request) {
        //build payer balance (we have to do this to record the available balance of each positive transaction)
        if (!payerBalanceRepository.hasAny()) {
            buildPayerBalance();
        }
        //while not satisfied, find the earliest and try to deduct it
        long deduct = request.getPoints();
        List<PayerBalance> modifiedList = new ArrayList<>();
        ArrayList<PayerBalanceVO> balanceVOList = new ArrayList<>();
        while (deduct > 0) {
            PayerBalance balance = payerBalanceRepository.getEarliestPositive();
            //can't be any negative result, so must find enough balance
            if (balance == null) {
                throw new RuntimeException();
            }
            Long points = balance.getPoints();
            balance.setPoints(Math.max(0, points - deduct));
            modifiedList.add(balance);
            PayerBalanceVO balanceVO = new PayerBalanceVO();
            balanceVO.setPayer(balance.getPayer());
            balanceVO.setPoints(deduct > points ? -points : -deduct);
            balanceVOList.add(balanceVO);
            deduct -= points;
        }
        //save all
        payerBalanceRepository.updateAll(modifiedList);
        SpendBalanceResult result = new SpendBalanceResult();
        result.setBalanceModification(balanceVOList);
        return result;
    }

    private void buildPayerBalance() {
        List<PayerBalance> balanceList = new ArrayList<>();
        List<Transaction> transactionList = transactionRepository.getAll().stream().sorted(Comparator.comparing(Transaction::getTimestamp)).collect(Collectors.toList());
        for (Transaction transaction : transactionList) {
            if (transaction.getPoints() > 0) {
                PayerBalance payerBalance = new PayerBalance();
                payerBalance.setPayer(transaction.getPayer());
                payerBalance.setTimestamp(transaction.getTimestamp());
                payerBalance.setPoints(transaction.getPoints());
                balanceList.add(payerBalance);
            } else {
                List<PayerBalance> previousBalance = balanceList.stream().filter(payerBalance -> payerBalance.getPayer().equals(transaction.getPayer())).collect(Collectors.toList());
                long deduct = -transaction.getPoints();
                //do until we have deducted enough points
                for (PayerBalance balance : previousBalance) {
                    Long points = balance.getPoints();
                    balance.setPoints(Math.max(0, points - deduct));
                    deduct -= points;
                    if (deduct <= 0) {
                        break;
                    }
                }
                //we must have points left
                if (deduct > 0) {
                    throw new RuntimeException();
                }
            }
        }
        payerBalanceRepository.insertAll(balanceList);
    }
}
