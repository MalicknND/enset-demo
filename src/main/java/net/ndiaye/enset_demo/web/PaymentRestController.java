package net.ndiaye.enset_demo.web;

import net.ndiaye.enset_demo.entities.Payment;
import net.ndiaye.enset_demo.entities.PaymentStatus;
import net.ndiaye.enset_demo.entities.PaymentType;
import net.ndiaye.enset_demo.entities.Student;
import net.ndiaye.enset_demo.repository.PaymentRepository;
import net.ndiaye.enset_demo.repository.StudentRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PaymentRestController {
    private PaymentRepository paymentRepository;
    private StudentRepository studentRepository;

    public PaymentRestController(StudentRepository studentRepository, PaymentRepository paymentRepository) {
        this.studentRepository = studentRepository;
        this.paymentRepository = paymentRepository;
    }

    @GetMapping("/payments")
    public List<Payment> allPayments() {
        return paymentRepository.findAll();
    }

    @GetMapping("/students/{code}/payments")
    public List<Payment> paymentsByStudent(@PathVariable String code){
        return paymentRepository.findByStudentCode(code);
    }

    @GetMapping("/payments/byStatus")
    public List<Payment> paymentsByStatus(@PathVariable PaymentStatus status){
        return paymentRepository.findByStatus(status);
    }

    @GetMapping("/payments/byType")
    public List<Payment> paymentsType(@PathVariable PaymentType type){
        return paymentRepository.findByType(type);
    }

    @GetMapping("/payments/{id}")
    public Payment getPaymentById(@PathVariable Long id) {
        return paymentRepository.findById(id).get();
    }

    @GetMapping("/students")
    public List<Student> allStudents() {
        return studentRepository.findAll();
    }

    @GetMapping("/students/{code}")
    public Student getStudentByCode(@PathVariable String code) {
        return studentRepository.findByCode(code);
    }

    @GetMapping("/studentsByProgram")
    public List<Student> getStudentsByProgramId(@RequestParam String programId) {
        return studentRepository.findByProgramId(programId);
    }
}
