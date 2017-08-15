package acoustically.pessenger;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.IOException;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    getPermission(Manifest.permission.RECEIVE_SMS);
    getPermission(Manifest.permission.READ_PHONE_STATE);
    try {
      if(userValidation()) {
        startService(new Intent(this, ReceiveSmsService.class));
      } else {

      }
    } catch (Exception e) {

    }
  }
  private void getPermission(String permission) {
    int receiveSmsPermission =
      ContextCompat.checkSelfPermission(this, permission);
    if(receiveSmsPermission == PackageManager.PERMISSION_DENIED) {
      ActivityCompat.requestPermissions(this, new String[]{permission}, 0);
    }
  }
  private String makeAccoundJsonData() throws Exception{
    String phoneNumber = ((TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE)).getLine1Number();
    JSONObject json = new JSONObject();
    json.put("client", "android");
    json.put("action", "userValidation");
    json.put("myPhoneNumber", phoneNumber);
    return json.toString();
  }
  private boolean userValidation() throws Exception{
    try {
      SocketConnector connector = new SocketConnector();
      Socket socket = connector.getSocket();
      String json = makeAccoundJsonData();
      ServerWriteThread writer = new ServerWriteThread(socket, json);
      writer.start();
      ServerReceiveThread receiver = new ServerReceiveThread(socket);
      receiver.start();
      return true;
    } catch (Exception e) {
      Log.e("error", "userValidation: cannot send data to server");
      throw new Exception();
    }
  }
}
