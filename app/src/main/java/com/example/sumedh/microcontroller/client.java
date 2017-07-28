package com.example.sumedh.microcontroller;

/**
 * Created by Sumedh on 7/4/2017.
 */


        import android.os.AsyncTask;
        import android.widget.TextView;
        import java.io.BufferedWriter;
        import java.io.IOException;
        import java.io.OutputStreamWriter;
        import java.io.PrintWriter;
        import java.net.Socket;
        import java.net.UnknownHostException;


public class client extends AsyncTask<Void,Void,String> {
    //private final Context context;
    private String dev_address;
    private int port=0;
    TextView respon;
    String result;
    String str;
    public client(String info, int port, TextView response,String str) throws IOException {
        //this.context=context;
        this.dev_address=info;
        this.port=port;
        this.respon=response;
        this.str=str;
    }

    @Override
    protected String doInBackground(Void... params) {
        Socket socket = null;
        try {
            socket = new Socket(dev_address, 8080);
            PrintWriter dout=new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
            dout.println(str);
            result="Data sent";
            socket.close();
        } catch (UnknownHostException e) {
            result="UnknownHostException"+e.toString();
        }catch (IOException e){
            result=" Data not sent IOException"+e.toString();
        }
        return result;
    }
    @Override
    protected void onPostExecute(String response){
        respon.setText(result.toString());
        super.onPostExecute(response);
    }
}

