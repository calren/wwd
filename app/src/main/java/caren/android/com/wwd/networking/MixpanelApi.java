package caren.android.com.wwd.networking;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;

import caren.android.com.wwd.MainActivity;

public class MixpanelApi extends AsyncTask<Activity, Void, JSONArray> {

    static JSONArray records;
    Activity activity;

    @Override
    public JSONArray doInBackground(Activity... activities) {
        activity = activities[0];
        try {
            return fetchEngageResults();
        } catch (Exception e) {
            System.out.println("error fetching results: " + e);
            return null;
        }
    }

    public static JSONArray fetchEngageResults() throws Exception {
        String API_KEY = "c57626aaf38fd600d92f06c89a1fef52";
        String API_SECRET = "e17f5267c93c8675acd60337c5ab1a48";

        // request params
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("api_key", API_KEY);
        params.put("expire", System.currentTimeMillis() + 60 * 1000);

        // build signature
        StringBuilder queryParams = new StringBuilder();
        StringBuilder sig = new StringBuilder();
        for (Map.Entry<String, Object> parameter : params.entrySet()) {
            queryParams.append(parameter.getKey()).append("=").append(parameter.getValue())
                    .append("&");
            sig.append(parameter.getKey()).append("=").append(parameter.getValue());
        }
        sig.append(API_SECRET);

        // md5 the sig
        byte[] hash = MessageDigest.getInstance("MD5").digest(sig.toString().getBytes("UTF-8"));
        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            int i = (b & 0xFF);
            if (i < 0x10) hex.append('0');
            hex.append(Integer.toHexString(i));
        }
        String sigHash = hex.toString();
        queryParams.append("sig=" + sigHash);

        // make request
        URL url = new URL("http://mixpanel.com/api/2.0/engage/?" + queryParams.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        conn.connect();
        InputStream stream = conn.getInputStream();
        java.util.Scanner s = new java.util.Scanner(stream).useDelimiter("\\A");
        String result = s.hasNext() ? s.next() : "";
        stream.close();

        // parse response
        try {
            JSONObject responseJson = new JSONObject(result);
            System.out.println("responseJson: " + responseJson);
            if (responseJson.has("error")) {
                throw new Exception(responseJson.toString());
            } else {
                records = responseJson.getJSONArray("results");
            }
        } catch (Exception e) {
            throw new Exception("Cannot parse response to JSON - response: " + result);
        }
        System.out.println("records: " + records);
        return records;
    }

    @Override
    public void onPostExecute(JSONArray jsonArray) {
        System.out.println("on post execute called");
        ((MainActivity) activity).populateUserList(records);
    }
}
