package com.shenme.wifiinfo.widget;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shenme.wifiinfo.R;
import com.shenme.wifiinfo.data.WifiInfo;

/**
 * Created by CANC on 2017/9/14.
 */

public class WifiDialogFragment extends DialogFragment {
    private WifiInfo wifiInfo;

    public WifiDialogFragment(WifiInfo wifiInfo) {
        this.wifiInfo = wifiInfo;
    }

    private Activity mActivity;
    private Window window;
    private View view;
    private TextView tvWifiName;
    private LinearLayout llCopy;
    private LinearLayout llCopyPwd;
    private LinearLayout llShare;
    private LinearLayout llSharePwd;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mActivity = getActivity();
        window = getDialog().getWindow();
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        //放置位置
        getDialog().getWindow().setGravity(Gravity.BOTTOM);
        view = LayoutInflater.from(mActivity).inflate(R.layout.dialog_fragment_wifi, null);

        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setWindowAnimations(R.style.anim_popup_bottombar);
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        initData();
        return view;
    }

    private void initData() {
        tvWifiName = view.findViewById(R.id.tv_wifi_name);
        llCopy = view.findViewById(R.id.ll_copy);
        llCopyPwd = view.findViewById(R.id.ll_copy_pwd);
        llShare = view.findViewById(R.id.ll_share);
        llSharePwd = view.findViewById(R.id.ll_share_pwd);

        tvWifiName.setText(wifiInfo.Ssid);
        llCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String wifiStr = "名称:" + wifiInfo.Ssid + "\n密码:" + wifiInfo.Password;
                //获取剪贴板管理器：
                ClipboardManager cm = (ClipboardManager) mActivity.getSystemService(Context.CLIPBOARD_SERVICE);
                // 创建普通字符型ClipData
                ClipData mClipData = ClipData.newPlainText("Label", wifiStr);
                // 将ClipData内容放到系统剪贴板里。
                cm.setPrimaryClip(mClipData);
                showMsg("复制" + wifiInfo.Ssid + "信息成功");
                dismiss();
            }
        });

        llCopyPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String wifiStr = wifiInfo.Password;
                //获取剪贴板管理器：
                ClipboardManager cm = (ClipboardManager) mActivity.getSystemService(Context.CLIPBOARD_SERVICE);
                // 创建普通字符型ClipData
                ClipData mClipData = ClipData.newPlainText("Label", wifiStr);
                // 将ClipData内容放到系统剪贴板里。
                cm.setPrimaryClip(mClipData);
                showMsg("复制" + wifiInfo.Ssid + "密码成功");
                dismiss();
            }
        });

        llShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String wifiStr = "名称:" + wifiInfo.Ssid + "\n密码:" + wifiInfo.Password;
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, wifiStr);
                startActivity(Intent.createChooser(intent, "分享" + wifiInfo.Ssid + "Wifi信息"));
                dismiss();
            }
        });

        llSharePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String wifiStr = wifiInfo.Password;
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, wifiStr);
                startActivity(Intent.createChooser(intent, "分享" + wifiInfo.Ssid + "Wifi密码"));
                dismiss();
            }
        });
    }

    private void showMsg(String msg) {
        Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show();
    }
}
