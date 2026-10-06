package net.ndiaye.enset_demo.web;

import net.ndiaye.enset_demo.entities.Payment;
import net.ndiaye.enset_demo.entities.PaymentStatus;
import net.ndiaye.enset_demo.entities.PaymentType;
import net.ndiaye.enset_demo.entities.Student;
import net.ndiaye.enset_demo.repository.PaymentRepository;
import net.ndiaye.enset_demo.repository.StudentRepository;
import net.ndiaye.enset_demo.services.PaymentService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
public class PaymentRestController {
    private final PaymentRepository paymentRepository;
    private final StudentRepository studentRepository;
    private final PaymentService paymentService;

    // on utilise ici
    public PaymentRestController(StudentRepository studentRepository, PaymentRepository paymentRepository, PaymentService paymentService) {
        this.studentRepository = studentRepository;
        this.paymentRepository = paymentRepository;
        this.paymentService = paymentService;
    }

    // Methode qui permet de consulter tous les paiements
    @GetMapping("/payments")
    public List<Payment> allPayments() {
        return paymentRepository.findAll();
    }

    // Methode qui permet de consulter tous les paiements d'un etudiant
    @GetMapping("/students/{code}/payments")
    public List<Payment> paymentsByStudent(@PathVariable String code){
        return paymentRepository.findByStudentCode(code);
    }

    // Methode qui permet de consulter tous les paiements d'un etudiant par status
    @GetMapping("/payments/byStatus")
    public List<Payment> paymentsByStatus(@RequestParam PaymentStatus status){
        return paymentRepository.findByStatus(status);
    }

    // Methode qui permet de consulter tous les paiements d'un etudiant par type
    @GetMapping("/payments/byType")
    public List<Payment> paymentsType(@RequestParam PaymentType type){
        return paymentRepository.findByType(type);
    }

    // Methode qui permet de consulter un paiement par son id
    @GetMapping("/payments/{id}")
    public Payment getPaymentById(@PathVariable Long id) {
        return paymentRepository.findById(id).get();
    }

    // Methode qui permet de consulter tous les etudiants
    @GetMapping("/students")
    public List<Student> allStudents() {
        return studentRepository.findAll();
    }

    // Methode qui permet de consulter un etudiant par son code
    @GetMapping("/students/{code}")
    public Student getStudentByCode(@PathVariable String code) {
        return studentRepository.findByCode(code);
    }

    // Methode qui permet de consulter tous les etudiants d'un programme
    @GetMapping("/studentsByProgram")
    public List<Student> getStudentsByProgramId(@RequestParam String programId) {
        return studentRepository.findByProgramId(programId);
    }


    // Methode qui permet de mettre à jour le status d'un paiement
    @PutMapping("/payments/{id}")
    public Payment updatePaymentStatus(@PathVariable Long id, @RequestParam PaymentStatus status) {
        return paymentService.updatePaymentStatus(id, status);
    }

    // Methode qui permet de sauvegarder un paiement avec un fichier PDF
    @PostMapping(path = "/payments", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Payment savePayment(@RequestParam MultipartFile file, LocalDate date, double amount, PaymentType type, String studentCode) throws IOException {
        return this.paymentService.savePayment(file, date, amount, type, studentCode);
    }

    // Methode qui permet de consulter le fichier d'un paiement
    @GetMapping(path = "/paymentFile/{paymentId}", produces = MediaType.APPLICATION_PDF_VALUE)
    public byte[] getPaymentFile(@PathVariable Long paymentId) throws IOException {
        return paymentService.getPaymentFile(paymentId);
    }

}
