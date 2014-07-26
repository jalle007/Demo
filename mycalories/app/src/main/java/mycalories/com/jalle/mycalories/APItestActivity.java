package mycalories.com.jalle.mycalories;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class APItestActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restapi);
        ParseClass.Init(getApplicationContext()); //Init Parse object

        Button btnParseCloud = (Button) findViewById(R.id.btnParseCloud);
        // Listening to register new account link
        btnParseCloud.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                delete("BELDHuVaVc");
            }
        });

        Button btnSelect = (Button) findViewById(R.id.btnSelect);
        // Listening to register new account link
        btnSelect.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Calendar c = Calendar.getInstance();
                c.set(Calendar.HOUR_OF_DAY, 0);  c.set(Calendar.MINUTE, 0);
                Date from =  c.getTime();
                c.add(Calendar.DATE,1);
             Date to =   c.getTime();

                select("BELDHuVaVc", from,to);
            }
        });

        // UPDATE
        Button btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                final ParseObject meal=new ParseObject("Meals");
                meal.put("objectId","W1lZOdUxKd");
                meal.put("meal","meal1");
                meal.put("calories", 1);
                meal.put("date",new Date());
                meal.put("user","UyYpbK6E9f");
             /*   try {
                    meal.save();
                } catch (ParseException e) {
                    e.printStackTrace();
                }*/
                update(meal);
            }
        });

        // CREATE
        Button btnCreate = (Button) findViewById(R.id.btnCreate);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                final ParseObject meal=new ParseObject("Meals");
              //  meal.put("objectId","W1lZOdUxKd");
                meal.put("meal","new meal");
                meal.put("calories", 111);
                meal.put("date",new Date());
                meal.put("user","UyYpbK6E9f");
             /*   try {
                    meal.save();
                } catch (ParseException e) {
                    e.printStackTrace();
                }*/
                create(meal);
            }
        });
    }
   // Create meal
    void create(ParseObject meal){
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("meal",meal.get("meal"));
        params.put("user",meal.get("user"));
        //params.put("objectId",meal.get("objectId"));
        params.put("date",meal.get("date"));
        params.put("calories",meal.get("calories"));

        List<ParseObject> meals;
        ParseCloud.callFunctionInBackground("create", params, new FunctionCallback<String>() {
            @Override
            public void done(String result, ParseException e) {
                if (e == null) {
                   Utils.MyToast(e.getMessage(),getApplicationContext());
                }else
                {
                    //   Log.d("delete_error", result);
                    Utils.MyToast(e.getMessage(),getApplicationContext());
                }
            }
        });
    }
    // Update meal
    void update(ParseObject meal){
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("meal",meal.get("meal"));
        params.put("user",meal.get("user"));
        params.put("objectId",meal.getObjectId ().toString ());
        params.put("date",meal.get("date"));
        params.put("calories",meal.get("calories"));

        List<ParseObject> meals;
        ParseCloud.callFunctionInBackground("update", params, new FunctionCallback<String>() {
            @Override
            public void done(String result, ParseException e) {
                if (e == null) {
                   Utils.MyToast(e.getMessage(),getApplicationContext());
                }else
                {
                    //   Log.d("delete_error", result);
                    Utils.MyToast(e.getMessage(),getApplicationContext());
                }
            }
        });
    }

    // SELECT ( parameters from to )
    void select(String user, Date from, Date to){
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("user",user);
        params.put("from",from);
        params.put("to",to);
         List<ParseObject> meals;//J
        ParseCloud.callFunctionInBackground("select", params, new FunctionCallback<List<ParseObject>>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                   Utils.MyToast("Objects selected: " + objects.size (),getApplicationContext());

                    for (ParseObject meal : objects) {
                     String m1 =    meal.get("meal").toString() ;
                        String m2=m1;
                    }

                }else
                {
                 //   Log.d("delete_error", result);
                    Utils.MyToast(e.getMessage(),getApplicationContext());
                }
            }
        });
    }

    // Delete meal
    void delete(String objectid){
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("objectid",objectid);
        ParseCloud.callFunctionInBackground("delete", params, new FunctionCallback<String>() {
            @Override
            public void done(String result, ParseException e) {
                if (e == null) {
                    Log.d("delete", result);
                    Utils.MyToast("Deleted",getApplicationContext());
                }else
                {
                    Log.d("delete_error", result);
                    Utils.MyToast(e.getMessage(),getApplicationContext());
                }
            }
        });
    }
}
