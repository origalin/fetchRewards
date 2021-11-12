package com.excercise.fetchrewards.web.controller;

import com.excercise.fetchrewards.common.web.*;
import com.excercise.fetchrewards.core.service.BalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/balance")
public class BalanceController {

    @Autowired
    private BalanceService balanceService;

    /**
     * show the points that left from their payers
     *
     * @return
     */
    @RequestMapping("show")
    public BaseResponse<ShowBalanceResult> showBalance(){
        ShowBalanceRequest request = new ShowBalanceRequest();
        ShowBalanceResult result = balanceService.showBalance(request);
        return BaseResponse.success(result);
    }

    /**
     * receives transactions to form data
     *
     * @param request
     * @return none
     */
    @RequestMapping("gain")
    public BaseResponse<GainBalanceResult> gainBalance(@RequestBody GainBalanceRequest request){
        GainBalanceResult result = balanceService.gainBalance(request);
        return BaseResponse.success(result);
    }

    /**
     * spend point
     *
     * @param request
     * @return all balances from payers affected (payer, points) after spending
     */
    @RequestMapping("spend")
    public BaseResponse<SpendBalanceResult> spendBalance(@RequestBody SpendBalanceRequest request){
        SpendBalanceResult result = balanceService.spendBalance(request);
        return BaseResponse.success(result);
    }
}
