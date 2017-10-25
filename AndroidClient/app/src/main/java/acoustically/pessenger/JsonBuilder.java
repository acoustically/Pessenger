package acoustically.pessenger;

import android.util.Log;

import org.json.JSONObject;

/**
 * Created by acoustically on 17. 10. 26.
 */

public class JsonBuilder {
  final static JSONObject buildJson(String data) {
    try {
      JSONObject jsonObject = new JSONObject(data);
      return jsonObject;
    } catch (Exception e) {
      Log.e("Error", e.getMessage(), e.fillInStackTrace());
      return null;
    }
  }
}
