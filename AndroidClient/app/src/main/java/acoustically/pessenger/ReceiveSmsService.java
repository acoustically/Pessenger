package acoustically.pessenger;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class ReceiveSmsService extends Service {
  String mServerUrl = "http://localhost:80";
  public ReceiveSmsService() {

  }

  @Override
  public IBinder onBind(Intent intent) {
    // TODO: Return the communication channel to the service.
    throw new UnsupportedOperationException("Not yet implemented");
  }

  @Override
  public void onCreate() {
    super.onCreate();
    setBroadcastReceiver();
  }
  private void setBroadcastReceiver() {
    IntentFilter intentFilter = new IntentFilter();
    intentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");

    registerReceiver(new BroadcastReceiver() {
      @Override
      public void onReceive(Context context, Intent intent) {
        if ("android.provider.Telephony.SMS_RECEIVED".equals(intent.getAction())) {
          Bundle bundle = intent.getExtras();
          Object messages[] = (Object[])bundle.get("pdus");
          SmsMessage smsMessage[] = new SmsMessage[messages.length];

          for(int i = 0; i < messages.length; i++) {
            smsMessage[i] = SmsMessage.createFromPdu((byte[])messages[i]);
          }
          sendSmsMessageToServer(smsMessage[0]);
          Toast.makeText(context, smsMessage[0].getMessageBody().toString(), Toast.LENGTH_LONG).show();
        }
      }
    }, intentFilter);
  }
  private void sendSmsMessageToServer(SmsMessage smsMessage) {
    HttpRequestThread httpRequestThread = new HttpRequestThread(mServerUrl, smsMessage);
    httpRequestThread.start();
  }
}
