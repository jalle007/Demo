package mycalories.com.jalle.mycalories;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.ListView;

import com.robotium.solo.Solo;

public class MealsActivityTest extends ActivityInstrumentationTestCase2<MealsActivity> {
private Solo solo;

public MealsActivityTest() {
   super(MealsActivity.class);
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

public void testAddMeal() throws Exception {
   //Unlock the lock screen
   solo.unlockScreen();
   solo.assertCurrentActivity("Expected mealEditor activity", "MealsActivity");
  // solo.clickOnMenuItem("Settings");

   //add
   solo.clickOnImage (1);
   //new meal
   solo.enterText(0, "meal 1");
   //date
  /* solo.clickOnButton(0);
   solo.sendKey (solo.ENTER);
   //time
   solo.clickOnButton(1);
   solo.sendKey (solo.ENTER);*/

   //calories
   solo.enterText(3, "100");
   //save
   solo.clickOnButton(3);
   solo.goBack ();

   solo.sleep(2000);
   //get the list view
   ListView myList = (ListView)solo.getView(R.id.lv);

   assertNotNull("No list views!", myList);
   assertTrue("No items in list view!", myList.getChildCount() > 0);

/*   solo.clickOnMenuItem("Add meal");
   //Assert that mealEditor activity is opened

   //In text field 0, enter meal 1
   solo.enterText(0, "meal 1");
   solo.goBack();
   //Clicks on menu item
   solo.clickOnMenuItem("Add meal");
   //In text field 0, type meal 2
   solo.typeText(0, "meal 2");
   //Go back to first activity
   solo.goBack();
   //Takes a screenshot and saves it in "/sdcard/Robotium-Screenshots/".
   solo.takeScreenshot();
   boolean mealsFound = solo.searchText("meal 1") && solo.searchText("meal 2");
   //Assert that meal 1 & meal 2 are found
   assertTrue("meal 1 and/or meal 2 are not found", mealsFound);*/

}
/*
public void testEditmeal() throws Exception {
   // Click on the second list line
   solo.clickInList(2);
   //Hides the soft keyboard
   solo.hideSoftKeyboard();
   // Change orientation of activity
   solo.setActivityOrientation(Solo.LANDSCAPE);
   // Change title
   solo.clickOnMenuItem("Edit title");
   //In first text field (0), add test
   solo.enterText(0, " test");
   solo.goBack();
   solo.setActivityOrientation(Solo.PORTRAIT);
   // (Regexp) case insensitive
   boolean mealFound = solo.waitForText("(?i).*?meal 1 test");
   //Assert that meal 1 test is found
   assertTrue("meal 1 test is not found", mealFound);

}

public void testRemovemeal() throws Exception {
   //(Regexp) case insensitive/text that contains "test"
   solo.clickOnText("(?i).*?test.*");
   //Delete meal 1 test
   solo.clickOnMenuItem("Delete");
   //meal 1 test should not be found
   boolean mealFound = solo.searchText("meal 1 test");
   //Assert that meal 1 test is not found
   assertFalse("meal 1 Test is found", mealFound);
   solo.clickLongOnText("meal 2");
   //Clicks on Delete in the context menu
   solo.clickOnText("Delete");
   //Will wait 100 milliseconds for the text: "meal 2"
   mealFound = solo.waitForText("meal 2", 1, 100);
   //Assert that meal 2 is not found
   assertFalse("meal 2 is found", mealFound);
}*/
}
