package com.example.demo.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity(name = "StudentRef")//default is the class name
@Table(name = "student", uniqueConstraints = {
        @UniqueConstraint(
                name = "student_email_unique",
                columnNames = "email")//we should define uniquenes here if want to customize the constraint name
})
public class Student {
    @SequenceGenerator(
            name = "student_sequence",
            sequenceName = "student_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "student_sequence"
    )
    @javax.persistence.Id
    @Column(
            name = "id",
            updatable = false
    )
    private Long Id;
    @Column(
            name = "first_name",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String firstName;
    @Column(
            name = "last_name",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String lastName;
    @Column(
            name = "email",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String email;
    @Column(
            name = "age",
            nullable = false
    )
    private Integer age;

    @OneToOne(
            mappedBy = "student",//name of the field on the entity that holds the FK
            orphanRemoval = true,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE}//Persist so I can use student  instance when creating transient IdCard, even if I saved afterwards on CommandLineRunner
    )
    private StudentIdCard studentIdCard;

    @OneToMany(
            //fetch = FetchType.EAGER, generates outer left join with books, even if not returned; waste of processing time
            mappedBy = "student",
            orphanRemoval = true, // when Student deleted, so wil books with their FK
            cascade = {CascadeType.PERSIST, //if book don't exist will be persisted
                    CascadeType.REMOVE}// delete all children  = books dependents
    )
    private List<Book> books = new ArrayList<>();

    /*
    @ManyToMany(
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE}
    )
    @JoinTable( // No needed since we created our own joinedTable with composed key
            name = "enrolment",
            joinColumns = @JoinColumn(
                    name = "student_id",
                    foreignKey = @ForeignKey(name = "enrolment_student_id_fk")
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "course_id",
                    foreignKey = @ForeignKey(name = "enrolment_course_id_fk")
            )
    )*/
    @OneToMany(
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            mappedBy = "student"
    )
    private List<Enrolment> enrolments = new ArrayList<>();
    //private Set<Course> courses = new HashSet<>(); better approach for ManyToMany

    public Student(String firstName, String lastName, String email, Integer age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.age = age;
    }

    public Student() {
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public List<Book> getBooks() {
        return books;
    }

    public List<Enrolment> getEnrolments() {
        return enrolments;
    }

    public void setStudentIdCard(StudentIdCard studentIdCard) {
        this.studentIdCard = studentIdCard;
    }

    public void addBook(Book book){
        if(!this.books.contains(book)){
            this.books.add(book);
            book.setStudent(this);
        }
    }

    public void removeBook(Book book){
        if(this.books.contains(book)){
            this.books.remove(book);
            book.setStudent(null);
        }
    }

    public void addEnrolment(Enrolment enrolment){
        if(!enrolments.contains(enrolment)){
            enrolments.add(enrolment);
        }
    }

    public void removeEnrolment( Enrolment enrolment){
        enrolments.remove(enrolment);
    }


    @Override
    public String toString() {
        return "Student{" +
                "Id=" + Id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                '}';
    }
}
