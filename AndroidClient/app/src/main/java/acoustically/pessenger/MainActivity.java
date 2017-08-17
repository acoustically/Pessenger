package acoustically.pessenger;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getPermission(this, android.Manifest.permission.RECEIVE_SMS);
    setContentView(R.layout.activity_main);
    startActivity(new Intent(this, ReceiveSmsService.class));
  }
  public static void navigateActivity(Activity activity, Class<?> cls) {
    Intent intent = new Intent(activity, cls);
    activity.startActivity(intent);
    activity.finish();
  }
  public static void getPermission(Activity activity, String permission) {
    int receiveSmsPermission =
      ContextCompat.checkSelfPermission(activity, permission);
    if(receiveSmsPermission == PackageManager.PERMISSION_DENIED) {
      ActivityCompat.requestPermissions(activity, new String[]{permission}, 0);
    }
  }
  public static String getPhoneNumber(Context context) {
    TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
    return telephonyManager.getLine1Number();
  }
}
