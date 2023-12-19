package differentWaysToPostRequestBody;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.annotations.Test;

import io.restassured.response.Response;

public class Students {
	int id;

// 1-Post request body using HashMap
//	@Test(priority = 1)
	void testPostUsingHashMap() {

		HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("name", "Scott");
		data.put("location", "France");
		data.put("phone", "1593571472");

		String courseArr[] = { "C", "C++" };
		data.put("courses", courseArr);

		Response response = given().contentType("application/json").body(data).when()
				.post("http://localhost:3000/students");
		id = response.jsonPath().getInt("id");
		response.then().statusCode(201).body("name", equalTo("Scott")).body("location", equalTo("France"))
				.body("phone", equalTo("1593571472")).body("courses[0]", equalTo("C"))
				.body("courses[1]", equalTo("C++")).header("Content-Type", "application/json; charset=utf-8").log()
				.all();
	}

// 2-Post request body using org.json
//	@Test(priority = 1)
	void testPostUsingJsonObject() {

		JSONObject data = new JSONObject();
		data.put("name", "Scott");
		data.put("location", "France");
		data.put("phone", "1593571472");

		String courseArr[] = { "C", "C++" };
		data.put("courses", courseArr);

		Response response = given().contentType("application/json").body(data.toString()).when()
				.post("http://localhost:3000/students");
		id = response.jsonPath().getInt("id");
		response.then().statusCode(201).body("name", equalTo("Scott")).body("location", equalTo("France"))
				.body("phone", equalTo("1593571472")).body("courses[0]", equalTo("C"))
				.body("courses[1]", equalTo("C++")).header("Content-Type", "application/json; charset=utf-8").log()
				.all();
	}

// 3-Post request body using POJO class(plain old java object)
//	@Test(priority = 1)
	void testPostUsingPOJO() {

		POJO_PostRequest data = new POJO_PostRequest();
		data.setName("Scott");
		data.setLocation("France");
		data.setPhone("1593571472");
		String coursesArr[] = { "C", "C++" };
		data.setCourses(coursesArr);

		Response response = given().contentType("application/json").body(data).when()
				.post("http://localhost:3000/students");
		id = response.jsonPath().getInt("id");
		response.then().statusCode(201).body("name", equalTo("Scott")).body("location", equalTo("France"))
				.body("phone", equalTo("1593571472")).body("courses[0]", equalTo("C"))
				.body("courses[1]", equalTo("C++")).header("Content-Type", "application/json; charset=utf-8").log()
				.all();
	}

// 4-Post request body using external json File
	@Test(priority = 1)
	void testPostUsingExternalJsonFile() throws FileNotFoundException {

		File f = new File(".\\body.json");
		FileReader fr = new FileReader(f);
		JSONTokener jt = new JSONTokener(fr);
		JSONObject data = new JSONObject(jt);

		Response response = given().contentType("application/json").body(data.toString()).when()
				.post("http://localhost:3000/students");
		id = response.jsonPath().getInt("id");
		response.then().statusCode(201).body("name", equalTo("Scott")).body("location", equalTo("France"))
				.body("phone", equalTo("1593571472")).body("courses[0]", equalTo("C"))
				.body("courses[1]", equalTo("C++")).header("Content-Type", "application/json; charset=utf-8").log()
				.all();
	}

	// deleting student records
	@Test(priority = 2)
	void testDelete() {

		when().delete("http://localhost:3000/students/" + id).then().statusCode(200);
	}

}
