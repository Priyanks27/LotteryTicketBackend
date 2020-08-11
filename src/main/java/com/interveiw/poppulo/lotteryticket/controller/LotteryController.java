package com.interveiw.poppulo.lotteryticket.controller;

import com.interveiw.poppulo.lotteryticket.Exceptions.CustomExceptions.*;
import com.interveiw.poppulo.lotteryticket.Validations.ValidationUtilities;
import com.interveiw.poppulo.lotteryticket.data.dto.ResponseDTO;
import com.interveiw.poppulo.lotteryticket.data.input.LotteryTicketInput;
import com.interveiw.poppulo.lotteryticket.data.model.LotteryTicket;
import com.interveiw.poppulo.lotteryticket.services.LotteryService;
import lombok.NonNull;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

@Log
@RestController
@RequestMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
public class LotteryController {

    @Autowired
    LotteryService lotteryService;

    @Autowired
    ValidationUtilities validationUtilities;

    @PostMapping("/ticket")
    @ResponseStatus(HttpStatus.CREATED)
    public LotteryTicket createTicket(@Valid @RequestBody @NonNull LotteryTicketInput lotteryTicketInput)
            throws ErrorReadingEnvironmentVariable, TicketInputMalformedException, ErrorGeneratingLineNumbers,
            ConstantLineException {
        log.log(Level.INFO, "Create Ticket ");
        return lotteryService.createTicket(lotteryTicketInput);
    }

    @GetMapping(value = "/ticket")
    public List<LotteryTicket> getAllTickets()
    {
        log.log(Level.INFO, "Get All tickets");
        return  lotteryService.getAllTickets();
    }

    @GetMapping("/ticket/{id}")
    public LotteryTicket getTicketById(@PathVariable @NonNull UUID id)
            throws LotteryTicketDoesNotExistException, TicketIdNullException {
        log.log(Level.INFO, "Getting ticket By ID : " + id);
        return lotteryService.getTicketById(id);
    }

    @PutMapping("/ticket/{id}")
    public LotteryTicket updateTicketById(@PathVariable @NonNull UUID id, @Valid @RequestBody LotteryTicket lotteryTicket)
              throws LotteryTicketDoesNotExistException, StatusAlreadyExistsException,
                    TicketIdNullException, LotteryTicketNullException, TicketInputMalformedException {
        log.log(Level.INFO, "Update tickets By ID : " + id);
        validationUtilities.validateLotteryTicketToSpecification(id, lotteryTicket);
        return lotteryService.updateTicketById(id, lotteryTicket);
    }

    @PutMapping("/status/{id}")
    public LotteryTicket getTicketStatusById(@PathVariable UUID id)
            throws LotteryTicketDoesNotExistException, TicketIdNullException, StatusAlreadyExistsException {
        log.log(Level.INFO, "check and update status of ticket by id : " + id);
        return  lotteryService.getTicketStatusById(id);
    }
}
