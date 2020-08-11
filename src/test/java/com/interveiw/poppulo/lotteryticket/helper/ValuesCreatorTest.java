package com.interveiw.poppulo.lotteryticket.helper;

import com.interveiw.poppulo.lotteryticket.Exceptions.CustomExceptions.ErrorReadingEnvironmentVariable;
import com.interveiw.poppulo.lotteryticket.config.EnvironmentVariables;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ValuesCreatorTest {

    @Autowired
    ValuesCreator valuesCreator;

    @Test
    void getValuesStrListTest() throws ErrorReadingEnvironmentVariable {
        List<String> expectedValues = new ArrayList<String>() {{
            add("0");
            add("1");
            add("2");
        }};

        List<String> originalList = valuesCreator.getValuesStrList();
        Boolean isListEqual = expectedValues.equals(originalList);
        assertEquals(Boolean.TRUE, isListEqual);
    }
}