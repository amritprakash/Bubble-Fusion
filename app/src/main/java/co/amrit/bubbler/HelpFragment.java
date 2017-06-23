package co.amrit.bubbler;

import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import static co.amrit.bubbler.BubbleActivity.removeHelpFragement;
import static co.amrit.bubbler.BubbleActivity.removeRateFragement;
import static co.amrit.bubbler.BubbleUtil.textAsBitmap;

/**
 * Created by hp on 01-06-2017.
 */

public class HelpFragment extends Fragment {

    FloatingActionButton helpOkButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_help, container, false);

        helpOkButton = (FloatingActionButton)view.findViewById(R.id.helpOkButton);
        helpOkButton.setImageBitmap(textAsBitmap("PLAY", 100, Color.WHITE));

        helpOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeHelpFragement();
            }
        });

        return view;
    }
}
