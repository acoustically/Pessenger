package acoustically.pessenger;

import android.Manifest;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONObject;

import java.net.Socket;

public class LogInActivity extends AppCompatActivity {
  Activity activity = this;
  class ReceiveThreadHandler extends Handler {
    @Override
    public void handleMessage(Message msg) {
      super.handleMessage(msg);
      if(msg.arg1 == 1) {
        //Navigate to main activity
        MainActivity.navigateActivity(activity, MainActivity.class);
        Log.e("success", "Log in Success");
      }  else {
        //Navigate to sign up activity
        MainActivity.navigateActivity(activity, SignUpActivity.class);
        Log.e("error", "this phone number is not signed up");
      }
    }
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_log_in);
    MainActivity.getPermission(this, Manifest.permission.READ_PHONE_STATE);
    try {
      logIn();
    } catch (Exception e) {
      Log.e("error", "fail to log in");
    }
  }

  private String makeAccoundJsonData() throws Exception{
    String phoneNumber = MainActivity.getPhoneNumber(this);
    JSONObject json = new JSONObject();
    json.put("client", "android");
    json.put("action", "logIn");
    json.put("phoneNumber", phoneNumber);
    return json.toString();
  }
  private void logIn() throws Exception{
    try {
      SocketConnector connector = new SocketConnector();
      Socket socket = connector.getSocket();
      String json = makeAccoundJsonData();
      ServerWriteThread writer = new ServerWriteThread(socket, json);
      writer.start();
      ServerReceiveThread receiver = new ServerReceiveThread(socket, new ReceiveThreadHandler());
      receiver.start();
    } catch (Exception e) {
      Log.e("error", "cannot send account data to server");
      throw new Exception();
    }
  }
}
