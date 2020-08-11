package com.interveiw.poppulo.lotteryticket.helper;

import com.interveiw.poppulo.lotteryticket.data.model.Line;
import com.interveiw.poppulo.lotteryticket.helper.RuleEngineHelper.RuleEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
public class GenerateStatus {

    @Autowired
    RuleEngine ruleEngine;

    public List<Line> calculateResult(List<Line> lines)
    {
        List<Line> updatedLines = new ArrayList<>();
        for(Line line : lines)
        {
            int result = ruleEngine.getResultByLine(line.getNumbers());
            line.setResult(result);
            updatedLines.add(line);
        }
        updatedLines.sort(Comparator.comparing(Line::getResult));
        return updatedLines;
    }
}
