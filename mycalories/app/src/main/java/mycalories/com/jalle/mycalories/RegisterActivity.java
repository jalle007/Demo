package mycalories.com.jalle.mycalories;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class RegisterActivity extends BaseActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set View to register.xml
        setContentView(mycalories.com.jalle.mycalories.R.layout.register);
        final TextView name = (TextView) findViewById(R.id.reg_fullname);
        final TextView email = (TextView) findViewById(R.id.reg_email);
        final TextView pass = (TextView) findViewById(R.id.reg_password);
        final TextView caloriesPerDay = (TextView) findViewById(R.id.txtcaloriesPerDay);

        TextView loginScreen = (TextView) findViewById(R.id.link_to_login);
        // Listening to Login Screen link
        loginScreen.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {
				// Switching to Login Screen/closing register screen
			finish();
			}
		});

       //Register new user in Parse.com cloud
        Button register =(Button) findViewById(R.id.btnRegister);
        register.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                ParseUser user = new ParseUser();

                user.setUsername(email.getText().toString());
                user.setPassword(pass.getText().toString());
                user.setEmail(email.getText().toString());
                final int maxCalories=Integer.parseInt( caloriesPerDay.getText().toString());
                user.put("caloriesPerDay",maxCalories);
                // other fields can be set just like with ParseObject
                user.put("name", name.getText().toString());

                user.signUpInBackground(new SignUpCallback() {
                    public void done(ParseException e) {
                        if (e == null) {
                            Toast.makeText(getApplicationContext(), "Successfully registered.", Toast.LENGTH_SHORT).show();
                            Utils.SetKey("maxCalories",String.valueOf(maxCalories),getApplicationContext());
                            finish();

                        } else {
                           Log.d("err", e.toString());
                            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}