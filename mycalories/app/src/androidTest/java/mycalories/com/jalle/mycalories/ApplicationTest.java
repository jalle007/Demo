package mycalories.com.jalle.mycalories;

import android.app.Application;
import android.test.ApplicationTestCase;

public class  ApplicationTest extends ApplicationTestCase<Application> {
public ApplicationTest () {
   super(Application.class);
}

public ApplicationTest (Class<Application> applicationClass) {
   super(applicationClass);
}

@Override
protected void setUp() throws Exception {
   super.setUp();
   createApplication();
   Main();
}

public void Main() throws Exception {
   //start Login test
   LoginActivityTest loginTest=new LoginActivityTest();
   loginTest.testLogin ();

   //start Meals add test
   MealsActivityTest testAddMeal=new MealsActivityTest();
   testAddMeal.testAddMeal ();
}
}