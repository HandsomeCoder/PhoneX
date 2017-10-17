package com.example.harshshah.phonex;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
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

public class MobileFragment extends Fragment {

    ListView listview_mobile;
    TextView textView;
    List<String> liststring ;
    private ArrayList results = new ArrayList();

    public MobileFragment(){

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mobile, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        onGetData(listview_mobile, view);

    }

    private void onGetData(View listview, View view) {

        listview_mobile = (ListView) view.findViewById(R.id.listview_mobile);
        PackageManager pm = getActivity().getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN,null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> list = pm.queryIntentActivities(intent, 0);
        for (ResolveInfo rinfo : list){
            results.add(rinfo.activityInfo.applicationInfo.loadLabel(pm).toString());
        }
        listview_mobile.setAdapter(new ArrayAdapter(getActivity(),android.R.layout.simple_expandable_list_item_1,results));
        String info = " There are " + list.size() + " Apps in this device";
        TextView textView = (TextView) view.findViewById(R.id.textView3);
        textView.setText(info);

    }
}

