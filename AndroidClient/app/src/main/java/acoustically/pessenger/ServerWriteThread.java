package acoustically.pessenger;


import android.util.Log;

import java.io.DataOutputStream;
import java.net.Socket;

/**
 * Created by acous on 2017-08-08.
 */

public class ServerWriteThread extends Thread {
  String mServerIp;
  int mServerPort;
  String mJsonData;
  Socket mSocket;
  DataOutputStream mSocketWriter;

  public ServerWriteThread(String jsonData) {
    this.mServerIp = "52.79.104.209";
    this.mServerPort = 8100;
    this.mJsonData = jsonData;
  }

  @Override
  public void run() {
    super.run();
    int count = 0;
    while(!setSocket() && count < 10) {
      count ++;
    }
    if(count >= 10) {
      Log.e("error", "error what create socket");
      return;
    }
    if(mSocket.isConnected()) {
      Log.e("success", "Success to Creating socket");
      try {
        mSocketWriter.writeUTF(mJsonData);
        Log.e("success", "Success to Writting to socket");
      } catch (Exception e) {
        Log.e("error", "Fail to writting to socket");
      }
    }
  }
  private boolean setSocket() {
    try {
      Log.e("error", "try to socket connect");
      mSocket = new Socket(mServerIp, mServerPort);
      mSocketWriter = new DataOutputStream(mSocket.getOutputStream());
    } catch (Exception e) {
      Log.e("error", "fail to creating socket");
      return false;
    }
    return true;
  }
}
