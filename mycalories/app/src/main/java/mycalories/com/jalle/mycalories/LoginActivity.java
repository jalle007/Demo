package mycalories.com.jalle.mycalories;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

// Main activity
public class LoginActivity extends BaseActivity {

      @Override
    public void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(mycalories.com.jalle.mycalories.R.layout.login);

         // check internet connection
          if (Utils.isOnline(getApplicationContext()))
              ParseClass.Init(getApplicationContext()); //Init Parse object

         // Listening to register new account link
          TextView registerScreen = (TextView) findViewById(R.id.link_to_register);
           registerScreen.setOnClickListener(new View.OnClickListener() {
              public void onClick(View v) {
                  // Switching to Register screen
                  Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                  startActivity(i);
              }
          });
          final TextView email = (TextView) findViewById(R.id.txtEmal);
          final TextView pass = (TextView) findViewById(R.id.txtPassword);

         // Listening to login link
          TextView btnLogin = (TextView) findViewById(R.id.btnLogin);
          btnLogin.setOnClickListener(new View.OnClickListener() {
              public void onClick(View v) {
                  if (Utils.isOnline(getApplicationContext())) {
                      ParseUser.logInInBackground(email.getText().toString(), pass.getText().toString(), new LogInCallback() {
                          public void done(ParseUser user, ParseException e) {
                              if (user != null) {
                                  // Switching to Meals screen after user has succesfull loged in
                                 // in this case user will not be presented with login screen until he is logged in
                                  Intent i = new Intent(getApplicationContext(), MealsActivity.class);
                                     startActivity(i);
                                  Toast.makeText(getApplicationContext(), "You are logged in", Toast.LENGTH_SHORT).show();
                              } else {
                                  Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                              }
                          }
                      });
                  }
              }
          });
      }

    @Override
    protected void onResume() {
        super.onResume();
        if ( Utils.isOnline (getApplicationContext ()) && ParseUser.getCurrentUser() !=null)
        {
            Intent i = new Intent(getApplicationContext(), MealsActivity.class);
            startActivity(i);
        }
    }

   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 1:
                showUserSettings();
                break;
        }
    }

    private void showUserSettings() {
        SharedPreferences sharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this);

    }*/
}
