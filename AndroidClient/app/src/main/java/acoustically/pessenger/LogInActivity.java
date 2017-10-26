package acoustically.pessenger;

import android.Manifest;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.params.HttpParams;
import org.json.JSONObject;

import java.net.Socket;

import AndroidHttpRequest.HttpRequestor;
import AndroidHttpRequest.HttpRequestorBuilder;
import AndroidHttpRequest.HttpResponseListener;

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
    Log.e("Error", "1");
    String phonenumber =  Environment.getPhoneNumber(this);
    Log.e("Error", "2");
    HttpRequestorBuilder builder = new HttpRequestorBuilder(Environment.getUrl("/users/"+phonenumber));
    Log.e("Error", "3");
    builder.addHeaders("Authorization", "Token acoustically");
    Log.e("Error", "4");
    HttpRequestor requestor = builder.build();
    Log.e("Error", "5");
    requestor.get(new HttpResponseListener() {
      @Override
      protected void httpResponse(String data) {
        try {
          JSONObject json = new JSONObject(data);
          if(json.get("response").equals("success")) {
            ActivityNavigator navigator = new ActivityNavigator(activity, MainActivity.class);
            navigator.navigate();
          } else {
            ActivityNavigator navigator = new ActivityNavigator(activity, SignUpActivity.class);
            navigator.navigate();
          }
        } catch (Exception e) {
          Log.e("Error", e.getMessage(), e.fillInStackTrace());
        }
      }
      @Override
      protected void httpExcepted(Exception e) {
        Log.e("Error", e.getMessage(), e.fillInStackTrace());
        Toast.makeText(activity, "Server Error", Toast.LENGTH_LONG).show();
      }
    });
  }
}
