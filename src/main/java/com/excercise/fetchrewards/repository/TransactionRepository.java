package com.excercise.fetchrewards.repository;

import com.excercise.fetchrewards.common.dao.PayerBalance;
import com.excercise.fetchrewards.common.dao.Transaction;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Repository implementation based on memory storage, deepcopy needed for a safe code
 */
@Component
public class TransactionRepository {

    private List<Transaction> data = new ArrayList<>();

    public List<Transaction> getAll() {
        return data;
    }

    public void insert(Transaction transaction) {
        transaction.setId((long) data.size());
        data.add(transaction);
    }
}
