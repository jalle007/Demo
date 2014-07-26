package mycalories.com.jalle.mycalories;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.parse.ParseUser;


// This base activity extends in all activities that require Menu screen
public class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    /*  Menu code
    // 1. My settings - used toset Max calories per day
    // 2. Logout - logout and switch to loginscreen
     */
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
