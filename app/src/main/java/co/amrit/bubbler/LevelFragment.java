package co.amrit.bubbler;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import static co.amrit.bubbler.BubbleUtil.getRandomColor;
import static co.amrit.bubbler.BubbleUtil.updateLevelMilestoneMainView;

/**
 * Created by hp on 01-06-2017.
 */

public class LevelFragment extends Fragment {

    TextView nextLevel;
    TextView nextMilestone;
    TextView levelText1;
    TextView levelText2;
    TextView levelText3;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_level, container, false);

        nextLevel = (TextView)view.findViewById(R.id.nextLevel);
        nextLevel.setTextColor(ColorStateList.valueOf(getRandomColor()));
        nextMilestone = (TextView)view.findViewById(R.id.nextMilestone);
        nextMilestone.setTextColor(ColorStateList.valueOf(getRandomColor()));
        nextLevel.setText(""+BubbleUtil.level);
        nextMilestone.setText(""+BubbleUtil.milestone);

        levelText1 = (TextView)view.findViewById(R.id.levelText1);
        levelText2 = (TextView)view.findViewById(R.id.levelText2);
        levelText3 = (TextView)view.findViewById(R.id.levelText3);
        levelText1.setTextColor(ColorStateList.valueOf(getRandomColor()));
        levelText2.setTextColor(ColorStateList.valueOf(getRandomColor()));
        levelText3.setTextColor(ColorStateList.valueOf(getRandomColor()));
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        updateLevelMilestoneMainView();
    }

}

