package com.example.tenminutestest.ui.other;

import com.example.tenminutestest.R;
import com.example.tenminutestest.ui.main.MainActivity;
import com.google.gson.annotations.SerializedName;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.Calendar;
import java.util.TimeZone;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Punch_pt extends AppCompatActivity {
    String TAG="show";
    String geturl1="http://120.24.191.82:8080/CheckSport";
    String geturl2="http://120.24.191.82:8080/CheckArts";
    String geturl3="http://120.24.191.82:8080/CheckTeaching";
    int takenumber=0;

    TextView num=null;
    ImageView mon_picture=null;
    ImageView tues_picture=null;
    ImageView wednes_picture=null;
    ImageView thur_picture=null;
    ImageView fri_picture=null;
    ImageView sta_picture=null;
    ImageView sun_picture=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_punch_pt);
        num=findViewById(R.id.number);        //修改打卡次数
        mon_picture=findViewById(R.id.monday);
        tues_picture=findViewById(R.id.tuesday);
        wednes_picture=findViewById(R.id.wednesday);
        thur_picture=findViewById(R.id.thursday);
        fri_picture=findViewById(R.id.friday);
        sta_picture=findViewById(R.id.staurday);
        sun_picture=findViewById(R.id.sunday);

        Calendar calendar=Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));  //时区
        int mYear=calendar.get(Calendar.YEAR);        //获取系统年份
        int mMonth=calendar.get(Calendar.MONTH);      //获取系统月份
        int mDay=calendar.get(Calendar.DAY_OF_MONTH); //获取系统日期
        SpeciaCalendar mCalendar=new SpeciaCalendar();  //获取自己创建的日历类
        boolean yeartype=mCalendar.isYear(mYear);       //判断是否为闰年
        int day=mCalendar.getDayOfMonth(yeartype,mMonth);  //判断月份所含天数
        int mway=calendar.get(Calendar.DAY_OF_WEEK);   //获取星期几

                HttpUtil getsuccess=new HttpUtil();
                getsuccess.sendOKHttp(geturl1, new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        //获取解析成功后的字符串
                        String reponseStr=response.body().string();
                        //使用gson接收数据
                        Gson gson=new Gson();
                        //将json的{}类数据用fromJson转换成数组存储，存储方式为键值对
                        responseresult getresponseresultOfsucceess=gson.fromJson(reponseStr,responseresult.class);
                            //此处写入接口，当发出信息的年份、月份、日期能够与mYear、mNonth、mDay相等时，替换打卡页下方日历图片并将 Text+1
                            if(getresponseresultOfsucceess.getSuccess()=="true")
                            {
                                num.setText(++takenumber);
                                if(1==mway)
                                {
                                    mon_picture.setBackground(getDrawable(R.drawable.agreed));
                                }
                                if(2==mway)
                                {
                                    tues_picture.setBackground(getDrawable(R.drawable.agreed));
                                }
                                if(3==mway)
                                {
                                    wednes_picture.setBackground(getDrawable(R.drawable.agreed));
                                }
                                if(4==mway)
                                {
                                    thur_picture.setBackground(getDrawable(R.drawable.agreed));
                                }
                                if(5==mway)
                                {
                                    fri_picture.setBackground(getDrawable(R.drawable.agreed));
                                }
                                if(6==mway)
                                {
                                    sta_picture.setBackground(getDrawable(R.drawable.agreed));
                                }
                                if(7==mway)
                                {
                                    sun_picture.setBackground(getDrawable(R.drawable.agreed));
                                }
                            }
                        }
                });

                if(mway==1)
                {
                    //此处将打卡页日历图片复原
                    mon_picture.setBackground(getDrawable(R.drawable.monday));
                    tues_picture.setBackground(getDrawable(R.drawable.tuesday));
                    wednes_picture.setBackground(getDrawable(R.drawable.wednesday));
                    thur_picture.setBackground(getDrawable(R.drawable.thursday));
                    fri_picture.setBackground(getDrawable(R.drawable.friday));
                    sta_picture.setBackground(getDrawable(R.drawable.staurday));
                    sun_picture.setBackground(getDrawable(R.drawable.sunday));
                }
            }

    public void backmine(View view) {
        Intent intent=new Intent(Punch_pt.this, MainActivity.class);
        intent.putExtra("id",4);
        startActivity(intent);
    }

    class HttpUtil {
        public void sendOKHttp(String address, Callback callback)
        {
            //创建okhttpClient对象
            OkHttpClient okHttpClient=new OkHttpClient();
            //创建request
            Request request=new Request.Builder().url(address).build();
            //添加回调方法
            okHttpClient.newCall(request).enqueue(callback);
        }
    }
}

class SpeciaCalendar {
    //判断是否为闰年
    public boolean isYear(int year) {
        if (year % 4 == 0)
            return true;
        else
            return false;
    }

    //得到每个月会有多少天
    public int getDayOfMonth(boolean isyear, int month) {
        int days = 0;
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                days = 31;
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                days = 30;
                break;
            case 2:
                if (isyear == true)
                    days = 28;
                else
                    days = 29;
                break;
        }
        return days;
    }
}

class responseresult
{
    @SerializedName("successtype")
    public String success;
    @SerializedName("messagecontent")
    public String message;
    @SerializedName("codenumber")
    public String code;
    @SerializedName("objectstring")
    public String object;
    public String getSuccess()
    {
        return success;
    }
}



