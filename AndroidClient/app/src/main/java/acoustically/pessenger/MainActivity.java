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
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    getPermission(Manifest.permission.RECEIVE_SMS);
    getPermission(Manifest.permission.READ_PHONE_STATE);
    startService(new Intent(this, ReceiveSmsService.class));
  }
  private void getPermission(String permission) {
    int receiveSmsPermission =
      ContextCompat.checkSelfPermission(this, permission);
    if(receiveSmsPermission == PackageManager.PERMISSION_DENIED) {
      ActivityCompat.requestPermissions(this, new String[]{permission}, 0);
    }
  }
}
