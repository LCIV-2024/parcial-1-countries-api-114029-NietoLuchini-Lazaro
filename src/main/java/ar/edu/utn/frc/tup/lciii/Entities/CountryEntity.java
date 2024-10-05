package ar.edu.utn.frc.tup.lciii.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountryEntity {
    @Id
    private String name;
    private long population;
    private double area;
    private String code;
    private String region;
    private List<String> borders;
    @ElementCollection
    @MapKeyColumn(name = "language_code")
    @Column(name = "language_name")
    private Map<String, String> languages;



    //CAMBIAR NO ES ESTO LO Q HAY Q GUARDAR EN LA BD
}
