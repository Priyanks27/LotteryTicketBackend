package com.interveiw.poppulo.lotteryticket.services.impl;

import com.interveiw.poppulo.lotteryticket.Exceptions.CustomExceptions.*;
import com.interveiw.poppulo.lotteryticket.Validations.ValidationUtilities;
import com.interveiw.poppulo.lotteryticket.config.EnvironmentVariables;
import com.interveiw.poppulo.lotteryticket.data.input.LotteryTicketInput;
import com.interveiw.poppulo.lotteryticket.data.model.Line;
import com.interveiw.poppulo.lotteryticket.data.model.LotteryTicket;
import com.interveiw.poppulo.lotteryticket.helper.LineCreator;
import com.interveiw.poppulo.lotteryticket.helper.GenerateStatus;
import com.interveiw.poppulo.lotteryticket.repository.LotteryRepository;
import com.interveiw.poppulo.lotteryticket.services.LotteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class LotteryServiceImpl implements LotteryService {

    @Autowired
    LotteryRepository lotteryRepository;

    @Autowired
    EnvironmentVariables environmentVariables;

    @Autowired
    LineCreator lineCreator;

    @Autowired
    GenerateStatus generateStatus;

    @Autowired
    ValidationUtilities validationUtilities;

    @Override
    public LotteryTicket createTicket(LotteryTicketInput lotteryTicketInput) throws TicketInputMalformedException,
            ErrorGeneratingLineNumbers, ErrorReadingEnvironmentVariable, ConstantLineException {
        validationUtilities.checkLotteryTicketInput(lotteryTicketInput);
        List<Line> lines = new ArrayList<>();
        if(lotteryTicketInput.getNumberOfLines().intValue() != environmentVariables.getNUMBER_OF_LINES_ON_TICKET())
            throw new ConstantLineException("Number of lines in ticket should be constant : " +
                    environmentVariables.getNUMBER_OF_LINES_ON_TICKET());

        for(int i=0; i < lotteryTicketInput.getNumberOfLines(); i++)
        {
            lines.add(lineCreator.GetNumber());
        }
        LotteryTicket lotteryTicket = new LotteryTicket();
        lotteryTicket.setLines(lines);
        return lotteryRepository.save(lotteryTicket);
    }

    // TODO : To be removed
    @Override
    public void deleteAllTickets() {
         lotteryRepository.deleteAll();
    }

    @Override
    public List<LotteryTicket> getAllTickets() {
        return lotteryRepository.findAll();
    }

    @Override
    public LotteryTicket getTicketById(UUID id) throws LotteryTicketDoesNotExistException, TicketIdNullException {
        validationUtilities.checkIdIsNull(id);
        Optional<LotteryTicket> optionalLotteryTicket = lotteryRepository.findById(id);
        validationUtilities.ifLotteryTicketExists(optionalLotteryTicket, id);
        return optionalLotteryTicket.get();
    }

    @Override
    public LotteryTicket updateTicketById(UUID id, LotteryTicket lotteryTicket)
            throws StatusAlreadyExistsException, LotteryTicketDoesNotExistException,
            TicketIdNullException, LotteryTicketNullException, TicketInputMalformedException {
        validationUtilities.checkIdIsNull(id);
        validationUtilities.checkLotteryTicketIsNull(lotteryTicket);
        Optional<LotteryTicket> optionalLotteryTicket = lotteryRepository.findById(id);
        validationUtilities.ifLotteryTicketExists(optionalLotteryTicket, id);
        validationUtilities.validateLotteryTicketToSpecification(id, lotteryTicket);
        LotteryTicket lotteryTicket1 = optionalLotteryTicket.get();
        validationUtilities.isLotteryTicketStatusChecked(lotteryTicket1);

        //Business Logic
        lotteryTicket1.setLines(lotteryTicket.getLines());

        return lotteryRepository.save(lotteryTicket1);
    }

    @Override
    public LotteryTicket getTicketStatusById(UUID id)
            throws StatusAlreadyExistsException, LotteryTicketDoesNotExistException, TicketIdNullException {

        validationUtilities.checkIdIsNull(id);
        Optional<LotteryTicket> optionalLotteryTicket = lotteryRepository.findById(id);
        validationUtilities.ifLotteryTicketExists(optionalLotteryTicket, id);
        LotteryTicket lotteryTicket = optionalLotteryTicket.get();
        if(lotteryTicket.getIsStatusChecked()) return lotteryTicket;

        //Business Logic
        lotteryTicket.setLines(generateStatus.calculateResult(lotteryTicket.getLines()));
        lotteryTicket.setIsStatusChecked(true);

        return lotteryRepository.save(lotteryTicket);
    }

    @Override
    public List<LotteryTicket> searchCheckedTickets() {
        Optional<List<LotteryTicket>> optionalLotteryTickets = lotteryRepository.findByIsStatusChecked(true);
        if(optionalLotteryTickets.isPresent()) return optionalLotteryTickets.get();
        else return new ArrayList<>();
    }

    @Override
    public void deleteTicketById(UUID id) {
        lotteryRepository.deleteById(id);
    }
}