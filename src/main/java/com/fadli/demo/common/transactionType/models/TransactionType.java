package com.fadli.demo.common.transactionType.models;

import com.fadli.demo.base.parentClasses.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "transaction_type", uniqueConstraints = @UniqueConstraint(columnNames = {"transaction_code"}))
public class TransactionType extends BaseEntity {

    @Column(name = "transaction_code")
    private String transactionCode;

    @Column(name = "transaction_name")
    private String transactionName;

    public String getTransactionCode() {
        return transactionCode;
    }

    public void setTransactionCode(String transactionCode) {
        this.transactionCode = transactionCode;
    }

    public String getTransactionName() {
        return transactionName;
    }

    public void setTransactionName(String transactionName) {
        this.transactionName = transactionName;
    }
}
