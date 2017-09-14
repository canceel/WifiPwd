package com.shenme.wifiinfo.manager;

/**
 * Created by CANC on 2017/9/13.
 */

import com.shenme.wifiinfo.data.WifiInfo;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WifiManage {

    public List<WifiInfo> Read() throws Exception {
        List<WifiInfo> wifiInfos = new ArrayList<>();

        Process process = null;
        DataOutputStream dataOutputStream = null;
        DataInputStream dataInputStream = null;
        StringBuffer wifiConf = new StringBuffer();
        try {
            process = Runtime.getRuntime().exec("su");
            dataOutputStream = new DataOutputStream(process.getOutputStream());
            dataInputStream = new DataInputStream(process.getInputStream());
            dataOutputStream
                    .writeBytes("cat /data/misc/wifi/wpa_supplicant.conf\n");
            dataOutputStream.writeBytes("exit\n");
            dataOutputStream.flush();
            InputStreamReader inputStreamReader = new InputStreamReader(
                    dataInputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(
                    inputStreamReader);
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                wifiConf.append(line);
            }
            bufferedReader.close();
            inputStreamReader.close();
            process.waitFor();
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                if (dataOutputStream != null) {
                    dataOutputStream.close();
                }
                if (dataInputStream != null) {
                    dataInputStream.close();
                }
                process.destroy();
            } catch (Exception e) {
                throw e;
            }
        }


        Pattern network = Pattern.compile("network=\\{([^\\}]+)\\}", Pattern.DOTALL);
        Matcher networkMatcher = network.matcher(wifiConf.toString());
        while (networkMatcher.find()) {
            String networkBlock = networkMatcher.group();
            Pattern ssid = Pattern.compile("ssid=\"([^\"]+)\"");
            Matcher ssidMatcher = ssid.matcher(networkBlock);

            if (ssidMatcher.find()) {
                /**
                 * ssid
                 */
                WifiInfo wifiInfo = new WifiInfo();
                wifiInfo.Ssid = ssidMatcher.group(1);
                /**
                 * pwd
                 */
                Pattern psk = Pattern.compile("psk=\"([^\"]+)\"");
                Matcher pskMatcher = psk.matcher(networkBlock);
                if (pskMatcher.find()) {
                    wifiInfo.Password = pskMatcher.group(1);
                } else {
                    wifiInfo.Password = "开放网络";
                }
//                /**
//                 * baseId
//                 */
//                Pattern bssid = Pattern.compile(" bssid=\"([^\"]+)\"");
//                Matcher bssidMatcher = bssid.matcher(networkBlock);
//                if (bssidMatcher.find()) {
//                    wifiInfo.bssid = bssidMatcher.group(1);
//                } else {
//                    wifiInfo.bssid = "";
//                }
//                /**
//                 * key_mgmt
//                 */
//                Pattern key_mgmt = Pattern.compile(" key_mgmt");
//                Matcher key_mgmtMatcher = key_mgmt.matcher(networkBlock);
//                if (key_mgmtMatcher.find()) {
//                    wifiInfo.key_mgmt = key_mgmtMatcher.group(1);
//                } else {
//                    wifiInfo.key_mgmt = "";
//                }
//                /**
//                 * priority
//                 */
//                Pattern priority = Pattern.compile(" priority=([^\"]+)");
//                Matcher priorityMatcher = priority.matcher(networkBlock);
//                if (priorityMatcher.find()) {
//                    wifiInfo.priority = priorityMatcher.group(1);
//                } else {
//                    wifiInfo.priority = "";
//                }
//                /**
//                 * id_str
//                 */
//                Pattern id_str = Pattern.compile(" id_str=\"([^\"]+)\"");
//                Matcher id_strMatcher = id_str.matcher(networkBlock);
//                if (id_strMatcher.find()) {
//                    wifiInfo.id_str = id_strMatcher.group(1);
//                } else {
//                    wifiInfo.id_str = "";
//                }
                wifiInfos.add(wifiInfo);
            }
        }

        return wifiInfos;
    }

}
