package com.studentapp.junit.studentsinfo;

import static org.hamcrest.Matchers.hasValue;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import com.studentapp.cucumber.serenity.StudentSerenitySteps;
import com.studentapp.model.StudentClass;
import com.studentapp.testbase.TestBase;
import com.studentapp.utils.ReusableSpecifications;
import com.studentapp.utils.TestUtils;

import io.restassured.http.ContentType;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;


@RunWith(SerenityRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class StudentsCRUDTest extends TestBase {

	static String firstName = "SMOKEUSER" + TestUtils.getRandomValue();
	static String lastName = "SMOKEUSER" + TestUtils.getRandomValue();
	static String programme = "ComputerScience";
	static String email = TestUtils.getRandomValue() + "xyz@gmail.com";
	static int studentId;

	@Steps
	StudentSerenitySteps steps;

	@Title("Este Test Deberia Crear un Nuevo Estudiante")
	@Test
	public void test001() {

		ArrayList<String> courses = new ArrayList<String>();
		courses.add("JAVA");
		courses.add("C++");

		steps.createStudent(firstName, lastName, email, programme, courses)
		.statusCode(201)
		.spec(ReusableSpecifications.getGenericResponseSpec());
	}

	@Title("Este Test verifica si el estudiante fue adicionado a la aplicacion ")
	@Test
	public void test002() {
		HashMap<String, Object> value=  steps.getStudentInfoByFirstName(firstName);
		assertThat(value, hasValue(firstName));
		studentId = (int) value.get("id");
	}

	@Title("Este Test permite actualizar la informacion y verificar la actualizacion de la informacion del estudiante")
	@Test
	public void test003() {
		ArrayList<String> courses = new ArrayList<String>();
		courses.add("JAVA");
		courses.add("C++");

		firstName= firstName + "_Actualizado";
		steps.updateStudent(studentId, firstName, lastName, email, programme, courses);
		
		HashMap<String, Object> value= steps.getStudentInfoByFirstName(firstName);
		assertThat(value, hasValue(firstName));
	}

	@Title("Eliminar un Estudiante y verificar que el estudiante fue eliminado")
	@Test
	public void test004(){
		steps.deleteStudent(studentId);
		steps.getStudentById(studentId)
		.statusCode(404).spec(ReusableSpecifications
		.getGenericResponseSpec());
	}


}
