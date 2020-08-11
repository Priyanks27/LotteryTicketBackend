package com.interveiw.poppulo.lotteryticket.helper.RuleEngineHelper;

import org.springframework.stereotype.Component;

@Component
public class RuleC {

    public Boolean isRuleCValid(String number)
    {
        if((number.charAt(0) != number.charAt(1)) && (number.charAt(0) != number.charAt(2)))
            return true;
        return false;
    }
}
