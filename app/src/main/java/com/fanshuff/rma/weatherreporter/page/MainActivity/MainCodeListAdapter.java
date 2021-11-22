package com.fanshuff.rma.weatherreporter.page.MainActivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fanshuff.rma.weatherreporter.R;
import com.fanshuff.rma.weatherreporter.entity.CityCodeInfo;

import java.util.List;

public class MainCodeListAdapter extends BaseAdapter {

    List<CityCodeInfo> cityCodeInfoList;
    private LayoutInflater layoutInflater;

    public MainCodeListAdapter (List<CityCodeInfo> cityCodeInfoList, Context context){
        this.cityCodeInfoList = cityCodeInfoList;
        this.layoutInflater = LayoutInflater.from(context);

    }



    @Override
    public int getCount() {
        return cityCodeInfoList == null?0:cityCodeInfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return cityCodeInfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = layoutInflater.inflate(R.layout.item_activity_main_list,null);
        CityCodeInfo cityCodeInfo = (CityCodeInfo) getItem(position);

        //在view 视图中查找 组件
        TextView mTextViewCityName = (TextView) view.findViewById(R.id.tv_item_activity_main_city_name);

        mTextViewCityName.setText(cityCodeInfo.getChineseName());


        return view;
    }


}
