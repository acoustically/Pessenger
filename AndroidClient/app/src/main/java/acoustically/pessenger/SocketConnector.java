package acoustically.pessenger;

import android.util.Log;

import java.net.Socket;

/**
 * Created by acous on 2017-08-15.
 */

public class SocketConnector {
  public static Socket getSocket() throws Exception{
    int count = 0;
    while(count < 5) {
      try {
        return new Socket("52.79.104.209", 8100);
      } catch (Exception e) {
        count++;
        Log.e("error", "fail to open socket" + count);
      }
    }
    throw new Exception();
  }
}
