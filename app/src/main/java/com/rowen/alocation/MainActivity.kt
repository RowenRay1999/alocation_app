package com.rowen.alocation

import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.baidu.location.LocationClient
import com.baidu.location.LocationClientOption
import com.baidu.mapapi.map.*
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener
import com.baidu.mapapi.model.LatLng
import com.baidu.mapapi.search.poi.*


class MainActivity : AppCompatActivity() {
    private val requestPermissions = arrayOf<String>(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION)

    private var mMapView: MapView? = null
    private var mBaiduMap: BaiduMap? = null
    private var mUiSettings: UiSettings? = null
    private var mLocationClient: LocationClient? =null
    private var mPoiSearch: PoiSearch? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //获取地图控件引用
        ActivityCompat.requestPermissions(this, requestPermissions, 10086);
        mMapView = findViewById<View>(R.id.bmapView) as MapView
        initView()
        initLocation()
    }

    override fun onResume() {
        super.onResume()
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView?.onResume()
    }

    override fun onPause() {
        mMapView?.onPause()
        super.onPause()
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
    }

    private fun initView() {
        var isShowCompass = true
        mBaiduMap = mMapView?.getMap()
        //普通地图 ,mBaiduMap是地图控制器对象
        mBaiduMap?.mapType = BaiduMap.MAP_TYPE_NORMAL
        mBaiduMap?.isMyLocationEnabled = true;
        //实例化UiSettings类对象
        mUiSettings = mBaiduMap?.uiSettings;
        //通过设置enable为true或false 选择是否显示指南针
        mUiSettings?.isCompassEnabled = isShowCompass;
        mBaiduMap?.setIndoorEnable(true);
        mPoiSearch = PoiSearch.newInstance();

        val onMapClickListener: OnMapClickListener = object : OnMapClickListener {
            /**
             * 地图单击事件回调函数
             *
             * @param point 点击的地理坐标
             */
            override fun onMapClick(point: LatLng) {
                //构建Marker图标
                //构建Marker图标
                mBaiduMap?.clear();
                val bitmap = BitmapDescriptorFactory
                    .fromResource(R.drawable.icon_marka)
                //构建MarkerOption，用于在地图上添加Marker
                //构建MarkerOption，用于在地图上添加Marker
                val option: OverlayOptions = MarkerOptions()
                    .position(point)
                    .icon(bitmap)
                    .isJoinCollision(true)// 设置marker参与碰撞
                    .isForceDisPlay(true) //设置压盖时 marker强制展示
                    .priority(3)//设置碰撞优先级为9
                //在地图上添加Marker，并显示
                //在地图上添加Marker，并显示
                mBaiduMap!!.addOverlay(option)

            }

            /**
             * 地图内 Poi 单击事件回调函数
             *
             * @param mapPoi 点击的 poi 信息
             */
            override fun onMapPoiClick(mapPoi: MapPoi) {}
        }
        //设置地图单击事件监听
        //设置地图单击事件监听
        mBaiduMap!!.setOnMapClickListener(onMapClickListener)

        val onGetPoiSearchResultListener: OnGetPoiSearchResultListener = object : OnGetPoiSearchResultListener {
            override fun onGetPoiResult(poiResult: PoiResult?) {
                
            }
            override fun onGetPoiDetailResult(poiDetailSearchResult: PoiDetailSearchResult) {}
            override fun onGetPoiIndoorResult(poiIndoorResult: PoiIndoorResult) {}
            override fun onGetPoiDetailResult(p0: PoiDetailResult?) {
                TODO("Not yet implemented")
            }
        }

        mPoiSearch?.setOnGetPoiSearchResultListener(onGetPoiSearchResultListener)
    }

    private fun initLocation() {
        //定位初始化
        mLocationClient = LocationClient(this)

        //通过LocationClientOption设置LocationClient相关参数
        val option = LocationClientOption()
        option.isOpenGnss = true// 打开gps

        option.setCoorType("bd09ll") // 设置坐标类型

        option.setScanSpan(1000)

        //设置locationClientOption
        mLocationClient?.locOption = option

        //注册LocationListener监听器
        //
        mLocationClient?.registerNotifyLocationListener {
            if (it == null || mMapView == null) {

            } else {
                val locData = MyLocationData.Builder()
                    .accuracy(it.radius) // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(it.direction).latitude(it.latitude)
                    .longitude(it.longitude).build()
                mBaiduMap?.setMyLocationData(locData)
                 //如果是第一次定位,就定位到以自己为中心

                var ll = LatLng(it.latitude, it.longitude); //获取用户当前经纬度
                var u = MapStatusUpdateFactory.newLatLng(ll);  //更新坐标位置
                mBaiduMap?.animateMapStatus(u);                            //设置地图位置

            }

        }
        //开启地图定位图层
        mLocationClient?.start()
    }

    override fun onDestroy() {
        mLocationClient?.stop();
        mBaiduMap?.isMyLocationEnabled = false;
        mMapView?.onDestroy();
        mMapView = null;
        super.onDestroy()
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管
    }

}