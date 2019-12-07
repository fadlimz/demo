package com.fadli.demo.budget.transactions.monthlyExpenseRecap.controllers;

import com.fadli.demo.base.constants.UriConstants;
import com.fadli.demo.base.responses.ApiResponse;
import com.fadli.demo.budget.transactions.monthlyExpenseRecap.models.MonthlyExpenseRecapDetail;
import com.fadli.demo.budget.transactions.monthlyExpenseRecap.services.MonthlyExpenseRecapDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = UriConstants.MONTHLY_EXPENSE_RECAP_DETAIL)
public class MonthlyExpenseRecapDetailController {

    @Autowired private MonthlyExpenseRecapDetailService monthlyExpenseRecapDetailService;

    @RequestMapping(method = RequestMethod.GET)
    public ApiResponse getList(@RequestParam String headerId) {
        List<MonthlyExpenseRecapDetail> result = monthlyExpenseRecapDetailService.getList(headerId);

        return ApiResponse.data("monthlyExpenseRecapDetail", result);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ApiResponse add(@RequestBody MonthlyExpenseRecapDetail entity) {
        MonthlyExpenseRecapDetail result = monthlyExpenseRecapDetailService.add(entity);

        return ApiResponse.data("monthlyExpenseRecapDetail", result);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ApiResponse edit(@RequestBody MonthlyExpenseRecapDetail entity) {
        MonthlyExpenseRecapDetail result = monthlyExpenseRecapDetailService.edit(entity);

        return ApiResponse.data("monthlyExpenseRecapDetail", result);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ApiResponse delete(@RequestBody MonthlyExpenseRecapDetail entity) {
        monthlyExpenseRecapDetailService.delete(entity);

        return ApiResponse.ok();
    }
}
