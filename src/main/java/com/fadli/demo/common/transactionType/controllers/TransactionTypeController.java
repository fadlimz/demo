package com.fadli.demo.common.transactionType.controllers;

import com.fadli.demo.base.constants.UriConstants;
import com.fadli.demo.base.responses.ApiResponse;
import com.fadli.demo.common.transactionType.models.TransactionType;
import com.fadli.demo.common.transactionType.services.TransactionTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = UriConstants.TRANSACTION_TYPE)
public class TransactionTypeController {

    @Autowired private TransactionTypeService transactionTypeService;

    @RequestMapping(method = RequestMethod.POST)
    public ApiResponse add(@RequestBody TransactionType transactionType) {
        TransactionType result = transactionTypeService.add(transactionType);

        return ApiResponse.data("transactionType", result);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ApiResponse edit(@RequestBody TransactionType transactionType) {
        TransactionType result = transactionTypeService.edit(transactionType);

        return ApiResponse.data("transactionType", result);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ApiResponse delete(@RequestBody TransactionType transactionType) {
        transactionTypeService.delete(transactionType);

        return ApiResponse.ok();
    }

}
