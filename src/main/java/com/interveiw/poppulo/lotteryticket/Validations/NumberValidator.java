package com.interveiw.poppulo.lotteryticket.Validations;

import com.interveiw.poppulo.lotteryticket.config.EnvironmentVariables;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class NumberValidator implements ConstraintValidator<Number, String> {

    @Autowired
    EnvironmentVariables environmentVariables;

    public boolean isNumberToSpecification(String s) {
        String possibleValuesStr = environmentVariables.getValues();
        String[] stringArrayList = possibleValuesStr.split(",");
        List<String> arrayList = Arrays.asList(stringArrayList);
        Integer length = arrayList.size();
        String possibleValues = String.join("", arrayList);
        String regexTemplate = "[%VALUE%]";
        regexTemplate = StringUtils.repeat(regexTemplate, length);
        String regex = regexTemplate.replaceAll("%VALUE%", possibleValues);
        System.out.println("value : " + regex);
        return s != null && s.matches(regex);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return isNumberToSpecification(s);
    }
}
