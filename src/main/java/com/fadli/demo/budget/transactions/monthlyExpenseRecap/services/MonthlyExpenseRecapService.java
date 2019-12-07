package com.fadli.demo.budget.transactions.monthlyExpenseRecap.services;

import com.fadli.demo.base.constants.TransactionTypeConstants;
import com.fadli.demo.base.parentClasses.BaseService;
import com.fadli.demo.base.utils.DateUtil;
import com.fadli.demo.budget.masters.salary.services.SalaryService;
import com.fadli.demo.budget.transactions.monthlyExpenseRecap.models.MonthlyExpenseRecap;
import com.fadli.demo.budget.transactions.monthlyExpenseRecap.repositories.MonthlyExpenseRecapRepository;
import com.fadli.demo.common.transactionType.services.TransactionTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MonthlyExpenseRecapService extends BaseService<MonthlyExpenseRecap> {

    @Autowired private MonthlyExpenseRecapRepository monthlyExpenseRecapRepository;
    @Autowired private SalaryService salaryService;
    @Autowired private TransactionTypeService transactionTypeService;

    @Override
    protected void initRepository() {
        repository = monthlyExpenseRecapRepository;
    }

    public List<MonthlyExpenseRecap> getList(Map<String, String> parameter) {
        return monthlyExpenseRecapRepository.getList(parameter);
    }

    public MonthlyExpenseRecap findByBk(String salaryId, String transactionType, String transactionNumber, String transactionYearMonth) {
        return monthlyExpenseRecapRepository.findByBk(salaryId, transactionType, transactionNumber, transactionYearMonth);
    }

    @Override
    protected void valMustExists(MonthlyExpenseRecap entity) {
        MonthlyExpenseRecap entityFromDb = findById(entity.getId());

        if (entityFromDb == null) {
            batchError("monthlyExpenseRecapService.monthlyExpenseRecap.not.found", entity.getTransactionNumber(), entity.getTransactionYearMonth());
        }
    }

    @Override
    protected void valMustNotExists(MonthlyExpenseRecap entity) {
        MonthlyExpenseRecap entityFromDb = findByBk(entity.getSalary().getId(),
                                                    entity.getTransactionType().getId(),
                                                    entity.getTransactionNumber(),
                                                    entity.getTransactionYearMonth());

        if (entityFromDb != null) {
            batchError("monthlyExpenseRecapService.monthlyExpenseRecap.already.exists", entity.getTransactionNumber(), entity.getTransactionYearMonth());
        }
    }

    @Override
    protected void persistReferenceEntity(MonthlyExpenseRecap entity) {
        if (entity.getSalary() != null) entity.setSalary(salaryService.findById(entity.getSalary().getId()));
    }

    @Override
    protected void defineValueOnAdd(MonthlyExpenseRecap entity) {
        entity.setTransactionType(transactionTypeService.findByBk(TransactionTypeConstants.MONTHLY_EXPENSE_RECAP));
        entity.setTransactionDate(DateUtil.getSystemDate());
    }

    @Override
    protected void setEditedValues(MonthlyExpenseRecap entity, MonthlyExpenseRecap entityFromDb) {
        entityFromDb.setTotalExpenseValue(entity.getTotalExpenseValue());
        entityFromDb.setOutstandingValue(entity.getOutstandingValue());
    }
}
