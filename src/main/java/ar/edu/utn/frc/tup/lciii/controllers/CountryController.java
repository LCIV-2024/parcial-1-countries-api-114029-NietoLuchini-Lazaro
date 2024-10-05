package ar.edu.utn.frc.tup.lciii.controllers;
import ar.edu.utn.frc.tup.lciii.Entities.CountryEntity;
import ar.edu.utn.frc.tup.lciii.dtos.common.CountryDTO;
import ar.edu.utn.frc.tup.lciii.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/countries")
public class CountryController {

    @Autowired
    private final CountryService countryService;


    @GetMapping()
    public List<CountryDTO> getCountries(@RequestParam(required = false) String nombre,@RequestParam (required = false) String codigo){

        return countryService.getAllCountries( nombre,codigo);

    }

    @GetMapping("/{region}")
    public List<CountryDTO> getCountriesByRegion(String region){
        return  countryService.getCountriesByContinen(region);
    }

    @GetMapping("/test")
    public List<CountryEntity> test(){
        return countryService.test();
    }

    @GetMapping("/most-borders")
    public CountryDTO getCountryMostBorders(){
        return countryService.getCountryMostborders();
    }

    @GetMapping("/language")
    public List<CountryDTO> getCountriesByIdioma(String idioma){
        return countryService.getCountriesByIdioma(idioma);
    }

}