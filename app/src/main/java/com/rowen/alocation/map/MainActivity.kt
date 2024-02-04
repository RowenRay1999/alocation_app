package com.rowen.alocation.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.baidu.location.LocationClient
import com.baidu.location.LocationClientOption
import com.baidu.mapapi.map.*
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener
import com.baidu.mapapi.model.LatLng
import com.baidu.mapapi.search.poi.*
import com.rowen.alocation.R
import com.rowen.alocation.constant.CommonConstants
import com.rowen.alocation.databinding.ActivityMainBinding

import com.rowen.alocation.user.LoginActivity
import com.rowen.alocation.user.UserCenterActivity
import com.rowen.alocation.utils.RecyclerDataUtils
import com.rowen.alocation.view.GaoDeBottomSheetBehavior
import com.rowen.alocation.vm.MainViewModel

class MainActivity : AppCompatActivity() {
    companion object {
        // 在伴生对象中获取类名
        val TAG = this::class.java.simpleName
    }

    private val requestPermissions = arrayOf<String>(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION)

    private var mbinding: ActivityMainBinding? = null

    private var mMapView: MapView? = null
    private var mBaiduMap: BaiduMap? = null
    private var mUiSettings: UiSettings? = null
    private var mLocationClient: LocationClient? =null
    private var mPoiSearch: PoiSearch? = null
    private var mBtUser: ImageButton? = null

    private var mViewModel: MainViewModel? = null
    private var mLoginStatus: Int? = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mbinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mbinding?.root)
        var actionBar = supportActionBar
        actionBar?.hide()
        mViewModel = MainViewModel(this)
        mbinding?.viewmodel = mViewModel
//        ImmersionBar.with(this)
//            .statusBarDarkFont(true)
//            .fitsSystemWindows(false)
//            .init()

        //获取地图控件引用
        ActivityCompat.requestPermissions(this, requestPermissions, 10086);
        mMapView = mbinding?.bmapView
        initData()
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
        mBtUser = mbinding?.btUser

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

        val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)
        RecyclerDataUtils.setRecyclerAdater(this, recyclerview, "测试数据", 50)
        val bottom_sheet = findViewById<ViewGroup>(com.rowen.alocation.R.id.bottom_sheet)
        val behavior: GaoDeBottomSheetBehavior<*> = GaoDeBottomSheetBehavior.from(bottom_sheet)
        behavior.setBottomSheetCallback(object : GaoDeBottomSheetBehavior.BottomSheetCallback() {
            @SuppressLint("WrongConstant")
            override fun onStateChanged(@NonNull bottomSheet: View, newState: Int) {
                when (newState) {
                    1 ->                         //过渡状态此时用户正在向上或者向下拖动bottom sheet
                        Log.e(TAG, "====用户正在向上或者向下拖动")
                    2 ->                         // 视图从脱离手指自由滑动到最终停下的这一小段时间
                        Log.e(TAG, "====视图从脱离手指自由滑动到最终停下的这一小段时间")
                    3 ->                         //处于完全展开的状态
                        Log.e(TAG, "====处于完全展开的状态")
                    4 ->                         //默认的折叠状态
                        Log.e(TAG, "====默认的折叠状态")
                    5 ->                         //下滑动完全隐藏 bottom sheet
                        Log.e(TAG, "====下滑动完全隐藏")
                    6 ->                         //下滑动完全隐藏 bottom sheet
                        Log.e(TAG, "====中间位置")
                }
            }

            override fun onSlide(@NonNull bottomSheet: View, slideOffset: Float) {
                Log.e(TAG, "====slideOffset=$slideOffset")
            }
        })

        var bitmap = BitmapDescriptorFactory.fromResource(R.drawable.user).bitmap

        mBtUser?.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 40, 40, false))
        mBtUser?.setOnClickListener {
            mLoginStatus?.let {
                if (it >= CommonConstants.USER.USER_LOGIN_STATUS_LOGIN) {
                    val newIntent = Intent(this, UserCenterActivity::class.java)
                    newIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(newIntent)
                } else {
                    val newIntent = Intent(this, LoginActivity::class.java)
                    newIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(newIntent)
                }
            }


        }
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

    fun initData() {
        mViewModel?.getLoginStatus()?.observe(this, Observer { status ->
            mLoginStatus = status
            when(status) {
                CommonConstants.USER.USER_LOGIN_STATUS_LOGIN -> {}
            }
        })
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