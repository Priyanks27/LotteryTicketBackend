package com.interveiw.poppulo.lotteryticket.services;

import com.interveiw.poppulo.lotteryticket.Exceptions.CustomExceptions.*;
import com.interveiw.poppulo.lotteryticket.data.input.LotteryTicketInput;
import com.interveiw.poppulo.lotteryticket.data.model.LotteryTicket;

import java.util.List;
import java.util.UUID;

public interface LotteryService {

    LotteryTicket createTicket(LotteryTicketInput lotteryTicketInput) throws TicketInputMalformedException, ErrorGeneratingLineNumbers, ErrorReadingEnvironmentVariable, ConstantLineException;

    public void deleteAllTickets();

    List<LotteryTicket> getAllTickets();

    LotteryTicket getTicketById(UUID id) throws LotteryTicketDoesNotExistException, TicketIdNullException;

    LotteryTicket updateTicketById(UUID id, LotteryTicket lotteryTicket)
            throws StatusAlreadyExistsException, LotteryTicketDoesNotExistException, TicketIdNullException,
            LotteryTicketNullException, TicketInputMalformedException;

    LotteryTicket getTicketStatusById(UUID id)
            throws StatusAlreadyExistsException, LotteryTicketDoesNotExistException, TicketIdNullException;

    List<LotteryTicket> searchCheckedTickets();

    void deleteTicketById(UUID id);
}
