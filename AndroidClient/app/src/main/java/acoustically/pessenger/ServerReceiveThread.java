package acoustically.pessenger;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Handler;

/**
 * Created by acous on 2017-08-15.
 */

public class ServerReceiveThread extends Thread {
  Socket mSocket;
  BufferedReader mReceiver;
  Handler mHandler;

  public ServerReceiveThread(Socket mSocket) throws Exception{
    this.mSocket = mSocket;
    this.mReceiver = new BufferedReader(new InputStreamReader(mSocket.getInputStream(), "ASCII"));
    //this.mHandler = handler;
  }

  @Override
  public void run() {
    super.run();
    try {
      Log.e("error", "receive thread start");
      String data = mReceiver.readLine();
      Log.e("success", "thread end : " + data);
    } catch (Exception e) {
      Log.e("error", "fail to read data from socket");
    }
  }
}
