package com.mjm.jpql.student.repos;

import com.mjm.jpql.student.entities.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface StudentRepository extends CrudRepository<Student, Integer>{

    @Query("select s from Student s")
    Page<Student> findAllStudents(Pageable pageable);

    @Query("select s.firstName, s.lastName from Student s")
    List<Object[]> findAllStudentsPartialData();

    @Query("select s from Student s where s.firstName=:firstName")
    List<Student> findAllStudentsByFirstName(@Param("firstName") String firstName);

    @Query("select s from Student s where s.score between :lscore and :hscore")
    List<Student> findAllStudentsByScore(@Param("lscore") Integer lscore,
                                         @Param("hscore") Integer hscore);

    @Modifying
    @Query("delete from Student s where s.firstName like :firstName")
    void deleteStudentByFirstName(@Param("firstName") String firstName);


    @Modifying
    @Query("Update Student s set  s.firstName= :firstName where s.id= :id")
    void updateStudentFirstNameById(@Param("firstName") String firstName,
                                    @Param("id") Integer id);
}
