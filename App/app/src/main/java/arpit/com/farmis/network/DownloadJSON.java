package arpit.com.farmis.network;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;


public class DownloadJSON extends AsyncTask<String, String, String>{

    /**
     * Before starting background thread Show Progress Dialog
     */
    JsonLoaded jsonLoaded;
    boolean failure = false;
    String json_url;

    public DownloadJSON(String json_url, JsonLoaded jsonLoaded) {
        this.json_url = json_url;
        this.jsonLoaded = jsonLoaded;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... args) {
        // TODO Auto-generated method stub
        // Check for success tag
        int success;
        Log.d("request!", "starting");
        // getting product details by making HTTP request
        JSONParser jsonParser = new JSONParser();
        JSONObject json = jsonParser.makeHttpRequest(
                json_url, "GET");

        // check your log for json response
        Log.d("attempt", json.toString());
        onPostExecute(json);


        return null;

    }

    /**
     * After completing background task Dismiss the progress dialog
     **/
    protected void onPostExecute(JSONObject json) {
        // dismiss the dialog once product delete

        jsonLoaded.onJsonLoaded(json);
    }
}