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
public class CreatePasswordActivity extends AppCompatActivity {

    EditText e1, e2;//Initializing editable textfields
    Button button;//initializing the button

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_password);

        e1 = findViewById(R.id.editText);
        e2 = findViewById(R.id.editText2);
        button = findViewById(R.id.button2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s1 = e1.getText().toString();
                String s2 = e2.getText().toString();

                //Setting password
                if(s1.equals("") || s2.equals(""))//Password hasn't been entered
                {
                    Toast.makeText(CreatePasswordActivity.this, "Please enter a password", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if (s1.equals(s2))//Passwords match so set password
                    {
                        SharedPreferences setiings = getSharedPreferences("PREFS", 0);
                        SharedPreferences.Editor editor = setiings.edit();
                        editor.putString("password", s1);
                        editor.apply();

                        //Go to the Main Activity
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);//Go to Main Activity
                        startActivity(intent);
                        finish();//To finish the activity once another activity begins
                    }
                    else//Passwords don't match
                    {
                        Toast.makeText(CreatePasswordActivity.this, "Passwords don't match", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
