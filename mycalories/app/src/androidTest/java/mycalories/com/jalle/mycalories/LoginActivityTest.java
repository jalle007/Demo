package mycalories.com.jalle.mycalories;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;

import com.parse.ParseUser;
import com.robotium.solo.Solo;

public class LoginActivityTest extends ActivityInstrumentationTestCase2<LoginActivity> {
private Solo solo;

public LoginActivityTest() {
   super(LoginActivity.class);
}

public void testLogin() throws Exception {

   solo.unlockScreen();
   //logout
   ParseUser.logOut ();
   solo.sleep(2000);

   solo.assertCurrentActivity("Expected Login activity", "LoginActivity");
   // solo.clickOnMenuItem("Settings");
   Activity currentActivity=solo.getCurrentActivity ();

  // solo.goBack ();
   //Sign in the user
   //Email
   solo.enterText(0, "jaskobh@hotmail.com");
   //password
   solo.enterText(1, "123");
   //login
  solo.clickOnButton(0);

    solo.sleep(2000);
   //get the list view
    currentActivity=solo.getCurrentActivity ();


   solo.assertCurrentActivity("Login unsucessful. Expected Meals activity", "MealsActivity");
}

@Override
public void setUp() throws Exception {
   //setUp() is run before a test case is started.
   //This is where the solo object is created.
   solo = new Solo(getInstrumentation(), getActivity());

}

@Override
public void tearDown() throws Exception {
   //tearDown() is run after a test case has finished.
   //finishOpenedActivities() will finish all the activities that have been opened during the test execution.
   solo.finishOpenedActivities();
}

}
