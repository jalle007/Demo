package mycalories.com.jalle.mycalories;

/*
 * This is an example test project created in Eclipse to test NotePad which is a sample 
 * project located in AndroidSDK/samples/android-11/NotePad
 * 
 * 
 * You can run these test cases either on the emulator or on device. Right click
 * the test project and select Run As --> Run As Android JUnit Test
 * 
 * @author Renas Reda, renas.reda@robotium.com
 * 
 */

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;



public class LoginActivityTest extends ActivityInstrumentationTestCase2<LoginActivity> {

private Solo solo;

public LoginActivityTest() {
   super(LoginActivity.class);

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

public void testLogin() throws Exception {

   //Unlock the lock screen
   solo.unlockScreen();

}
}
