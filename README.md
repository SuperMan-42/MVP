# MVP

App based on Material Design + MVP + Rxjava + Retrofit + Okhttp + Glide

本项目本着简洁的思想，让开发更加简单，抽取出了core做为库，可以直接引入进行快捷开发，项目仍在改进中，如果有好的建议或者发现什么问题欢迎[issue](https://github.com/SuperMan42/MVP/issues),email<424346976@qq.com>，如果感觉对你有帮助也欢迎点个star，fork，本项目仅做学习交流使用

简书:[http://www.jianshu.com/users/3231e0f0f920/latest_articles](http://www.jianshu.com/users/3231e0f0f920/latest_articles)

QQ群:482866708  
![](https://github.com/SuperMan42/MVP/blob/master/share.png)

## Preview
![](https://github.com/SuperMan42/MVP/blob/master/hpw.gif)

[Download APK](http://pro-app-mt.fir.im/06c652ca03b6a152edd08935170fd24562ecb695.apk?AWSAccessKeyId=e0cada7f00f2465b929656d799937873&Expires=1478501552&Signature=t4jtAq%2BmWpyk4rDOEYij8V0aDlo%3D&filename=app-release.apk_1.0.apk)
(Android 5.0 or above)  

![](https://github.com/SuperMan42/MVP/blob/master/download.png)

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
* 自己封装了recyclerview和recyclerviewpager实现下拉刷新，上拉加载更多和pagerview功能（只需简单几句代码即可实现各种列表，无需adapter，无需自己设计分页加载）
* 使用x5WebView做阅览页
* 日报首页的头部可以循环滚动（使用了rxjava轮循和recyclerviewpager）

## 使用
1. 导入core库
2. 接口定义(demo)  

``` 
abstract class DailyPresenter extends CoreBasePresenter<DailyModel, DailyView> {
        public abstract void getDailyData();

        public abstract void startInterval();
    }

    interface DailyModel extends CoreBaseModel {
        Observable<DailyListBean> getDailyData();

        Observable<ZhihuDetailBean> getZhihuDetails(int anInt);
    }

    interface DailyView extends CoreBaseView {
        void showContent(DailyListBean info);

        void doInterval(int i);
    }
```
model(只处理数据)  
presenter(用来处理vm的业务逻辑)  
view(界面交互)  

3. 实现model(demo)

```
public class DailyModel implements ZhihuContract.DailyModel {

    @Override
    public Observable<DailyListBean> getDailyData() {
        return RxService.createApi(ZhiHuApi.class).getDailyList().compose(RxUtil.rxSchedulerHelper());
    }

    @Override
    public Observable<ZhihuDetailBean> getZhihuDetails(int anInt) {
        return RxService.createApi(ZhiHuApi.class).getDetailInfo(anInt).compose(RxUtil.rxSchedulerHelper());
    }
}
```
4. 实现presenter(demo)

```
public class DailyPresenter extends ZhihuContract.DailyPresenter {
    private int topCount = 0;
    private int currentTopCount = 0;

    @Override
    public void onStart() {

    }

    @Override
    public void getDailyData() {
        mRxManager.add(mModel
                .getDailyData()
                .subscribe(
                        dailyListBean -> {
                            mView.showContent(dailyListBean);
                            topCount = dailyListBean.getTop_stories().size();
                        }, e -> mView.showError("数据加载失败ヽ(≧Д≦)ノ")
                ));
    }

    @Override
    public void startInterval() {
        mRxManager.add(Observable.interval(5, TimeUnit.SECONDS)
                .compose(RxUtil.rxSchedulerHelper())
                .subscribe(aLong -> {
                            if (currentTopCount == topCount)
                                currentTopCount = 0;
                            mView.doInterval(currentTopCount++);
                        }
                ));
    }
}
```
三者的创建无先后顺序，按自己的业务逻辑来  
RxManage用于管理订阅者，观察者以及事件  
发送事件：`mRxManage.post(Constants.msg, user);`  
接受事件：`mRxManage.on(Constants.msg, arg ->mView.initUserInfo((_User) arg));`  

5. 列表的实现  

```
public class WechatFragment extends CoreBaseFragment<WechatPresenter, WechatModel> implements ZhihuContract.WechatView {
    CoreRecyclerView coreRecyclerView;
    private static int pageNum = 10;

    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public View getLayoutView() {
        coreRecyclerView = new CoreRecyclerView(mContext).init(new BaseQuickAdapter<WXItemBean, BaseViewHolder>(R.layout.item_weichat) {
            @Override
            protected void convert(BaseViewHolder helper, WXItemBean item) {
                Glide.with(mContext).load(item.getPicUrl()).crossFade().into((ImageView) helper.getView(R.id.iv_wechat_item_image));
                helper.setText(R.id.tv_wechat_item_title, item.getTitle())
                        .setText(R.id.tv_wechat_item_from, item.getDescription())
                        .setText(R.id.tv_wechat_item_time, item.getCtime())
                        .setOnClickListener(R.id.ll_click, v -> {
                            WechatDetailsActivity.start(mContext, item.getTitle(), item.getUrl());
                        });
            }
        }).openLoadMore(pageNum, page -> mPresenter.getWechatData(pageNum, page))
                .openRefresh();
        return coreRecyclerView;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void showContent(List<WXItemBean> mList) {
        coreRecyclerView.getAdapter().addData(mList);
    }

    @Override
    public void showError(String msg) {
        coreRecyclerView.showLoadMoreFailedView();
    }
}
```
无需自己创建adapter，无需自己写下拉刷新和上拉加载更多的逻辑（只要添加`openLoadMore(pageSize, addDataListener)  openRefresh()`即可实现刷新和加载）
无需写xml布局文件（也可写，demo里两种实现方式）只需要在`getLayoutView()` 中 return  

```
new CoreRecyclerView(mContext).init(new BaseQuickAdapter<WXItemBean, BaseViewHolder>(R.layout.item_weichat) {
    @Override
    protected void convert(BaseViewHolder helper, WXItemBean item) {
         //viewholder      
    }
})
        
```
即可实现列表（使用recyclerviewpager也是如初简单，具体看demo,recyclerviewpager可以实现viewpager所有功能）

## TODO
还有很多。。。

## Thanks
[知乎日报API](https://github.com/izzyleung/ZhihuDailyPurify/wiki/%E7%9F%A5%E4%B9%8E%E6%97%A5%E6%8A%A5-API-%E5%88%86%E6%9E%90)&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[微信精选API](http://www.tianapi.com/#wxnew)&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

参考了很多大神的作品
