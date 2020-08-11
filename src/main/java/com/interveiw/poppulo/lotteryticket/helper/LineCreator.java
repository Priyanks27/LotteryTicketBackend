package com.interveiw.poppulo.lotteryticket.helper;

import com.interveiw.poppulo.lotteryticket.Exceptions.CustomExceptions.ErrorGeneratingLineNumbers;
import com.interveiw.poppulo.lotteryticket.Exceptions.CustomExceptions.ErrorReadingEnvironmentVariable;
import com.interveiw.poppulo.lotteryticket.config.EnvironmentVariables;
import com.interveiw.poppulo.lotteryticket.data.model.Line;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.logging.Level;

@Component
@Log
public class LineCreator {

    @Autowired
    RandomNumberGenerator randomNumberGenerator;

    @Autowired
    EnvironmentVariables environmentVariables;

    public Line GetNumber() throws ErrorGeneratingLineNumbers, ErrorReadingEnvironmentVariable {
        Line line = new Line();
        String errorMessage = "Error generating line for a ticket ";
        try {
            Integer digitsInALine = Integer.valueOf(environmentVariables.getDIGITS_IN_A_LINE());
            String number = "";
            for (int i = 0; i < digitsInALine; i++) {
                String randomNumber = randomNumberGenerator.getRandomNumber();
                number = number + randomNumber;
            }
            line.setNumbers(number);
        }  catch (ErrorReadingEnvironmentVariable e) {
            log.log(Level.SEVERE, errorMessage + e.getStackTrace());
            throw new ErrorReadingEnvironmentVariable(errorMessage);
        } catch (Exception e) {
            log.log(Level.SEVERE, errorMessage + e.getStackTrace());
            throw new ErrorGeneratingLineNumbers(errorMessage);
        }
        return line;
    }
}
