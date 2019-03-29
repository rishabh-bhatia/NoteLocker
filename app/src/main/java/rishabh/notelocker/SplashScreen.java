package rishabh.notelocker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Thread SplashSc = new Thread(){
            public void run(){
                try {
                    sleep(3000);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);//Go to MainActivity
                    startActivity(intent);
                    finish();//To finish the activity once another activity begins
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };//Thread to run splashScreen
        SplashSc.start();//The thread SplashScreen thread will start
    }
}
