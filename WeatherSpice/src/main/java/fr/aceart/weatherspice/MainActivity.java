package fr.aceart.weatherspice;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.octo.android.robospice.JacksonSpringAndroidSpiceService;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import fr.aceart.weatherspice.REST.Model.Observation;
import fr.aceart.weatherspice.REST.Request.WeatherRequest;

public class MainActivity extends Activity {

    private SpiceManager contentManager = new SpiceManager( JacksonSpringAndroidSpiceService.class );

    private Button btnPerformRequest;
    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPerformRequest = (Button) findViewById(R.id.btnPerformRequest);
        tvResult = (TextView) findViewById(R.id.tvResult);

        btnPerformRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performRequest();
            }
        });
    }

    @Override
    protected void onStart() {
        contentManager.start( this );
        super.onStart();
    }

    @Override
    protected void onStop() {
        contentManager.shouldStop();
        super.onStop();
    }

    private void performRequest() {
        WeatherRequest request = new WeatherRequest("yourapikey","FR","Bordeaux");
        contentManager.execute( request, request.createCacheKey(), DurationInMillis.ONE_HOUR, new WeatherRequestListener() );
    }

    private class WeatherRequestListener implements RequestListener<Observation> {

        @Override
        public void onRequestFailure( SpiceException e ) {
            Toast.makeText(MainActivity.this, "Error during request: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onRequestSuccess( Observation observation ) {

            if ( observation == null && observation.getCurrent_observation() != null ) {
                return;
            }

            MainActivity.this.tvResult.setText(String.format("Temp : %s C, Wind : %s km/h",
                    observation.getCurrent_observation().getTemp_c(),
                    observation.getCurrent_observation().getWind_kph()));
        }
    }
    
}
