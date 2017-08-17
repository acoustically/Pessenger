package acoustically.pessenger;

import android.util.Log;

import java.net.Socket;

/**
 * Created by acous on 2017-08-15.
 */

public class SocketConnector {

  class ConnectThread extends Thread {
    private SocketConnector mConnector;

    public ConnectThread(SocketConnector mConnector) {
      this.mConnector = mConnector;
    }

    @Override
    public void run() {
      super.run();
      try {
        mConnector.mSocket = setSocket();
        Log.e("success", "socket is created");
      } catch (Exception e) {
        Log.e("error", "finally fail to open socket");
      }
    }

    private Socket setSocket() throws Exception {
      int count = 0;
      while (count < 5) {
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

  private Socket mSocket;

  public Socket getSocket() throws Exception{
    Thread thread = new ConnectThread(this);
    thread.start();
    thread.join();
    if(mSocket.isConnected()) {
      return mSocket;
    } else {
      throw new Exception();
    }
  }
}
