package com.interveiw.poppulo.lotteryticket.helper;

import com.interveiw.poppulo.lotteryticket.Exceptions.CustomExceptions.ErrorReadingEnvironmentVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Random;

@Component
public class RandomNumberGenerator {

    @Autowired
    ValuesCreator valuesCreator;

    public String getRandomElement(List<String> list)
    {
        Random random = new Random();
        return list.get(random.nextInt(list.size()));
    }

    public String getRandomNumber() throws ErrorReadingEnvironmentVariable {
        return getRandomElement(valuesCreator.getValuesStrList());
    }
}
