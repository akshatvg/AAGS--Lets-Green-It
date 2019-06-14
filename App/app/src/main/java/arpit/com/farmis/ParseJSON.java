package arpit.com.farmis;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ParseJSON {

    private String TAG = ParseJSON.class.getSimpleName();

    JSONObject json;

    public ParseJSON(JSONObject json) throws JSONException {
        this.json = json;
    }

    public ArrayList<Float> getJSONArray(String key) throws JSONException {
        ArrayList<Float> returnItems = new ArrayList<>();
        JSONArray arrItems = json.getJSONArray(key);
        // looping through All Contacts
        for(int i = 0; i < arrItems.length(); i++){
            Log.d(TAG, key+" => "+arrItems.get(i));
            returnItems.add(Float.parseFloat(String.valueOf(arrItems.getDouble(i))));
        }
        return returnItems;
    }

}
