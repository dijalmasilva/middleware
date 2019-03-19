package br.com.conductor.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(noClassnameStored = true)
public class Person implements Serializable {

    @Id
    private ObjectId id;

    private String name;

    private String personId;

    @Indexed(unique = true)
    private String cpf;

    private List<String> photos;

    public void addPhotos(List<String> photos) {
        this.photos.addAll(photos);
    }
}
