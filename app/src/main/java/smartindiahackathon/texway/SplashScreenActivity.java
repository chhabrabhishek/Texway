package smartindiahackathon.texway;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        changeStatusBarColor();
        TextView textView=(TextView) findViewById(R.id.texway);

        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/BlendaScript.otf");
        textView.setTypeface(custom_font);

        Thread mythread=new Thread(){
            @Override
            public void run(){
                try {
                    sleep(2000);
                    Intent intent=new Intent(getApplicationContext(),WelcomeActivity.class);
                    startActivity(intent);
                    finish();
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            }

        };
        mythread.start();
    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#3E85F7"));
        }
    }
}
