package resources;



import config.Config;
import io.restassured.RestAssured;

public class Utils {
	
	public String requestSpecifications()
	{
		return RestAssured.baseURI=Config.BASE_URI;
	}
	
	public static boolean ValidateLatLag(double latitude, double longitude) 
	{
		if (latitude >= LatLag.LAT_START && latitude <= LatLag.LAT_END && longitude >= LatLag.LNG_START
				&& longitude <= LatLag.LNG_END) 
		{
			return true;
		}
		return false;
	}
	
	public static double ValidateTaskCompletedPercentage (double completedTask, double totalTask) {
		return (completedTask/totalTask) * 100;
	}

}
