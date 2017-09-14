# 查看已连接过wifi的密码
#### 只有已Root的手机可用
##### 通过adb命令获取已连接过的wifi密码，所以只有已root的手机才能使用

### 查看连接过的 WiFi 密码

**注：需要 root 权限。**

命令：

```sh
adb shell
su
cat /data/misc/wifi/*.conf
```

#### app下载: [点击下载](https://raw.githubusercontent.com/canceel/WifiPwd/master/app.apk)



<img src="https://raw.githubusercontent.com/canceel/WifiPwd/master/images/1.jpg" width=590 height=980/>

<img src="https://raw.githubusercontent.com/canceel/WifiPwd/master/images/2.jpg" width=590 height=980/>
