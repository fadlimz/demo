package com.fadli.demo.common.transactionType.services;

import com.fadli.demo.base.parentClasses.BaseService;
import com.fadli.demo.common.transactionType.models.TransactionType;
import com.fadli.demo.common.transactionType.repositories.TransactionTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionTypeService extends BaseService<TransactionType> {

    @Autowired private TransactionTypeRepository transactionTypeRepository;

    @Override
    protected void initRepository() {
        repository = transactionTypeRepository;
    }

    public TransactionType findByBk(String transactionCode) {
        return transactionTypeRepository.findByBk(transactionCode);
    }
}
