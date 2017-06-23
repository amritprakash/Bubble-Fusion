package co.amrit.bubbler;

import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.design.widget.FloatingActionButton;
import android.widget.TextView;

import com.google.android.gms.ads.InterstitialAd;

import java.util.List;
import java.util.Random;

import static co.amrit.bubbler.BubbleActivity.addLevelFragement;
import static co.amrit.bubbler.BubbleActivity.rateAppFragement;

/**
 * Created by Amrit on 28-05-2017.
 */

public class BubbleUtil {

    static final String playStoreUri = "http://play.google.com/store/apps";
    static final int initialBubblePoint = 7;
    static final int decreaseBubblePoint = 7;
    static int highScore;
    static int scoreColor;
    static Random random = new Random();
    static FloatingActionButton best;
    static Bubble centerBubble;
    static int level = 0;
    static int milestone = 49;
    static int highestBubblePoint = 7;
    static TextView levelView;
    static TextView milestoneView;

    static int[] allColors = {Color.parseColor("#fff44336"),
                            Color.parseColor("#ff9c27b0"),
                            Color.parseColor("#ff4caf50"),
                            Color.parseColor("#ffffc107")};

    static int[] twoColors = {Color.parseColor("#fff44336"),
            Color.parseColor("#ff9c27b0")};

    static int[] threeColors = {Color.parseColor("#fff44336"),
            Color.parseColor("#ff9c27b0"),
            Color.parseColor("#ff4caf50")};

    public static int getRandomColor(){

        return allColors[random.nextInt(4)];
    }

    public static int getLevelColor(){

        if(level < 1){
            return twoColors[random.nextInt(2)];
        }else if(level < 5){
            return threeColors[random.nextInt(3)];
        }else{
            return allColors[random.nextInt(4)];
        }

    }

    public static void updateHighScore(){
        best.setImageBitmap(textAsBitmap(""+highScore, 100, Color.WHITE));
        best.setBackgroundTintList(ColorStateList.valueOf(scoreColor));
    }

    public static Bitmap textAsBitmap(String text, float textSize, int textColor) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(textSize);
        paint.setColor(textColor);
        paint.setTextAlign(Paint.Align.LEFT);
        float baseline = -paint.ascent(); // ascent() is negative
        int width = (int) (paint.measureText(text) + 0.0f); // round
        int height = (int) (3*baseline + paint.descent() + 0.0f);
        Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(image);
        canvas.drawText(text, 0, 2*baseline, paint);
        return image;
    }

    public static void updateLevel(){
        level++;
        if(level < 2){
            milestone*=2;
        }else if(level < 5){
            milestone+=196;
        }else if(level < 10){
            milestone+=49;
        }else if(level < 15){
            milestone+=98;
        }else if(level < 25){
            milestone*=2;
        }else{
            milestone*=7;
        }

    }

    public static void updateLevelMilestoneMainView(){
        levelView.setText(""+level);
        levelView.setTextColor(ColorStateList.valueOf(getRandomColor()));
        milestoneView.setText(""+milestone);
        milestoneView.setTextColor(ColorStateList.valueOf(getRandomColor()));
    }

    public static void calculateHighBubblePoint(){
        int temp = centerBubble.bubblePoint;
        for(Bubble b : centerBubble.neighbourBubbles){
            if(b.bubblePoint > temp){
                temp = b.bubblePoint;
            }
        }
        if(temp > highestBubblePoint){
            highestBubblePoint = temp;
        }

        if(highestBubblePoint >= milestone){
            addLevelFragement();
            updateLevel();
        }
    }

    public static boolean checkIfLevelIncreasing(int in){
        int temp = centerBubble.bubblePoint;
        for(Bubble b : centerBubble.neighbourBubbles){
            if(b.bubblePoint > temp){
                temp = b.bubblePoint;
            }
        }
        if(temp > centerBubble.bubblePoint){
            temp = centerBubble.bubblePoint;
        }
        if(in > temp && in >= milestone){
            return true;
        }

        return false;
    }

}