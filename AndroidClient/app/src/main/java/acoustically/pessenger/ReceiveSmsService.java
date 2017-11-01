package acoustically.pessenger;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.SmsMessage;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import AndroidHttpRequest.HttpRequestorBuilder;
import AndroidHttpRequest.HttpResponseListener;
import acoustically.pessenger.tools.Environment;

public class ReceiveSmsService extends Service {
  Context context = this;

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
          try {
            JSONObject json = buildJson(getSmsMessage(intent), Environment.getPhoneNumber(context));
            sendSmsMessageToServer(json);
          } catch (Exception e) {
            Log.e("Error", e.getMessage(), e.fillInStackTrace());
          }
        }
      }
    }, intentFilter);
  }
  private void sendSmsMessageToServer(JSONObject json) throws JSONException {
    HttpRequestorBuilder builder = new HttpRequestorBuilder(Environment.getUrl("/sms-message/new"));
    builder.build().post(json, new HttpResponseListener() {
      @Override
      protected void httpResponse(String data) {

      }

      @Override
      protected void httpExcepted(Exception e) {
        Log.e("Error", e.getMessage(), e.fillInStackTrace());
      }
    });
  }
  private JSONObject buildJson(SmsMessage smsMessage, String phoneNumber) throws Exception{
    JSONObject json = new JSONObject();
    json.put("phone_number", phoneNumber);
    json.put("from_", smsMessage.getOriginatingAddress());
    json.put("body", smsMessage.getMessageBody());
    return json;
  }

  private SmsMessage getSmsMessage(Intent intent) {
    Bundle bundle = intent.getExtras();
    Object messages[] = (Object[])bundle.get("pdus");
    SmsMessage smsMessage[] = new SmsMessage[messages.length];
    for(int i = 0; i < messages.length; i++) {
      smsMessage[i] = SmsMessage.createFromPdu((byte[])messages[i]);
    }
    return smsMessage[0];
  }
}
