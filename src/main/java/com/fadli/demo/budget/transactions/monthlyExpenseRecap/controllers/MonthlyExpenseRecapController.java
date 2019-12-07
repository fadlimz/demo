package com.fadli.demo.budget.transactions.monthlyExpenseRecap.controllers;

import com.fadli.demo.base.constants.UriConstants;
import com.fadli.demo.base.responses.ApiResponse;
import com.fadli.demo.budget.transactions.monthlyExpenseRecap.models.MonthlyExpenseRecap;
import com.fadli.demo.budget.transactions.monthlyExpenseRecap.services.MonthlyExpenseRecapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = UriConstants.MONTHLY_EXPENSE_RECAP)
public class MonthlyExpenseRecapController {

    @Autowired private MonthlyExpenseRecapService monthlyExpenseRecapService;

    @RequestMapping(method = RequestMethod.GET, value = UriConstants.ID)
    public ApiResponse get(@PathVariable String id) {
        MonthlyExpenseRecap result = monthlyExpenseRecapService.findById(id);
        result.setMonthlyExpenseRecapDetails(null);

        return ApiResponse.data("monthlyExpenseRecap", result);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ApiResponse getList(Map<String, String> parameter) {
        List<MonthlyExpenseRecap> result = monthlyExpenseRecapService.getList(parameter);

        return ApiResponse.data("monthlyExpenseRecap", result);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ApiResponse add(@RequestBody MonthlyExpenseRecap entity) {
        MonthlyExpenseRecap result = monthlyExpenseRecapService.add(entity);

        return ApiResponse.data("monthlyExpenseRecap", result);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ApiResponse edit(@RequestBody MonthlyExpenseRecap entity) {
        MonthlyExpenseRecap result = monthlyExpenseRecapService.edit(entity);

        return ApiResponse.data("monthlyExpenseRecap", result);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ApiResponse delete(@RequestBody MonthlyExpenseRecap entity) {
        monthlyExpenseRecapService.delete(entity);

        return ApiResponse.ok();
    }
}
