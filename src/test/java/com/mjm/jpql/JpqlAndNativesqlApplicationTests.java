package com.mjm.jpql;

import com.mjm.jpql.student.entities.Student;
import com.mjm.jpql.student.repos.StudentRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JpqlAndNativesqlApplicationTests {

	@Autowired
	private StudentRepository repository;

	@Test
	public void contextLoads() {
	}

	@Test
	public void testStudentCreate() {

		Student student = new Student();
		student.setFirstName("Mark");
		student.setLastName(("Brown"));
		student.setScore(85);
		repository.save(student);

		Student  student2 = new Student();
		student2.setFirstName("Jim");
		student2.setLastName(("Jam"));
		student2.setScore(95);
		repository.save(student2);

		Student  student3 = new Student();
		student3.setFirstName("Marth");
		student3.setLastName(("Grime"));
		student3.setScore(88);
		repository.save(student3);

	}

	@Test
	public void testFindAllStudents(){
		Page<Student> s = repository.findAllStudents(new PageRequest(0, 2, Sort.Direction.ASC,"id"));
		System.out.println(s);
	}

	@Test
	public void testFindAllStudentsPartialData(){
		List<Object[]> l = repository.findAllStudentsPartialData();
		for (Object[] o : l) {
			System.out.println(o[0] + " " + o[1]);
		}
	}

	@Test
	public void testFindAllStudentsByFirstname(){
		List<Student> s = repository.findAllStudentsByFirstName("Mark");
		System.out.println(s);
	}

	@Test
	public void testFindAllStudentsByScore(){
		List<Student> s = repository.findAllStudentsByScore(80, 90);
		System.out.println(s);
	}

	@Test
	@Transactional  // needed for delete operation
	@Rollback(false)
	public void deleteStudentByFirstName(){
		repository.deleteStudentByFirstName("Mark");
		List<Student> s = repository.findAllStudentsByFirstName("Mark");
		System.out.println(s);
	}

	@Test
	@Transactional  // needed for DML operation
	@Rollback(false)
	public void test(){
		repository.updateStudentFirstNameById("Jimmy", 3);
		List<Student> s = repository.findAllStudentsByFirstName("Mark");
		System.out.println(s);
	}
}
