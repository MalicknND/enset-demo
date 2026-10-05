package net.ndiaye.enset_demo;

import net.ndiaye.enset_demo.entities.Payment;
import net.ndiaye.enset_demo.entities.PaymentStatus;
import net.ndiaye.enset_demo.entities.PaymentType;
import net.ndiaye.enset_demo.entities.Student;
import net.ndiaye.enset_demo.repository.PaymentRepository;
import net.ndiaye.enset_demo.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Random;
import java.util.UUID;

@SpringBootApplication
public class EnsetDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(EnsetDemoApplication.class, args);
	}


	@Bean
	CommandLineRunner commandLineRunner(StudentRepository studentRepository, PaymentRepository paymentRepository){
		return args -> {
			studentRepository.save(Student.builder()
					.id(UUID.randomUUID().toString())
					.firstName("John")
					.lastName("Doe")
					.code("S001")
					.programId("P001")
					.photo("john_doe.jpg")
					.build());

			studentRepository.save(Student.builder()
					.id(UUID.randomUUID().toString())
					.firstName("Jane")
					.lastName("Smith")
					.code("S002")
					.programId("P001")
					.photo("jane_smith.jpg")
					.build());

			studentRepository.save(Student.builder()
					.id(UUID.randomUUID().toString())
					.firstName("Alice")
					.lastName("Johnson")
					.code("S003")
					.programId("P002")
					.photo("alice_johnson.jpg")
					.build());

			studentRepository.save(Student.builder()
					.id(UUID.randomUUID().toString())
					.firstName("Malick")
					.lastName("Ndiaye")
					.code("S004")
					.programId("P001")
					.photo("malick_ndiaye.jpg")
					.build());

			PaymentType[] paymentTypes = PaymentType.values();
			Random random = new Random();
			studentRepository.findAll().forEach(st -> {
				for (int i = 0; i < 10; i++) {
					int index = random.nextInt(paymentTypes.length);
					Payment payment = Payment.builder()
							.amount(1000 + (int)(Math.random() * 20000))
							.type(paymentTypes[index])
							.status(PaymentStatus.CREATED)
							.student(st)
							.build();

					paymentRepository.save(payment);
				}
			});
		};
	}

}
