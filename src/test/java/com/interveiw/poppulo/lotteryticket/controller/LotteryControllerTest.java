package com.interveiw.poppulo.lotteryticket.controller;

import com.google.gson.Gson;
import com.interveiw.poppulo.lotteryticket.data.input.LotteryTicketInput;
import com.interveiw.poppulo.lotteryticket.data.model.Line;
import com.interveiw.poppulo.lotteryticket.data.model.LotteryTicket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
class LotteryControllerTest {

    MockMvc mockMvc;

    @Autowired
    WebApplicationContext context;

    private LotteryTicket lotteryTicket;

    private List<LotteryTicket> lotteryTicketList = new ArrayList<>();

    private List<Line> lines = new ArrayList<>();

    private LotteryTicketInput lotteryTicketInput;

    @BeforeEach
    public void setup()
    {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        for(int i = 0; i < 3; i++) {
            lotteryTicketList.add(getLotteryTicket());
        }
        lotteryTicketInput = new LotteryTicketInput(3L);
    }

    private LotteryTicket getLotteryTicket() {

        Line line1 = Line.builder()
                .numbers("111")
                .result(-1)
                .build();
        lines.add(line1);

        Line line2 = Line.builder()
                .numbers("110")
                .result(-1)
                .build();
        lines.add(line2);

        Line line3 = Line.builder()
                .numbers("012")
                .result(-1)
                .build();
        lines.add(line3);

        lotteryTicket = LotteryTicket.builder()
                .id(UUID.randomUUID())
                .lines(lines)
                .isStatusChecked(false)
                .build();
        return lotteryTicket;
    }

    @Test
    void createTicketTest() throws Exception {
        String content = new Gson().toJson(lotteryTicketInput);
        MvcResult response = mockMvc.perform(post("/ticket").content(content)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated())
                .andReturn();


        assertEquals(HttpStatus.CREATED.value(), response.getResponse().getStatus());
    }

    @Test
    void getAllTicketsTest() throws Exception {
        MvcResult result = mockMvc.perform(get("/ticket")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

    @Test
    void getTicketByIdTest() throws Exception {
        UUID id = UUID.randomUUID();
        MvcResult result = mockMvc.perform(get("/ticket/" + id)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

    @Test
    void updateTicketByIdTest() throws Exception {
        UUID id = UUID.randomUUID();
        String content = new Gson().toJson(lotteryTicket);
        MvcResult result = mockMvc.perform(put("/ticket/" + id).content(content)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

    @Test
    void getTicketStatusByIdTest() throws Exception {
        UUID id = UUID.randomUUID();
        MvcResult result = mockMvc.perform(put("/status/" + id)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }
}