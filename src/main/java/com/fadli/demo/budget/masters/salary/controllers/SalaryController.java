package com.fadli.demo.budget.masters.salary.controllers;

import com.fadli.demo.base.constants.UriConstants;
import com.fadli.demo.base.responses.ApiResponse;
import com.fadli.demo.budget.masters.salary.models.Salary;
import com.fadli.demo.budget.masters.salary.services.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = UriConstants.SALARY)
public class SalaryController {

    @Autowired private SalaryService salaryService;

    @RequestMapping(method = RequestMethod.GET)
    public ApiResponse getList(@RequestParam Map<String, String> parameter) {
        List<Salary> resultList = salaryService.getList(parameter);

        return ApiResponse.data("salaries", resultList);
    }

    @RequestMapping(method = RequestMethod.GET, value = UriConstants.ID)
    public ApiResponse getById(@PathVariable String id) {
        Salary result = salaryService.findById(id);

        return ApiResponse.data("salary", result);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/by-user")
    public ApiResponse getByCode(@RequestParam String salaryCode) {
        Salary result =  salaryService.findByBk(salaryCode);

        return ApiResponse.data("salary", result);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ApiResponse add(@RequestBody Salary salary) {
        Salary result = salaryService.add(salary);

        return ApiResponse.data("salary", result);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ApiResponse edit(@RequestBody Salary salary) {
        Salary result = salaryService.edit(salary);

        return ApiResponse.data("salary", result);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ApiResponse delete(@RequestBody Salary salary) {
        salaryService.delete(salary);

        return ApiResponse.ok();
    }
}
