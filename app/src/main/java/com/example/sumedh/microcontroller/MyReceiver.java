package com.example.sumedh.microcontroller;

/**
 * Created by Sumedh on 7/4/2017.
 */




        import android.content.BroadcastReceiver;
        import android.content.Context;
        import android.content.Intent;
        import android.net.wifi.p2p.WifiP2pDevice;
        import android.net.wifi.p2p.WifiP2pDeviceList;
        import android.net.wifi.p2p.WifiP2pManager;
        import android.widget.Toast;
        import java.util.ArrayList;
        import java.util.List;



public class MyReceiver extends BroadcastReceiver {
    private WifiP2pManager mManager;
    private WifiP2pManager.Channel channel;
    private MainActivity activity;
    //private WifiP2pManager.PeerListListener plistlistener;
    private List<WifiP2pDevice> peers = new ArrayList<WifiP2pDevice>();

    public MyReceiver(WifiP2pManager manager, WifiP2pManager.Channel channel,
                      MainActivity activity) {
        super();
        this.mManager = manager;
        this.channel = channel;
        this.activity = activity;
        //this.plistlistener = plistlistener;
    }

    @Override
    public void onReceive(final Context context, Intent intent) {
        String action = intent.getAction();
        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
            // Determine if Wifi P2P mode is enabled or not, alert
            // the Activity.
            int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
            if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
                Toast.makeText(context, "Wifi Direct enabled", Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(context, "Wifi Direct disabled", Toast.LENGTH_LONG).show();
            }
        } else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {
            //Toast.makeText(context, "PEERS CHANGED", Toast.LENGTH_LONG).show();
            //if (mManager != null) {
            final WifiP2pManager.PeerListListener plistlistener = new WifiP2pManager.PeerListListener() {

                @Override
                public void onPeersAvailable(WifiP2pDeviceList peerList) {
                    //MainActivity.flag=0;
                    peers.clear();
                    peers.addAll(peerList.getDeviceList());
                    activity.displayPeers(peerList);

                    //ArrayList<WifiP2pDevice> newpeers = (ArrayList<WifiP2pDevice>) peerList.getDeviceList();
                    //activity.displayPeers(peerList);
                }
            };
            mManager.requestPeers(channel, plistlistener);
            //}
        }else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {
            //Toast.makeText(context, "CONNECTED CHANGED", Toast.LENGTH_LONG).show();
        } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {
            //DeviceListFragment fragment = (DeviceListFragment) activity.getFragmentManager().findFragmentById(R.id.frag_list);
            //fragment.updateThisDevice((WifiP2pDevice) intent.getParcelableExtra(WifiP2pManager.EXTRA_WIFI_P2P_DEVICE));

        }
        mManager.discoverPeers(channel ,new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                //Toast.makeText(context, "Peer Discovery successfull", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int r) {
                //Toast.makeText(getApplicationContext(),"Peer Discovery Failed",Toast.LENGTH_LONG).show();
            }
        });


    }
}

