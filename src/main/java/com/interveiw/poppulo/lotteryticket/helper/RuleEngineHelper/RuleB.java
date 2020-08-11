package com.interveiw.poppulo.lotteryticket.helper.RuleEngineHelper;

import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class RuleB {

    public Boolean isRuleBValid(String number)
    {
        List<Integer> list = new ArrayList<>();
        char[] charArr = number.toCharArray();
        for(char ch : charArr)
            list.add(Integer.valueOf(ch));
        return list.stream().distinct().count() <= 1;
    }
}
