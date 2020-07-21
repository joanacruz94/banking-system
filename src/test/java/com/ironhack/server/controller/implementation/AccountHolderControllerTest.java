package com.ironhack.server.controller.implementation;

import com.ironhack.server.dto.AccountHolderDTO;
import com.ironhack.server.model.AccountHolder;
import com.ironhack.server.model.Address;
import com.ironhack.server.service.AccountHolderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class AccountHolderControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    AccountHolderService accountHolderService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        LocalDate dateOfBirth = LocalDate.of(1994, Month.AUGUST, 24);
        Address address = new Address("Braga", "Portugal", "Praceta João Beltrão", 12,  "4715-292");
        AccountHolder user = new AccountHolder("Joana Marta da Cruz", "joanamartadacruz@gmail.com", "joanacruz", dateOfBirth, address);
        user.setId(new Long(1));

        List<AccountHolder> users = new ArrayList<>();
        users.add(user);

        AccountHolderDTO accountHolderDTO = new AccountHolderDTO(user.getId(), user.getName(), user.getEmail(),
                user.getDateOfBirth().getDayOfMonth() + " " + user.getDateOfBirth().getMonth() + " " + user.getDateOfBirth().getYear(),
                address, new ArrayList<>());

        List<AccountHolderDTO> accountHolderList = users.stream().map((u) ->
                new AccountHolderDTO(u.getId(), u.getName(), u.getEmail(),
                        u.getDateOfBirth().getDayOfMonth() + " " + u.getDateOfBirth().getMonth() + " " + u.getDateOfBirth().getYear(),
                        address, new ArrayList<>())).collect(Collectors.toList());

        when(accountHolderService.findAccountHolderById(new Long(1))).thenReturn(accountHolderDTO);
        when(accountHolderService.findAllAccountHolders()).thenReturn(accountHolderList);
    }

    @Test
    void getAccountHolder() throws Exception {
        mockMvc.perform(get("/api/accountHolder/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("Joana Cruz"))
                .andExpect(jsonPath("$.email").value("joanamartadacruz@gmail.com"))
                .andExpect(jsonPath("$.dateOfBirth").value("24 AUGUST 1994"))
                .andExpect(jsonPath("$.accountsID", hasSize(0)))
        ;
    }

    @Test
    void getAllAccountHolders() throws Exception {
        mockMvc.perform(get("/api/accountHolder/accountHolders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].name").value("Joana Cruz"))
                .andExpect(jsonPath("$[0].email").value("joanamartadacruz@gmail.com"))
                .andExpect(jsonPath("$[0].dateOfBirth").value("24 AUGUST 1994"))
        ;
    }

}
