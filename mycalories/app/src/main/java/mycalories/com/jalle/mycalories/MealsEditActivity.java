package mycalories.com.jalle.mycalories;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseUser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import mycalories.com.jalle.mycalories.DatePickerFragment.OnDateSetListener;
import mycalories.com.jalle.mycalories.TimePickerFragment.OnTimeSetListener;

// Used to create or Edit meal
public class MealsEditActivity extends BaseActivity  {
    public DateFormat df1 = DateFormat.getDateInstance((DateFormat.MEDIUM ));
    public EditText txtMeal, txtDate,txtTime, txtCalories;

    private int position;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meals_edit);
        txtMeal =(EditText) findViewById(R.id.txtMeal);
        txtDate = (EditText) findViewById(R.id.txtDate);
        txtTime = (EditText) findViewById(R.id.txtTime);
        txtCalories = (EditText) findViewById(R.id.txtCalories);

        Button btnDate = (Button) findViewById(R.id.btnDate);
        btnDate.setOnClickListener(new View.OnClickListener() {
                                       public void onClick(View v) {
                                           DatePickerFragment datePicker = new DatePickerFragment();
                                           datePicker.show(getFragmentManager(), "datePicker");
                                           datePicker.setOnDateSetListener(new OnDateSetListener() {

                                               public void onDateSet(Date date) {
                                                    final EditText txtDate=(EditText) findViewById(R.id.txtDate);

                                                   txtDate.setText(df1.format(date));
                                               }
                                           });
           }
       });

        Button btnTime = (Button) findViewById(R.id.btnTime);
        btnTime.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TimePickerFragment timePicker = new TimePickerFragment();
                timePicker.show(getFragmentManager(), "timePicker");
                timePicker.setOnTimeSetListener(new OnTimeSetListener() {

                     @Override
                    public void onTimeSet(Time time) {
                        SimpleDateFormat HHMM = new SimpleDateFormat("hh:mm");
                       txtTime.setText(time.hour + ":" + time.minute + "");
                 }
                });
            }
        });

        Button btnCancel = (Button) findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               finish();
                    }});

        //create meal
        Bundle extras = getIntent().getExtras();
        SimpleDateFormat   tf = new SimpleDateFormat("hh:mm", Locale.ENGLISH);

        //E diting meal
        if (extras != null) {
            position = extras.getInt("position");
            txtMeal.setText(  extras.getString("meal"));
            txtCalories.setText( (String.valueOf(extras.getInt("calories"))));
            Date d = new Date (extras.getLong("date"));
            txtDate.setText(df1.format(d).toString());
            txtTime.setText(tf.format(d).toString());
        }else   //set current time for new meal
        {
            Calendar c = Calendar.getInstance();
            txtDate.setText(df1.format(c.getTime()));
            txtTime.setText(tf.format(c.getTime()));
        }

        //Save meal
        Button btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if( (txtDate.getText().toString().matches("") ) || (txtTime.getText().toString().matches("") )
                        ||(txtMeal.getText().toString().matches("") )  ||(txtCalories.getText().toString().matches("") ))
                {
                    Utils.MyToast("Please fill all values", getApplicationContext());
                    return ;
                }
                Bundle bundle = new Bundle();

                 //combine date & time
                Date newDate = Utils.combineDates(txtDate,txtTime);
                if (newDate == null){
                    Utils.MyToast("Please fill in all values", getApplicationContext());
                    return ;
                }
                long millis = newDate.getTime(); //pass date as long

                bundle.putLong("date", millis);
                bundle.putString("meal", txtMeal.getText().toString());
                bundle.putInt("calories",Integer.parseInt ( txtCalories.getText().toString()));
                bundle.putString("user", ParseUser.getCurrentUser().getObjectId().toString());
                bundle.putInt("position", position);

                Intent intent = new Intent();
                intent.putExtras(bundle);

                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }


    //Preferences menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, 0, 0, "My settings");
        menu.add(Menu.NONE, 1, 0, "Logout");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                startActivity(new Intent(this, PrefsActivity.class));
                return true;
            case 1:
                ParseUser.logOut();
                startActivity(new Intent(this, LoginActivity.class));
                Utils.MyToast("Logout", getApplicationContext());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

