package com.addresspicker.huichao.addresspickerlibrary;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;


import com.addresspicker.huichao.addresspickerlibrary.address.Province;
import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lihuichao on 2017/9/27.
 */

public class InitAreaTask extends AsyncTask<Integer, Integer, Boolean> {
    protected onLoadingAddressListener onLoadingAddressListener;
    Context mContext;
    ArrayList<Province> provinces;


    public InitAreaTask(Context context, ArrayList<Province> provinces) {
        mContext = context;
        this.provinces = provinces;
    }

    public void setOnLoadingAddressListener(onLoadingAddressListener onLoadingAddressListener) {
        this.onLoadingAddressListener = onLoadingAddressListener;
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected void onPostExecute(Boolean result) {
        if (provinces.size() > 0) {
            this.onLoadingAddressListener.onLoadFinished();
        } else {
            Toast.makeText(mContext, "数据初始化失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected Boolean doInBackground(Integer... params) {
        String address = null;
        InputStream in = null;
        try {
            in = mContext.getResources().getAssets().open("address.txt");
            byte[] arrayOfByte = new byte[in.available()];
            in.read(arrayOfByte);
            address = new String(arrayOfByte, "UTF-8");

            List<Province> provinces1 = JSON.parseArray(address, Province.class);
            provinces.addAll(provinces1);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
        }
        return false;
    }

    public interface onLoadingAddressListener {
        void onLoadFinished();
    }

}
