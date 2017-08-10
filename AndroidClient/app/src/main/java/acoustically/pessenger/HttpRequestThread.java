package acoustically.pessenger;


import android.telephony.SmsMessage;

/**
 * Created by acous on 2017-08-08.
 */

public class HttpRequestThread extends Thread {
  String mHttpServerUrl;
  String mMethod;
  SmsMessage mSmsMessage;

  public HttpRequestThread(String mHttpServerUrl, SmsMessage mSmsMessage) {
    this.mHttpServerUrl = mHttpServerUrl;
    this.mSmsMessage = mSmsMessage;
  }

  @Override
  public void run() {
    super.run();
  }
}
