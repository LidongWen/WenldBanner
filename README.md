# 一款商业级Banner控件 

既然敢说商业级，那么就支持足够的自定义与扩展性!  实现思路请看这 [开发一款商业级Banner控件](http://www.jianshu.com/p/a175a6bd80ca)

效果图：

![](http://upload-images.jianshu.io/upload_images/1599843-f43b6654680d8587.gif?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![](http://upload-images.jianshu.io/upload_images/1599843-56d6addd9c2cb735.gif?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## 可以做到以下效果

* 设置自定义指示器效果
* 设置指示器位置
* 设置是否循环
* 是否可以跳转
* 页面切换间隔
* 页面切换持续时间
* 是否支持手动滑动
* 是否反向切换页面（切换方向）

# use

## 1、引用:
版本号： [![](https://jitpack.io/v/LidongWen/WenldBanner.svg)](https://jitpack.io/#LidongWen/WenldBanner)

```
// root build.gradle
repositories {
    jcenter()
    maven { url "https://www.jitpack.io" }
}
// yout project build.gradle
dependencies {
        compile 'com.github.LidongWen:WenldBanner:xxx'
}
```

## 2. 使用 WenldBanner:
### 2.1.  xml  
    ```
        <com.wenld.wenldbanner.WenldBanner
            xmlns:app="http://schemas.android.com/apk/res-auto"
            android:id="@+id/commonBanner"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            app:canLoop="true"    //设置是否循环
            app:canTurn="true"  //是否可以跳转
            app:autoTurnTime="5000"// 页面切换间隔
            app:scrollDuration="2000"//页面切换持续时间
            app:isTouchScroll="true" //是否支持手动滑动
            app:reverse="true" // 是否反向切换页面（切换方向）
       />
    ```

### 2.2.  Java代码中设置属性
```
        wenldBanner = (WenldBanner) findViewById(R.id.commonBanner);
        //初始化指示器
        defaultPageIndicator = new DefaultPageIndicator(this);
       //设置指示器样式  选中图标与未选中图标
        defaultPageIndicator.setPageIndicator(new int[]{R.mipmap.ic_page_indicator, R.mipmap.ic_page_indicator_focused});

        //设置 view 与 数据
        wenldBanner.setPages(Common.holder, Common.datas);
        wenldBanner
                .setPageIndicatorListener(defaultPageIndicator)  //设置指示器监听
                .setIndicatorView(defaultPageIndicator)  //设置指示器VIew
                .setPageIndicatorAlign(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.CENTER_HORIZONTAL);    //设置指示器位置
```

还可以设置任意指示器样式，指示器监听事件，指示器位置等等...  
![指示器样式1](http://upload-images.jianshu.io/upload_images/1599843-f9fc7e28b006baef.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![指示器样式2](http://upload-images.jianshu.io/upload_images/1599843-c03b1e1b4e718504.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![指示器样式3](http://upload-images.jianshu.io/upload_images/1599843-7f830ca0705632a4.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

### 3.  自定义拆分使用：

比如实现这个效果：

![](http://upload-images.jianshu.io/upload_images/1599843-173710c737f240ca.gif?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

**注意**：
先将根目录设置 `  android:clipChildren="false"`，在设置AutiTurnViewPager的宽度 给 viewpPager留出一些空隙`android:layout_width="250dp"` 
在代码中设置`PageTransformer`查看转换效果

xml文件

 ```
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    ...
    android:clipChildren="false">

    <com.wenld.wenldbanner.AutoTurnViewPager
        android:id="@+id/autoTurnViewPager"
        android:layout_width="250dp"
        android:clipToPadding="false"
        ... 

    <com.wenld.wenldbanner.DefaultPageIndicator
      ... 
</FrameLayout>
```

java 要设置`PageTransformer`否则看不出效果

```
protected void onCreate(Bundle savedInstanceState) {
    autoTurnViewPager.setPageTransformer(new ZoomOutPageTransformer());
}
```

> **· v 2.0.0**  
>    更换无限循环方式，使操作更加流畅   
>    优化代码，真实循环采用观察者模式
