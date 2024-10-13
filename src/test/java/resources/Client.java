package resources;

import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class Client {
	
	public static Response get(String endpoint) {
		return given().when().get(endpoint);
	}
}
