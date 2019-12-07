package com.fadli.demo.budget.masters.salary.services;

import com.fadli.demo.base.parentClasses.BaseService;
import com.fadli.demo.base.utils.Calculation;
import com.fadli.demo.budget.masters.salary.models.Salary;
import com.fadli.demo.budget.masters.salary.repositories.SalaryRepository;
import com.fadli.demo.common.user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SalaryService extends BaseService<Salary> {

    @Autowired private SalaryRepository salaryRepository;
    @Autowired private UserService userService;

    public List<Salary> getList(Map<String, String> parameter) {
        return salaryRepository.getList(parameter);
    }

    public Salary findByBk(String userId) {
        return salaryRepository.findByBk(userId);
    }

    @Override
    protected void initRepository() {
        repository = salaryRepository;
    }

    @Override
    protected void valMustExists(Salary entity) {
        Salary entityFromDb = findById(entity.getId());
        if (entityFromDb == null) {
            batchError("salaryService.salary.not.found");
        }
    }

    @Override
    protected void valMustNotExists(Salary entity) {
        Salary entityFromDb = findByBk(entity.getUser().getId());
        if (entityFromDb != null) {
            batchError("salaryService.user.already.exists", entity.getUser().getUserCode());
        }
    }

    @Override
    protected void persistReferenceEntity(Salary entity) {
        if (entity.getUser() != null) entity.setUser(userService.findById(entity.getUser().getId()));
    }

    @Override
    protected void defineValueOnAdd(Salary entity) {
        entity.setInsuranceValue(0D);
        entity.setTaxValue(0D);
    }

    @Override
    protected void processValuesBeforeSave(Salary entity) {
        setInsuranceValue(entity);
        setTaxValue(entity);
        setNettoValue(entity);
    }

    private void setInsuranceValue(Salary entity) {

        if (entity.getInsurancePct() == null) {
            entity.setInsurancePct(0D);
        }
        Double insurancePctDecimal = new Calculation(entity.getInsurancePct()).divide(100D);
        Double insuranceValue = new Calculation(insurancePctDecimal).multiple(entity.getSalaryGrossValue());

        entity.setInsuranceValue(insuranceValue);
    }

    private void setTaxValue(Salary entity) {

        if (entity.getTaxPct() == null) {
            entity.setTaxPct(0D);
        }
        Double taxPctDecimal = new Calculation(entity.getTaxPct()).divide(100D);
        Double taxValue = new Calculation(taxPctDecimal).multiple(entity.getSalaryGrossValue());

        entity.setTaxValue(taxValue);
    }

    private void setNettoValue(Salary entity) {

        Double totalCutValue = new Calculation(entity.getInsuranceValue()).add(entity.getTaxValue());
        Double nettoValue = new Calculation(entity.getSalaryGrossValue()).substract(totalCutValue);

        entity.setSalaryNettoValue(nettoValue);
    }

    @Override
    protected void setEditedValues(Salary entity, Salary entityFromDb) {
        entityFromDb.setSalaryNettoValue(entity.getSalaryNettoValue());
        entityFromDb.setSalaryGrossValue(entity.getSalaryGrossValue());
        entityFromDb.setInsurancePct(entity.getInsurancePct());
        entityFromDb.setTaxPct(entity.getTaxPct());
        entityFromDb.setInsuranceValue(entity.getInsuranceValue());
        entityFromDb.setTaxValue(entity.getTaxValue());
    }
}
