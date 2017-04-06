package com.smart.reboundspringdemo;

import android.os.Bundle;
import android.os.Handler;
import android.support.animation.SpringAnimation;
import android.support.animation.SpringForce;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringSystem;

public class MainActivity extends AppCompatActivity {

    View mViewShow1, mViewShow2, mViewShow3, mViewShow4, mViewShow5, mViewShow6, mViewShow7, mViewShow8;
    View mViewShow11, mViewShow12, mViewShow13, mViewShow14, mViewShow15, mViewShow16, mViewShow17, mViewShow18;
    boolean open = false;

    Spring mSpring = SpringSystem.create()
            .createSpring()
//            .setVelocity(5)
            //张力系数、阻力系数
//            ;
            .setSpringConfig(new SpringConfig(100, 20));
    boolean openRebound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        springAnimation();
        rebound();
    }

    private void rebound() {
        mViewShow11 = findViewById(R.id.view_show11);
        mViewShow12 = findViewById(R.id.view_show12);
        mViewShow13 = findViewById(R.id.view_show13);
        mViewShow14 = findViewById(R.id.view_show14);
        mViewShow15 = findViewById(R.id.view_show15);
        mViewShow16 = findViewById(R.id.view_show16);
        mViewShow17 = findViewById(R.id.view_show17);
        mViewShow18 = findViewById(R.id.view_show18);
        findViewById(R.id.iv_action_show_rebound).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controlByRebound();
                if (!openRebound) {
                    openRebound = true;
                } else {
                    openRebound = false;
                }
            }
        });
        mSpring.addListener(new SimpleSpringListener() {
            @Override
            public void onSpringUpdate(Spring spring) {
                float currentValue = (float) spring.getCurrentValue();
                float scale = 1 - currentValue;
                controlByScale(mViewShow11, scale, 0);
                controlByScale(mViewShow12, scale, 110);
                controlByScale(mViewShow13, scale, 220);
                controlByScale(mViewShow14, scale, 330);
                controlByScale(mViewShow15, scale, 110);
                controlByScale(mViewShow16, scale, 220);
                controlByScale(mViewShow17, scale, 330);
                controlByScale(mViewShow18, scale, 440);
            }
        });
    }

    private void controlByScale(final View view, final float scale, int delay) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                view.setScaleX(scale);
                view.setScaleY(scale);
            }
        }, delay);
    }

    private void controlByRebound() {
        mSpring.setEndValue(openRebound ? 0 : 1);
    }

    private void springAnimation() {
        mViewShow1 = findViewById(R.id.view_show1);
        mViewShow2 = findViewById(R.id.view_show2);
        mViewShow3 = findViewById(R.id.view_show3);
        mViewShow4 = findViewById(R.id.view_show4);
        mViewShow5 = findViewById(R.id.view_show5);
        mViewShow6 = findViewById(R.id.view_show6);
        mViewShow7 = findViewById(R.id.view_show7);
        mViewShow8 = findViewById(R.id.view_show8);
        findViewById(R.id.iv_action_show).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controlBySpringAnim(mViewShow1, open, 0);
                controlBySpringAnim(mViewShow2, open, 110);
                controlBySpringAnim(mViewShow3, open, 220);
                controlBySpringAnim(mViewShow4, open, 330);
                controlBySpringAnim(mViewShow5, open, 110);
                controlBySpringAnim(mViewShow6, open, 220);
                controlBySpringAnim(mViewShow7, open, 330);
                controlBySpringAnim(mViewShow8, open, 440);
                if (!open) {
                    open = true;
                } else {
                    open = false;
                }
            }
        });
    }

    private void controlBySpringAnim(View view, boolean open, int delay) {
        float start = 0;
        float end = 1;
        if (open) {
            start = 0;
            end = 1;
        } else {
            start = 1;
            end = 0;
        }
        SpringForce springForce = new SpringForce(end)
                //阻力
                .setDampingRatio(1.0f)
//                .setDampingRatio(SpringForce.DAMPING_RATIO_NO_BOUNCY)
                //张力
                .setStiffness(100.0f);
//                .setStiffness(SpringForce.STIFFNESS_VERY_LOW);
        final SpringAnimation springX = new SpringAnimation(view, SpringAnimation.SCALE_X).setSpring(springForce);
        final SpringAnimation springY = new SpringAnimation(view, SpringAnimation.SCALE_Y).setSpring(springForce);
        springX.cancel();
        springY.cancel();
        springX.setStartValue(start);
        springY.setStartValue(start);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                springX.start();
                springY.start();
            }
        }, delay);
    }
}
