package com.rowen.alocation

import android.Manifest
import android.app.Application
import android.os.Build
import androidx.core.app.ActivityCompat
import com.baidu.location.LocationClient
import com.baidu.mapapi.CoordType
import com.baidu.mapapi.SDKInitializer

class AlocationApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        SDKInitializer.setAgreePrivacy(applicationContext,true);
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.initialize(this);
        LocationClient.setAgreePrivacy(true);
        SDKInitializer.setCoordType(CoordType.BD09LL);

    }
}