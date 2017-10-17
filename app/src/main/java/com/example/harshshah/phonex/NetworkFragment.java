package com.example.harshshah.phonex;

import android.app.usage.NetworkStatsManager;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.text.format.Formatter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Harsh Shah on 22/09/2017.
 */

public class NetworkFragment extends Fragment {

    ListView listview_network;
    List<String> liststring ;

    public NetworkFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_network, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        onGetData(listview_network, view);

    }

    public void onGetData(View view, View v){

        listview_network = (ListView) v.findViewById(R.id.ListNetwork);
        liststring = new ArrayList<String>();
        HashMap<String,String> item_to_value = new HashMap<>();

        WifiManager mainWifi;
        mainWifi=(WifiManager)getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo winfo = mainWifi.getConnectionInfo();
        String bssd = winfo.getBSSID();
        int rssi = winfo.getRssi();
        int linkspeed = winfo.getLinkSpeed();
        String ssid = winfo.getSSID();
        int network_id = winfo.getNetworkId();
        float frequency = winfo.getFrequency();
        String macAdd = winfo.getMacAddress();
        String ipAddress= Formatter.formatIpAddress(mainWifi.getConnectionInfo().getIpAddress());

        String BSSD = (bssd == null) ? "BSSD not Found" : bssd;
        String SSID = (ssid == "<unknown ssid>") ? "SSID not Found" : ssid;
        String RSSI = (rssi <= 0) ? "RSSI not Found" : String.valueOf(rssi);
        String NETID = (network_id <= 0) ? "NETWORK ID not Found" : String.valueOf(network_id);
        String LINKSPEED = (linkspeed <= 0) ? "LINKSPEED not Found" : String.valueOf(linkspeed);
        String FREQ = (frequency <= 0.0) ? "FREQUENCY not Found" : String.valueOf(frequency);
        String IP = (ipAddress == null) ? "IP ADDRESS not Found" : ipAddress;
        String MAC = (macAdd == null) ? "MAC ADDRESS not Found" : macAdd;


        item_to_value.put("BSSID",BSSD);
        item_to_value.put("SSID (WIFI Name)",SSID);
        item_to_value.put("RSSI",RSSI);
        item_to_value.put("Network ID",NETID);
        item_to_value.put("Link Speed (Mbps)",LINKSPEED);
        item_to_value.put("Frequency (MHz)",FREQ);
        item_to_value.put("IP Address (IPv4)",IP);
        item_to_value.put("MAC Address",MAC);

        List<HashMap<String,String>> itemList = new ArrayList<>();
        SimpleAdapter adapter = new SimpleAdapter(getActivity(),itemList,
                R.layout.listview_style, new String[]{"F","L"},
                new int[]{R.id.textView,R.id.textView2});

        System.out.println(item_to_value);
        Iterator it = item_to_value.entrySet().iterator();
        while(it.hasNext()){
            HashMap<String, String> resultsMap = new HashMap<>();
            Map.Entry pair = (Map.Entry)it.next();
            resultsMap.put("F", pair.getKey().toString());
            String val = (pair.getValue() == null)? "Value not Found":pair.getValue().toString();
            resultsMap.put("L", val.toUpperCase());
            itemList.add(resultsMap);
        }

        listview_network.setAdapter(adapter);

    }

}
