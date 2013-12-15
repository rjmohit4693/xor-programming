package com.xorprogramming.ui.splash;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import com.xorprogramming.R;

// -------------------------------------------------------------------------
/**
 * This class shows a splash screen with an images such as a logo. The length of time that it is shown can be controlled
 * or be indefinite. A SpashScreen can be shown over top of an Activity and is intended to be used in onCreate.
 * 
 * @author Steven Roberts
 * @version 1.0.0
 */
public class SplashScreen
{
    private Dialog  dialog;
    private Context c;
    private int     res;
    private int     bgColor;
    
    
    // ----------------------------------------------------------
    /**
     * Create a new SplashScreen object.
     * 
     * @param c
     *            The context
     * @param res
     *            The resource of the image to display in the splash screen
     */
    public SplashScreen(Context c, int res)
    {
        this(c, res, Color.WHITE);
    }
    
    
    // ----------------------------------------------------------
    /**
     * Create a new SplashScreen object.
     * 
     * @param c
     *            The context
     * @param res
     *            The resource of the image to display in the splash screen
     * @param bgColor
     *            The color of the background
     */
    public SplashScreen(Context c, int res, int bgColor)
    {
        this.c = c;
        this.res = res;
        this.bgColor = bgColor;
    }
    
    
    // ----------------------------------------------------------
    /**
     * Shows the splash screen indefinitely
     */
    public void show()
    {
        dialog = new InnerDialog(c, R.style.splash_screen);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(null);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        ImageView image = new ImageView(c);
        image.setImageResource(res);
        image.setBackgroundColor(bgColor);
        image.setScaleType(ScaleType.FIT_CENTER);
        image.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        dialog.setContentView(image);
        dialog.show();
    }
    
    
    // ----------------------------------------------------------
    /**
     * Shows the splash screen for a given period of time then fades away
     * 
     * @param time
     *            The length of time to show the splash screen, in milliseconds
     */
    public void show(int time)
    {
        show();
        new Handler().postDelayed(new Runnable()
        {
            public void run()
            {
                if (dialog.isShowing())
                {
                    dismiss();
                }
            }
        }, time);
    }
    
    
    // ----------------------------------------------------------
    /**
     * Closes the splash screen if it is currently being displayed
     */
    public void dismiss()
    {
        if (dialog != null)
        {
            dialog.dismiss();
            dialog = null;
        }
    }
    
    
    private class InnerDialog
        extends Dialog
    {
        public InnerDialog(Context c, int style)
        {
            super(c, style);
            setOnKeyListener(new OnKeyListener()
            {
                public boolean onKey(DialogInterface dialogInterface, int keyCode, KeyEvent event)
                {
                    // Consume all key events to prevent key presses like the search button from closing the dialog
                    return true;
                }
            });
        }
    }
}
