public class LambdaHandler implements RequestHandler<Map<String, String>, Weather> {
    @Autowired
    private WeatherService weatherService;

    @Override
    public Weather handleRequest(Map<String, String> input, Context context) {
        String city = input.get("city");
        return weatherService.getWeatherByCity(city);
    }
}
