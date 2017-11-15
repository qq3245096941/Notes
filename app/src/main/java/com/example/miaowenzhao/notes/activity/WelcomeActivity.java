package com.example.miaowenzhao.notes.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;

import com.example.miaowenzhao.notes.MainActivity;
import com.example.miaowenzhao.notes.R;
import com.example.miaowenzhao.notes.uitls.Uitls;

/**
 * Created by miaowenzhao on 2017/11/8.
 */

public class WelcomeActivity extends AppCompatActivity {
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    text_body.setVisibility(View.VISIBLE);
                    ObjectAnimator animator = ObjectAnimator.ofFloat(text_body, "alpha", 0f, 1f);
                    animator.setDuration(1000);
                    animator.setInterpolator(new AccelerateDecelerateInterpolator());
                    animator.start();
                    animator.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            handler.sendEmptyMessageDelayed(2,1000);
                        }
                    });
                    break;
                case 2:
                    startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                    WelcomeActivity.this.finish();
                    break;
            }
            return false;
        }
    });

    private TextView text_body;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        text_body = (TextView) findViewById(R.id.text_body);
        Uitls.setTypeface(text_body,getAssets());
        handler.sendEmptyMessageDelayed(1,500);
    }
}
