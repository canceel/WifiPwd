package com.shenme.wifiinfo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.shenme.wifiinfo.adapter.WifiInfoAdapter;
import com.shenme.wifiinfo.data.WifiInfo;
import com.shenme.wifiinfo.manager.WifiManage;
import com.shenme.wifiinfo.widget.WifiDialogFragment;

import java.util.List;

public class MainActivity extends AppCompatActivity implements WifiInfoAdapter.WifiInterFace {
    private List<WifiInfo> wifiInfos;
    private ListView listView;
    private WifiInfoAdapter adapter;
    private Activity mActivity;
    private FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mActivity = this;
        WifiManage wifiManage = new WifiManage();
        try {
            wifiInfos = wifiManage.Read();
        } catch (Exception e) {
            e.printStackTrace();
        }
        listView = (ListView) findViewById(R.id.listView);
        adapter = new WifiInfoAdapter(wifiInfos, mActivity, this);
        listView.setAdapter(adapter);

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
    }

    @Override
    public void showWifiInfo(WifiInfo wifiInfo) {
    }

    @Override
    public void showMenu(WifiInfo wifiInfo) {
        Fragment fragment = fragmentManager.findFragmentByTag("dialogFragment");
        if (fragment != null) {
            fragmentTransaction.remove(fragment);
        }
        WifiDialogFragment dialogFragment = new WifiDialogFragment(wifiInfo);
        dialogFragment.show(fragmentManager, "dialogFragment");//显示一个Fragment并且给该Fragment添加一个Tag，可通过findFragmentByTag找到该Fragment

    }
}
