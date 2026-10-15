package com.roger.catloadinglibrary;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

/**
 * Local stand-in for the JCenter-only `catloadinglibrary:1.0.9` artifact.
 *
 * <p>Provides the exact API surface used by
 * {@code virtual.camera.app.view.base.LoadingActivity}:
 * <ul>
 *   <li>{@link DialogFragment#show(androidx.fragment.app.FragmentManager, String)}</li>
 *   <li>{@link DialogFragment#isAdded()}</li>
 *   <li>{@link DialogFragment#getDialog()}</li>
 *   <li>{@link DialogFragment#dismiss()}</li>
 *   <li>{@link #setBackgroundColor(int)}</li>
 *   <li>{@link #setClickCancelAble(boolean)}</li>
 * </ul>
 *
 * <p>Renders a centered indeterminate progress spinner over a solid background.
 */
public class CatLoadingView extends DialogFragment {

    private int mBackgroundColor = Color.TRANSPARENT;
    private boolean mClickCancelAble = true;

    public void setBackgroundColor(int colorResOrInt) {
        mBackgroundColor = colorResOrInt;
    }

    public void setClickCancelAble(boolean able) {
        mClickCancelAble = able;
        setCancelable(able);
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.setCanceledOnTouchOutside(able);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(requireContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        FrameLayout root = new FrameLayout(requireContext());
        root.setBackgroundColor(mBackgroundColor);

        ProgressBar spinner = new ProgressBar(requireContext());
        FrameLayout.LayoutParams lp =
                new FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.WRAP_CONTENT,
                        FrameLayout.LayoutParams.WRAP_CONTENT,
                        Gravity.CENTER);
        root.addView(spinner, lp);

        dialog.setContentView(root);
        dialog.setCanceledOnTouchOutside(mClickCancelAble);
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null && dialog.getWindow() != null) {
            dialog.getWindow().setLayout(
                    android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                    android.view.ViewGroup.LayoutParams.MATCH_PARENT);
        }
    }
}
