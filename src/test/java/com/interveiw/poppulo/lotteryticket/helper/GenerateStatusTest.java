package com.interveiw.poppulo.lotteryticket.helper;

import com.interveiw.poppulo.lotteryticket.data.model.Line;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GenerateStatusTest {

    @Autowired
    GenerateStatus generateStatus;

    private List<Line> lines = new ArrayList<>();

    @Test
    void calculateResultTest() {

        Line line1 = new Line("011", -1);
        lines.add(line1);
        Line line2 = new Line("222", -1);
        lines.add(line2);
        Line line3 = new Line("012", -1);
        lines.add(line3);
        Line line4 = new Line("202", -1);
        lines.add(line4);
        List<Line> updatedLines = generateStatus.calculateResult(lines);

        assertEquals(0, updatedLines.get(0).getResult());
        assertEquals(1, updatedLines.get(1).getResult());
        assertEquals(5, updatedLines.get(2).getResult());
        assertEquals(10, updatedLines.get(3).getResult());

    }
}