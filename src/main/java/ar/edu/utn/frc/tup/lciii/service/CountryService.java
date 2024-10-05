package ar.edu.utn.frc.tup.lciii.service;

import ar.edu.utn.frc.tup.lciii.Entities.CountryEntity;
import ar.edu.utn.frc.tup.lciii.clients.CountryClient;
import ar.edu.utn.frc.tup.lciii.dtos.common.CountryDTO;
import ar.edu.utn.frc.tup.lciii.model.Country;
import ar.edu.utn.frc.tup.lciii.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CountryService {

        @Autowired
        private final CountryRepository countryRepository;

        private final RestTemplate restTemplate;

        @Autowired
        ModelMapper modelMapper;
        public List<CountryDTO> getAllCountries(String nombre, String codigo) {
                String url = "https://restcountries.com/v3.1/all";

                List<Map<String, Object>> response = restTemplate.getForObject(url, List.class);

                if (response == null) {
                        // Manejar el caso donde la respuesta es nula
                        throw new RuntimeException("No se pudo obtener la lista de países."); // o retornar Collections.emptyList();
                }
                List<CountryDTO> listCountryDto = response.stream()
                        .map(this::mapToCountry)
                        .map(country -> {

                                CountryEntity countryEntity = modelMapper.map(country, CountryEntity.class);

                                countryRepository.save(countryEntity);
                                return mapToDTO(country);
                        })
                        .collect(Collectors.toList());

                if (nombre == null && codigo == null) {
                        return listCountryDto;
                } else if (nombre != null) {
                        return listCountryDto.stream()
                                .filter(countryDTO -> countryDTO.getName().equals(nombre))
                                .collect(Collectors.toList());
                } else if (codigo != null) {
                        return listCountryDto.stream()
                                .filter(countryDTO -> countryDTO.getCode().equals(codigo))
                                .collect(Collectors.toList());
                }


                return listCountryDto.stream()
                        .limit(10)
                        .collect(Collectors.toList());
        }


        private Country mapToCountry(Map<String, Object> countryData) {
                Map<String, Object> nameData = (Map<String, Object>) countryData.get("name");

                // Obtener los idiomas, asegurándote de que no sea null
                Map<String, String> languages = (Map<String, String>) countryData.get("languages");

                if (languages == null) {
                        languages = new HashMap<>();
                }

                return Country.builder()
                        .name((String) nameData.get("common"))
                        .population(((Number) countryData.get("population")).longValue())
                        .area(((Number) countryData.get("area")).doubleValue())
                        .region((String) countryData.get("region"))
                        .borders((List<String>) countryData.getOrDefault("borders", new ArrayList<>()))
                        .languages(languages) //
                        .code((String) countryData.get("cca3"))
                        .build();
        }




        private CountryDTO mapToDTO(Country country) {
                return new CountryDTO(country.getCode(), country.getName());
        }


        public List<CountryDTO> getCountriesByContinen(String region){

          List<CountryEntity>listCountry= countryRepository.findAll();

          List<CountryEntity>listCountriesByRegion= listCountry.stream().filter(countryEntity -> countryEntity.getRegion().equals(region)).collect(Collectors.toList());

          List<CountryDTO> countryDTOList= listCountriesByRegion.stream().map(countryEntity -> modelMapper.map(countryEntity,CountryDTO.class)).collect(Collectors.toList());

          return countryDTOList;
        }

        public List<CountryEntity> test(){
                return countryRepository.findAll();
        }


        public List<CountryDTO> getCountriesByIdioma(String idioma) {
                List<CountryEntity> listCountry = countryRepository.findAll();

                List<CountryEntity> listCountriesByIdioma = listCountry.stream()
                        .filter(countryEntity -> countryEntity.getLanguages().containsValue(idioma))
                        .collect(Collectors.toList());

                List<CountryDTO> countryDTOList = listCountriesByIdioma.stream()
                        .map(countryEntity -> modelMapper.map(countryEntity, CountryDTO.class))
                        .collect(Collectors.toList());

                return countryDTOList;
        }



        public CountryDTO getCountryMostborders(){

                List<CountryEntity>listCountry= countryRepository.findAll();


                List<Country>listCountries=  listCountry.stream().map(countryEntity -> modelMapper.map(countryEntity,Country.class)).collect(Collectors.toList());

                Optional<Country> countryMostBorders = listCountries.stream()
                        .max(Comparator.comparingInt(country -> country.getBorders().size()));

                return modelMapper.map(countryMostBorders,CountryDTO.class);

        }

}