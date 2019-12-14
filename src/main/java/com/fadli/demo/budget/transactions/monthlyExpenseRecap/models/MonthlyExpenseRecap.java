package com.fadli.demo.budget.transactions.monthlyExpenseRecap.models;

import com.fadli.demo.base.parentClasses.BaseEntity;
import com.fadli.demo.base.parentClasses.TransactionEntity;
import com.fadli.demo.budget.masters.salary.models.Salary;
import com.fadli.demo.common.transactionType.models.TransactionType;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "monthly_expense_recap", uniqueConstraints = @UniqueConstraint(columnNames = {"salary_id", "transaction_number", "transaction_type", "transaction_year_month"}))
public class MonthlyExpenseRecap extends TransactionEntity {

    @OneToOne
    @JoinColumn(name = "salary_id")
    private Salary salary;

    @Column(name = "transaction_date")
    private Date transactionDate;

    @Column(name = "total_expense_value")
    private Double totalExpenseValue;

    @Column(name = "outstanding_value")
    private Double outstandingValue;

    @OneToMany(mappedBy = "monthlyExpenseRecap", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<MonthlyExpenseRecapDetail> monthlyExpenseRecapDetails;

    public Salary getSalary() {
        return salary;
    }

    public void setSalary(Salary salary) {
        this.salary = salary;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Double getTotalExpenseValue() {
        return totalExpenseValue;
    }

    public void setTotalExpenseValue(Double totalExpenseValue) {
        this.totalExpenseValue = totalExpenseValue;
    }

    public Double getOutstandingValue() {
        return outstandingValue;
    }

    public void setOutstandingValue(Double outstandingValue) {
        this.outstandingValue = outstandingValue;
    }

    public List<MonthlyExpenseRecapDetail> getMonthlyExpenseRecapDetails() {
        return monthlyExpenseRecapDetails;
    }

    public void setMonthlyExpenseRecapDetails(List<MonthlyExpenseRecapDetail> monthlyExpenseRecapDetails) {
        this.monthlyExpenseRecapDetails = monthlyExpenseRecapDetails;
    }
}
