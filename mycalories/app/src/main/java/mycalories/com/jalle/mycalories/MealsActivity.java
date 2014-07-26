package mycalories.com.jalle.mycalories;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MealsActivity extends BaseActivity {
    private static final int ACTIVITY_CREATE = 0;
    private static final int ACTIVITY_EDIT = 1;
    final int icon = R.drawable.meal;
    public int maxCalories ;
    public String  maxC;
    public Date fromDate, toDate;
    public TextView txtUser, txtCalories, txtMax,txtFromTo;
    public EditText txtFromDate,txtFromTime,txtToDate,txtToTime;
    public List<ParseObject> meals=  new ArrayList<ParseObject>();
   public DateFormat df1 = DateFormat.getDateInstance((DateFormat.MEDIUM ));
    public DateFormat df = new SimpleDateFormat ("yyyy.MM.dd hh:mm");
    private Dialog progressDialog;
    public ListView lv;
    public int totalCalories=0;

    @Override
    protected void onResume() {
        super.onResume();
        refreshTotals();
    }

       /* refresh bottom line with total calories and max calories
       * set color:
       * RED - if totalCalories > MaxCalories per day
       * GREEN- if totalCalories <= MaxCalories per day
        */
      private void refreshTotals() {
        maxC = Utils.GetKey("maxCalories", getApplicationContext());
        maxCalories =(maxC.matches("")?0: Integer.parseInt(maxC));
        txtMax =(TextView) findViewById(R.id.txtMax);
        txtMax.setText("Max calories: " + maxCalories);
        txtMax.setText("Max calories: " + maxCalories);

        txtCalories=(TextView) findViewById(R.id.txtCalories);
        txtCalories.setText(String.valueOf(totalCalories));
        txtCalories.setTextColor(totalCalories <maxCalories ? getResources().getColor(R.color.green): getResources().getColor(R.color.red));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meals);
        txtUser=(TextView) findViewById(R.id.txtUser);
       txtFromTo=(TextView) findViewById (R.id.txtFromTo);

           maxC =Utils.GetKey("maxCalories", getApplicationContext());
            maxCalories =(maxC.matches("")?0: Integer.parseInt(maxC));

        ParseClass.Init(getApplicationContext());

       // Button to add meal
       final ImageView imgAdd = (ImageView) findViewById(R.id.imgAdd);
       imgAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(MealsActivity.this, MealsEditActivity.class);
                startActivityForResult(i, ACTIVITY_CREATE);
            }
        });

      /*Set filter menu
             used to filter meals between 2 dates
              */
        final LinearLayout layfilter=(LinearLayout)this.findViewById(R.id.layFilter);
        layfilter.bringToFront(); layfilter.invalidate();
        Button btnhide = (Button) findViewById(R.id.btnhide);
        btnhide.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Switch filter visibility
                layfilter.setVisibility(layfilter.getVisibility() ==LinearLayout.VISIBLE ?LinearLayout.GONE:LinearLayout.VISIBLE);
            }
        });
       initFilter();

       //refresh list
       new RemoteDataTask().execute();

       //Set filter button event
       Button btnFilter = (Button) findViewById(R.id.btnFilter);
       btnFilter.setOnClickListener(new View.OnClickListener() {
           public void onClick(View v) {
               //switch filter visibility
                fromDate =Utils.combineDates(txtFromDate,txtFromTime);
                toDate =Utils.combineDates(txtToDate,txtToTime);
               if (fromDate == null || toDate == null)
               {
                   Utils.MyToast("Please fill in all values", getApplicationContext());
                   return ;
               }
               layfilter.setVisibility(LinearLayout.GONE);
               new RemoteDataTask().execute();
           }
       });
    }

    /*Initialize fitler settings
    by default fitler is set to current date with hours from 00:00 to 00:00 next day
     */
    public void initFilter() {
        //from date
        Button btnFromDate = (Button) findViewById(R.id.btnFromDate);
        txtFromDate=(EditText) findViewById(R.id.txtFromDate);

        btnFromDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DatePickerFragment datePicker = new DatePickerFragment();
                datePicker.show(getFragmentManager(), "datePicker");
                datePicker.setOnDateSetListener(new DatePickerFragment.OnDateSetListener() {

                    public void onDateSet(Date date) {

                        txtFromDate.setText(df1.format(date));
                        if (txtFromTime.getText().toString().matches(""))
                            txtFromTime.setText("00:00");
                    }
                });
            }
        });

        //from time
        Button btnFromTime = (Button) findViewById(R.id.btnFromTime);
        txtFromTime = (EditText) findViewById(R.id.txtFromTime);
        btnFromTime.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TimePickerFragment timePicker = new TimePickerFragment();
                timePicker.show(getFragmentManager(), "timePicker");
                timePicker.setOnTimeSetListener(new TimePickerFragment.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(Time time) {

                        //  String t = new   SimpleDateFormat("hh:mm").format(time);
                        txtFromTime.setText(time.hour + ":" + time.minute + "");
                    }
                });
            }
        });

        //To date
        Button btnToDate = (Button) findViewById(R.id.btnToDate);
        txtToDate=(EditText) findViewById(R.id.txtToDate);
        btnToDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DatePickerFragment datePicker = new DatePickerFragment();
                datePicker.show(getFragmentManager(), "datePicker");
                datePicker.setOnDateSetListener(new DatePickerFragment.OnDateSetListener() {

                    public void onDateSet(Date date) {

                        txtToDate.setText(df1.format(date));
                        if (txtToTime.getText().toString().matches("")) txtToTime.setText("00:00");
                    }
                });
            }
        });

        //To time
        Button btnToTime = (Button) findViewById(R.id.btnToTime);
        txtToTime = (EditText) findViewById(R.id.txtToTime);

        btnToTime.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TimePickerFragment timePicker = new TimePickerFragment();
                timePicker.show(getFragmentManager(), "timePicker");
                timePicker.setOnTimeSetListener(new TimePickerFragment.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(Time time) {

                        //  String t = new   SimpleDateFormat("hh:mm").format(time);
                       txtToTime.setText(time.hour + ":" + time.minute + "");

                    }
                });
            }
        });
    }

    // Load list
    private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
        protected Void doInBackground(Void... params) {
           if (fromDate != null && toDate != null)
            {
               ;
            }else
           // if both parameters are nulll then set dates to today
            {  Calendar c = Calendar.getInstance();
                c.set(Calendar.HOUR_OF_DAY, 0);  c.set(Calendar.MINUTE, 0);
               fromDate=  c.getTime();
                c.add(Calendar.DATE, 1);
               toDate =   c.getTime();
            }

           // Gets the current list of meals in sorted order
           Select( fromDate, toDate);
           return null;
        }

        @Override
        protected void onPreExecute() {
            MealsActivity.this.progressDialog = ProgressDialog.show(MealsActivity.this, "", "Loading...", true);
            super.onPreExecute(); }
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void result) {
           FillList ();
        }
    }

    //This method is run after user finished editing / adding meal item
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (intent == null) {
            return;
        }
        final Bundle extras = intent.getExtras();

        switch (requestCode) {

            case ACTIVITY_CREATE: //Create new meal object
               Long newLong = extras.getLong("date");
               Date newDate =new Date (newLong) ;

               final ParseObject meal = new ParseObject("Meals");
               meal.put("meal", extras.getString("meal"));
               meal.put("date",newDate);
               meal.put("calories",extras.getInt("calories"));
               meal.put("user",ParseUser.getCurrentUser().getObjectId().toString());

               // Call to API function Create meal
               Create (meal);
                break;

            case ACTIVITY_EDIT: // Edit the remote object
               final ParseObject meal2;
               meal2 = meals.get(extras.getInt("position"));
               meal2.put("meal", extras.getString("meal"));
               meal2.put("calories", extras.getInt("calories"));
               meal2.put("date",new Date (extras.getLong("date", -1)));

               // call to API function Update meal
               Update (meal2);
                break;
        }
    }


// Create meal method
public  void  Create(ParseObject meal){
   HashMap<String, Object> params = new HashMap<String, Object>();
   params.put("meal",meal.get("meal"));
   params.put("user",meal.get("user"));
   //params.put("objectId",meal.get("objectId"));
   params.put("date",meal.get("date"));
   params.put("calories",meal.get("calories"));

   //callback function call to Parse cloud code API
   ParseCloud.callFunctionInBackground("create", params, new FunctionCallback<String> () {
      @Override
      public void done(String result, ParseException e) {
         if (e == null) {
            new RemoteDataTask().execute();
            Utils.MyToast (result, getApplicationContext ());
         } else {
            Log.d("create_error", result);
             Utils.MyToast (e.getMessage (), getApplicationContext ());
         }
      }
   });
}
// Update meal
public void Update(ParseObject meal){
   HashMap<String, Object> params = new HashMap<String, Object>();
   params.put("meal",meal.get("meal"));
   params.put("user",meal.get("user"));
   params.put("objectId",meal.getObjectId ().toString ());
   params.put("date",meal.get("date"));
   params.put("calories",meal.get("calories"));

   //callback function call to Parse cloud code API
   ParseCloud.callFunctionInBackground("update", params, new FunctionCallback<String>() {
      @Override
      public void done(String result, ParseException e) {
         if (e == null) {
            new RemoteDataTask().execute();
            Utils.MyToast(result,getApplicationContext());
         }else
         {
            Log.d("update_error", result);
            Utils.MyToast(e.getMessage(),getApplicationContext());
         }
      }
   });
}

// SELECT ( parameters from to )
public void Select( Date from, Date to){
   HashMap<String, Object> params = new HashMap<String, Object>();
   params.put("user",ParseUser.getCurrentUser ().getObjectId ().toString ());
   params.put("from",from);
   params.put("to",to);

   //callback function call to Parse cloud code API
   ParseCloud.callFunctionInBackground("select", params, new FunctionCallback<List<ParseObject>>() {
      @Override
      public void done(List<ParseObject> objects, ParseException e) {
         if (e == null) {
           if (   meals != null) meals.clear ();
            for (ParseObject obj : objects) {
              meals.add( obj);
            }
            // Fill list view with seleced meals
            FillList ();
         }else
         {
            Log.d("error", "select error");
              Utils.MyToast(e.getMessage(),getApplicationContext());
         }
      }
   });
}

// Delete meal
public  void Delete(String objectid){

   HashMap<String, Object> params = new HashMap<String, Object>();
   params.put("objectid",objectid);
   ParseCloud.callFunctionInBackground ("delete", params, new FunctionCallback<String> () {
      @Override
      public void done(String result, ParseException e) {
         if (e == null) {
            Log.d ("delete", result);

            Utils.MyToast(result,getApplicationContext());
         } else {
            Log.d ("delete_error", result);
            Utils.MyToast(e.getMessage(),getApplicationContext());
         }
      }
   });
}

// Put the list of meals into the list view
   void FillList()
   {
   List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>> ();
   if (meals != null) {
      totalCalories=0;
      for (ParseObject meal : meals) {

         HashMap<String, String> hm = new HashMap<String,String>();
         hm.put("meal", (String) meal.get("meal"));

         Date datum = (Date) meal.getDate("date");
         DateFormat df = new SimpleDateFormat ("yyyy.MM.dd hh:mm");

         String reportDate = df.format(datum);

         hm.put("date",reportDate);
         int calories = (Integer) meal.getInt("calories");
         hm.put("calories",   String.valueOf(calories) );
         hm.put("icon", Integer.toString(icon) );
         aList.add(hm);
         totalCalories+=calories;
      }
   }

   // Keys used in Hashmap
   String[] from = { "icon","meal","date", "calories" };
   // Ids of views in listview_layout
   int[] to = { R.id.icon,R.id.meal,R.id.date, R.id.calories};
   // R.layout.listview_layout defines the layout of each item
   SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), aList, R.layout.meal_row, from, to);
   // Getting a reference to listview of main.xml layout file
   lv = ( ListView ) findViewById(R.id.lv);
   //we add context menu for Delete
   registerForContextMenu(lv);
   //ON item click open EditMeal actrivity
   lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
         Intent i = new Intent(getApplicationContext(), MealsEditActivity.class);
         String m =  meals.get(position).getString("meal").toString();
         i.putExtra("meal",m);
         i.putExtra("user",ParseUser.getCurrentUser().getObjectId().toString());
         i.putExtra("calories",meals.get(position).getInt("calories"));
         i.putExtra("position", position);
         Date d =  meals.get(position).getDate("date");
         i.putExtra("date", d.getTime());

         try {
            startActivityForResult(i, ACTIVITY_EDIT);
         }catch (Exception ex)
         {         Log.d ("tag", ex.getMessage ().toString ());  }
      }
   });

   // Setting the adapter to the listView
   lv.setAdapter(adapter);

   String title ="Welcome, ";
   title += ParseUser.getCurrentUser().get("name").toString(); // get user name
   txtUser.setText( title );
   txtFromTo.setText ("Meals from : " + df.format (fromDate) + "\r\nto : " + df.format(toDate));
   refreshTotals();
   MealsActivity.this.progressDialog.dismiss();
}

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, 0, 0, "Delete");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

                // Delete the remote object
                final ParseObject meal = meals.get(info.position);
                Delete (meal.getObjectId ().toString ());

               // Refresh list
                new RemoteDataTask().execute();

                return true;
        }
        return super.onContextItemSelected(item);
    }
}