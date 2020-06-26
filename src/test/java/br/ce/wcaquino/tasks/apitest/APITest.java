package br.ce.wcaquino.tasks.apitest;

import static org.hamcrest.Matchers.is;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class APITest {

	@BeforeClass
	public static void setup() {
		RestAssured.baseURI = "http://localhost:8001/tasks-backend";
	}
	
	
	@Test
	public void deveRetornarOsDadosDoBackend() {
		RestAssured
			.given()
			.when()
				.get("/todo")
			.then()
				.statusCode(200)
			;
	}
	
	
	@Test
	public void deveAdicionarTarefasComSucesso() {
		RestAssured
			.given()
				.contentType(ContentType.JSON)
				.body("{\"task\":\"Task informada no corpo\",\"dueDate\":\"2030-06-30\"}")
			.when()
				.post("/todo")
			.then()
				.statusCode(201)
			;
	}
	
	@Test
	public void naoDeveAdicionarTarefasSemDescricao() {
		RestAssured
			.given()
				.contentType(ContentType.JSON)
				.body("{\"dueDate\":\"2030-06-30\"}")
			.when()
				.post("/todo")
			.then()
				.body("message", is("Fill the task description"))
				.statusCode(400)
			;
	}	
	
	
	@Test
	public void naoDeveAdicionarTarefasComDataPassada() {
		RestAssured
			.given()
				.contentType(ContentType.JSON)
				.body("{\"task\":\"Task informada no corpo\",\"dueDate\":\"2000-06-30\"}")
			.when()
				.post("/todo")
			.then()
				.statusCode(400)
				.body("message", is("Due date must not be in past"))
			;
	}	
	
	@Test
	public void naoDeveAdicionarTarefasSemData() {
		RestAssured
			.given()
				.contentType(ContentType.JSON)
				.body("{\"task\":\"Task informada no corpo\"}")
			.when()
				.post("/todo")
			.then()				
				.body("message", is("Fill the due date"))
			;
	}	
	
	
}







