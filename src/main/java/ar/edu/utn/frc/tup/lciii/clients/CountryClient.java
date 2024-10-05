package ar.edu.utn.frc.tup.lciii.clients;

import ar.edu.utn.frc.tup.lciii.model.Country;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class CountryClient {
    RestTemplate restTemplate= new RestTemplate();

    public List<Country> getCountrys(){

        ResponseEntity<List<Country>> responseEntity=restTemplate.exchange("https://restcountries.com/v3.1/all",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Country>>() {
                });
        List<Country> listCountry=responseEntity.getBody();

        return listCountry;
    }
}
