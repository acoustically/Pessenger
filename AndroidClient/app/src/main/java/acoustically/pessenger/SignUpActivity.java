package acoustically.pessenger;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.net.Socket;

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
  }
  private String buildJson(String phoneNumber, String password) throws Exception{
    JSONObject json = new JSONObject();
    json.put("phoneNumber", phoneNumber);
    json.put("password", password);
    return json.toString();
  }
}
