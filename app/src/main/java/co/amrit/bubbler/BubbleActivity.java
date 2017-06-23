package co.amrit.bubbler;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.List;

import static co.amrit.bubbler.BubbleUtil.getRandomColor;
import static co.amrit.bubbler.BubbleUtil.playStoreUri;
import static co.amrit.bubbler.BubbleUtil.textAsBitmap;

/**
 * Created by Amrit on 28-05-2017.
 */
public class BubbleActivity extends AppCompatActivity {

    Bubble centerBubble;
    Bubble upBubble;
    Bubble downBubble;
    Bubble rightDownBubble;
    Bubble rightUpBubble;
    Bubble leftDownBubble;
    Bubble leftUpBubble;

    TextView bubbleB1;
    TextView bubbleU;
    TextView bubbleB2;
    TextView bubbleB3;
    TextView bubbleL;
    TextView bubbleE;

    TextView fusionF;
    TextView fusionU;
    TextView fusionS;
    TextView fusionI;
    TextView fusionO;
    TextView fusionN;

    TextView scoreH1;
    TextView scoreI;
    TextView scoreG;
    TextView scoreH2;
    TextView scoreS;
    TextView scoreC;
    TextView scoreO;
    TextView scoreR;
    TextView scoreE;

    FloatingActionButton best;

    TextView levelL1;
    TextView levelE1;
    TextView levelV;
    TextView levelE2;
    TextView levelL2;

    TextView level;

    TextView milestoneM;
    TextView milestoneI;
    TextView milestoneL;
    TextView milestoneE1;
    TextView milestoneS;
    TextView milestoneT;
    TextView milestoneO;
    TextView milestoneN;
    TextView milestoneE2;

    TextView milestone;

    ImageView shareButton;
    ImageView helpButton;

    private AdView homeFooterAdd;

    static FragmentManager fragmentManager;
    static Fragment helpFragment;
    static Fragment rateFragment;
    static Fragment levelFragment;

    static InterstitialAd mInterstitialAd;

    static boolean firstTime = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bubble);
        context = this;
        //get stored high score info and initialize bubbles
        SharedPreferences prefs = this.getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        BubbleUtil.highScore = prefs.getInt("highScore", 0); //0 is the default value
        BubbleUtil.scoreColor = prefs.getInt("highColor", getRandomColor());

        //get stored rateInfo
        AdUtil.rateApp = prefs.getBoolean("rateApp", false); //0 is the default value
        AdUtil.gameCount = prefs.getInt("gameCount", 0)+1;

        //checkIfItsFirstTime
        firstTime = prefs.getBoolean("firstTime", true);

        initializeBubbles();
        initializeNameBubbles();
        BubbleUtil.best = best;
        BubbleUtil.centerBubble = centerBubble;
        BubbleUtil.levelView = level;
        BubbleUtil.milestoneView = milestone;

        //Share
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareIt();
            }
        });

        //help
        fragmentManager = getFragmentManager();
        helpFragment = new HelpFragment();
        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addHelpFragement();
            }
        });

        //Ad
        homeFooterAdd = (AdView) findViewById(R.id.homeFooterAdd);
        MobileAds.initialize(this, "ca-app-pub-7987038464914684~1955264052");

        AdRequest adRequest = new AdRequest.Builder().build();
        homeFooterAdd.loadAd(adRequest);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-7987038464914684/4048629255");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }

        });

        //rate App
        rateFragment = new RateFragment();

        //level
        levelFragment = new LevelFragment();
        ConstraintLayout rootLayout = (ConstraintLayout)findViewById(R.id.rootLayout);
        rootLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeLevelFragement();
            }
        });

        //helpForFirstTime;
        if(firstTime){
            final TextView helpText1 = (TextView)findViewById(R.id.fHelpText1);
            final TextView helpText2 = (TextView)findViewById(R.id.fHelpText2);
            final TextView helpText3 = (TextView)findViewById(R.id.fHelpText3);

            final ImageView centerHand = (ImageView)findViewById(R.id.centerHand);
            final ImageView topHand = (ImageView)findViewById(R.id.topHand);
            final ImageView topRightHand = (ImageView)findViewById(R.id.topRightHand);

            //Initialize bubble for help
            int color1 = Color.parseColor("#fff44336");
            int color2 = Color.parseColor("#ff9c27b0");
            centerBubble.setBackgroundTintList(ColorStateList.valueOf(color1));
            upBubble.setBackgroundTintList(ColorStateList.valueOf(color1));
            downBubble.setBackgroundTintList(ColorStateList.valueOf(color1));
            rightDownBubble.setBackgroundTintList(ColorStateList.valueOf(color2));
            rightUpBubble.setBackgroundTintList(ColorStateList.valueOf(color2));
            leftDownBubble.setBackgroundTintList(ColorStateList.valueOf(color2));
            leftUpBubble.setBackgroundTintList(ColorStateList.valueOf(color1));

            final Animation anim = new AlphaAnimation(0.0f, 1.0f);
            anim.setDuration(200); //You can manage the blinking time with this parameter
            anim.setStartOffset(20);
            anim.setRepeatMode(Animation.REVERSE);
            anim.setRepeatCount(Animation.INFINITE);
            helpText1.startAnimation(anim);

            //first help message
            centerHand.setVisibility(View.VISIBLE);

            //second help message
            centerBubble.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    helpText1.clearAnimation();
                    centerHand.setVisibility(View.INVISIBLE);
                    helpText2.startAnimation(anim);
                    topHand.setVisibility(View.VISIBLE);
                    centerBubble.executeOnClickCode(v);

                    upBubble.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            helpText2.clearAnimation();
                            topHand.setVisibility(View.INVISIBLE);
                            helpText3.startAnimation(anim);
                            topRightHand.setVisibility(View.VISIBLE);
                            upBubble.executeOnClickCode(v);

                            rightUpBubble.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    helpText3.clearAnimation();
                                    topRightHand.setVisibility(View.INVISIBLE);
                                    rightUpBubble.executeOnClickCode(v);

                                    centerBubble.setOnClickCustomListener();
                                    upBubble.setOnClickCustomListener();
                                    downBubble.setOnClickCustomListener();
                                    rightDownBubble.setOnClickCustomListener();
                                    rightUpBubble.setOnClickCustomListener();
                                    leftDownBubble.setOnClickCustomListener();
                                    leftUpBubble.setOnClickCustomListener();
                                }
                            });
                        }
                    });
                }
            });
            firstTime = false;
        }else{
            centerBubble.setOnClickCustomListener();
            upBubble.setOnClickCustomListener();
            downBubble.setOnClickCustomListener();
            rightDownBubble.setOnClickCustomListener();
            rightUpBubble.setOnClickCustomListener();
            leftDownBubble.setOnClickCustomListener();
            leftUpBubble.setOnClickCustomListener();
        }

        //Test Add
        /*AdRequest adRequest = new AdRequest.Builder().addTestDevice("57456BEE5009A6C4CF8B0EA337C83E33").build();
        homeFooterAdd.loadAd(adRequest);*/

    }

    public static void rateAppFragement(){
        if(fragmentManager.findFragmentById(R.id.rateFrame) == null){
            fragmentManager.beginTransaction()
                    .addToBackStack("rateFragment")
                    .replace(R.id.rateFrame, rateFragment)
                    .commit();
        }
    }

    public static void removeRateFragement(){
        if(fragmentManager.findFragmentById(R.id.rateFrame) != null){
            /*fragmentManager.beginTransaction()
                    .remove(rateFragment)
                    .commit();*/
            fragmentManager.popBackStack();
        }
    }

    public static void addHelpFragement(){
        if(fragmentManager.findFragmentById(R.id.helpFrame) == null){
            fragmentManager.beginTransaction()
                    .addToBackStack("helpFragment")
                    .replace(R.id.helpFrame, helpFragment)
                    .commit();
        }
    }

    public static void removeHelpFragement(){
        if(fragmentManager.findFragmentById(R.id.helpFrame) != null){
            /*fragmentManager.beginTransaction()
                    .remove(rateFragment)
                    .commit();*/
            fragmentManager.popBackStack();
        }
    }


    static Context context ;
    public static void addLevelFragement(){

        fragmentManager.beginTransaction()
                    .addToBackStack("levelFragment")
                    .replace(R.id.levelFrame, levelFragment)
                    .commit();
    }

    public void removeLevelFragement(){

        if(fragmentManager.findFragmentById(R.id.levelFrame) != null){
            /*fragmentManager.beginTransaction()
                    .remove(levelFragment)
                    .commit();*/
            fragmentManager.popBackStack();
        }
        BubbleUtil.updateLevelMilestoneMainView();
    }

    private void shareIt() {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Bubble Fusion");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Hey Friends Checkout This Interesting Game "+ playStoreUri+"/details?id=co.amrit.bubbler");
        startActivity(Intent.createChooser(sharingIntent, "Share with"));
    }

    @Override
    public void onPause() {
        if (homeFooterAdd != null) {
            homeFooterAdd.pause();
        }
        super.onPause();
    }

    /** Called when returning to the activity */
    @Override
    public void onResume() {
        super.onResume();
        if (homeFooterAdd != null) {
            homeFooterAdd.resume();
        }
    }

    /** Called before the activity is destroyed */
    @Override
    public void onDestroy() {
        if (homeFooterAdd != null) {
            homeFooterAdd.destroy();
        }
        super.onDestroy();
    }

    @Override
    protected  void onStop(){
        super.onStop();

        SharedPreferences prefs = this.getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("highScore", BubbleUtil.highScore);
        editor.putInt("highColor", BubbleUtil.scoreColor);
        editor.putBoolean("firstTime", firstTime);
        if(AdUtil.gameCount >=100){
            editor.putBoolean("rateApp", false);
            editor.putInt("gameCount", 0);
        }else{
            editor.putBoolean("rateApp", AdUtil.rateApp);
            editor.putInt("gameCount", AdUtil.gameCount);
        }
        editor.commit();
    }

    private void initializeNameBubbles(){
        bubbleB1 = (TextView) findViewById(R.id.bubbleB1);
        bubbleU = (TextView) findViewById(R.id.bubbleU);
        bubbleB2 = (TextView) findViewById(R.id.bubbleB2);
        bubbleB3 = (TextView) findViewById(R.id.bubbleB3);
        bubbleL = (TextView) findViewById(R.id.bubbleL);
        bubbleE = (TextView) findViewById(R.id.bubbleE);

        bubbleB1.setTextColor(getRandomColor());
        bubbleU.setTextColor(ColorStateList.valueOf(getRandomColor()));
        bubbleB2.setTextColor(ColorStateList.valueOf(getRandomColor()));
        bubbleB3.setTextColor(ColorStateList.valueOf(getRandomColor()));
        bubbleL.setTextColor(ColorStateList.valueOf(getRandomColor()));
        bubbleE.setTextColor(ColorStateList.valueOf(getRandomColor()));

        fusionF = (TextView) findViewById(R.id.fussionF);
        fusionU = (TextView) findViewById(R.id.fussionU);
        fusionS = (TextView) findViewById(R.id.fussionS);
        fusionI = (TextView) findViewById(R.id.fussionI);
        fusionO = (TextView) findViewById(R.id.fussionO);
        fusionN = (TextView) findViewById(R.id.fussionN);

        fusionF.setTextColor(ColorStateList.valueOf(getRandomColor()));
        fusionU.setTextColor(ColorStateList.valueOf(getRandomColor()));
        fusionS.setTextColor(ColorStateList.valueOf(getRandomColor()));
        fusionI.setTextColor(ColorStateList.valueOf(getRandomColor()));
        fusionO.setTextColor(ColorStateList.valueOf(getRandomColor()));
        fusionN.setTextColor(ColorStateList.valueOf(getRandomColor()));

        shareButton = (ImageView) findViewById(R.id.shareButton);
        helpButton = (ImageView) findViewById(R.id.helpButton);
        helpButton.setImageTintList(ColorStateList.valueOf(getRandomColor()));
        shareButton.setImageTintList(ColorStateList.valueOf(getRandomColor()));

        scoreH1 = (TextView) findViewById(R.id.scoreH1);
        scoreI = (TextView) findViewById(R.id.scoreI);
        scoreG = (TextView) findViewById(R.id.scoreG);
        scoreH2 = (TextView) findViewById(R.id.scoreH2);
        scoreS = (TextView) findViewById(R.id.scoreS);
        scoreC = (TextView) findViewById(R.id.scoreC);
        scoreO = (TextView) findViewById(R.id.scoreO);
        scoreR = (TextView) findViewById(R.id.scoreR);
        scoreE = (TextView) findViewById(R.id.scoreE);
        best = (FloatingActionButton) findViewById(R.id.best);

        //Set Dynamically
        best.setImageBitmap(textAsBitmap(""+BubbleUtil.highScore, 100, Color.WHITE));

        scoreH1.setTextColor(ColorStateList.valueOf(getRandomColor()));
        scoreI.setTextColor(ColorStateList.valueOf(getRandomColor()));
        scoreG.setTextColor(ColorStateList.valueOf(getRandomColor()));
        scoreH2.setTextColor(ColorStateList.valueOf(getRandomColor()));
        scoreS.setTextColor(ColorStateList.valueOf(getRandomColor()));
        scoreC.setTextColor(ColorStateList.valueOf(getRandomColor()));
        scoreO.setTextColor(ColorStateList.valueOf(getRandomColor()));
        scoreR.setTextColor(ColorStateList.valueOf(getRandomColor()));
        scoreE.setTextColor(ColorStateList.valueOf(getRandomColor()));
        //set Dynamically
        best.setBackgroundTintList(ColorStateList.valueOf(BubbleUtil.scoreColor));

        levelL1 = (TextView) findViewById(R.id.levelL1);
        levelE1 = (TextView) findViewById(R.id.levelE1);
        levelV = (TextView) findViewById(R.id.levelV);
        levelE2 = (TextView) findViewById(R.id.levelE2);
        levelL2 = (TextView) findViewById(R.id.levelL2);
        level = (TextView) findViewById(R.id.level);

        level.setText(""+BubbleUtil.level);

        levelL1.setTextColor(ColorStateList.valueOf(getRandomColor()));
        levelE1.setTextColor(ColorStateList.valueOf(getRandomColor()));
        levelV.setTextColor(ColorStateList.valueOf(getRandomColor()));
        levelE2.setTextColor(ColorStateList.valueOf(getRandomColor()));
        levelL2.setTextColor(ColorStateList.valueOf(getRandomColor()));
        level.setTextColor(ColorStateList.valueOf(getRandomColor()));

        milestoneM = (TextView) findViewById(R.id.milestoneM);
        milestoneI = (TextView) findViewById(R.id.milestoneI);
        milestoneL = (TextView) findViewById(R.id.milestoneL);
        milestoneE1 = (TextView) findViewById(R.id.milestoneE1);
        milestoneS = (TextView) findViewById(R.id.milestoneS);
        milestoneT = (TextView) findViewById(R.id.milestoneT);
        milestoneO = (TextView) findViewById(R.id.milestoneO);
        milestoneN = (TextView) findViewById(R.id.milestoneN);
        milestoneE2 = (TextView) findViewById(R.id.milestoneE2);
        milestone = (TextView) findViewById(R.id.milestone);

        milestone.setText(""+BubbleUtil.milestone);

        milestoneM.setTextColor(ColorStateList.valueOf(getRandomColor()));
        milestoneI.setTextColor(ColorStateList.valueOf(getRandomColor()));
        milestoneL.setTextColor(ColorStateList.valueOf(getRandomColor()));
        milestoneE1.setTextColor(ColorStateList.valueOf(getRandomColor()));
        milestoneS.setTextColor(ColorStateList.valueOf(getRandomColor()));
        milestoneT.setTextColor(ColorStateList.valueOf(getRandomColor()));
        milestoneO.setTextColor(ColorStateList.valueOf(getRandomColor()));
        milestoneN.setTextColor(ColorStateList.valueOf(getRandomColor()));
        milestoneE2.setTextColor(ColorStateList.valueOf(getRandomColor()));
        milestone.setTextColor(ColorStateList.valueOf(getRandomColor()));
    }

    private void initializeBubbles(){

        centerBubble = (Bubble) findViewById(R.id.centerBubble);
        upBubble = (Bubble) findViewById(R.id.upBubble);
        downBubble = (Bubble) findViewById(R.id.downBubble);
        rightDownBubble = (Bubble) findViewById(R.id.rightDownBubble);
        rightUpBubble = (Bubble) findViewById(R.id.rightUpBubble);
        leftDownBubble = (Bubble) findViewById(R.id.leftDownBubble);
        leftUpBubble = (Bubble) findViewById(R.id.leftUpBubble);

        List<Bubble> centerNeighbour = new ArrayList<Bubble>();
        centerNeighbour.add(upBubble);
        centerNeighbour.add(downBubble);
        centerNeighbour.add(rightDownBubble);
        centerNeighbour.add(rightUpBubble);
        centerNeighbour.add(leftDownBubble);
        centerNeighbour.add(leftUpBubble);
        centerBubble.setNeighbourBubbles(centerNeighbour);

        List<Bubble> downNeighbour = new ArrayList<Bubble>();
        downNeighbour.add(centerBubble);
        downNeighbour.add(rightDownBubble);
        downNeighbour.add(leftDownBubble);
        downBubble.setNeighbourBubbles(downNeighbour);

        List<Bubble> upNeighbour = new ArrayList<Bubble>();
        upNeighbour.add(centerBubble);
        upNeighbour.add(rightUpBubble);
        upNeighbour.add(leftUpBubble);
        upBubble.setNeighbourBubbles(upNeighbour);

        List<Bubble> rightUpNeighbour = new ArrayList<Bubble>();
        rightUpNeighbour.add(centerBubble);
        rightUpNeighbour.add(rightDownBubble);
        rightUpNeighbour.add(upBubble);
        rightUpBubble.setNeighbourBubbles(rightUpNeighbour);

        List<Bubble> rightDownNeighbour = new ArrayList<Bubble>();
        rightDownNeighbour.add(centerBubble);
        rightDownNeighbour.add(rightUpBubble);
        rightDownNeighbour.add(downBubble);
        rightDownBubble.setNeighbourBubbles(rightDownNeighbour);

        List<Bubble> leftUpNeighbour = new ArrayList<Bubble>();
        leftUpNeighbour.add(centerBubble);
        leftUpNeighbour.add(leftDownBubble);
        leftUpNeighbour.add(upBubble);
        leftUpBubble.setNeighbourBubbles(leftUpNeighbour);

        List<Bubble> leftDownNeighbour = new ArrayList<Bubble>();
        leftDownNeighbour.add(centerBubble);
        leftDownNeighbour.add(leftUpBubble);
        leftDownNeighbour.add(downBubble);
        leftDownBubble.setNeighbourBubbles(leftDownNeighbour);
    }

}