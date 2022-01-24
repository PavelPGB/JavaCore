package HW7JC;
/**
 * Java Core. HomeWork-7
 *
 * @author Pavel Pulyk
 * @version 0.1 24.01.2022
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class HomeWork7JC {
    public static void main(String[] args) throws IOException {
        UserInterfaceView interfaceView = new UserInterfaceView();

        interfaceView.runInterface();
    }
}

interface WeatherModel {
    void getWeather(String selectedCity, Period period) throws IOException;
}

enum Period {
    NOW, FIVE_DAYS
}

class Accuweather implements WeatherModel {
    private static final String PROTOKOL = "https";
    private static final String BASE_HOST = "dataservice.accuweather.com";
    private static final String FORECASTS = "forecasts";
    private static final String VERSION = "v1";
    private static final String DAILY = "daily";
    private static final String ONE_DAY = "1day";
    private static final String FIVE_DAYS = "5day";
    private static final String API_KEY = "NGRNjquRgN3JWVgFi0DtXG0hNTwbeP5c";
    private static final String API_KEY_QUERY_PARAM = "apikey";
    private static final String LOCATIONS = "locations";
    private static final String CITIES = "cities";
    private static final String AUTOCOMPLETE = "autocomplete";

    private static final OkHttpClient okHttpClient = new OkHttpClient();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public void getWeather(String selectedCity, Period period) throws IOException {
        switch (period) {
            case NOW:
                HttpUrl httpUrl = new HttpUrl.Builder()
                        .scheme(PROTOKOL)
                        .host(BASE_HOST)
                        .addPathSegment(FORECASTS)
                        .addPathSegment(VERSION)
                        .addPathSegment(DAILY)
                        .addPathSegment(ONE_DAY)
                        .addPathSegment(detectCityKey(selectedCity))
                        .addQueryParameter(API_KEY_QUERY_PARAM, API_KEY)
                        .build();

                Request request = new Request.Builder()
                        .url(httpUrl)
                        .build();

                Response oneDayForecastResponse = okHttpClient.newCall(request).execute();
                String weatherResponse = oneDayForecastResponse.body().string();
                System.out.println(new GsonBuilder().setPrettyPrinting().create().toJson(new JsonParser().parse(weatherResponse)));
                break;
                case FIVE_DAYS:
                    HttpUrl httpUrl1 = new HttpUrl.Builder()
                        .scheme(PROTOKOL)
                        .host(BASE_HOST)
                        .addPathSegment(FORECASTS)
                        .addPathSegment(VERSION)
                        .addPathSegment(DAILY)
                        .addPathSegment(FIVE_DAYS)
                        .addPathSegment(detectCityKey(selectedCity))
                        .addQueryParameter(API_KEY_QUERY_PARAM, API_KEY)
                        .build();

                    Request request1 = new Request.Builder()
                        .url(httpUrl1)
                        .build();

                    Response fiveDayForecastResponse = okHttpClient.newCall(request1).execute();
                    String weatherResponse1 = fiveDayForecastResponse.body().string();
                    System.out.println(new GsonBuilder().setPrettyPrinting().create().toJson(new JsonParser().parse(weatherResponse1)));
                break;
        }
    }

    private String detectCityKey(String selectCity) throws IOException {
        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme(PROTOKOL)
                .host(BASE_HOST)
                .addPathSegment(LOCATIONS)
                .addPathSegment(VERSION)
                .addPathSegment(CITIES)
                .addPathSegment(AUTOCOMPLETE)
                .addQueryParameter(API_KEY_QUERY_PARAM, API_KEY)
                .addQueryParameter("q", selectCity)
                .build();

        Request request = new Request.Builder()
                .url(httpUrl)
                .get()
                .addHeader("accept", "application/json")
                .build();

        Response response = okHttpClient.newCall(request).execute();
        String responseString = response.body().string();

        String cityKey = objectMapper.readTree(responseString).get(0).at("/Key").asText();
        return cityKey;
    }
}

class Controller {
    private WeatherModel weatherModel = new Accuweather();
    private Map<Integer, Period> variants = new HashMap<>();

    public Controller() {
        variants.put(1, Period.NOW);
        variants.put(5, Period.FIVE_DAYS);
    }

    public void getWeather(String userInput, String selectedCity) throws IOException {
        Integer userIntegerInput = Integer.parseInt(userInput);

        switch (variants.get(userIntegerInput)) {
            case NOW:
                weatherModel.getWeather(selectedCity, Period.NOW);
                break;
            case FIVE_DAYS:
                weatherModel.getWeather(selectedCity, Period.FIVE_DAYS);
        }
    }
}

class UserInterfaceView {
    private Controller controller = new Controller();

        void runInterface() {
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("Enter city name: ");
                String city = scanner.nextLine();

                System.out.println("Enter 1 for today's weather; " +
                        "Enter 5 for a 5 day forecast; Enter 0 to exit. ");

                String command = scanner.nextLine();

                if (command.equals("0")) break;

                try {
                    controller.getWeather(command, city);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
