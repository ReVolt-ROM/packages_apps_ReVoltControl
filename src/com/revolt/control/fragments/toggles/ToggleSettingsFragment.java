package com.revolt.control.fragments.toggles;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.revolt.control.R;
import com.revolt.control.settings.BaseSetting;
import com.revolt.control.settings.BaseSetting.OnSettingChangedListener;
import com.revolt.control.settings.SingleChoiceSetting;

/**
 * Created by roman on 12/30/13.
 */
public class ToggleSettingsFragment extends Fragment implements OnSettingChangedListener {

    BaseSetting mTogglesFast, mSwipeToSwitch;
    SingleChoiceSetting mTogglesPerRow, mToggleStyle, mToggleSide;

    public ToggleSettingsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_toggle_setup, container, false);

        mTogglesFast = (BaseSetting) v.findViewById(R.id.toggles_fast_toggle);
        mSwipeToSwitch = (BaseSetting) v.findViewById(R.id.toggles_swipe_to_switch);
        mTogglesPerRow = (SingleChoiceSetting) v.findViewById(R.id.toggles_per_row);
        mToggleStyle = (SingleChoiceSetting) v.findViewById(R.id.toggles_style);
        mToggleSide = (SingleChoiceSetting) v.findViewById(R.id.toggles_fast_side);

        mToggleStyle.setOnSettingChangedListener(this);


        return v;
    }


    @Override
    public void onSettingChanged(String table, String key, String oldValue, String value) {
        if (table.equals("revolt") && key.equals(mToggleStyle.getKey())) {
            if (value == null || value.isEmpty()) {
                // defualt state
                mTogglesPerRow.setVisibility(View.VISIBLE);
                mTogglesFast.setVisibility(View.VISIBLE);
                mToggleSide.setVisibility(View.VISIBLE);
                mSwipeToSwitch.setVisibility(View.VISIBLE);
            } else {
                mTogglesPerRow.setVisibility(value.equals("0" /* 0 is the tile */)
                        ? View.VISIBLE : View.GONE);
                mTogglesFast.setVisibility(value.equals("0" /* 0 is the tile */)
                        ? View.VISIBLE : View.GONE);
                mToggleSide.setVisibility(value.equals("0" /* 0 is the tile */)
                        ? View.VISIBLE : View.GONE);
                mSwipeToSwitch.setVisibility(value.equals("0" /* 0 is the tile */)
                        ? View.VISIBLE : View.GONE);
            }
        }
    }
}
