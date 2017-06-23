package co.amrit.bubbler;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import static co.amrit.bubbler.BubbleActivity.rateFragment;
import static co.amrit.bubbler.BubbleActivity.removeRateFragement;
import static co.amrit.bubbler.BubbleUtil.playStoreUri;

/**
 * Created by hp on 02-06-2017.
 */

public class RateFragment extends Fragment {

    Button rateButton1;
    Button rateButton2;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_rate, container, false);

        rateButton1 = (Button)view.findViewById(R.id.rateButton1);
        rateButton2 = (Button)view.findViewById(R.id.rateButton2);

        rateButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeRateFragement();
            }
        });

        rateButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rateApp();
                removeRateFragement();
            }
        });

        return view;
    }

    private void rateApp(){
        final Uri uri = Uri.parse(playStoreUri+"/details?id=" + getActivity().getApplicationContext().getPackageName());
        final Intent rateAppIntent = new Intent(Intent.ACTION_VIEW, uri);

        if (getActivity().getPackageManager().queryIntentActivities(rateAppIntent, 0).size() > 0)
        {
            startActivity(rateAppIntent);
        }
        else
        {
    /* handle your error case: the device has no way to handle market urls */
        }
    }
}