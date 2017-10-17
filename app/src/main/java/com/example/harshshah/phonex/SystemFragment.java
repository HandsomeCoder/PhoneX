package com.example.harshshah.phonex;

import android.app.ActivityManager;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.app.ActivityManager.RunningAppProcessInfo;



/**
 * Created by Harsh Shah on 22/09/2017.
 */

public class SystemFragment extends Fragment {

    ListView listview_system;
    List<String> liststring ;
    ProcessBuilder processBuilder;
    String Holder = "";
    String[] DATA = {"/system/bin/cat", "/proc/cpuinfo"};
    InputStream inputStream;
    Process process ;
    byte[] byteArry ;

    public SystemFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_system, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        onGetData(listview_system, view);

    }


    private void onGetData(View listview, View view) {

        listview_system = (ListView) view.findViewById(R.id.listview_system);
        liststring = new ArrayList<String>();
        HashMap<String,String> item_to_value = new HashMap<>();

        TelephonyManager tel = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        String networkOperator = tel.getNetworkOperator();

        item_to_value.put("Device Name", Build.MODEL);
        item_to_value.put("Device Manufacturer",Build.MANUFACTURER);
        item_to_value.put("OS Version",Build.VERSION.RELEASE);
        item_to_value.put("Boot Loader",Build.BOOTLOADER);
        item_to_value.put("Build ID",Build.ID);
        item_to_value.put("Finger print",Build.FINGERPRINT);
        item_to_value.put("Product",Build.PRODUCT);
        System.out.println("out ---------------------> "+networkOperator.length());
        if(networkOperator.length() == 0){
            item_to_value.put("Network Operator", "No network available");
            item_to_value.put("Mobile Country Code", "No network available");
            item_to_value.put("Mobile Network Code", "No network available");
        }
        else {
            item_to_value.put("Network Operator", networkOperator);
            item_to_value.put("Mobile Country Code", networkOperator.substring(0, 3));
            item_to_value.put("Mobile Network Code", networkOperator.substring(3));
        }
        HashMap<String, String> x = getCPUInfo();
        item_to_value.putAll(x);

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
        listview_system.setAdapter(adapter);
    }

    public HashMap<String, String> getCPUInfo () {
        HashMap<String, String> output = new HashMap<> ();
        try {
            BufferedReader br = new BufferedReader (new FileReader("/proc/cpuinfo"));
            String str;
            while ((str = br.readLine ()) != null) {
                String[] data = str.split (":");
                if (data.length > 1) {
                    String key = data[0].trim ().replace (" ", "_");
                    if (key.equals ("model_name")) key = "cpu_model";
                    String value = data[1].trim ();
                    if (key.equals ("cpu_model"))
                        value = value.replaceAll ("\\s+", " ");
                    output.put (key, value);
                }
            }
            br.close ();
        } catch (IOException e) {
            e.printStackTrace ();
        }
        return output;
    }
}
