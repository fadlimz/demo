package com.fadli.demo.budget.masters.salary.models;

import com.fadli.demo.base.parentClasses.BaseEntity;
import com.fadli.demo.common.user.models.User;

import javax.persistence.*;

@Entity
@Table(name = "salary", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id"}))
public class Salary extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "salary_gross_value")
    private Double salaryGrossValue;

    @Column(name = "insurance_pct")
    private Double insurancePct;

    @Column(name = "insurance_value")
    private Double insuranceValue;

    @Column(name = "tax_pct")
    private Double taxPct;

    @Column(name = "tax_value")
    private Double taxValue;

    @Column(name = "salary_netto_value")
    private Double salaryNettoValue;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Double getSalaryGrossValue() {
        return salaryGrossValue;
    }

    public void setSalaryGrossValue(Double salaryGrossValue) {
        this.salaryGrossValue = salaryGrossValue;
    }

    public Double getInsurancePct() {
        return insurancePct;
    }

    public void setInsurancePct(Double insurancePct) {
        this.insurancePct = insurancePct;
    }

    public Double getInsuranceValue() {
        return insuranceValue;
    }

    public void setInsuranceValue(Double insuranceValue) {
        this.insuranceValue = insuranceValue;
    }

    public Double getTaxPct() {
        return taxPct;
    }

    public void setTaxPct(Double taxPct) {
        this.taxPct = taxPct;
    }

    public Double getTaxValue() {
        return taxValue;
    }

    public void setTaxValue(Double taxValue) {
        this.taxValue = taxValue;
    }

    public Double getSalaryNettoValue() {
        return salaryNettoValue;
    }

    public void setSalaryNettoValue(Double salaryNettoValue) {
        this.salaryNettoValue = salaryNettoValue;
    }
}
