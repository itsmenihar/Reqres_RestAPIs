package differentWaysToPostRequestBody;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;

import org.testng.annotations.Test;

public class Students {

	// Post request body using HashMap
	@Test(priority = 1)
	void testPostUsingHashMap() {

		HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("name", "Scott");
		data.put("location", "France");
		data.put("phone", "1593571472");

		String courseArr[] = { "C", "C++" };
		data.put("courses", courseArr);

		given().contentType("application/json").body(data).when().post("http://localhost:3000/students").then()
				.statusCode(201).body("name", equalTo("Scott")).body("location", equalTo("France"))
				.body("phone", equalTo("1593571472")).body("courses[0]", equalTo("C"))
				.body("courses[1]", equalTo("C++")).header("Content-Type", "application/json; charset=utf-8").log()
				.all();
	}

	// deleting student records
	@Test(priority = 2, dependsOnMethods = { "testPostUsingHashMap" })
	void testDelete() {

		when().delete("http://localhost:3000/students/4").then().statusCode(200);
	}
}
