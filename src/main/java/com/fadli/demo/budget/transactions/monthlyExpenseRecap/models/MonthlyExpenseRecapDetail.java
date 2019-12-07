package com.fadli.demo.budget.transactions.monthlyExpenseRecap.models;

import com.fadli.demo.base.parentClasses.BaseEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "monthly_expense_recap_detail", uniqueConstraints = @UniqueConstraint(columnNames = {"monthly_expense_recap", "date_input", "description"}))
public class MonthlyExpenseRecapDetail extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "monthly_expense_recap")
    private MonthlyExpenseRecap monthlyExpenseRecap;

    @Column(name = "date_input")
    private Date dateInput;

    @Column(name = "expense_value")
    private Double expenseValue;

    @Column(name = "description")
    private String description;

    public MonthlyExpenseRecap getMonthlyExpenseRecap() {
        return monthlyExpenseRecap;
    }

    public void setMonthlyExpenseRecap(MonthlyExpenseRecap monthlyExpenseRecap) {
        this.monthlyExpenseRecap = monthlyExpenseRecap;
    }

    public Date getDateInput() {
        return dateInput;
    }

    public void setDateInput(Date dateInput) {
        this.dateInput = dateInput;
    }

    public Double getExpenseValue() {
        return expenseValue;
    }

    public void setExpenseValue(Double expenseValue) {
        this.expenseValue = expenseValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
