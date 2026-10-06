package net.ndiaye.enset_demo.services;

import jakarta.transaction.Transactional;
import net.ndiaye.enset_demo.entities.Payment;
import net.ndiaye.enset_demo.entities.PaymentStatus;
import net.ndiaye.enset_demo.entities.PaymentType;
import net.ndiaye.enset_demo.repository.PaymentRepository;
import net.ndiaye.enset_demo.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.UUID;

@Service
@Transactional
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final StudentRepository studentRepository;

    public PaymentService(PaymentRepository paymentRepository, StudentRepository studentRepository) {
        this.paymentRepository = paymentRepository;
        this.studentRepository = studentRepository;
    }



    public Payment savePayment( MultipartFile file, LocalDate date, double amount, PaymentType type, String studentCode) throws IOException {
        Path folderPath = Paths.get(System.getProperty("user.home"), "enset-data", "payments");
        if (!Files.exists(folderPath)) {
            Files.createDirectories(folderPath);
        }
        String fileName = UUID.randomUUID().toString();
        Path filePath = Paths.get(System.getProperty("user.home"), "enset-data", "payments", fileName + ".pdf");
        Files.copy(file.getInputStream(), filePath);
        Payment payment = Payment.builder()
                .date(date)
                .type(type)
                .student(studentRepository.findByCode(studentCode))
                .amount(amount)
                .file(filePath.toUri().toString())
                .status(PaymentStatus.CREATED)
                .build();
        return paymentRepository.save(payment);
    }



    public Payment updatePaymentStatus( Long id, PaymentStatus status) {
        Payment payment = paymentRepository.findById(id).get();
        payment.setStatus(status);
        return paymentRepository.save(payment);
    }


    public byte[] getPaymentFile( Long paymentId) throws IOException {
        Payment payment = paymentRepository.findById(paymentId).get();
        return Files.readAllBytes(Path.of(URI.create(payment.getFile())));
    }
}
