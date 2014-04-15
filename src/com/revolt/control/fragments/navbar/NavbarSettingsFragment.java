package com.revolt.control.fragments.navbar;

import android.app.Fragment;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.revolt.control.R;
import com.revolt.control.settings.BaseSetting;
import com.revolt.control.settings.BaseSetting.OnSettingChangedListener;
import com.revolt.control.settings.CheckboxSetting;
import com.revolt.control.settings.SingleChoiceSetting;


public class NavbarSettingsFragment extends Fragment implements OnSettingChangedListener {

    public NavbarSettingsFragment() {

    }

    CheckboxSetting mToggleNavbar;

    boolean hasNavbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        hasNavbar = getActivity().getResources()
                .getBoolean(com.android.internal.R.bool.config_showNavigationBar);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_navbar_settings, container, false);

            mToggleNavbar = (CheckboxSetting) v.findViewById(R.id.setting_toggle_navbar);
            mToggleNavbar.setChecked(Settings.REVOLT.getBoolean(getActivity().getContentResolver(),
                    Settings.REVOLT.ENABLE_NAVIGATION_BAR, hasNavbar));

        return v;
    }


    @Override
    public void onSettingChanged(String table, String key, String oldValue, String value) {
    }
}
