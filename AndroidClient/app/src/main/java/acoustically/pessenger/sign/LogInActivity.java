package acoustically.pessenger.sign;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import AndroidHttpRequest.HttpRequestor;
import AndroidHttpRequest.HttpRequestorBuilder;
import AndroidHttpRequest.HttpResponseListener;
import acoustically.pessenger.tools.Environment;
import acoustically.pessenger.MainActivity;
import acoustically.pessenger.R;

public class LogInActivity extends AppCompatActivity {
  Activity activity = this;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_log_in);
    Environment.setPermission(this, Manifest.permission.READ_PHONE_STATE);
    logIn();
  }

  private void logIn() {
    String phonenumber =  Environment.getPhoneNumber(this);
    HttpRequestorBuilder builder = new HttpRequestorBuilder(Environment.getUrl("/user/"+phonenumber));
    builder.addHeaders("Authorization", "Token acoustically");
    HttpRequestor requestor = builder.build();
    requestor.get(new HttpResponseListener() {
      @Override
      protected void httpResponse(String data) {
        try {
          JSONObject json = new JSONObject(data);
          Log.e("Error", json.toString());
          if(json.get("response").equals("success")) {
            activity.startActivity(new Intent(activity, MainActivity.class));
            activity.finish();
          } else {
            if(json.getJSONObject("error").getInt("errno") == 1602) {
              activity.startActivity(new Intent(activity, SignUpActivity.class));
              activity.finish();
            }
          }
        } catch (Exception e) {
          Log.e("Error", e.getMessage(), e.fillInStackTrace());
        }
      }
      @Override
      protected void httpExcepted(Exception e) {
        Log.e("Error", e.getMessage(), e.fillInStackTrace());
        Toast.makeText(activity, e.getMessage(), Toast.LENGTH_LONG).show();
      }
    });
  }
}
