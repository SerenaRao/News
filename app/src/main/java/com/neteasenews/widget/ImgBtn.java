package com.neteasenews.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageButton;

/**
 * @author Yuan
 * @time 2016/6/25  13:11
 * @desc ${TODD}
 */
public class ImgBtn extends ImageButton {
    public ImgBtn(Context context) {
        super(context);
    }

    public ImgBtn(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        setAlpha((isPressed() || isFocused() || isSelected()) ? 0.5f : 1.0f);
    }

}
