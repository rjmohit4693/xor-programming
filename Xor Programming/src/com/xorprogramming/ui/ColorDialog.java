package com.xorprogramming.ui;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import com.xorprogramming.R;

public class ColorDialog
    extends Dialog
{
    private int     r;
    private int     g;
    private int     b;
    private int     a;

    private View    alphaRow;

    private View    color;
    private SeekBar redBar;
    private SeekBar greenBar;
    private SeekBar blueBar;
    private SeekBar alphaBar;


    public ColorDialog(Context context)
    {
        super(context);
        init();
    }


    public ColorDialog(Context context, int theme)
    {
        super(context, theme);
        init();
    }


    private void init()
    {
        setContentView(R.layout.dialog_color);
        setTitle(R.string.default_color_dialog_title);
        setCancelable(false);

        alphaRow = findViewById(R.id.alpha);
        color = findViewById(R.id.color_view);

        redBar = (SeekBar)findViewById(R.id.red_seek_bar);
        redBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener()
        {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                r = progress;
                updateColor();
            }


            public void onStartTrackingTouch(SeekBar seekBar)
            {
                // Nothing needed
            }


            public void onStopTrackingTouch(SeekBar seekBar)
            {
                // Nothing needed
            }
        });
        greenBar = (SeekBar)findViewById(R.id.green_seek_bar);
        greenBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener()
        {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                g = progress;
                updateColor();
            }


            public void onStartTrackingTouch(SeekBar seekBar)
            {
                // Nothing needed
            }


            public void onStopTrackingTouch(SeekBar seekBar)
            {
                // Nothing needed
            }
        });
        blueBar = (SeekBar)findViewById(R.id.blue_seek_bar);
        blueBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener()
        {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                b = progress;
                updateColor();
            }


            public void onStartTrackingTouch(SeekBar seekBar)
            {
                // Nothing needed
            }


            public void onStopTrackingTouch(SeekBar seekBar)
            {
                // Nothing needed
            }
        });
        alphaBar = (SeekBar)findViewById(R.id.alpha_seek_bar);
        alphaBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener()
        {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                a = progress;
                updateColor();
            }


            public void onStartTrackingTouch(SeekBar seekBar)
            {
                // Nothing needed
            }


            public void onStopTrackingTouch(SeekBar seekBar)
            {
                // Nothing needed
            }
        });

        hasAlpha(false);
        updateColor();
    }


    public void hasAlpha(boolean alpha)
    {
        if (alpha)
        {
            a = alphaBar.getProgress();
            alphaRow.setVisibility(View.VISIBLE);
        }
        else
        {
            a = 0xFF;
            alphaRow.setVisibility(View.GONE);
        }
    }

    public void setColor(int color)
    {
        alphaBar.setProgress((color >> 24) & 0xFF);
        redBar.setProgress((color >> 16) & 0xFF);
        greenBar.setProgress((color >> 8) & 0xFF);
        blueBar.setProgress(color & 0xFF);
    }


    public int getColor()
    {
        return Color.argb(a, r, g, b);
    }


    private void updateColor()
    {
        color.setBackgroundColor(getColor());
    }


    public ColorDialogState saveState()
    {
        ColorDialogState state = new ColorDialogState();
        state.stateR = r;
        state.stateG = g;
        state.stateB = b;
        state.stateA = a;
        state.hasAlpha = alphaRow.getVisibility() == View.VISIBLE;
        state.wasShowing = isShowing();
        dismiss();
        return state;
    }


    public void restoreState(ColorDialogState state)
    {
        redBar.setProgress(state.stateR);
        greenBar.setProgress(state.stateG);
        blueBar.setProgress(state.stateB);
        alphaBar.setProgress(state.stateA);
        hasAlpha(state.hasAlpha);
        if (state.wasShowing)
        {
            show();
        }
    }


    public class ColorDialogState
    {
        private int     stateR;
        private int     stateG;
        private int     stateB;
        private int     stateA;

        private boolean hasAlpha;

        private boolean wasShowing;

    }
}
