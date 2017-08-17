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

  class ReceiveThreadHandler extends Handler {
    @Override
    public void handleMessage(Message msg) {
      super.handleMessage(msg);
      if(msg.arg1 == 1) {
        //Navigate to main activity
        MainActivity.navigateActivity(activity, MainActivity.class);
        Log.e("success", "sign up success");
      }  else {
        Toast.makeText(activity, "fail to sign up", Toast.LENGTH_LONG).show();
        Log.e("error", "fail to sign up");
      }
    }
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sign_up);
  }

  public void signUpClick(View view) {
    EditText passwordBox = (EditText)findViewById(R.id.password);
    String password = passwordBox.getText().toString();
    String phoneNumber = MainActivity.getPhoneNumber(this);
    Log.e("test", password);
    try {
      SocketConnector connector = new SocketConnector();
      Socket socket = connector.getSocket();
      ServerWriteThread sender = new ServerWriteThread(socket, buildJson(phoneNumber, password));
      sender.start();
      ServerReceiveThread receiver = new ServerReceiveThread(socket, new ReceiveThreadHandler());
      receiver.start();
    } catch (Exception e) {
      Log.e("error", "fails to send sign up data to server");
    }
  }
  private String buildJson(String phoneNumber, String password) throws Exception{
    JSONObject json = new JSONObject();
    json.put("phoneNumber", phoneNumber);
    json.put("password", password);
    return json.toString();
  }
}
