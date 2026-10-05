package net.ndiaye.enset_demo.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@NoArgsConstructor @AllArgsConstructor @Getter @Setter @ToString @Builder
public class Payment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private LocalDate date;
    private double amount;
    private paymentType type;
    private PaymentStatus status;
    private String file;

    @ManyToOne
    private Student student;
}
