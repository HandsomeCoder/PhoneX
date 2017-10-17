package com.example.harshshah.phonex;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    public Fragment cur_fragment;
    TextView current_title;


    MobileFragment mobileFragment;
    NetworkFragment networkFragment;
    SystemFragment systemFragment;
    SensorFragment sensorFragment;
    DeviceFragment deviceragment;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        System.out.println();
        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener()
                {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item)
                    {
                        Fragment selectedFragment = null;
                        switch (item.getItemId())
                        {
                            case R.id.menu_mobile:
                                if(mobileFragment==null) mobileFragment = new MobileFragment();
                                selectedFragment = mobileFragment;
                                break;
                            case R.id.menu_network:
                                if(networkFragment==null) networkFragment = new NetworkFragment();
                                selectedFragment = networkFragment;
                                break;
                            case R.id.menu_system:
                                if(systemFragment==null) systemFragment = new SystemFragment();
                                selectedFragment = systemFragment;
                                break;
                            case R.id.menu_sensor:
                                if(sensorFragment==null) sensorFragment = new SensorFragment();
                                selectedFragment = sensorFragment;
                                break;
                            case R.id.menu_device:
                                if(deviceragment==null) deviceragment = new DeviceFragment();
                                selectedFragment = deviceragment;
                                break;
                        }
                        setFragment(selectedFragment);
                        return true;
                    }
                });

        setFragment(new DeviceFragment());

    }

    @Override
    public void setTitle(CharSequence title)
    {
        super.setTitle(title);
        current_title.setText(title);
    }

    public void setFragment(Fragment f)
    {
        getSupportFragmentManager().popBackStack();
        /*if(cur_fragment!=null)
        cur_fragment.onDestroy();*/
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();


        if(f!=null)
        {
            transaction.replace(R.id.frame_layout, f);
            transaction.commit();
            cur_fragment = f;
        }



    }
    @Override
    public void onBackPressed()
    {/*
        if(cur_fragment instanceof UserFragmentDetail)
        {
            setFragment(new UserFragmentEvent());
        }
        else if(cur_fragment instanceof UserFragmentEvent)
        {
            if(userFragment==null) userFragment = new UserFragment();
            setFragment(userFragment);
        }
        else if(cur_fragment instanceof EventsIndividualFragment)
        {
            setFragment(new EventsListViewDepartmentFragment());
        }
        else if(cur_fragment instanceof EventsListViewDepartmentFragment)
        {
            if(eventsFragment==null) eventsFragment = new EventsFragment();
            setFragment(eventsFragment);
        }
        else
        {
            super.onBackPressed();
        }*/
    }
}
