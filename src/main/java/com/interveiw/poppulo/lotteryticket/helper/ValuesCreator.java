package com.interveiw.poppulo.lotteryticket.helper;

import com.interveiw.poppulo.lotteryticket.Exceptions.CustomExceptions.ErrorReadingEnvironmentVariable;
import com.interveiw.poppulo.lotteryticket.config.EnvironmentVariables;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

@Log
@Component
public class ValuesCreator {

    @Autowired
    EnvironmentVariables environmentVariables;

    public List<String> getValuesStrList() throws ErrorReadingEnvironmentVariable {
        List<String> valuesList = new ArrayList<>();
        try {
            for (String valueStr : environmentVariables.getValues().split(",")) {
                valuesList.add(valueStr);
            }
        }catch (Exception e) {
            log.log(Level.SEVERE, "Error occurred while reading values environment variable");
            throw new ErrorReadingEnvironmentVariable("Error occurred while reading values environment variable");
        }
        return valuesList;
    }
}
