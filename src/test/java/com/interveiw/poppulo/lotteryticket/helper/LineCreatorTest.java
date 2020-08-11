package com.interveiw.poppulo.lotteryticket.helper;

import com.interveiw.poppulo.lotteryticket.Exceptions.CustomExceptions.ErrorGeneratingLineNumbers;
import com.interveiw.poppulo.lotteryticket.Exceptions.CustomExceptions.ErrorReadingEnvironmentVariable;
import com.interveiw.poppulo.lotteryticket.data.model.Line;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LineCreatorTest {

    @Autowired
    LineCreator lineCreator;

    @Test
    void getNumber() throws ErrorReadingEnvironmentVariable, ErrorGeneratingLineNumbers {
        Line line = lineCreator.GetNumber();
        assertEquals(Boolean.TRUE, line.getNumbers().length() > 0);
        assertEquals(Boolean.TRUE, line.getResult() == -1);
    }
}