package headers;

import static io.restassured.RestAssured.*;

import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.*;

import org.testng.annotations.Test;

public class HeadersValidation {

	@Test(priority = 1)
	void testHeader() {
		given().when().get("https://reqres.in/api/users?page=2").then().log().headers();
	}

	@Test(priority = 2)
	void testHeaders() {
		Response response = given().when().get("https://reqres.in/api/users?page=2");
		Headers h = response.getHeaders();
		for (Header hd : h) {
			String headerName = hd.getName();
			String headerValue = hd.getValue();
			System.out.println(headerName + "" + headerValue);
		}
	}
}
