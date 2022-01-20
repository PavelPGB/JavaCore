/**
 * Java Core. HomeWork-6
 *
 * @author Pavel Pulyk
 * @version 0.1 20.01.2022
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

class HomeWork6JC {
    public static void main(String[] args) throws IOException {
        Accuweather aw = new Accuweather();

        System.out.println(aw.getWeather("Simferopol", Period.NOW));
    }
}

interface WeatherModel {
    String getWeather(String selectedCity, Period period) throws IOException;
}

enum Period {
    NOW
}

class Accuweather implements WeatherModel {
    private static final String PROTOKOL = "https";
    private static final String BASE_HOST = "dataservice.accuweather.com";
    private static final String FORECASTS = "forecasts";
    private static final String VERSION = "v1";
    private static final String DAILY = "daily";
    private static final String ONE_DAY = "1day";
    private static final String API_KEY = "NGRNjquRgN3JWVgFi0DtXG0hNTwbeP5c";
    private static final String API_KEY_QUERY_PARAM = "apikey";
    private static final String LOCATIONS = "locations";
    private static final String CITIES = "cities";
    private static final String AUTOCOMPLETE = "autocomplete";

    private static final OkHttpClient okHttpClient = new OkHttpClient();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public String getWeather(String selectedCity, Period period) throws IOException {
        String weatherResponse = null;
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
                weatherResponse = oneDayForecastResponse.body().string();
                System.out.println(weatherResponse);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + period);
        }
        return weatherResponse;
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
        String responseCity = response.body().string();

        String cityKey = objectMapper.readTree(responseCity).get(0).at("/Key").asText();
        return cityKey;
    }
}

