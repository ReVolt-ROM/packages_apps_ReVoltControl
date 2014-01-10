package com.revolt.control.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.revolt.control.R;
import com.revolt.control.settings.BaseSetting.OnSettingChangedListener;
import com.revolt.control.settings.CheckboxSetting;

public class StatusbarSettingsFragment extends Fragment implements OnSettingChangedListener {

    CheckboxSetting mBatteryIndicator, mBatteryIndicatorPlugged;

    public StatusbarSettingsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_statusbar_settings, container, false);

        mBatteryIndicator = (CheckboxSetting) v.findViewById(R.id.battery_percentage_indicator);
        mBatteryIndicatorPlugged = (CheckboxSetting) v.findViewById(R.id.battery_percentage_indicator_plugged);

        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBatteryIndicator.setOnSettingChangedListener(this);
    }

    @Override
    public void onSettingChanged(String table, String key, String oldValue, String value) {
        if ("revolt".equals(table)) {
            mBatteryIndicatorPlugged.setVisibility(mBatteryIndicator.isChecked() ? View.VISIBLE : View.GONE);
        }
    }
}
