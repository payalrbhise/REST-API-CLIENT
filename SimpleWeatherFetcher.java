import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class SimpleWeatherFetcher {

    public static String fetchWeather(double lat, double lon) {
        String api = "https://api.open-meteo.com/v1/forecast?latitude=" + lat +
                     "&longitude=" + lon + "&current_weather=true";
        StringBuilder responseContent = new StringBuilder();

        try {
            URL url = new URL(api);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("GET");

            if (http.getResponseCode() == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(http.getInputStream()));
                String line;
                while ((line = in.readLine()) != null) {
                    responseContent.append(line);
                }
                in.close();
            } else {
                System.out.println("HTTP Error: " + http.getResponseCode());
            }
        } catch (Exception e) {
            System.out.println("Connection failed: " + e.getMessage());
        }

        return responseContent.toString();
    }

    // Parses the JSON and prints selected weather details
    public static void displayWeather(String jsonData) {
        try {
            JSONObject root = new JSONObject(jsonData);
            JSONObject current = root.getJSONObject("current_weather");

            System.out.println("🌦️ Weather Summary");
            System.out.println("------------------------");
            System.out.println("Temperature : " + current.getDouble("temperature") + "°C");
            System.out.println("Wind Speed  : " + current.getDouble("windspeed") + " km/h");
            System.out.println("Timestamp   : " + current.getString("time"));
            System.out.println("------------------------");

        } catch (Exception e) {
            System.out.println("Error parsing data: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        double latitude = 40.7128;   // Example: New York City
        double longitude = -74.0060;

        String weatherData = fetchWeather(latitude, longitude);
        displayWeather(weatherData);
    }
}
