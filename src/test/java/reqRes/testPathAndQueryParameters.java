package reqRes;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.annotations.Test;

public class testPathAndQueryParameters {

	// https://reqres.in/api/users?page=2&id=5
	@Test
	void testGetUser() {
		given().pathParam("myPath", "users").queryParam("page", 2).queryParam("id", 5).when()
				.get("https://reqres.in/api/{myPath}").then().statusCode(200).log().all();
	}
}
