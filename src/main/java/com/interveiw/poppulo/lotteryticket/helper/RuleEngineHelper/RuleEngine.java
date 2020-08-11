package com.interveiw.poppulo.lotteryticket.helper.RuleEngineHelper;

import com.interveiw.poppulo.lotteryticket.config.EnvironmentVariables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Component
public class RuleEngine {

    @Autowired
    RuleA ruleA;

    @Autowired
    RuleB ruleB;

    @Autowired
    RuleC ruleC;

   @Autowired
   EnvironmentVariables environmentVariables;

    public int getResultByLine(String numbers) {
        Map<String, Integer> ruleMapByPriority = environmentVariables.getRuleMapByPriority();
        Iterator<String> iterator = ruleMapByPriority.keySet().iterator();
        while(iterator.hasNext())
        {
            String key = (String) iterator.next();
            switch (key){

                case "A":
                    if(ruleA.isRuleAValid(numbers))
                        return ruleMapByPriority.get(key);
                    break;

                case "B":
                    if(ruleB.isRuleBValid(numbers))
                        return ruleMapByPriority.get(key);
                    break;

                case "C":
                    if(ruleC.isRuleCValid(numbers))
                        return ruleMapByPriority.get(key);
                    break;

                case "default":
                    return ruleMapByPriority.get(key);

                default: return 0;
            }
        }
        return 0;
    }
}
