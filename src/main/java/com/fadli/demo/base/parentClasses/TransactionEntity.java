package com.fadli.demo.base.parentClasses;

import com.fadli.demo.common.transactionType.models.TransactionType;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import java.util.Date;

@MappedSuperclass
public class TransactionEntity extends BaseEntity {
    @OneToOne
    @JoinColumn(name = "transaction_type")
    private TransactionType transactionType;

    @Column(name = "transaction_number")
    private String transactionNumber;

    @Column(name = "transaction_year_month")
    private String transactionYearMonth;

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public String getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public String getTransactionYearMonth() {
        return transactionYearMonth;
    }

    public void setTransactionYearMonth(String transactionYearMonth) {
        this.transactionYearMonth = transactionYearMonth;
    }
}
