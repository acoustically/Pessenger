package acoustically.pessenger.sign;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import AndroidHttpRequest.HttpRequestor;
import AndroidHttpRequest.HttpRequestorBuilder;
import AndroidHttpRequest.HttpResponseListener;
import acoustically.pessenger.tools.ActivityNavigator;
import acoustically.pessenger.tools.Environment;
import acoustically.pessenger.MainActivity;
import acoustically.pessenger.R;

public class SignUpActivity extends AppCompatActivity {
  Activity activity = this;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sign_up);
  }

  public void signUpClick(View view) {
    EditText passwordBox = (EditText)findViewById(R.id.password);
    String password = passwordBox.getText().toString();
    String phoneNumber = Environment.getPhoneNumber(this);
    signUp(phoneNumber, password);
  }
  private JSONObject buildJson(String phoneNumber, String password) throws JSONException{
    JSONObject json = new JSONObject();
    json.put("phone_number", phoneNumber);
    json.put("password", password);
    return json;
  }
  private void signUp(String phone_number, String password) {
    HttpRequestorBuilder builder = new HttpRequestorBuilder(Environment.getUrl("/user/new"));
    HttpRequestor requestor = builder.build();
    try {
      Log.e("test", "test");
      requestor.post(buildJson(phone_number, password), new HttpResponseListener() {
        @Override
        protected void httpResponse(String data) {
          try {
            JSONObject json = new JSONObject(data);
            if(json.getString("response").equals("success")){
              ActivityNavigator activityNavigator = new ActivityNavigator(activity, MainActivity.class);
              activityNavigator.navigate();
            } else {
              Toast.makeText(activity, "Server Error", Toast.LENGTH_LONG).show();
            }
          } catch (JSONException e) {
            Log.e("Error", e.getMessage(), e.fillInStackTrace());
          }
        }

        @Override
        protected void httpExcepted(Exception e) {
          Log.e("Error", e.getMessage(), e.fillInStackTrace());
          Toast.makeText(activity, e.getMessage(), Toast.LENGTH_LONG).show();
        }
      });
    } catch (JSONException e) {
      Log.e("Error", e.getMessage(), e.fillInStackTrace());
    }
  }
}
