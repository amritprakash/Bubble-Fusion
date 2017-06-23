package co.amrit.bubbler;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

import java.util.ArrayList;
import java.util.List;

import static co.amrit.bubbler.AdUtil.displayAdAfterClick;
import static co.amrit.bubbler.AdUtil.displayAdAfterFusion;
import static co.amrit.bubbler.AdUtil.displayInterstitialAd;
import static co.amrit.bubbler.BubbleUtil.calculateHighBubblePoint;
import static co.amrit.bubbler.BubbleUtil.decreaseBubblePoint;
import static co.amrit.bubbler.BubbleUtil.initialBubblePoint;
import static co.amrit.bubbler.BubbleUtil.textAsBitmap;
import static co.amrit.bubbler.BubbleUtil.updateHighScore;

/**
 * Created by Amrit on 28-05-2017.
 */

public class Bubble extends FloatingActionButton {

    private Bubble bubble;
    private static int animationDuration = 500;
    private static int animationZeroDuration = 250;
    private static final String LOG_TAG = "Bubble";
    Animation enlargeAnimation;
    Animation shrinkAnimation;
    Animation shrinkToZeroAnimation;
    Animation enlargeToOriginalAnimation;
    int bubblePoint = 7;
    List<Bubble> neighbourBubbles = new ArrayList<Bubble>();

    public Bubble(Context context){
        super(context, null, 0);
        setRandomColor();
        setBubblePoint(initialBubblePoint);
        initializeAnimations();
    }

    public Bubble(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        setRandomColor();
        setBubblePoint(initialBubblePoint);
        initializeAnimations();
    }

    public Bubble(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setRandomColor();
        setBubblePoint(initialBubblePoint);
        initializeAnimations();
    }

    @Override
    public void setBackgroundColor(@ColorInt int color) {
        Log.i(LOG_TAG, "Setting a custom background.");
        this.setBackgroundTintList(ColorStateList.valueOf(color));
    }

    public int getBackgroundColor() {
        Log.i(LOG_TAG, "Setting a custom background.");
        return this.getBackgroundTintList().getDefaultColor();
    }

    public void changeColor(){
        int currColor = this.getBackgroundTintList().getDefaultColor();
        int randColor = BubbleUtil.getLevelColor();
        while(currColor == randColor){
            randColor = BubbleUtil.getLevelColor();
        }
        this.setBackgroundTintList(ColorStateList.valueOf(randColor));
    }

    private void setRandomColor(){
        this.setBackgroundTintList(ColorStateList.valueOf(BubbleUtil.getLevelColor()));
        this.setCompatElevation(1);
    }

    private void setBubblePoint(int point){
        this.setImageBitmap(textAsBitmap(""+point, 100, Color.WHITE));
        this.bubblePoint = point;
    }

    private void decreaseBubblePoint(int point){
        if(this.bubblePoint != 0){
            this.bubblePoint -= point;
            this.setImageBitmap(textAsBitmap(""+this.bubblePoint, 100, Color.WHITE));
            calculateHighBubblePoint();
        }
    }

    public void setNeighbourBubbles(List<Bubble> bubbles){
        this.neighbourBubbles = bubbles;
    }



    public void setOnClickCustomListener(){
        this.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                executeOnClickCode(view);
            }
        });
    }

    public void initializeAnimations(){
        bubble = this;
        enlargeAnimation = new ScaleAnimation(
                1f, 3f, // Start and end values for the X axis scaling
                1f, 3f, // Start and end values for the Y axis scaling
                Animation.RELATIVE_TO_SELF, 0.5f, // Pivot point of X scaling
                Animation.RELATIVE_TO_SELF, 0.5f); // Pivot point of Y scaling
        enlargeAnimation.setFillAfter(true); // Needed to keep the result of the animation
        enlargeAnimation.setDuration(animationDuration);

        shrinkAnimation = new ScaleAnimation(
                3f, 1f,
                3f, 1f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        shrinkAnimation.setFillAfter(true);
        shrinkAnimation.setDuration(animationDuration);

        shrinkToZeroAnimation = new ScaleAnimation(
                1f, 0f,
                1f, 0f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        shrinkToZeroAnimation.setFillAfter(true);
        shrinkToZeroAnimation.setDuration(animationZeroDuration);

        enlargeToOriginalAnimation = new ScaleAnimation(
                0f, 1f,
                0f, 1f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        enlargeToOriginalAnimation.setFillAfter(true);
        enlargeToOriginalAnimation.setDuration(animationZeroDuration);

        enlargeAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                int i = calculateBubblePoint();

                if(i > BubbleUtil.highScore){
                    BubbleUtil.highScore = i;
                    BubbleUtil.scoreColor = bubble.getBackgroundTintList().getDefaultColor();
                    updateHighScore();
                }
                setBubblePoint(i);
                calculateHighBubblePoint();
                bubble.startAnimation(shrinkAnimation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        shrinkAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

                for(Bubble b : neighbourBubbles){
                    b.startAnimation(shrinkToZeroAnimation);
                }
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        shrinkToZeroAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                for(Bubble b : neighbourBubbles){
                    b.changeColor();
                    b.setBubblePoint(initialBubblePoint);
                    b.startAnimation(enlargeToOriginalAnimation);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    public void executeOnClickCode(View view){

        AdUtil.clickAfterLastInterstitialAd++;
        AdUtil.clickAfterLastLevel++;
        if(isNeighbourOfSameColor()){
            AdUtil.fusionAfterLastInterstitialAd++;
            int i = calculateBubblePoint();
            if(BubbleUtil.checkIfLevelIncreasing(i)){
                AdUtil.level = BubbleUtil.level +1;
                AdUtil.clickAfterLastLevel = 0;
                if(displayAdAfterFusion()){
                    AdUtil.holdAdDueToLevel = true;
                }
            }else{
                if(displayAdAfterFusion()){
                    AdUtil.holdAdDueTofusion = true;
                    AdUtil.fusionAfterLastInterstitialAd = 0;
                    AdUtil.clickAfterLastInterstitialAd = 0;
                }
            }
            view.startAnimation(enlargeAnimation);
        }else{
            changeColor();
            decreaseBubblePoint(decreaseBubblePoint);
            if(displayAdAfterClick()){
                displayInterstitialAd();
                AdUtil.fusionAfterLastInterstitialAd = 0;
                AdUtil.clickAfterLastInterstitialAd = 0;
            }
        }
    }

    private boolean isNeighbourOfSameColor(){
        for(Bubble b : neighbourBubbles){
            if(b.getBackgroundTintList().getDefaultColor() != getBackgroundColor()){
                return false;
            }
        }
        return true;
    }

    private int calculateBubblePoint(){
        int i = this.bubblePoint;
        for(Bubble b : neighbourBubbles){
            i += b.bubblePoint;
        }
        return i;
    }

}