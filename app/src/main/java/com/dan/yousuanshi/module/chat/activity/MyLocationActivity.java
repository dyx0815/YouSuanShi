package com.dan.yousuanshi.module.chat.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.dan.yousuanshi.R;
import com.dan.yousuanshi.module.chat.adapter.LocationAddressAdapter;
import com.dan.yousuanshi.module.chat.bean.LocationAddress;
import com.dan.yousuanshi.utils.FileUtils;
import com.dan.yousuanshi.utils.StatusBarUtil;
import com.dan.yousuanshi.utils.ToastUtils;
import com.dan.yousuanshi.utils.UserUtils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyLocationActivity extends AppCompatActivity implements PoiSearch.OnPoiSearchListener {

    @BindView(R.id.mapView)
    MapView mapView;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.iv_get_location)
    ImageView ivGetLocation;
    @BindView(R.id.btn_sure)
    Button btnSure;

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    private AMap aMap;
    private MyLocationStyle myLocationStyle;
    private PoiSearch.Query query = null;
    private PoiSearch poiSearch;
    private UiSettings uiSettings;
    private Marker marker;
    private List<LocationAddress> addressList = new ArrayList<>();
    private LocationAddressAdapter adapter;
    private String province;
    private String city;
    private String area;
    private String cityCode;
    private double latitude;
    private double longitude;
    private LocationAddress locationAddress;
    private boolean isMove = true;
    private PoiItem poiItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setColor(this,getColor(R.color.white),0);
        setContentView(R.layout.activity_my_location);
        ButterKnife.bind(this);
        mapView.onCreate(savedInstanceState);
        if (aMap == null) {
            aMap = mapView.getMap();
        }
        initMap();
        location();
        adapter = new LocationAddressAdapter(this, addressList);
        adapter.setOnItemClick(new LocationAddressAdapter.OnItemClick() {
            @Override
            public void onItemClick(int position) {
                isMove = false;
                moveMapCamera(addressList.get(position).getLatitude(), addressList.get(position).getLongitude());
                locationAddress = addressList.get(position);
                addressList.get(position).setSelect(true);
                for (LocationAddress item : addressList) {
                    if (!item.equals(addressList.get(position))) {
                        item.setSelect(false);
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        initSearch();
        aMap.setOnMapTouchListener(new AMap.OnMapTouchListener() {
            @Override
            public void onTouch(MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    isMove = true;
                }
            }
        });
        Intent intent = getIntent();
        locationAddress = intent.getParcelableExtra("locationAddress");
        if (locationAddress != null) {
            btnSure.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
            ivGetLocation.setVisibility(View.GONE);
            moveMapCamera(locationAddress.getLatitude(), locationAddress.getLongitude());
        }
    }

    @OnClick({R.id.ll_back, R.id.btn_sure, R.id.iv_get_location})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.btn_sure:
                Intent intent = new Intent(this, ChatActivity.class);
                intent.putExtra("path", screenShot());
                intent.putExtra("locationAddress", locationAddress);
                setResult(4, intent);
                finish();
                break;
            case R.id.iv_get_location:
                location();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，实现地图生命周期管理
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，实现地图生命周期管理
        mapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，实现地图生命周期管理
        mapView.onSaveInstanceState(outState);
    }

    private void location() {
        if (mLocationClient == null) {
            //初始化定位
            mLocationClient = new AMapLocationClient(getApplicationContext());
            //设置定位回调监听
            mLocationClient.setLocationListener(new AMapLocationListener() {
                @Override
                public void onLocationChanged(AMapLocation aMapLocation) {
                    if (aMapLocation != null) {
                        if (aMapLocation.getErrorCode() == 0) {
                            if (poiItem == null) {
                                moveMapCamera(aMapLocation.getLatitude(), aMapLocation.getLongitude());
                            }
//                            addmark(aMapLocation.getLatitude(), aMapLocation.getLongitude());
                            query = new PoiSearch.Query("未央区", "", aMapLocation.getCityCode());
                            province = aMapLocation.getProvince();//省或直辖市
                            city = aMapLocation.getCity();//地级市或直辖市
                            area = aMapLocation.getDistrict();//区或县或县级市
                            cityCode = aMapLocation.getCityCode();
                            latitude = aMapLocation.getLatitude();
                            longitude = aMapLocation.getLongitude();
                        } else {
                            //定位失败
                            ToastUtils.showToast(MyLocationActivity.this, "定位失败！");
                            Log.e("MyLocationActivity", "定位失败！" + "错误码：" + aMapLocation.getErrorCode());
                        }
                    }
                }
            });
            //初始化AMapLocationClientOption对象
            mLocationOption = new AMapLocationClientOption();
            mLocationOption.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.SignIn);
            if (null != mLocationClient) {
                mLocationClient.setLocationOption(mLocationOption);
                //设置场景模式后最好调用一次stop，再调用start以保证场景模式生效
                mLocationClient.stopLocation();
                mLocationClient.startLocation();
            }
            //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //获取最近3s内精度最高的一次定位结果：
            // 设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
            mLocationOption.setOnceLocationLatest(true);
            //设置是否返回地址信息（默认返回地址信息）
            mLocationOption.setNeedAddress(true);
            //单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
            mLocationOption.setHttpTimeOut(20000);
            //关闭缓存机制
            mLocationOption.setLocationCacheEnable(false);
            //给定位客户端对象设置定位参数
            mLocationClient.setLocationOption(mLocationOption);
        }
        //启动定位
        if (mLocationClient.isStarted()) {
            mLocationClient.stopLocation();
        }
        mLocationClient.startLocation();
    }

    private void initMap() {
        if (myLocationStyle == null) {
//            myLocationStyle = new MyLocationStyle();
//            myLocationStyle.interval(2000);
//            myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW);
//            myLocationStyle.showMyLocation(true);//设置是否显示定位小蓝点，用于满足只想使用定位，不想使用定位小蓝点的场景，设置false以后图面上不再有定位蓝点的概念，但是会持续回调位置信息。
//            aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
//            aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
            //监测地图画面的移动
            aMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
                @Override
                public void onCameraChangeFinish(CameraPosition cameraPosition) {
                    if (isMove) {
                        latSearchList(cameraPosition.target.latitude, cameraPosition.target.longitude);
                    }
                    addmark(cameraPosition.target.latitude, cameraPosition.target.longitude);
                }

                @Override
                public void onCameraChange(CameraPosition cameraPosition) {
                }
            });
            uiSettings = aMap.getUiSettings();
            //是否允许显示地图缩放按钮
            uiSettings.setZoomControlsEnabled(false);
        }
    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
        Log.e("MyLocationActivity", "搜索周边成功！");
        addressList.clear();
        for (PoiItem item : poiResult.getPois()) {
            LocationAddress locationAddress = new LocationAddress();
            locationAddress.setLocationName(item.getTitle());
            locationAddress.setLocationAddess(province + city + area+item.getTitle());
            locationAddress.setLatitude(item.getLatLonPoint().getLatitude());
            locationAddress.setLongitude(item.getLatLonPoint().getLongitude());
            addressList.add(locationAddress);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {
    }

    private void addmark(double latitude, double longitude) {

        if (marker == null) {
            marker = aMap.addMarker(new MarkerOptions()
                    .position(new LatLng(latitude, longitude))
                    .draggable(true));
        } else {
            marker.setPosition(new LatLng(latitude, longitude));
            aMap.invalidate();
        }


    }

    //把地图画面移动到定位地点
    private void moveMapCamera(double latitude, double longitude) {
        aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 14));
        aMap.moveCamera(CameraUpdateFactory.zoomTo(17));
    }

    /**
     * 周边搜索
     *
     * @param latitude
     * @param longitude
     */
    private void latSearchList(double latitude, double longitude) {
        LatLonPoint point = new LatLonPoint(latitude, longitude);
        GeocodeSearch geocodeSearch = new GeocodeSearch(this);
        RegeocodeQuery regeocodeQuery = new RegeocodeQuery(point, 2000, geocodeSearch.AMAP);
        geocodeSearch.getFromLocationAsyn(regeocodeQuery);
        geocodeSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
            @Override
            public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int rCode) {
                if (1000 == rCode) {
                    RegeocodeAddress address = regeocodeResult.getRegeocodeAddress();
                    StringBuffer stringBuffer = new StringBuffer();
                    List<PoiItem> pois = address.getPois();//获取周围兴趣点
                    addressList.clear();
                    for (PoiItem item : pois) {
                        LocationAddress locationAddress = new LocationAddress();
                        locationAddress.setLocationName(item.getTitle());
                        locationAddress.setLocationAddess(province + city + area+item.getTitle());
                        locationAddress.setLatitude(item.getLatLonPoint().getLatitude());
                        locationAddress.setLongitude(item.getLatLonPoint().getLongitude());
                        addressList.add(locationAddress);
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

            }
        });
    }

    //poi搜索
    private void searchList(String cityCode, String road) {
        query = new PoiSearch.Query(road, "", cityCode);
        query.setPageSize(15);
        query.setPageNum(1);
        PoiSearch poiSearch = new PoiSearch(this, query);
        poiSearch.setOnPoiSearchListener(this);
        poiSearch.setBound(new PoiSearch.SearchBound(new LatLonPoint(latitude, longitude), 2000, true));
        poiSearch.searchPOIAsyn();
    }

    private void initSearch() {
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                searchList(cityCode, etSearch.getText().toString());
                return true;
            }
        });
    }

    /**
     * 地图截图
     *
     * @return
     */
    private String screenShot() {
        String path = getApplicationContext().getFilesDir().getAbsolutePath() + "/screen/location/"
                + UserUtils.getInstance().getUserBean().getUser_id() + "location" + System.currentTimeMillis() + ".png";
        if (FileUtils.isFolderExists(getApplicationContext().getFilesDir().getAbsolutePath() + "/screen/location/")) {
            aMap.getMapScreenShot(new AMap.OnMapScreenShotListener() {
                @Override
                public void onMapScreenShot(Bitmap bitmap) {
                    if (bitmap == null) {
                        return;
                    }
                    try {
                        FileOutputStream fos = new FileOutputStream(path);
                        boolean b = bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                        try {
                            fos.flush();
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (b) {
                            Log.e("MyLocationActivity", "截屏成功！");
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        return path;
    }
}
