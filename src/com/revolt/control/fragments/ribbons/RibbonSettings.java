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
import com.android.internal.util.aokp.NavRingHelpers;
import com.revolt.control.R;
import com.revolt.control.settings.CheckboxSetting;
import com.revolt.control.settings.ColorPickerSetting;
import com.revolt.control.settings.SingleChoiceSetting;
import net.margaritov.preference.colorpicker.ColorPickerDialog.OnColorChangedListener;

public class RibbonSettings extends Fragment implements OnSeekBarChangeListener, OnColorChangedListener {
    private CheckboxSetting mEnable;
    private SingleChoiceSetting mLongSwipe;
    private SingleChoiceSetting mLongPress;
    private CheckboxSetting mSwipeVibrate;
    private SingleChoiceSetting mHandleLocation;
    private SingleChoiceSetting mAutoTimeout;
    private SingleChoiceSetting mAnimationType;
    private SeekBar mAnimationDuration;
    private ImageView mHandleColor;
    private ImageView mRibbonColor;
    private SeekBar mHandleHeight;
    private SeekBar mHandleWidth;
    private SeekBar mRibbonSpace;
    private SeekBar mRibbonSize;
    private Context mContext;
    private String mRibbon;
    public String[] SETTINGS_REVOLT;
    private int mSeekBarProgress;
    private int mColorCounter;

    private String[] mActions;
    private String[] mActionCodes;

    private int[] mAnimations;
    private String[] mAnimationsStrings;
    private String[] mAnimationsNum;

    public RibbonSettings() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get NavRing Actions
        mContext = getActivity();
        mActionCodes = NavRingHelpers.getNavRingActions();
        mActions = new String[mActionCodes.length];
        int actionqty = mActions.length;
        for (int i = 0; i < actionqty; i++) {
            mActions[i] = AwesomeConstants.getProperName(mContext,
                    mActionCodes[i]);
        }

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
        View main = inflater.inflate(R.layout.fragment_ribbons_settings, container, false);

        mEnable = (CheckboxSetting) main.findViewById(R.id.enable_ribbon);
        mEnable.setKey(SETTINGS_REVOLT[AokpRibbonHelper.ENABLE_RIBBON]);
        mEnable.setChecked(Settings.REVOLT.getBoolean(mContext.getContentResolver(), SETTINGS_REVOLT[AokpRibbonHelper.ENABLE_RIBBON], false));


        mLongSwipe = (SingleChoiceSetting) main.findViewById(R.id.ribbon_long_swipe);
        mLongSwipe.setKey(SETTINGS_REVOLT[AokpRibbonHelper.LONG_SWIPE]);
        mLongSwipe.setEntryValues(mActionCodes);
        mLongSwipe.setEntries(mActions);
        mLongSwipe.updateSummary();

        mLongPress = (SingleChoiceSetting) main.findViewById(R.id.ribbon_long_press);
        mLongPress.setKey(SETTINGS_REVOLT[AokpRibbonHelper.LONG_PRESS]);
        mLongPress.setEntryValues(mActionCodes);
        mLongPress.setEntries(mActions);
        mLongPress.updateSummary();

        mSwipeVibrate = (CheckboxSetting) main.findViewById(R.id.enable_gesture_vibrate);
        mSwipeVibrate.setKey(SETTINGS_REVOLT[AokpRibbonHelper.HANDLE_VIBRATE]);
        mSwipeVibrate.setChecked(Settings.REVOLT.getBoolean(mContext.getContentResolver(), SETTINGS_REVOLT[AokpRibbonHelper.HANDLE_VIBRATE], false));

        mHandleLocation = (SingleChoiceSetting) main.findViewById(R.id.ribbon_swipe_location);
        mHandleLocation.setKey(SETTINGS_REVOLT[AokpRibbonHelper.HANDLE_LOCATION]);
        mHandleLocation.setEntryValues(mContext.getResources().getStringArray(R.array.ribbon_handle_location_values));
        mHandleLocation.setEntries(mContext.getResources().getStringArray(R.array.ribbon_handle_location_entries));
        mHandleLocation.updateSummary();

        mAnimationType = (SingleChoiceSetting) main.findViewById(R.id.animation_type);
        mAnimationType.setKey(SETTINGS_REVOLT[AokpRibbonHelper.RIBBON_ANIMATION_TYPE]);
        mAnimationType.setEntryValues(mAnimationsNum);
        mAnimationType.setEntries(mAnimationsStrings);
        mAnimationType.updateSummary();

        mAutoTimeout = (SingleChoiceSetting) main.findViewById(R.id.auto_hide_duration);
        mAutoTimeout.setKey(SETTINGS_REVOLT[AokpRibbonHelper.AUTO_HIDE_DURATION]);
        mAutoTimeout.setEntryValues(mContext.getResources().getStringArray(R.array.hide_ribbon_timeout_values));
        mAutoTimeout.setEntries(mContext.getResources().getStringArray(R.array.hide_ribbon_timeout_entries));
        mAutoTimeout.updateSummary();

        mHandleColor = (ImageView) main.findViewById(R.id.handle_color);
        mHandleColor.setImageBitmap(getPreviewBitmap(Settings.REVOLT.getInt(mContext.getContentResolver(), SETTINGS_REVOLT[AokpRibbonHelper.HANDLE_COLOR], Color.TRANSPARENT)));
        mHandleColor.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                mColorCounter = 0;
                ColorPickerDialog picker = new ColorPickerDialog(mContext, Settings.REVOLT.getInt(mContext.getContentResolver(), SETTINGS_REVOLT[AokpRibbonHelper.HANDLE_COLOR], Color.TRANSPARENT));
                picker.setAlphaSliderVisible(true);
                picker.setOnColorChangedListener(RibbonSettings.this);
                picker.show();
            }
        });

        mRibbonColor = (ImageView) main.findViewById(R.id.ribbon_color);
        mRibbonColor.setImageBitmap(getPreviewBitmap(Settings.REVOLT.getInt(mContext.getContentResolver(), SETTINGS_REVOLT[AokpRibbonHelper.RIBBON_COLOR], Color.BLACK)));
        mRibbonColor.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                mColorCounter = 1;
                ColorPickerDialog picker = new ColorPickerDialog(mContext, Settings.REVOLT.getInt(mContext.getContentResolver(), SETTINGS_REVOLT[AokpRibbonHelper.RIBBON_COLOR], Color.BLACK));
                picker.setAlphaSliderVisible(true);
                picker.setOnColorChangedListener(RibbonSettings.this);
                picker.show();
            }
        });

        mHandleWidth = (SeekBar) main.findViewById(R.id.drag_handle_width);
        mHandleWidth.setProgress(Settings.REVOLT.getInt(mContext.getContentResolver(), SETTINGS_REVOLT[AokpRibbonHelper.HANDLE_WEIGHT], 30));
        mHandleWidth.setOnSeekBarChangeListener(this);

        mHandleHeight = (SeekBar) main.findViewById(R.id.drag_handle_height);
        mHandleHeight.setProgress(Settings.REVOLT.getInt(mContext.getContentResolver(), SETTINGS_REVOLT[AokpRibbonHelper.HANDLE_HEIGHT], 50));
        mHandleHeight.setOnSeekBarChangeListener(this);

        mAnimationDuration = (SeekBar) main.findViewById(R.id.animation_duration);
        mAnimationDuration.setProgress(Settings.REVOLT.getInt(mContext.getContentResolver(), SETTINGS_REVOLT[AokpRibbonHelper.RIBBON_ANIMATION_DURATION], 50));
        mAnimationDuration.setOnSeekBarChangeListener(this);

        mRibbonSize = (SeekBar) main.findViewById(R.id.ribbon_size);
        mRibbonSize.setProgress(Settings.REVOLT.getInt(mContext.getContentResolver(), SETTINGS_REVOLT[AokpRibbonHelper.RIBBON_SIZE], 30));
        mRibbonSize.setOnSeekBarChangeListener(this);

        mRibbonSpace = (SeekBar) main.findViewById(R.id.ribbon_space);
        mRibbonSpace.setProgress(Settings.REVOLT.getInt(mContext.getContentResolver(), SETTINGS_REVOLT[AokpRibbonHelper.RIBBON_MARGIN], 5));
        mRibbonSpace.setOnSeekBarChangeListener(this);

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
        if (seekBar == mHandleWidth) {
            Settings.REVOLT.putInt(mContext.getContentResolver(), SETTINGS_REVOLT[AokpRibbonHelper.HANDLE_WEIGHT], mSeekBarProgress);
        } else if (seekBar == mHandleHeight) {
            Settings.REVOLT.putInt(mContext.getContentResolver(), SETTINGS_REVOLT[AokpRibbonHelper.HANDLE_HEIGHT], mSeekBarProgress);
        } else if (seekBar == mAnimationDuration) {
            Settings.REVOLT.putInt(mContext.getContentResolver(), SETTINGS_REVOLT[AokpRibbonHelper.RIBBON_ANIMATION_DURATION], mSeekBarProgress);
        } else if (seekBar == mRibbonSpace) {
            Settings.REVOLT.putInt(mContext.getContentResolver(), SETTINGS_REVOLT[AokpRibbonHelper.RIBBON_MARGIN], mSeekBarProgress);
        } else if (seekBar == mRibbonSize) {
            Settings.REVOLT.putInt(mContext.getContentResolver(), SETTINGS_REVOLT[AokpRibbonHelper.RIBBON_SIZE], mSeekBarProgress);
        }
    }

    @Override
    public void onColorChanged(int color) {
        switch(mColorCounter) {
            case 0:
                mHandleColor.setImageBitmap(getPreviewBitmap(color));
                Settings.REVOLT.putInt(mContext.getContentResolver(), SETTINGS_REVOLT[AokpRibbonHelper.HANDLE_COLOR], color);
                break;
            case 1:
                mRibbonColor.setImageBitmap(getPreviewBitmap(color));
                Settings.REVOLT.putInt(mContext.getContentResolver(), SETTINGS_REVOLT[AokpRibbonHelper.RIBBON_COLOR], color);

                break;
        }
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
