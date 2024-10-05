package ar.edu.utn.frc.tup.lciii.service;

import ar.edu.utn.frc.tup.lciii.Entities.CountryEntity;
import ar.edu.utn.frc.tup.lciii.dtos.common.CountryDTO;
import ar.edu.utn.frc.tup.lciii.repository.CountryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CountryServiceTest {



    @Mock
    CountryRepository countryRepository;
    @Mock
    RestTemplate restTemplate;

    @InjectMocks
    CountryService countryService;
    @Mock
    ModelMapper modelMapper;

    private List<CountryEntity> countryEntityList = new ArrayList<>(Arrays.asList());
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);


        Map<String, String> languages1 = new HashMap<>();
        languages1.put("en", "English");
        languages1.put("fr", "French");

        CountryEntity country1 = new CountryEntity();
        country1.setName("Canada");
        country1.setPopulation(38005238);
        country1.setArea(9984670);
        country1.setCode("CAN");
        country1.setRegion("America");
        country1.setBorders(Arrays.asList("USA"));
        country1.setLanguages(languages1);

        Map<String, String> languages2 = new HashMap<>();
        languages1.put("en", "English");
        languages1.put("fr", "French");

        CountryEntity country2 = new CountryEntity();
        country1.setName("USA");
        country1.setPopulation(38005238);
        country1.setArea(9984670);
        country1.setCode("CAN");
        country1.setRegion("America");
        country1.setBorders(Arrays.asList("USA"));
        country1.setLanguages(languages1);
    }

    @Test
    public void testGetAllCountries() {
        // Datos de prueba
        List<Map<String, Object>> mockResponse = new ArrayList<>();

        Map<String, Object> country1 = new HashMap<>();
        country1.put("name", Collections.singletonMap("common", "Country1"));
        country1.put("cca3", "C1");
        country1.put("population", 1000000);
        country1.put("area", 10000);
        country1.put("region", "Region1");
        country1.put("languages", Collections.singletonMap("eng", "English"));

        Map<String, Object> country2 = new HashMap<>();
        country2.put("name", Collections.singletonMap("common", "Country2"));
        country2.put("cca3", "C2");
        country2.put("population", 2000000);
        country2.put("area", 20000);
        country2.put("region", "Region2");
        country2.put("languages", Collections.singletonMap("spa", "Spanish"));

        mockResponse.add(country1);
        mockResponse.add(country2);


        when(restTemplate.getForObject(anyString(), eq(List.class))).thenReturn(mockResponse);


        List<CountryDTO> result = countryService.getAllCountries(null, null);

        assertNotNull(result);
    }


    @Test
    public void testGetCountriesByContinen() {

        when(countryRepository.findAll()).thenReturn(countryEntityList);


        List<CountryDTO> expectedCountryDTOList = new ArrayList<>(Arrays.asList());
        CountryDTO countryDTO1 = new CountryDTO();
        expectedCountryDTOList.add(countryDTO1);

        when(modelMapper.map(any(CountryEntity.class), eq(CountryDTO.class))).thenReturn(countryDTO1);


        List<CountryDTO> result = countryService.getCountriesByContinen("America");


        assertEquals(0, result.size());
        assertFalse(result.contains(countryDTO1));

    }

    @Test
    void test1() {
    }

    @Test
    void getCountriesByIdioma() {

        when(countryRepository.findAll()).thenReturn(countryEntityList);
        String idioma = "English";

        List<CountryDTO> result = countryService.getCountriesByIdioma(idioma);



    }

    @Test
    void getCountryMostborders() {
    }
}