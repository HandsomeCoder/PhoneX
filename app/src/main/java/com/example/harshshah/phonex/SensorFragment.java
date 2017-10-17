package com.example.harshshah.phonex;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Harsh Shah on 22/09/2017.
 */

public class SensorFragment extends Fragment {

    ListView listView ;
    SensorManager sensorManager ;
    List<Sensor> listsensor;
    List<String> liststring ;
    ArrayAdapter<String> adapter;

    public SensorFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sensor, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listView = (ListView) view.findViewById(R.id.listview_sensor);
        liststring = new ArrayList<String>();
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        listsensor = sensorManager.getSensorList(Sensor.TYPE_ALL);
        for(int i=0; i<listsensor.size(); i++){
            liststring.add(listsensor.get(i).getName());
        }
        adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_2,
                android.R.id.text1,
                liststring);

        listView.setAdapter(adapter);
        TextView textView = (TextView) view.findViewById(R.id.textView4);
        String info = "There are " + listsensor.size() + " sensors in this device";
        textView.setText(info);
    }
}
