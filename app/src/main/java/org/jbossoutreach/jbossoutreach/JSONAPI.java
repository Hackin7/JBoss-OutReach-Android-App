package org.jbossoutreach.jbossoutreach;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;


public class JSONAPI extends AsyncTask<String, Void, ArrayList<String>>{
    @Override
    protected void onPreExecute() {}

    @Override
    protected ArrayList<String> doInBackground(String... params) {
        // Retrieving Data
        String response = null;
        try {
            URL url = new URL(params[0]);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            // read the response
            InputStream in = new BufferedInputStream(conn.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder sb = new StringBuilder();
            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append('\n');
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            response = sb.toString();
        } catch (MalformedURLException e) {
            Log.e(TAG, "MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            Log.e(TAG, "ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "IOException: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }

        String data = response;
        ArrayList<String> content = new ArrayList<String>();
        try {
            JSONArray jsonData = new JSONArray(data);
            for (int i = 0; i < jsonData.length(); i++) {
                JSONObject repo = jsonData.getJSONObject(i);
                content.add(repo.get(params[1]).toString());
            }
        } catch (JSONException e) {
            Log.e(TAG,"JSONException Error: "+e.getMessage());
        }
        return content;
    }

    @Override
    protected void onPostExecute(ArrayList<String> content) {

    }

}