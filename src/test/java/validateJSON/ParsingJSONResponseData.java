package validateJSON;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import org.json.JSONObject;

public class ParsingJSONResponseData {

	@Test(priority = 1)
	void testJsonResponse() {
//		Approach-1
		/*
		 * given() .contentType("ContentType.JSON") .when()
		 * .get("http://localhost:3000/store") .then() .statusCode(200)
		 * .header("Content-Type", "application/json; charset=utf-8")
		 * .body("book[3].title", equalTo("The Lord of the Rings"));
		 */
//		Approach-2
		Response response = given().contentType("ContentType.JSON").when().get("http://localhost:3000/store");

		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.header("Content-Type"), "application/json; charset=utf-8");
		String bookName = response.jsonPath().get("book[3].title").toString();
		Assert.assertEquals(bookName, "The Lord of the Rings");

	}

	@Test(priority = 2)
	void testJsonResponseBodyData() {
		double sum = 0;
		boolean status = false;
		Response response = given().contentType("ContentType.JSON").when().get("http://localhost:3000/store");
		JSONObject jo = new JSONObject(response.asString());
		for (int i = 0; i < jo.getJSONArray("book").length(); i++) {
			String bookTitle = jo.getJSONArray("book").getJSONObject(i).getString("title");
			if (bookTitle.equalsIgnoreCase("The Lord of the Rings")) {
				status = true;
				break;
			}
		}
		Assert.assertEquals(status, true);

//		Validate total price of book
		for (int i = 0; i < jo.getJSONArray("book").length(); i++) {
			double bookPrice = jo.getJSONArray("book").getJSONObject(i).getDouble("price");
			sum = sum + bookPrice;
		}
		System.out.println("The total price of books present in the store is :" + sum);

	}
}
