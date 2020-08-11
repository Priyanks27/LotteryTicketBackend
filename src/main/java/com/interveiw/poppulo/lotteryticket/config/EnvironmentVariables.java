package com.interveiw.poppulo.lotteryticket.config;

import lombok.Data;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Data
@Getter
@Component
public class EnvironmentVariables {

    @Value("${app.Number_of_lines_on_ticket}")
    private Integer NUMBER_OF_LINES_ON_TICKET;

    @Value("${app.digits_in_a_line}")
    private String DIGITS_IN_A_LINE;

    @Value("${app.Values}")
    private String values;

    @Value("#{${app.rule.map}}")
    Map<String, Integer> ruleMapByPriority;
}
