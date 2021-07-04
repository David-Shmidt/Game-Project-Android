package syntax.org.il.gameproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN = 3100;

    //variables
    Animation upAnim;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setContentView(R.layout.splash);

            //Animations
            upAnim = AnimationUtils.loadAnimation(this,R.anim.up_anim);
            image = findViewById(R.id.splash_imageView);
            image.setAnimation(upAnim);

            //Move to another activity
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                 Intent intent = new Intent(SplashActivity.this,StartActivity.class);
                    startActivity(intent);
                    finish();
             }
         },SPLASH_SCREEN);

    }
}
