package com.imuxuan.floatingview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Local stand-in for the JCenter-only artifact {@code com.imuxuan:floatingview:1.6}
 * (its GitHub host, Imuxuan/FloatingView, has been deleted, and the artifact only
 * ever existed on the now-dead JCenter - no live Maven repository serves it).
 *
 * <p>Reproduces the API surface VCamera compiles against: construction with a
 * {@link Context} (plus the standard AttributeSet overloads) and normal
 * {@code View} touch handling. The original "magnet" auto-snap animation is not
 * reproduced: the sole consumer, {@code EnFloatView}, is never instantiated
 * anywhere in the app, so this class exists to keep that source compiling.</p>
 */
public class FloatingMagnetView extends FrameLayout {

    public FloatingMagnetView(Context context) {
        super(context);
    }

    public FloatingMagnetView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FloatingMagnetView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
