package com.interveiw.poppulo.lotteryticket.Validations;

import com.interveiw.poppulo.lotteryticket.Exceptions.CustomExceptions.*;
import com.interveiw.poppulo.lotteryticket.config.EnvironmentVariables;
import com.interveiw.poppulo.lotteryticket.data.input.LotteryTicketInput;
import com.interveiw.poppulo.lotteryticket.data.model.Line;
import com.interveiw.poppulo.lotteryticket.data.model.LotteryTicket;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class ValidationUtilities {

    @Autowired
    EnvironmentVariables environmentVariables;

    public void checkLotteryTicketInput(LotteryTicketInput lotteryTicketInput) throws TicketInputMalformedException {
        if(lotteryTicketInput.getNumberOfLines() == null || lotteryTicketInput.getNumberOfLines() == null
        || lotteryTicketInput.getNumberOfLines() < 1)
            throw new TicketInputMalformedException("Please specify the number of lines in a ticket!");
    }

    public void ifLotteryTicketExists(Optional<LotteryTicket> optionalLotteryTicket, UUID id)
            throws LotteryTicketDoesNotExistException {
        if(!optionalLotteryTicket.isPresent())
            throw new LotteryTicketDoesNotExistException("Lottery ticket with id " + id + " does not exist");
    }

    public void checkIdIsNull(UUID id) throws TicketIdNullException {
        if(id == null)
            throw new TicketIdNullException("Lottery ticket id cannot be null!");
    }

    public void checkLotteryTicketIsNull(LotteryTicket lotteryTicket) throws LotteryTicketNullException {
        if(lotteryTicket == null)
            throw new LotteryTicketNullException("Lottery ticket id cannot be null!");
    }

    public void isLotteryTicketStatusChecked(LotteryTicket lotteryTicket) throws StatusAlreadyExistsException {
        if(lotteryTicket.getIsStatusChecked())
            throw new StatusAlreadyExistsException("Lottery ticket status already checked, ticket cannot be amended now.");
    }

    public void validateLotteryTicketToSpecification(UUID id, LotteryTicket lotteryTicket)
            throws TicketInputMalformedException {
        List<Line> lines = lotteryTicket.getLines();
        if(lines.size() != environmentVariables.getNUMBER_OF_LINES_ON_TICKET())
            throw new TicketInputMalformedException("Number of lines on a ticket are not matching specification!");

        if(!id.equals(lotteryTicket.getId()))
            throw new TicketInputMalformedException("Id in the request body and parameter does not match");

        if(lotteryTicket.getIsStatusChecked())
            throw new TicketInputMalformedException("Status cannot be changed manually");

        if(lines.stream().filter(p -> p.getResult() != -1).findFirst().isPresent())
            throw new TicketInputMalformedException("Result cannot be changed manually");

        int digitsInaLine = Integer.valueOf(environmentVariables.getDIGITS_IN_A_LINE());
        if(lines.stream().filter(p -> p.getNumbers().length() != digitsInaLine).findFirst().isPresent())
            throw new TicketInputMalformedException("Number of digits in a line should be : " + digitsInaLine);

        for(Line line : lines)
            if (!isNumberToSpecification(line.getNumbers()))
                throw new TicketInputMalformedException(line.getNumbers() +
                        " not allowed. Values can only be assigned from this list : " +
                        environmentVariables.getValues());
    }

    private Boolean isNumberToSpecification(String s) {
        String possibleValuesStr = environmentVariables.getValues();
        String[] stringArrayList = possibleValuesStr.split(",");
        List<String> arrayList = Arrays.asList(stringArrayList);
        Integer length = arrayList.size();
        String possibleValues = String.join("", arrayList);
        String regexTemplate = "[%VALUE%]";
        regexTemplate = StringUtils.repeat(regexTemplate, length);
        String regex = regexTemplate.replaceAll("%VALUE%", possibleValues);
        System.out.println("value : " + regex);
        return s != null && s.matches(regex);
    }
}
