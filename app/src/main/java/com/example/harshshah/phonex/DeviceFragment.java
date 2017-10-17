package com.example.harshshah.phonex;



import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Harsh Shah on 22/09/2017.
 */

public class DeviceFragment extends Fragment {

    ListView listview_device;
    List<String> liststring ;
    ArrayAdapter<String> adapter;
    static final int PERMISSION_READ_STATE = 123;

    public DeviceFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_device, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        onGetData(listview_device, view);


    }

    private void onGetData(View listview_device, View view) {
        int permissionCheck = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_PHONE_STATE);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            TelephonyManager(view);
        }
        else {
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.READ_PHONE_STATE},PERMISSION_READ_STATE);
        }
    }

    private void TelephonyManager(View view) {

        listview_device = (ListView) view.findViewById(R.id.listview_device);
        liststring = new ArrayList<String>();
        HashMap<String,String> item_to_value = new HashMap<>();

        TelephonyManager Tmanager = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        int phoneType = Tmanager.getPhoneType();
        String strptype = "";
        switch (phoneType){
            case (TelephonyManager.PHONE_TYPE_CDMA): strptype= "CDMA";
                break;
            case (TelephonyManager.PHONE_TYPE_GSM): strptype= "GSM";
                break;
            case (TelephonyManager.PHONE_TYPE_NONE): strptype = "NONE";
                break;
        }

        item_to_value.put("Device Name", Build.MODEL);
        item_to_value.put("Device Manufacturer",Build.MANUFACTURER);
        item_to_value.put("SIM Type",strptype);
        item_to_value.put("Roaming",String.valueOf(Tmanager.isNetworkRoaming()).toUpperCase());
        item_to_value.put("IMEI Number",Tmanager.getDeviceId());
        item_to_value.put("Subscriber ID",Tmanager.getSubscriberId());
        item_to_value.put("SIM Serial Number",Tmanager.getSimSerialNumber());
        item_to_value.put("SIM Operator",Tmanager.getSimOperator());
        item_to_value.put("SIM Operator Name",Tmanager.getSimOperatorName());
        item_to_value.put("SIM State",String.valueOf(Tmanager.getSimState()));
        item_to_value.put("Network Country ISO",Tmanager.getNetworkCountryIso());
        item_to_value.put("Network Operator",Tmanager.getNetworkOperator());
        item_to_value.put("Network Operator Name",Tmanager.getNetworkOperatorName());
        item_to_value.put("SIM Country ISO",Tmanager.getSimCountryIso());
      //  item_to_value.put("Software Version",Tmanager.getDeviceSoftwareVersion());
        item_to_value.put("Voice Mail Number",Tmanager.getVoiceMailNumber());;

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

        listview_device.setAdapter(adapter);
    }

}
