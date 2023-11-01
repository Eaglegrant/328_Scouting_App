package robotics.scouting.current;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class BlueAllianceAPI {
    private RequestQueue requestQueue; // change to non-static

    public BlueAllianceAPI(Context context) {
        requestQueue = Volley.newRequestQueue(context);
    }

    private final String BASE_URL = "https://www.thebluealliance.com/api/v3";
    private final String API_KEY = "0iRhF6zqSQlvSD2VGHX9WS8bIXPVIXU9quISdVV0b0wrD5Z00T2LFSHaxRHLr6et";

    // Method for retrieving data from a specific endpoint
    public void getTeamData(String call, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        String url = BASE_URL + call;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, listener, errorListener) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("X-TBA-Auth-Key", API_KEY); // set the API key in the headers
                return headers;
            }
        };
        requestQueue.add(request); // add the request to the queue
    }
}
