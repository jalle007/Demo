package mycalories.com.jalle.mycalories;

import android.content.Context;
import android.util.Log;

import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * Created by Jalle on 13.7.2014..
 * Only init method is used here
 */
public class ParseClass {
public static final String APIKEY="eU0VtI9jfOT6mXxMtoZ7NwTk1NwEr0oa7RNEFDaY";
public static final String TOKEN="owykCd2YQ0zwsiFeyVuffOsECb2Kuk9236yui9KU";

// Initialize parse object
public static void Init(Context ctx){
        try {
            Parse.initialize(ctx, APIKEY, TOKEN);

          //  ParseUser.enableAutomaticUser();
            ParseACL defaultACL = new ParseACL();
            // If you would like all objects to be private by default, remove this line.
            defaultACL.setPublicReadAccess(true);
            ParseACL.setDefaultACL(defaultACL, true);

        }catch (Exception ex)
        {            Log.d("err", ex.getMessage().toString());
        } }

    public static void Save(String key, String value){
        try {

            ParseObject testObject = new ParseObject("TestObject");
            testObject.put(key,value);
            testObject.saveInBackground();
            Log.d("saved", value);
        }catch (Exception ex)
        {
            Log.d("saved", ex.getMessage().toString());
        } }

    public static String Read(String key){
        try {

            final ParseObject testObject = new ParseObject("TestObject");

            ParseQuery<ParseObject> query = ParseQuery.getQuery("TestObject");
            query.getInBackground("8Trr0WvjUa", new GetCallback<ParseObject>() {
                public void done(ParseObject object, ParseException e) {
                    if (e == null) {
                        String objectId = testObject.getObjectId();
                       String key =  testObject.getString("key");
                        Log.d("Read", key);
                    } else {
                        Log.d("Read", "err");
                    }
                }

            });
        }catch (Exception ex)
        {
            Log.d("saved", ex.getMessage().toString());
        }
        return "";
    }
}
//
//Application ID  eU0VtI9jfOT6mXxMtoZ7NwTk1NwEr0oa7RNEFDaY
//Client Key  owykCd2YQ0zwsiFeyVuffOsECb2Kuk9236yui9KU
//Javascript Key  dG0L1x7rYlWfb35r3kI5zNbAbcmP1tl2IxrqjFZw
// .NET Key   sT2yylOtK4fCgrCoECKWxm589nPaUfK8YwcMQVUf
//REST API Key    vE5SqMzVcrX8mhq9Yfsc8Vyvxps1Nk2IIpHAIxJ5
//Master Key  B1dIHNB2wsbpRgelbW5SuyyB6tGKKDZ8ezvfACze