package com.addresspicker.huichao.addresspicker;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.addresspicker.huichao.addresspickerlibrary.CityPickerDialog;
import com.addresspicker.huichao.addresspickerlibrary.InitAreaTask;
import com.addresspicker.huichao.addresspickerlibrary.Util;
import com.addresspicker.huichao.addresspickerlibrary.address.City;
import com.addresspicker.huichao.addresspickerlibrary.address.County;
import com.addresspicker.huichao.addresspickerlibrary.address.Province;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements InitAreaTask.onLoadingAddressListener{
    private ArrayList<Province> provinces;
    private Dialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (provinces == null) {
            provinces = new ArrayList<>();
        }
        if (provinces.size() > 0) {
            showAddressDialog();
        } else {
            //没有地址数据，就去初始化
            InitAreaTask initAreaTask = new InitAreaTask(MainActivity.this, provinces);
            initAreaTask.execute(0);
            initAreaTask.setOnLoadingAddressListener(MainActivity.this);
        }
    }
    private void showAddressDialog() {//初始化地址数据后，显示地址选择框
        new CityPickerDialog(MainActivity.this, provinces, null, null, null,
                new CityPickerDialog.onCityPickedListener() {

                    @Override
                    public void onPicked(Province selectProvince,
                                         City selectCity, County selectCounty) {
                        StringBuilder address = new StringBuilder();
                        address.append(
                                selectProvince != null ? selectProvince
                                        .getAreaName() : "")
                                .append(selectCity != null ? selectCity
                                        .getAreaName() : "");
                        if (selectCounty != null) {
                            String areaName = selectCounty.getAreaName();
                            if (areaName != null) {
                                address.append(areaName);
                            }
                        }
                        Toast.makeText(MainActivity.this,address.toString(),Toast.LENGTH_SHORT).show();
                    }
                }).show();
    }

    @Override
    public void onLoadFinished() {//加载完成的监听
        progressDialog.dismiss();
        showAddressDialog();
    }

    @Override
    public void onLoading() {//加载中监听
        progressDialog = Util.createLoadingDialog(MainActivity.this, "请稍等...", true, 0);
        progressDialog.show();
    }
}
