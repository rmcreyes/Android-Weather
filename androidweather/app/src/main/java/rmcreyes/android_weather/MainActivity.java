package rmcreyes.android_weather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private Button get_weather_btn;
    private TextView weather_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        get_weather_btn = (Button) findViewById(R.id.get_weather_btn);
        weather_text = (TextView) findViewById(R.id.weather_text);

        weather_text.setVisibility(View.INVISIBLE);

        get_weather_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://api.openweathermap.org/data/2.5/weather?q=Houston,us&appid=f53f39621075eab67a85b4f0f1a0447d&units=metric";
                // send a request using the OpenWeatherMap API, expecting a JSONObject in return
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // retrieve a jsonObject with the key string "main"
                            JSONObject jsonObject = response.getJSONObject("main");
                            // obtain and display the temperature attached to the main jsonObject
                            String weather = String.valueOf((int) jsonObject.getDouble("temp")) +
                                    " \u00b0C";
                            weather_text.setText(weather);
                            weather_text.setVisibility(View.VISIBLE);
                        } catch(JSONException e) {
                            // in the case where the JSONObject is of invalid structure,
                            // outline a corresponding error message
                            weather_text.setText("error: invalid response");
                            weather_text.setVisibility(View.VISIBLE);
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // in the case where no response is received, outline a corresponding
                        // error message
                        weather_text.setText("error: request failed");
                    }
                }
                );
                // send the request
                RequestQueue queue = Volley.newRequestQueue(v.getContext());
                queue.add(jsonObjectRequest);
            }
        });


    }
}
