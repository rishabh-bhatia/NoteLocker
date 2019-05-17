package rishabh.notelocker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/* This class extends AppCompat because an AppCompat theme is used. */
public class SplashScreen extends AppCompatActivity {

    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //Loading Password to the app
        SharedPreferences settings = getSharedPreferences("PREFS", 0);
        password = settings.getString("password", "");

        Thread SplashSc = new Thread(){
            public void run(){
                try {
                    sleep(2000);
                    //If there's no password
                    if(password.equals(""))
                    {
                        Intent intent = new Intent(getApplicationContext(), CreatePasswordActivity.class);//Go to Create password Activity
                        startActivity(intent);
                        finish();//To finish the activity once another activity begins
                    }
                    //If there's a password already
                    else
                    {
                        Intent intent = new Intent(getApplicationContext(), EnterPasswordActivity.class);//Go to Enter password Activity
                        startActivity(intent);
                        finish();//To finish the activity once another activity begins
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };//Thread to run splashScreen
        SplashSc.start();//The thread SplashScreen thread will start
    }
}
