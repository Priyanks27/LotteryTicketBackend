package com.interveiw.poppulo.lotteryticket.services.impl;

import com.interveiw.poppulo.lotteryticket.Exceptions.CustomExceptions.*;
import com.interveiw.poppulo.lotteryticket.data.input.LotteryTicketInput;
import com.interveiw.poppulo.lotteryticket.data.model.Line;
import com.interveiw.poppulo.lotteryticket.data.model.LotteryTicket;
import com.interveiw.poppulo.lotteryticket.services.LotteryService;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

// Integration test suite
@SpringBootTest
class LotteryServiceImplTest {

    @Autowired
    LotteryService lotteryService;

    private LotteryTicketInput lotteryTicketInput;
    @BeforeEach
    public void setup()
    {
        lotteryTicketInput = new LotteryTicketInput(3L);
    }

    @Test
    void createTicketTest() throws ErrorReadingEnvironmentVariable, TicketInputMalformedException,
            ErrorGeneratingLineNumbers, ConstantLineException {
        LotteryTicket lotteryTicket = lotteryService.createTicket(lotteryTicketInput);
        assertEquals(Boolean.TRUE, lotteryTicket.getLines().size() > 0);
        lotteryService.deleteTicketById(lotteryTicket.getId());
    }

    @Test
    void getAllTicketsTest() throws ErrorReadingEnvironmentVariable, TicketInputMalformedException,
            ErrorGeneratingLineNumbers, ConstantLineException {
        LotteryTicket lotteryTicket = lotteryService.createTicket(lotteryTicketInput);
        assertEquals(Boolean.TRUE, lotteryTicket.getLines().size() > 0);
        assertEquals(Boolean.TRUE, lotteryService.getAllTickets().size() > 0);
        lotteryService.deleteTicketById(lotteryTicket.getId());
    }

    @Test
    void getTicketByIdTest() throws LotteryTicketDoesNotExistException, TicketIdNullException,
            ErrorReadingEnvironmentVariable, TicketInputMalformedException, ErrorGeneratingLineNumbers,
            ConstantLineException {
        LotteryTicket lotteryTicket = lotteryService.createTicket(lotteryTicketInput);
        UUID id = lotteryTicket.getId();
        assertEquals(id, lotteryService.getTicketById(id).getId());
        lotteryService.deleteTicketById(lotteryTicket.getId());
    }

    @Test
    void updateTicketByIdTest()
            throws LotteryTicketDoesNotExistException, StatusAlreadyExistsException,
            TicketIdNullException, LotteryTicketNullException, TicketInputMalformedException,
            ErrorReadingEnvironmentVariable, ErrorGeneratingLineNumbers, ConstantLineException {

        LotteryTicket lotteryTicket = lotteryService.createTicket(lotteryTicketInput);
        List<Line> lines = lotteryTicket.getLines();

        Line expectedLine = lotteryTicket.getLines().get(0);
        expectedLine.setNumbers("000");

        lines.set(0, expectedLine);


        LotteryTicket lotteryTicketUpdated = lotteryService.updateTicketById(lotteryTicket.getId(), lotteryTicket);
        Line generatedLine = lotteryService.getTicketById(lotteryTicketUpdated.getId()).getLines().get(0);

        assertEquals("000", generatedLine.getNumbers());
        lotteryService.deleteTicketById(lotteryTicket.getId());
    }

    @Test
    void getTicketStatusByIdTest() throws LotteryTicketDoesNotExistException,
            TicketIdNullException, StatusAlreadyExistsException, ErrorReadingEnvironmentVariable,
            TicketInputMalformedException, ErrorGeneratingLineNumbers, ConstantLineException {

        LotteryTicket lotteryTicket = lotteryService.createTicket(lotteryTicketInput);
        assertEquals(Boolean.TRUE, lotteryTicket.getLines().size() > 0);
        assertEquals(Boolean.FALSE, lotteryTicket.getIsStatusChecked());
        LotteryTicket lotteryTicketStatusChecked = lotteryService.getTicketStatusById(lotteryTicket.getId());

        assertEquals(Boolean.TRUE, lotteryTicketStatusChecked.getIsStatusChecked());
        assertEquals(Boolean.FALSE, lotteryTicketStatusChecked.getLines().stream().filter(p -> p.getResult() == -1)
                .collect(Collectors.toList()).size() > 0);
        lotteryService.deleteTicketById(lotteryTicket.getId());
    }

    //Integration test
    @Test
    void updateTicketAfterCheckStatus() throws ErrorReadingEnvironmentVariable,
            TicketInputMalformedException, ErrorGeneratingLineNumbers, LotteryTicketDoesNotExistException,
            TicketIdNullException, StatusAlreadyExistsException, LotteryTicketNullException, ConstantLineException {

        LotteryTicket lotteryTicket = lotteryService.createTicket(lotteryTicketInput);
        assertEquals(Boolean.TRUE, lotteryTicket.getLines().size() > 0);
        assertEquals(Boolean.FALSE, lotteryTicket.getIsStatusChecked());

        LotteryTicket lotteryTicketStatusChecked = lotteryService.getTicketStatusById(lotteryTicket.getId());
        assertEquals(Boolean.TRUE, lotteryTicketStatusChecked.getIsStatusChecked());

        List<Line> lines = lotteryTicketStatusChecked.getLines();
        Line expectedLine = lotteryTicket.getLines().get(0);
        expectedLine.setNumbers("000");
        lines.set(0, expectedLine);

        try {
            lotteryService.updateTicketById(lotteryTicket.getId(), lotteryTicket);
            fail("Should have thrown an exception");
        }catch (Exception e) {
            assertTrue(true);
        }
        lotteryService.deleteTicketById(lotteryTicket.getId());
    }
}