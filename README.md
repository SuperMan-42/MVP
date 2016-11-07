# MVP

App based on Material Design + MVP + Rxjava + Retrofit + Okhttp + Glide

本项目本着简洁的思想，让开发更加简单，抽取出了core做为库，可以直接引入进行快捷开发，项目仍在改进中，如果有好的建议或者发现什么问题欢迎[issue](https://github.com/SuperMan42/MVP/issues),email<424346976@qq.com>，如果感觉对你有帮助也欢迎点个star，fork，本项目仅做学习交流使用

## Preview
![](https://github.com/SuperMan42/MVP/blob/master/hpw.gif)

[Download APK]()
(Android 5.0 or above)

## Points
* 使用Rxjava配合Retrofit2+okhttp做网络请求和缓存
* 使用RxUtil对线程操作和网络请求结果处理做了封装
* 使用RxManager对订阅生命周期做了统一管理
* 使用RxBus做了组件间通信
* 使用RxPermissions对android6.0进行权限申请
* 使用Material Design控件和动画
* 使用MVP架构整个项目，并且抽取出core做为库，导入core即可省去50%的代码开发哦
* 使用Glide做图片处理和加载
* 使用Fragmentation简化Fragment的操作和懒加载
* 自己封装了recyclerview和recyclerviewpager实现上拉刷新，下拉加载更多和pagerview功能（只需简单几句代码即可实现各种列表，无需adapter，无需自己设计分页加载）
* 使用x5WebView做阅览页

## 使用
