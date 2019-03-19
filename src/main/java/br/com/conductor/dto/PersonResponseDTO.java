package br.com.conductor.dto;

import br.com.conductor.model.Person;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonResponseDTO implements Serializable {

    private String id;
    private String name;
    private String personId;
    private String cpf;
    private List<String> photos;

    public static PersonResponseDTO buildPersonResponse(Person p) {
        PersonResponseDTO personResponseDTO = new PersonResponseDTO();
        personResponseDTO.setId(p.getId().toHexString());
        personResponseDTO.setCpf(p.getCpf());
        personResponseDTO.setName(p.getName());
        personResponseDTO.setPersonId(p.getPersonId());
        personResponseDTO.setPhotos(p.getPhotos());
        return personResponseDTO;
    }
}
