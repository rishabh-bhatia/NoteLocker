package rishabh.notelocker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/* This class extends AppCompat because an AppCompat theme is used. */
public class EnterPasswordActivity extends AppCompatActivity {

    EditText e1;
    Button button;

    String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_password);

        //Loading Password to the app
        SharedPreferences settings = getSharedPreferences("PREFS", 0);
        password = settings.getString("password", "");

        e1 = findViewById(R.id.editText3);
        button = findViewById(R.id.button3);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s1 = e1.getText().toString();

                if (s1.equals(password))
                {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);//Go to Main Activity
                    startActivity(intent);
                    finish();//To finish the activity once another activity begins
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Wrong Password!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
