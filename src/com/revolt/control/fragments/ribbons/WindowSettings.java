package com.revolt.control.fragments.ribbons;

import net.margaritov.preference.colorpicker.ColorPickerDialog;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.android.internal.util.aokp.AokpRibbonHelper;
import com.android.internal.util.aokp.AwesomeAnimationHelper;
import com.android.internal.util.aokp.AwesomeConstants;
import com.revolt.control.R;
import com.revolt.control.settings.ColorPickerSetting;
import com.revolt.control.settings.SingleChoiceSetting;
import net.margaritov.preference.colorpicker.ColorPickerDialog.OnColorChangedListener;

public class WindowSettings extends Fragment implements OnSeekBarChangeListener, OnColorChangedListener {
    private SingleChoiceSetting mAnimationType;
    private SeekBar mAnimationDuration;
    private ImageView mWindowColor;
    private SeekBar mWindowSpace;
    private SeekBar mWindowSize;
    private Context mContext;
    public String[] SETTINGS_REVOLT;
    private int mSeekBarProgress;
    private int mColorCounter;

    private int[] mAnimations;
    private String[] mAnimationsStrings;
    private String[] mAnimationsNum;

    public WindowSettings() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get NavRing Actions
        mContext = getActivity();
        SETTINGS_REVOLT = Settings.REVOLT.APP_WINDOW;
        mAnimations = AwesomeAnimationHelper.getAnimationsList();
        int animqty = mAnimations.length;
        mAnimationsStrings = new String[animqty];
        mAnimationsNum = new String[animqty];
        for (int i = 0; i < animqty; i++) {
            mAnimationsStrings[i] = AwesomeAnimationHelper.getProperName(mContext, mAnimations[i]);
            mAnimationsNum[i] = String.valueOf(mAnimations[i]);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedinstanceState) {
        View main = inflater.inflate(R.layout.fragment_window_settings, container, false);


        mAnimationType = (SingleChoiceSetting) main.findViewById(R.id.animation_type);
        mAnimationType.setKey(SETTINGS_REVOLT[AokpRibbonHelper.WINDOW_ANIMATION]);
        mAnimationType.setEntryValues(mAnimationsNum);
        mAnimationType.setEntries(mAnimationsStrings);
        mAnimationType.updateSummary();

        mWindowColor = (ImageView) main.findViewById(R.id.window_color);
        mWindowColor.setImageBitmap(getPreviewBitmap(Settings.REVOLT.getInt(mContext.getContentResolver(), SETTINGS_REVOLT[AokpRibbonHelper.WINDOW_COLOR], Color.BLACK)));
        mWindowColor.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                mColorCounter = 1;
                ColorPickerDialog picker = new ColorPickerDialog(mContext, Settings.REVOLT.getInt(mContext.getContentResolver(), SETTINGS_REVOLT[AokpRibbonHelper.WINDOW_COLOR], Color.BLACK));
                picker.setAlphaSliderVisible(true);
                picker.setOnColorChangedListener(WindowSettings.this);
                picker.show();
            }
        });

        mAnimationDuration = (SeekBar) main.findViewById(R.id.animation_duration);
        mAnimationDuration.setProgress(Settings.REVOLT.getInt(mContext.getContentResolver(), SETTINGS_REVOLT[AokpRibbonHelper.WINDOW_ANIMATION_DURATION], 50));
        mAnimationDuration.setOnSeekBarChangeListener(this);

        mWindowSize = (SeekBar) main.findViewById(R.id.window_size);
        mWindowSize.setProgress(Settings.REVOLT.getInt(mContext.getContentResolver(), SETTINGS_REVOLT[AokpRibbonHelper.WINDOW_SIZE], 30));
        mWindowSize.setOnSeekBarChangeListener(this);

        mWindowSpace = (SeekBar) main.findViewById(R.id.window_space);
        mWindowSpace.setProgress(Settings.REVOLT.getInt(mContext.getContentResolver(), SETTINGS_REVOLT[AokpRibbonHelper.WINDOW_SPACE], 5));
        mWindowSpace.setOnSeekBarChangeListener(this);

        return main;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        mSeekBarProgress = progress;
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        if (seekBar == mAnimationDuration) {
            Settings.REVOLT.putInt(mContext.getContentResolver(), SETTINGS_REVOLT[AokpRibbonHelper.WINDOW_ANIMATION_DURATION], mSeekBarProgress);
        } else if (seekBar == mWindowSpace) {
            Settings.REVOLT.putInt(mContext.getContentResolver(), SETTINGS_REVOLT[AokpRibbonHelper.WINDOW_SPACE], mSeekBarProgress);
        } else if (seekBar == mWindowSize) {
            Settings.REVOLT.putInt(mContext.getContentResolver(), SETTINGS_REVOLT[AokpRibbonHelper.WINDOW_SIZE], mSeekBarProgress);
        }
    }

    @Override
    public void onColorChanged(int color) {
            mWindowColor.setImageBitmap(getPreviewBitmap(color));
            Settings.REVOLT.putInt(mContext.getContentResolver(), SETTINGS_REVOLT[AokpRibbonHelper.WINDOW_COLOR], color);
    }

    private Bitmap getPreviewBitmap(int color) {
        int d = (int) (mContext.getResources().getDisplayMetrics().density * 31); // 30dip
        Bitmap bm = Bitmap.createBitmap(d, d, Config.ARGB_8888);
        int w = bm.getWidth();
        int h = bm.getHeight();
        int c = color;
        for (int i = 0; i < w; i++) {
            for (int j = i; j < h; j++) {
                c = (i <= 1 || j <= 1 || i >= w - 2 || j >= h - 2) ? Color.GRAY : color;
                bm.setPixel(i, j, c);
                if (i != j) {
                    bm.setPixel(j, i, c);
                }
            }
        }

        return bm;
    }
}
