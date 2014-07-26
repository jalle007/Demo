package mycalories.com.jalle.mycalories;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Jalle on 14.7.2014..
 * methods:
 * isOnline(Context ctx)
 */

public class Utils {

    //Check internet connectivity (Wifi , 3g)
    public static boolean isOnline(Context ctx) {
        ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        MyToast("Please check your connection.", ctx);
        return false;
    }

    //Simple Toast function
    public static  void MyToast(String msg,Context ctx){
        Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
    }

    //Handle preferences
    public static  boolean SetKey(String key, String value,Context ctx){
        SharedPreferences sharedPreference= PreferenceManager.getDefaultSharedPreferences(ctx);
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putString(key,value);
        try {
            editor.commit();
            return true;
        }catch (    Exception ex)
        {
            Log.d("mycalories",ex.getMessage().toString());
            return false;
        }
    }
    public static  String GetKey(String key, Context ctx){
        SharedPreferences sharedPreference= PreferenceManager.getDefaultSharedPreferences(ctx);
        return  sharedPreference.getString(key,"");

    }

      // combine two textview dates into 1
    public static Date combineDates(EditText date1, EditText time1){
        if (date1==null || time1==null )
            return null;

        DateFormat df1 = DateFormat.getDateInstance((DateFormat.MEDIUM ));
        Date d1 = null, t1 = null;
        try {
            d1= df1.parse(date1.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat df = new SimpleDateFormat("hh:mm", Locale.ENGLISH);
        try {
            t1=df.parse(time1.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateTime(d1,t1);
    }
    // combine date and time and return date type
    public static Date dateTime(Date date, Date time) {
        Calendar calendarA = Calendar.getInstance();
        calendarA.setTime(date);
        Calendar calendarB = Calendar.getInstance();
        calendarB.setTime(time);

        calendarA.set(Calendar.HOUR_OF_DAY, calendarB.get(Calendar.HOUR_OF_DAY));
        calendarA.set(Calendar.MINUTE, calendarB.get(Calendar.MINUTE));
        calendarA.set(Calendar.SECOND, calendarB.get(Calendar.SECOND));
        calendarA.set(Calendar.MILLISECOND, calendarB.get(Calendar.MILLISECOND));

        return calendarA.getTime();
    }
}
