package com.example.carogame.ui.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

public class SquareCellView extends AppCompatTextView {

    public SquareCellView(Context context) {
        super(context);
    }

    public SquareCellView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareCellView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // Ép View thành hình vuông
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
