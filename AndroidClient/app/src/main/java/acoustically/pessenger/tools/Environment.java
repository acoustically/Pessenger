package acoustically.pessenger.tools;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;

/**
 * Created by acoustically on 17. 10. 26.
 */

public class Environment {
  final static public String serverUrl = "http://52.79.104.209:5000";
  static public String getUrl(String filename) {
    return serverUrl + filename;
  }
  public static String getPhoneNumber(Context context) {
    TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
    return telephonyManager.getLine1Number();
  }
  public static void setPermission(Activity activity, String permission) {
    int receiveSmsPermission =
      ContextCompat.checkSelfPermission(activity, permission);
    if(receiveSmsPermission == PackageManager.PERMISSION_DENIED) {
      ActivityCompat.requestPermissions(activity, new String[]{permission}, 0);
    }
  }
}
