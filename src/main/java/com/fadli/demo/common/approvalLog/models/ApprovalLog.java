package com.fadli.demo.common.approvalLog.models;

import com.fadli.demo.base.parentClasses.BaseEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "approval_log", uniqueConstraints = @UniqueConstraint(columnNames = {"transaction_code", "transaction_id",
        "transaction_number", "transaction_year_month"}))
public class ApprovalLog extends BaseEntity {

    @Column(name = "transaction_code")
    private String transactionCode;

    @Column(name = "transaction_id")
    private String transactionId;

    @Column(name = "transaction_number")
    private String transactionNumber;

    @Column(name = "transaction_year_month")
    private String transactionYearMonth;

    @Column(name = "confirmation_status")
    private String confirmationStatus;

    @Column(name = "confirmation_date")
    private Date confirmationDate;

    public String getTransactionCode() {
        return transactionCode;
    }

    public void setTransactionCode(String transactionCode) {
        this.transactionCode = transactionCode;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
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

    public String getConfirmationStatus() {
        return confirmationStatus;
    }

    public void setConfirmationStatus(String confirmationStatus) {
        this.confirmationStatus = confirmationStatus;
    }

    public Date getConfirmationDate() {
        return confirmationDate;
    }

    public void setConfirmationDate(Date confirmationDate) {
        this.confirmationDate = confirmationDate;
    }
}
