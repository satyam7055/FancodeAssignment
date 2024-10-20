package config;

import org.testng.annotations.BeforeClass;

import io.restassured.RestAssured;

public class TestBase {
	
	@BeforeClass
	public void setUp() {
		RestAssured.baseURI=Config.BASE_URI;
	}

}
