import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import org.json.JSONObject;
import org.json.JSONArray;

public class WeatherMain {
	//common method to get json object from given url object
	public JSONObject CommonMethod(URL url) {
		StringBuilder sb = new StringBuilder();
		try{
			//create https connection
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			//if connection response code is not 200 throw exception
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "+ conn.getResponseCode());
			}
			//createing buffer reader object to get connection object input stream
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			String output;
			while ((output = br.readLine()) != null) {
				//append all strings to string builder
				sb.append(output+"\n");
			}
			//close buffer reader
			br.close();
			//close connection
			conn.disconnect();
		}
		//catch exceptions
		catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//create json object
		JSONObject json = new JSONObject(sb.toString());
		return json;
	}
	
	public static void main(String[] args)  {
		//check args has passed or not
		if (args.length > 0) 
		{
			System.out.println("The command line"+ " arguments are:" +args[0]);
			try {
				//
				URL url = new URL("https://api.weather.gov/points/" + args[0]);
				WeatherMain weather = new WeatherMain();
				JSONObject json = weather.CommonMethod(url);
				String forecast = json.getJSONObject("properties").getString("forecast");

				URL urlforecast = new URL(forecast);
				JSONObject jsonforecast = weather.CommonMethod(urlforecast);
				//parse properties and then nested periods list as JSON object
				JSONObject forecastjson = jsonforecast.getJSONObject("properties");
				JSONArray forecastperiods = forecastjson.getJSONArray("periods");
				System.out.println("forecast report for next 5 days including today's report");
				//Iterate over forecast list 12 times to get next 5 days and today
				for (int i=0;i<12;i++) {
					System.out.println(forecastperiods.getJSONObject(i).getString("name")+":"+forecastperiods.getJSONObject(i));
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
		else {
			System.out.println("please pass {latitude,longitude} arguments");
		}
	}

}
