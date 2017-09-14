# 查看已连接过wifi的密码
#### 只有已Root的手机可用
##### 通过adb命令获取已连接过的wifi密码，所以只有已root的手机才能使用
`adb shell
 su
 cat /data/misc/wifi/*.conf`
#### app下载: [点击下载](https://raw.githubusercontent.com/canceel/WifiPwd/master/app.apk)
