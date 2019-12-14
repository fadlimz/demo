package com.fadli.demo.budget.transactions.monthlyExpenseRecap.services;

import com.fadli.demo.base.parentClasses.TransactionDetailService;
import com.fadli.demo.budget.transactions.monthlyExpenseRecap.models.MonthlyExpenseRecap;
import com.fadli.demo.budget.transactions.monthlyExpenseRecap.models.MonthlyExpenseRecapDetail;
import com.fadli.demo.budget.transactions.monthlyExpenseRecap.repositories.MonthlyExpenseRecapDetailRepository;
import com.fadli.demo.budget.transactions.monthlyExpenseRecap.repositories.MonthlyExpenseRecapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
public class MonthlyExpenseRecapDetailService extends TransactionDetailService<MonthlyExpenseRecapDetail, MonthlyExpenseRecap> {

    @Autowired private MonthlyExpenseRecapDetailRepository monthlyExpenseRecapDetailRepository;
    @Autowired private MonthlyExpenseRecapRepository monthlyExpenseRecapRepository;
    @Autowired private MonthlyExpenseRecapService monthlyExpenseRecapService;

    public MonthlyExpenseRecapDetail findByBk(String monthlyExpenseRecapId, Date dateInput, String description) {
        return monthlyExpenseRecapDetailRepository.findByBk(monthlyExpenseRecapId, dateInput, description);
    }

    public List<MonthlyExpenseRecapDetail> getList(String monthlyExpenseRecapId) {
        return monthlyExpenseRecapDetailRepository.getList(monthlyExpenseRecapId);
    }

    public Double getTotalExpense(String monthlyExpenseRecapId) {
        return monthlyExpenseRecapDetailRepository.getTotalExpense(monthlyExpenseRecapId);
    }

    @Override
    @Transactional
    public MonthlyExpenseRecapDetail add(MonthlyExpenseRecapDetail entity) {
        initRootEntity(monthlyExpenseRecapService.findById(entity.getMonthlyExpenseRecap().getId()));

        return super.add(entity);
    }

    @Override
    protected void initRepository() {
        repository = monthlyExpenseRecapDetailRepository;
    }

    @Override
    protected void initRootRepository() {
        rootRepository = monthlyExpenseRecapRepository;
    }

    @Override
    public void initRootEntity(MonthlyExpenseRecap rootEntity) {
        this.rootEntity.set(rootEntity);
    }

    @Override
    protected void valMustNotExists(MonthlyExpenseRecapDetail entity) {
        MonthlyExpenseRecapDetail entityFromDb = findByBk(entity.getMonthlyExpenseRecap().getId(), entity.getDateInput(), entity.getDescription());

        if (entityFromDb != null) {
            batchError("monthlyExpenseRecapDetailService.monthlyExpenseRecapDetail.already.exists", entity.getDescription());
        }
    }

    @Override
    protected void valMustExists(MonthlyExpenseRecapDetail entity) {
        MonthlyExpenseRecapDetail entityFromDb = findById(entity.getId());

        if (entityFromDb == null) {
            batchError("monthlyExpenseRecapDetailService.monthlyExpenseRecapDetail.not.found", entity.getDescription());
        }
    }

    @Override
    protected void setEditedValues(MonthlyExpenseRecapDetail entity, MonthlyExpenseRecapDetail entityFromDb) {
        entityFromDb.setMonthlyExpenseRecap(monthlyExpenseRecapService.findById(entity.getMonthlyExpenseRecap().getId()));
        entityFromDb.setDateInput(entity.getDateInput());
        entityFromDb.setDescription(entity.getDescription());
    }

    @Override
    protected void persistReferenceEntity(MonthlyExpenseRecapDetail entity) {
        if (entity.getMonthlyExpenseRecap() != null) entity.setMonthlyExpenseRecap(monthlyExpenseRecapService.findById(entity.getMonthlyExpenseRecap().getId()));
    }
}
