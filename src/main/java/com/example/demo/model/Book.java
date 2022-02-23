package com.example.demo.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "Book")
@Table(name = "book")
public class Book {
    @Id
    @SequenceGenerator(
            name = "book_sequence",
            sequenceName = "book_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "book_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;
    @Column(name = "book_name", nullable = false)
    private String bookName;
    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(
            name = "student_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "student_id_fk")
    )
    private Student student;

    public Book() {
    }

    public Book(String bookName, LocalDateTime createdAt) {
        this.bookName = bookName;
        this.createdAt = createdAt;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", bookName='" + bookName + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
