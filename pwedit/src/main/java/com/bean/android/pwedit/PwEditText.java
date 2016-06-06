package com.bean.android.pwedit;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

public class PwEditText extends EditText {

    private boolean toggleMode = false, isShowingPassword = false;
    private Drawable drawableRight;
    private Rect bounds;
    private int visibilityIndicatorDelete = R.drawable.delete;
    private int visibilityIndicatorShow = R.drawable.show;
    private int visibilityIndicatorHide = R.drawable.hide;

    public PwEditText(Context context) {
        super(context);
        init(null);
    }

    public PwEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);

    }

    public PwEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PwEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }


    private void init(AttributeSet attrs) {

        if(attrs!=null) {
            TypedArray attrsArray = getContext().obtainStyledAttributes(attrs, R.styleable.PwEditText);
            toggleMode = attrsArray.getBoolean(R.styleable.PwEditText_toggleMode, toggleMode);
            if (toggleMode) {
                visibilityIndicatorShow = attrsArray.getResourceId(R.styleable.PwEditText_drawable_show, visibilityIndicatorShow);
                visibilityIndicatorHide = attrsArray.getResourceId(R.styleable.PwEditText_drawable_hide, visibilityIndicatorHide);
                setInputType(EditorInfo.TYPE_CLASS_TEXT | EditorInfo.TYPE_TEXT_VARIATION_PASSWORD);
            } else {
                visibilityIndicatorDelete = attrsArray.getResourceId(R.styleable.PwEditText_drawable_delete, visibilityIndicatorDelete);
            }
            attrsArray.recycle();
        }

        if (!TextUtils.isEmpty(getText())) {
            showEditTextVisibilityIndicator(true);
        }
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    showEditTextVisibilityIndicator(true);
                } else {
                    showEditTextVisibilityIndicator(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void showEditTextVisibilityIndicator(boolean show) {
        if (toggleMode && show) {
            setCompoundDrawablesWithIntrinsicBounds(null, null, isShowingPassword ?
                            getResources().getDrawable(visibilityIndicatorHide) :
                            getResources().getDrawable(visibilityIndicatorShow), null);
        } else if (show) {
            setCompoundDrawablesWithIntrinsicBounds(null, null,
                    getResources().getDrawable(visibilityIndicatorDelete), null);
        } else {
            setCompoundDrawables(null, null, null, null);
        }
    }

    private void togglePasswordVisibility() {
        if (isShowingPassword) {
            setInputType(EditorInfo.TYPE_CLASS_TEXT | EditorInfo.TYPE_TEXT_VARIATION_PASSWORD);
        } else {
            setInputType(EditorInfo.TYPE_CLASS_TEXT | EditorInfo.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        }
        isShowingPassword = !isShowingPassword;
        showEditTextVisibilityIndicator(true);
        // by default the cursor is set to the start of text after toggle, this method set it to the end
        setSelection(getText().length());
    }

    private void deleteEditTextContent() {
        getText().clear();
    }

    @Override
    public void setCompoundDrawables(Drawable left, Drawable top,
                                     Drawable right, Drawable bottom) {
        if (right != null) {
            //keep a reference to the right drawable so later on touch we can check if touch is on the drawable
            drawableRight = right;
        }
        super.setCompoundDrawables(left, top, right, bottom);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_UP && drawableRight != null) {
            bounds = drawableRight.getBounds();
            final int x = (int) event.getX();
            // half of bounds.width, to increase the touch area
            int delta = bounds.width() >> 1;
            // check if the touch is within bounds of drawableRight icon
            if (x >= (this.getRight() - this.getPaddingRight() - bounds.width() - delta)
                    && x <= (this.getRight() - this.getPaddingRight()) + delta) {
                if(toggleMode){
                    togglePasswordVisibility();
                } else{
                    deleteEditTextContent();
                }
                //use this to prevent the keyboard from coming up
                event.setAction(MotionEvent.ACTION_CANCEL);
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void finalize() throws Throwable {
        drawableRight = null;
        bounds = null;
        super.finalize();
    }

}
