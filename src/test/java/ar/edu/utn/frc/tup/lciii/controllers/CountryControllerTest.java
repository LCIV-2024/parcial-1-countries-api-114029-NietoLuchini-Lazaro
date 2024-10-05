package ar.edu.utn.frc.tup.lciii.controllers;

import ar.edu.utn.frc.tup.lciii.dtos.common.CountryDTO;
import ar.edu.utn.frc.tup.lciii.service.CountryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(CountryController.class)
class CountryControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    CountryService countryService;

    List<CountryDTO> list= new ArrayList<>(Arrays.asList(new CountryDTO("123","Argentina")));

    @Test
    void getCountries() throws Exception  {
        when(countryService.getAllCountries(null,null)).thenReturn(list);

        mockMvc.perform(get(""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].code").value("123"))
                .andExpect(jsonPath("$[0].nombre").value("Argentina"));
    }

    @Test
    void getCountriesByRegion()throws Exception  {
        when(countryService.getCountriesByContinen("America")).thenReturn(list);

        mockMvc.perform(get(""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].code").value("123"))
                .andExpect(jsonPath("$[0].nombre").value("Argentina"));
    }

    @Test
    void test1() {
    }

    @Test
    void getCountryMostBorders() {
    }

    @Test
    void getCountriesByIdioma() {
    }
}