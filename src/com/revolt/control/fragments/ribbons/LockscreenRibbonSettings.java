package com.revolt.control.fragments.ribbons;

import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class LockscreenRibbonSettings extends HorizontalRibbonSettings {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedinstanceState) {
        SETTINGS_REVOLT = Settings.REVOLT.AOKP_LOCKSCREEN_RIBBON;
        return super.onCreateView(inflater, container, savedinstanceState);
    }
}
