package fr.aceart.weatherspice.REST.Request;

import android.net.Uri;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

import fr.aceart.weatherspice.REST.Model.Observation;


public class WeatherRequest extends SpringAndroidSpiceRequest<Observation> {

    private String apiKey;
    private String countryCode;
    private String city;

    public WeatherRequest(String apiKey, String countryCode, String city) {
        super(Observation.class);
        this.apiKey = apiKey;
        this.countryCode = countryCode;
        this.city = city;
    }

    @Override
    public Observation loadDataFromNetwork() throws Exception {

        // With Uri.Builder class we can build our url is a safe manner
        Uri.Builder uriBuilder = Uri.parse(String.format("http://api.wunderground.com/api/%s/conditions/q/%s/%s.json", apiKey, countryCode, city)).buildUpon();

        String url = uriBuilder.build().toString();

        return getRestTemplate().getForObject(url, Observation.class);
    }

    /**
     * This method generates a unique cache key for this request. In this case our cache key depends just on the
     * keyword.
     *
     * @return
     */
    public String createCacheKey() {
        return "observation." + countryCode + "." + city;
    }
}
