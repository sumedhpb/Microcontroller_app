package com.example.sumedh.microcontroller;

/**
 * Created by Sumedh on 7/4/2017.
 */

        import android.util.Log;
        import android.widget.TextView;
        import java.io.BufferedReader;
        import java.io.IOException;
        import java.io.InputStreamReader;
        import java.net.ServerSocket;
        import java.net.Socket;
        import java.net.SocketException;



public class server {
    public static String str = "";
    static Main3Activity activity;
    TextView msgg;

    public server(Main3Activity activity,TextView msge) {
        this.activity = activity;
        this.msgg=msge;
        //this.str=str;
        Thread server_socket_thread=new Thread(new server_socket());
        server_socket_thread.start();
    }

    private class server_socket extends Thread {
        @Override
        public void run() {
            try {
                ServerSocket ss = new ServerSocket(8080);
                while (true) {
                    Socket s = ss.accept();
                    final String message=new BufferedReader(new InputStreamReader(s.getInputStream())).readLine();
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            msgg.setText(msgg.getText()+"\n"+message);

                        }
                    });
                    ss.close();
                }

            } catch(SocketException e){
                Log.e("SocketException",e.toString());
                //msgg.setText("Data Not sent");
            } catch (IOException e) {
                //Toast.makeText(getApplicationContext(),"Nothing to receive",Toast.LENGTH_LONG).show();
                Log.e("IOException", e.toString());
                //msgg.setText("Data Not sent");

            } catch (NullPointerException e) {
                Log.e("NullpointerException", e.toString());
                //msgg.setText("Data not sent");

            }

        }
    }

}


