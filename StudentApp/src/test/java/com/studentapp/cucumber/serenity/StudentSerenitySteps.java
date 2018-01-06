package com.studentapp.cucumber.serenity;

import java.util.HashMap;
import java.util.List;

import com.studentapp.model.StudentClass;
import com.studentapp.utils.ReusableSpecifications;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

public class StudentSerenitySteps {

	@Step("Creando Estudiante con firstName:{0}, lastName:{1}, email:{2}, programme:{3}, courses:{4}")
	public ValidatableResponse createStudent(String firstName, String lastName, String email, String programme, List<String> courses) {
		StudentClass student = new StudentClass();
		student.setFirstName(firstName);
		student.setLastName(lastName);
		student.setProgramme(programme);
		student.setEmail(email);
		student.setCourses(courses);
		
		
		return SerenityRest.rest().given()
//		.contentType(ContentType.JSON)
		.spec(ReusableSpecifications.getGenericRequestSpec())
		.when()
		.body(student)
		.post()
		.then();
	}
	
	@Step("Obteniendo la informacion del estudiante con firstName: {0}")
	public HashMap<String, Object> getStudentInfoByFirstName(String firstName){
		String p1 = "findAll{it.firstName=='";
		String p2 = "'}.get(0)";
		
		return SerenityRest.rest().given()
		.when()
		.get("/list")
		.then()
		.log()
		.all()
		.statusCode(200)
		.extract()
		.path(p1+firstName+p2);
	}
	
	@Step("Actualizando informacion del estudiante con studentId:{0}, firstName:{1}, lastName:{2}, email:{3}, programme:{4}, courses:{5}")
	public ValidatableResponse updateStudent(int studentId, String firstName, String lastName, String email, String programme, List<String> courses) {
		StudentClass student = new StudentClass();
		student.setFirstName(firstName);
		student.setLastName(lastName);
		student.setProgramme(programme);
		student.setEmail(email);
		student.setCourses(courses);
		
		
		return SerenityRest.rest().given()
//		.contentType(ContentType.JSON)
		.spec(ReusableSpecifications.getGenericRequestSpec())
		.log()
		.all()
		.when()
		.body(student)
		.put("/" + studentId)
		.then();
	}
	
	@Step("Eliminando el estudiante con ID:{0}")
	public void deleteStudent(int studentId) {
		SerenityRest
		.rest()
		.given()
		.when()
		.delete("/" + studentId)
		.then();
	}
	
	@Step("Consultando la informacion del estudiante con studentId:{0}")
	public ValidatableResponse getStudentById(int studentId) {
		return SerenityRest
		.rest()
		.given()
		.when()
		.get("/" + studentId)
		.then();
	}
}
