package net.ndiaye.enset_demo.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@NoArgsConstructor @AllArgsConstructor @Getter @Setter @Builder
public class Student {
    private String id;
    private String firstName;
    private String lastName;

    @Column(unique = true)
    private String code;
    private String programId;
    private String photo;
}
