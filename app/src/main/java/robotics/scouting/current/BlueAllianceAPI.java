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
    private static RequestQueue requestQueue;

    public BlueAllianceAPI(Context context) {
        requestQueue = Volley.newRequestQueue(context);
    }

    private static final String BASE_URL = "https://www.thebluealliance.com/api/v3";
    private static final String API_KEY = "0iRhF6zqSQlvSD2VGHX9WS8bIXPVIXU9quISdVV0b0wrD5Z00T2LFSHaxRHLr6et";

    public void getTeamData(String call, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        String url = BASE_URL + call;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, listener, errorListener) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("X-TBA-Auth-Key", API_KEY);
                return headers;
            }
        };
        requestQueue.add(request);
    }
}




