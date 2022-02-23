package com.example.demo.repository;

import com.example.demo.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    //JavaPersistentQueryLanguage JPQL see documentation
    @Query("SELECT s FROM StudentRef s WHERE s.email = ?1")
    Optional<Student>  findStudentsByEmail(String email);

    List<Student> findStudentsByFirstNameEqualsAndAgeEquals(String firstName, Integer Age);

    @Query("SELECT s FROM StudentRef s WHERE s.firstName = ?1 AND s.age > ?2")
    List<Student> selectStudentesWithFirstNameAndOlderThan(String firstName, Integer Age);

    @Query(value = "SELECT * FROM student s WHERE s.first_name = ?1 AND s.age > ?2", nativeQuery = true)
    List<Student> selectStudentesWithFirstNameAndOlderThanNative(String firstName, Integer Age);

    //with @params
//    @Query(value = "SELECT * FROM student s WHERE s.first_name = :firstName AND s.age > :age"
//            , nativeQuery = true)
//    List<Student> selectStudentesWithFirstNameAndOlderThanNative(
//            @Param("firstName") String firstName,
//            @Param("age") Integer Age);
    @Transactional
    @Modifying
    @Query("DELETE FROM StudentRef u WHERE u.Id = ?1")
    int DeleteStudentById(Long id); // int to get the number of row affected
}
