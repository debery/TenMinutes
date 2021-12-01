package com.example.tenminutestest;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class User_IO {

    public static boolean saveinfos(String Uid, String Upsd,String Uname, Context context){
        String msg=null;
        FileOutputStream fos=null;
        try{
        fos=context.openFileOutput("data",Context.MODE_PRIVATE);
        msg=Uid+"::"+Upsd+"::"+Uname;
        fos.write(msg.getBytes());
        return true;
    }  catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static boolean reSaveInfo( String avatar,Context context){
        String msg=null;
        List<String> user=get_userinfos(context);

        FileOutputStream fos=null;
        try{
            fos=context.openFileOutput("data",Context.MODE_PRIVATE);
            msg= user.get(0) +"::"+user.get(1)+"::"+user.get(2)+"::"+avatar;
            fos.write(msg.getBytes());
            return true;
        }  catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static List<String> get_userinfos(Context context){
        FileInputStream fis=null;
        try {
            fis=context.openFileInput("data");
            byte[] buff=new byte[fis.available()];
            fis.read(buff);
            String msg=new String(buff);
            String[] userinfos=msg.split("::");
            List<String> userList=new LinkedList<>();
            userList.add(0,userinfos[0]);
            userList.add(1,userinfos[1]);
            userList.add(2,userinfos[2]);
            if(userinfos.length>3){
                userList.add(3,userinfos[3]);
            }
            return userList;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

