package hw8;
/**
 * Java Core. HomeWork-8
 *
 * @author Pavel Pulyk
 * @version 0.1 27.01.2022
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class HomeWork8JC {
    public static void main(String[] args) throws IOException {
        UserInterfaceView interfaceView = new UserInterfaceView();

        interfaceView.runInterface();
    }
}

interface WeatherModel {
    void getWeather(String selectedCity, Period period) throws IOException;
    public List<Weather> getSavedToDBWeather();
}

enum Period {
    NOW, FIVE_DAYS, DB
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
    private DataBaseRepository dataBaseRepository = new DataBaseRepository();

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

                try {
                    dataBaseRepository.saveWeatherToDataBase(new Weather("Simferopol", "2022-01-27", "freezing", "-3"));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
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

    @Override
    public List<Weather> getSavedToDBWeather() {
        return dataBaseRepository.getSavedToDBWeather();
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

    Controller() {
        variants.put(1, Period.NOW);
        variants.put(5, Period.FIVE_DAYS);
        variants.put(2, Period.DB);
    }

    void getWeather(String userInput, String selectedCity) throws IOException {
        Integer userIntegerInput = Integer.parseInt(userInput);

        switch (variants.get(userIntegerInput)) {
            case NOW:
                weatherModel.getWeather(selectedCity, Period.NOW);
                break;
            case FIVE_DAYS:
                weatherModel.getWeather(selectedCity, Period.FIVE_DAYS);
                break;
            case DB:
                weatherModel.getSavedToDBWeather();
                break;
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
                    "Enter 5 for a 5 day forecast; Enter 2 to get data from the database; Enter 0 to exit. ");

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

class DataBaseRepository {
    private static final String DB_PATH = "jdbc:sqlite:gb.db";
    private String getWeather = "select * from weather";
    private String insertWeather = "insert into weather (city, localdate, weathertext, temperature) values (?, ?, ?)";

    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    boolean saveWeatherToDataBase(Weather weather) throws SQLException {
        try (Connection connection = DriverManager.getConnection(DB_PATH)) {
            PreparedStatement saveWeather = connection.prepareStatement(insertWeather);
            saveWeather.setString(1, weather.getCity());
            saveWeather.setString(2, weather.getLocalDate());
            saveWeather.setString(3, weather.getWeatherText());
            saveWeather.setString(4, weather.getTemperature());
            return saveWeather.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        throw new SQLException("Save weather to database failed!");
    }

    void saveWeatherToDataBase(List<Weather> weatherList) throws SQLException {
        try (Connection connection = DriverManager.getConnection(DB_PATH)) {
            PreparedStatement saveWeather = connection.prepareStatement(insertWeather);
            for (Weather weather : weatherList) {
                saveWeather.setString(1, weather.getCity());
                saveWeather.setString(2, weather.getLocalDate());
                saveWeather.setString(3, weather.getWeatherText());
                saveWeather.setString(4, weather.getTemperature());
                saveWeather.addBatch();
            }
            saveWeather.executeBatch();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    List<Weather> getSavedToDBWeather() {
        List<Weather> weather = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DB_PATH)) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(getWeather);
            while (resultSet.next()) {
                System.out.print(resultSet.getInt("id"));
                System.out.println(" ");
                System.out.print(resultSet.getString("city"));
                System.out.println(" ");
                System.out.print(resultSet.getString("localdate"));
                System.out.println(" ");
                System.out.print(resultSet.getString("weatherText"));
                System.out.println(" ");
                System.out.print(resultSet.getString("temperature"));
                System.out.println(" ");
                weather.add(new Weather(resultSet.getString("city"),
                        resultSet.getString("localdate"),
                        resultSet.getString("weatherText"),
                        resultSet.getString("temperature")));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return weather;
    }
}

class Weather {
    private String city;
    private String localDate;
    private String weatherText;
    private String temperature;

    Weather(String city, String localDate, String weatherText, String temperature) {
        this.city = city;
        this.localDate = localDate;
        this.weatherText = weatherText;
        this.temperature = temperature;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLocalDate() {
        return localDate;
    }

    public void setLocalDate(String localDate) {
        this.localDate = localDate;
    }

    public String getWeatherText() {
        return weatherText;
    }

    public void setWeatherText(String weatherText) {
        this.weatherText = weatherText;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "city='" + city + '\'' +
                ", localDate='" + localDate + '\'' +
                ", weatherText='" + weatherText + '\'' +
                ", temperature=" + String.format("%.2f", temperature) +
                '}';
    }
}
