package com.revolt.control.fragments.navbar;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.revolt.control.R;
import com.revolt.control.settings.BaseSetting;
import com.revolt.control.settings.BaseSetting.OnSettingChangedListener;
import com.revolt.control.settings.SingleChoiceSetting;


public class NavbarSettingsFragment extends Fragment implements OnSettingChangedListener {

    public NavbarSettingsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_navbar_settings, container, false);

        return v;
    }


    @Override
    public void onSettingChanged(String table, String key, String oldValue, String value) {
    }
}
