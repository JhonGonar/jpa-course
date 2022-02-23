package com.example.demo;

import com.example.demo.model.*;
import com.example.demo.repository.StudentIdCardRepository;
import com.example.demo.repository.StudentRepository;
import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository){


        return args -> {
            var faker = new Faker();
            var firstName = faker.name().firstName();
            var lastName = faker.name().lastName();
            var email = String.format("%s.%s@fakermail.co", firstName, lastName);
            Student student = new Student(firstName,
                    lastName,
                    email,
                    faker.number().numberBetween(17, 55)
            );
            student.addBook(
                    new Book("Clean Code", LocalDateTime.now().minusDays(4))
            );
            student.addBook(
                    new Book("Think and Grow Rich", LocalDateTime.now())
            );
            student.addBook(
                    new Book("Spring Data JPA", LocalDateTime.now().minusYears(1))
            );

            StudentIdCard studentIdCard =
                    new StudentIdCard("123456789",
                            student);

            student.setStudentIdCard(studentIdCard);

//            student.enrolToCourse(
//                    new Course("Computer Science", "IT")
//            );
//            student.enrolToCourse(new Course("Spring Data JPA", "IT"));

            student.addEnrolment(
                    new Enrolment(
                    new EnrolmentId(1L, 1L),
                    student,
                    new Course("Computer Science", "IT"),
                    LocalDateTime.now())
            );
            student.addEnrolment(
                    new Enrolment(
                    new EnrolmentId(1L, 2L),
                    student,
                    new Course("Spring Data JPA", "IT"),
                    LocalDateTime.now().minusDays(18))
            );

            studentRepository.save(student);

//            studentIdCardRepository.save(studentIdCard);
            studentRepository.findById(1L).ifPresent(s ->{
                System.out.println("fetch book lazy...");
                List<Book> books = student.getBooks();
                books.forEach(
                        book -> System.out.println(s.getFirstName() +
                                " borrowed " + book.getBookName())
                );
            });
//            studentRepository.findById(1L).ifPresent(System.out::println);//query join with Student Id Card but not printed on to toString
//            studentIdCardRepository.findById(1L).ifPresent(System.out::println);
//            studentRepository.deleteById(1L);
        };
        /* previously inside return
            ///////
                        GenerateRadmonStudent(studentRepository);

            PageRequest pageRequest = PageRequest.of(
                    0,
                    5,
                    Sort.by("firstName").ascending()
            );
            Page<Student> page = studentRepository.findAll(pageRequest);
            System.out.println(page);
        ///////
            var maria = new Student("María", "Rios", "mr@mail.com", 23);
            var maria2 = new Student("María", "Calle", "mc@mail.com", 18);
            var maria3 = new Student("María", "Mendez", "mm@mail.com", 23);
            var maria4 = new Student("María", "Desamparada", "md@mail.com", 22);

            var ahmed = new Student("Ahmed", "Ali", "aa@mail.com", 22);
            System.out.println("<---- Adding Maria andl Ahmed --->");
            studentRepository.saveAll(List.of(maria, ahmed, maria2, maria3, maria4));
            System.out.println("<---- Number of students --->");
            System.out.println(studentRepository.count());
            studentRepository.findById(2L).ifPresentOrElse(
                    System.out::println,
                    () ->
                            System.out.println("Student id 2 doesn't exist")
            );
            studentRepository.findById(2L).ifPresentOrElse(
                    System.out::println,
                    () ->
                            System.out.println("Student id 3 doesn't exist")
            );
            System.out.println("<--- Select all students --->");
            var students = studentRepository.findAll();
            students.forEach(System.out::println);

            System.out.println("<--- Deleting maria --->");
            studentRepository.deleteById(1L);
            System.out.println("<--- Final number of students --->");
            System.out.println(studentRepository.count());

            studentRepository.findStudentsByEmail("aa@mail.com")
                    .ifPresentOrElse(
                            System.out::println,
                            ()-> System.out.println("studente with email aa@mail.com not found")
                    );

            studentRepository.findStudentsByFirstNameEqualsAndAgeEquals("María", 23)
                    .forEach(System.out::println);

            studentRepository.selectStudentesWithFirstNameAndOlderThanNative("María", 20)
                    .forEach(System.out::println);

            System.out.println("<--- Deleting maria id 5 ---->");
            System.out.println(studentRepository.DeleteStudentById(5L));
*/
    }

    private void sorting(StudentRepository studentRepository) {
        var sort = Sort.by(/*Sort.Direction.ASC, */"firstName")
                .ascending()
                .and(Sort.by("age").descending());
        studentRepository.findAll(sort)
                .forEach(student -> System.out.println(
                        student.getFirstName() + " " + student.getAge()));
    }

    private void GenerateRadmonStudent(StudentRepository studentRepository) {
        var faker = new Faker();
        for (int i = 0; i <= 20; i++) {
            var firstName = faker.name().firstName();
            var lastName = faker.name().lastName();
            var email = String.format("%s.%s@fakermail.co", firstName, lastName);
            Student student = new Student(firstName,
                    lastName,
                    email,
                    faker.number().numberBetween(17, 55)
            );
            studentRepository.save(student);
        }
    }

}
