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
  String mXmlData;
  Socket mSocket;
  BufferedWriter mSocketWriter;

  public ServerWriteThread(String mServerIp, String xmlData) {
    this.mServerIp = mServerIp;
    this.mXmlData = xmlData;
  }

  @Override
  public void run() {
    super.run();
    int count = 0;
    while(!mSocket.isConnected() || count < 10) {
      count ++;
      setSocket();
    }
    if(count >= 10) {
      Log.e("error", "Fail to socket create");
    } else if(mSocket.isConnected()) {
      try {
        mSocketWriter.write(mXmlData);
      } catch (Exception e) {
        Log.e("error", "Fail to write to socket");
      }
    } else {

    }
  }
  private boolean setSocket() {
    try {
      mSocket = new Socket(mServerIp, 14000);
      mSocketWriter = new BufferedWriter(new OutputStreamWriter(mSocket.getOutputStream()));
    } catch (Exception e) {
      return false;
    }
    return true;
  }
}
