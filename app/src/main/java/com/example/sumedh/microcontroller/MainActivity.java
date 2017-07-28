package com.example.sumedh.microcontroller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Handler;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.net.wifi.p2p.*;
import android.net.wifi.p2p.WifiP2pDevice;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

//MainActivity
public class MainActivity extends AppCompatActivity {
    private final IntentFilter mintentFilter = new IntentFilter();
    public static WifiP2pManager mManager;
    public static Channel mchan;
    public MyReceiver mreceiver;
    private int count = 0;
    public static WifiP2pInfo info;
    public static String address;
    private WifiP2pDeviceList LIST;
    public static int flag = 0;
    public static WifiP2pDevice dev;
    TextView message;
    EditText e2;
    WifiP2pDeviceList pl;
    Runnable runnable;
    Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        mchan = mManager.initialize(this, getMainLooper(), null);
        mreceiver = new MyReceiver(mManager, mchan, this);
        mintentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        mintentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        mintentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        mintentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);


    }
    public void connectDevices(View v) {
        e2 = (EditText) findViewById(R.id.editText);
        String choice = e2.getText().toString();
        int j = 1;
        /*if ((choice != "") && (pl.getDeviceList().size()>0) &&(dev==null)) {
            for (WifiP2pDevice peer : pl.getDeviceList()) {
                if (Integer.valueOf(choice) == j) {
                    dev = peer;
                }
                j=j+1;
            }
            WifiP2pConfig config = new WifiP2pConfig();
            config.deviceAddress = dev.deviceAddress;
            mManager.connect(mchan, config, new ActionListener() {
                @Override
                public void onSuccess() {
                    Toast.makeText(getApplicationContext(), "Connection successfull", Toast.LENGTH_LONG).show();
                    flag=1;
                    message = (TextView) findViewById(R.id.textView2);

                    mManager.requestConnectionInfo(mchan, new WifiP2pManager.ConnectionInfoListener() {
                        @Override
                        public void onConnectionInfoAvailable(WifiP2pInfo infor) {
                            TextView group=(TextView) findViewById(R.id.textView4);
                            //info.groupOwnerAddress.getAddress();

                            info=infor;
                            group.setText("Group Ownder/Server?:"+info.isGroupOwner);
                            e2.setText("Enter message");
                            address = getIpAddress();
                            message.setText(address);
                            //flag=1;

                        }
                    });

                }

                @Override
                public void onFailure(int reason) {
                    Toast.makeText(getApplicationContext(), "Connection unsuccessfull", Toast.LENGTH_LONG).show();

                }
            });

        }
        else{*/
        message = (TextView) findViewById(R.id.textView2);
        address = getIpAddress();
        if(address!=" ") {
            message.setText(address);
            e2.setText(address);
        }
        //}

    }
    public String getIpAddress() {
        String ip = " ";
        try {
            Enumeration<NetworkInterface> enumNetworkInterfaces = NetworkInterface
                    .getNetworkInterfaces();
            while (enumNetworkInterfaces.hasMoreElements()){
                NetworkInterface networkInterface = enumNetworkInterfaces
                        .nextElement();
                Enumeration<InetAddress> enumInetAddress = networkInterface
                        .getInetAddresses();
                while (enumInetAddress.hasMoreElements()) {
                    InetAddress inetAddress = enumInetAddress.nextElement();
                    if (inetAddress.isSiteLocalAddress()) {
                        ip =inetAddress.getHostAddress();
                    }
                    /*else{
                        ip="hello";
                    }*/
                }
            }

        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            ip += "Something Wrong! " + e.toString() + "\n";
        }
        return ip;
    }
    public void ClientSocket(View v) throws IOException {
        count += 1;
        Button b = (Button) findViewById(R.id.button2);
        if ((count %2)==1) {
            b.setText("Stop sending");
            message=(TextView) findViewById(R.id.textView2);
            e2 = (EditText) findViewById(R.id.editText);
            runnable = new Runnable() {
                @Override
                public void run() {

                    client myClient = null;
                    try {
                        EditText ip = (EditText) findViewById(R.id.editText3);
                        myClient = new client(ip.getText().toString(), 8080, message, e2.getText().toString());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    myClient.execute();

                    //message.setText("Data Sent");
                     /*else {
                        message.setText("Cannot send data");
                    }*/
                    handler.postDelayed(this, 1000);
                }
            };
            runnable.run();
        } else {
            handler.removeCallbacks(runnable);
            b.setText("Send data");
            message.setText("Data sending process completed");
        }
    }
    /*public void stop(View v){
        handler.removeCallbacks(runnable);
        Button b=(Button) findViewById(R.id.button5);
        b.setText("Send data");
    }*/



    @Override
    public void onResume() {
        super.onResume();
        registerReceiver(mreceiver, mintentFilter);
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(mreceiver);
    }



    public void displayPeers(WifiP2pDeviceList PEERLIST) {
        pl=PEERLIST;
        TextView e1 = (TextView) findViewById(R.id.textView);
        e1.setText("");
        int i = 1;
        for (WifiP2pDevice peer : PEERLIST.getDeviceList()) {
            e1.setText(e1.getText()+"\n"+i+peer.deviceName);
            //count=count+1;
            i = i + 1;
        }

    }
    /*public void Clear(View v){

        //TextView  response=(TextView) findViewById(R.id.textView);
        response.setText("");
    }*/
    public void receive(View v) throws IOException {
        /*server serv=new server();
        serv.execute();*/
        Intent i = new Intent(this, Main3Activity.class);
        startActivity(i);

    }
}






