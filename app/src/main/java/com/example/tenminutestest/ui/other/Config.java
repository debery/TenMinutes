package com.example.tenminutestest.ui.other;

//设置界面

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tenminutestest.BaseActivity;
import com.example.tenminutestest.R;

public class Config extends BaseActivity {
    private String[]configname={"个人资料","账号管理","消息设置","隐私设置","缓存清理","用户协议","隐私政策","安全条款","关于我们"};
    ListView config_list=null;
    TextView configItemname=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        config_list=findViewById(R.id.config_list);
        Config.configAdapter ConAda=new Config.configAdapter();
        config_list.setAdapter(ConAda);
    }

    private class configAdapter extends BaseAdapter
    {

        @Override
        public int getCount() {
            return configname.length;
        }

        @Override
        public Object getItem(int position) {
            return configname[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView==null){
                convertView=View.inflate(Config.this, R.layout.config_items,null);
                configItemname=convertView.findViewById(R.id.configtext);
                convertView.setTag(configItemname);
            }
            else
            {
                configItemname=(TextView) convertView.getTag();
            }
            configItemname.setText(configname[position]);
            return convertView;
        }
    }
}