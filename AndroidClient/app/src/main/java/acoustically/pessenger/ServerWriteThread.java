package acoustically.pessenger;


import android.util.Log;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by acous on 2017-08-08.
 */

public class ServerWriteThread extends Thread {
  String mJsonData;
  Socket mSocket;
  DataOutputStream mSocketWriter;

  public ServerWriteThread(Socket socket, String jsonData) throws IOException{
    this.mSocket = socket;
    mSocketWriter = new DataOutputStream(mSocket.getOutputStream());
    this.mJsonData = jsonData;
  }

  @Override
  public void run() {
    super.run();
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
}
