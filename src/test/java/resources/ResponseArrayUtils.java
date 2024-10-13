package resources;

import io.restassured.response.Response;


public class ResponseArrayUtils {
	public static <T> T[] getResponseAsArray(Response response, Class<T[]> responseType) {
        return response.getBody().as(responseType);
    }
}
