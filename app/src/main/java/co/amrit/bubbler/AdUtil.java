package co.amrit.bubbler;

import android.util.Log;

import static co.amrit.bubbler.BubbleActivity.mInterstitialAd;
import static co.amrit.bubbler.BubbleActivity.rateAppFragement;

/**
 * Created by hp on 03-06-2017.
 */

public class AdUtil {

    static boolean rateApp = false;
    static int gameCount = 0;
    static int clickAfterLastInterstitialAd = 0;
    static int fusionAfterLastInterstitialAd = 0;
    static Integer clickAfterLastLevel = 0;
    static int level = 0;
    static boolean holdAdDueToLevel = false;
    static boolean holdAdDueTofusion = false;
    final static int displayAdAfterFusionCountLevel0 = 15;
    final static int displayAdAfterFusionCountLevel1 = 15;
    final static int displayAdAfterFusionCountLevel2 = 45;
    final static int displayAdAfterClickCountLevel0 = 45;
    final static int displayAdAfterClickCountLevel1 = 90;
    final static int displayAdAfterClickCountLevel2 = 150;
    final static int minClickAfterLevelForAd = 2;

    public static boolean displayAdAfterFusion(){
        boolean flag = false;
        if(level == 0 && fusionAfterLastInterstitialAd >= displayAdAfterFusionCountLevel0){
            flag = true;
        }
        if(level == 1 && fusionAfterLastInterstitialAd >= displayAdAfterFusionCountLevel1){
            flag = true;
        }
        if(level >= 2 && fusionAfterLastInterstitialAd >= displayAdAfterFusionCountLevel2){
            flag = true;
        }
        if(flag){
            if(!rateApp && level == 2){
                rateApp = true;
                rateAppFragement();
            }
            return true;
        }
        return false;
    }

    public static boolean displayAdAfterClick(){

        if(holdAdDueToLevel && clickAfterLastLevel >= minClickAfterLevelForAd){
            holdAdDueToLevel = false;
            return true;
        }
        if(holdAdDueTofusion){
            holdAdDueTofusion = false;
            return true;
        }
        if(level == 0 && clickAfterLastInterstitialAd >= displayAdAfterClickCountLevel0){
            return true;
        }
        if(level == 1 && clickAfterLastInterstitialAd >= displayAdAfterClickCountLevel1){
            return true;
        }
        if(level >= 2 && clickAfterLastInterstitialAd >= displayAdAfterClickCountLevel2){
            return true;
        }

        return false;
    }

    public static void displayInterstitialAd(){
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Log.d("INTERSTITIAL_AD", "The interstitial wasn't loaded yet.");
        }
    }
}
