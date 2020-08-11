package com.interveiw.poppulo.lotteryticket.helper.RuleEngineHelper;

import org.springframework.stereotype.Component;

@Component
public class RuleA {

    public Boolean isRuleAValid(String number)
    {
        int result = 0;
        for(int i = 0; i < number.length(); i++)
        {
            int num = Integer.parseInt(String.valueOf(number.charAt(i)));
            result = result + num;
        }
        if(result == 2) return true;
        return false;
    }
}
