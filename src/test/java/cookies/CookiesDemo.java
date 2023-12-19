package cookies;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.Map;

import org.testng.annotations.Test;

import io.restassured.response.Response;

public class CookiesDemo {

	@Test(priority = 1)
	void getCookiesInfo() {
		Response response = given().when().get("https://www.google.com");

		// get single cookie info
//		cookie_value = response.getCookie("AEC");

		Map<String, String> cookies_value = response.getCookies();
		for (String key : cookies_value.keySet()) {
			String cookie_value = response.getCookie(key);
			response.then().cookie(key, cookie_value).log().all();
			
		}

	}

}
