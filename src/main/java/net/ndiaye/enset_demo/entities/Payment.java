package net.ndiaye.enset_demo.entities;

import java.time.LocalDate;

public class Payment {

    private String id;
    private LocalDate date;
    private double amount;
    private paymentType type;
    private PaymentStatus status;
}
