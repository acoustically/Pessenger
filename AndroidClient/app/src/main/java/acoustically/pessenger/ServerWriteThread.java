package acoustically.pessenger;


import android.util.Log;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * Created by acous on 2017-08-08.
 */

public class ServerWriteThread extends Thread {
  String mServerIp;
  int mServerPort;
  String mJsonData;
  Socket mSocket;
  BufferedWriter mSocketWriter;

  public ServerWriteThread(String jsonData) {
    this.mServerIp = "52.79.104.209";
    this.mServerPort = 8100;
    this.mJsonData = jsonData;
  }

  @Override
  public void run() {
    super.run();
    int count = 0;
    while(!setSocket() || count < 10) {
      count ++;
    }
    if(count >= 10) {
      Log.e("error", "Fail to socket create");
      return;
    } else if(mSocket.isConnected()) {
      try {
        mSocketWriter.write(mJsonData);
      } catch (Exception e) {
        Log.e("error", "Fail to write to socket");
      }
    } else {

    }
  }
  private boolean setSocket() {
    try {
      mSocket = new Socket(mServerIp, mServerPort);
      mSocketWriter = new BufferedWriter(new OutputStreamWriter(mSocket.getOutputStream(), "UTF8"));
    } catch (Exception e) {
      Log.e("error", "fail to create socket");
      return false;
    }
    return true;
  }
}
