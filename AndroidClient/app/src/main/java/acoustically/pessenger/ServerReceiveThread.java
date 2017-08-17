package acoustically.pessenger;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by acous on 2017-08-15.
 */

public class ServerReceiveThread extends Thread {
  Socket mSocket;
  BufferedReader mReceiver;
  Handler mHandler;

  public ServerReceiveThread(Socket socket, Handler handler) throws Exception{
    this.mSocket = socket;
    this.mReceiver = new BufferedReader(new InputStreamReader(mSocket.getInputStream(), "ASCII"));
    this.mHandler = handler;
  }

  @Override
  public void run() {
    super.run();
    Message message = Message.obtain();
    try {
      Log.e("error", "start to receiving data");
      String data = mReceiver.readLine();
      Log.e("success", "result : " + data);
      message.arg1 = Integer.parseInt(data);
    } catch (Exception e) {
      message.arg1 = 2;
      Log.e("error", "fail to read data from socket");
    } finally {
      mHandler.sendMessage(message);
    }
  }
}
