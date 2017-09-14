package com.shenme.wifiinfo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shenme.wifiinfo.R;
import com.shenme.wifiinfo.data.WifiInfo;

import java.util.List;

/**
 * Created by CANC on 2017/9/13.
 */

public class WifiInfoAdapter extends BaseAdapter {
    private List<WifiInfo> wifiInfos;
    private Context mContext;
    private WifiInterFace lisenter;

    public WifiInfoAdapter(List<WifiInfo> wifiInfos, Context context, WifiInterFace lisenter) {
        this.wifiInfos = wifiInfos;
        this.mContext = context;
        this.lisenter = lisenter;
    }

    @Override
    public int getCount() {
        return wifiInfos == null ? 0 : wifiInfos.size();
    }

    @Override
    public WifiInfo getItem(int i) {
        return wifiInfos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_wifi, null);
            convertView.setBackgroundResource(R.drawable.recycler_bg);
            holder.llWifi = convertView.findViewById(R.id.rl_wifi);
            holder.wifiName = convertView.findViewById(R.id.wifi_name);
            holder.wifiInfo = convertView.findViewById(R.id.wifi_info);
            holder.ivMore = convertView.findViewById(R.id.iv_more);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final WifiInfo wifi = getItem(position);
        holder.wifiName.setText(wifi.Ssid);
        holder.wifiInfo.setText(wifi.Password);

        holder.llWifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lisenter.showMenu(wifi);
            }
        });

        holder.llWifi.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                lisenter.showMenu(wifi);
                return false;
            }
        });

        holder.ivMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lisenter.showMenu(wifi);
            }
        });
        return convertView;
    }

    class ViewHolder {
        RelativeLayout llWifi;
        TextView wifiName;
        TextView wifiInfo;
        ImageView ivMore;
    }

    public interface WifiInterFace {
        void showWifiInfo(WifiInfo wifiInfo);

        void showMenu(WifiInfo wifiInfo);
    }
}
