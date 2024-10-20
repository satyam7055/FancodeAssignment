package StepsDefinitions;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import config.StatusCode;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import pojo.User;
import pojo.Todo;
import resources.ResponseArrayUtils;
import resources.Utils;

import static io.restassured.RestAssured.given;


public class StepDefinitions {
	
	ExtentReports extent = new ExtentReports();
	ExtentSparkReporter spark = new ExtentSparkReporter("Reports/TestResults.html");
	public ExtentTest logger;
	public User[] usersArray;
	public Todo[] todoArray;
	public ExtentTest test;
	public String userName;
	public User user;
	
	double passPercentValue = 50;
	String queryParamAttribute = "userId";

	@BeforeTest
	public void beforeTest() {
		extent.attachReporter(spark);
	}

	
	@Given("User has the todo tasks")
	public void user_has_the_todo_tasks() {
		
		test = extent.createTest("Getting the users who have completed Task more than 50%");
		logger = test;
		
		RestAssured.baseURI="http://jsonplaceholder.typicode.com";
		Response userResponse = given().when().get("/users");
		Assert.assertTrue(userResponse.getStatusCode() == StatusCode.Code_200.getCode(), "Response Failed");
		test.pass("Response recieved successfully");
		
		test.log(Status.INFO, "User Fetched from the endpoint");
		usersArray = ResponseArrayUtils.getResponseAsArray(userResponse, User[].class);
	}
    


	@And("User belongs to the city FanCode")
	public void user_belongs_to_the_city_fan_code() 
	{
		for (User user : usersArray) 
		{
			double userLat =Double.parseDouble(user.getAddress().getGeo().getLat());
			double userLng = Double.parseDouble(user.getAddress().getGeo().getLng());

			test.log(Status.INFO, "Checking for the condition of Lattitude and Longitude for user : " + user.getName());
			if (Utils.ValidateLatLag(userLat, userLng)) 
			{
				userName = user.getName();
				test.log(Status.INFO, "Fetching the Todo list of " + userName
						+ " who matched the condition of Lattitude and Longitude ");
				Response todoResponse = given().queryParam(queryParamAttribute, user.getId()).when().get("/todos");
				Assert.assertTrue(todoResponse.getStatusCode() == StatusCode.Code_200.getCode(), "Response Failed");

				todoArray = ResponseArrayUtils.getResponseAsArray(todoResponse, Todo[].class);
				
			}
			
		}
				
				
	}
		

	@Then("User Completed task percentage should be greater than {int}%")
	public void user_completed_task_percentage_should_be_greater_than(Integer int1)
	{
		
		
		int totalTask = todoArray.length;
		int completedTask = 0;

		test.log(Status.INFO, "Getting the count for completed task for the user : " + userName);
		for (Todo todo : todoArray) {
			if (todo.isCompleted() == true) 
			{
				completedTask++;
			}
		}

		test.log(Status.INFO, "Checking the Task Completed Percentage more than 50% for user : " + userName);
		Assert.assertTrue(Utils.ValidateTaskCompletedPercentage(completedTask, totalTask) > passPercentValue);
		System.out.println(Utils.ValidateTaskCompletedPercentage(completedTask, totalTask));
	}

	
}
