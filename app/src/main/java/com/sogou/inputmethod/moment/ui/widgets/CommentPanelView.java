package com.sogou.inputmethod.moment.ui.widgets;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.boisneyphilippe.githubarchitecturecomponents.R;
import com.sogou.inputmethod.moment.ui.interfaces.OnKeyBoardStateListener;
import com.sogou.inputmethod.moment.utils.Utils;

public class CommentPanelView extends LinearLayout implements OnKeyBoardStateListener {
    private LinearLayout mLayoutPanel;

    private EditText mEditText;
    private View sendView;

    private FrameLayout mLayoutNull;

    private int mDisplayHeight;

    public CommentPanelView(Context context) {
        super(context);
        init();
    }

    public CommentPanelView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CommentPanelView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getY() < Utils.getScreenHeight() - Utils.dp2px(254f) && isShowing()) {
            dismiss();
        }
        return super.onTouchEvent(event);
    }

    public boolean isShowing() {
        return mLayoutPanel != null && mLayoutPanel.getVisibility() == VISIBLE;
    }


    private void showSoftKeyBoard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null && mEditText != null) {
            mEditText.post(() -> {
                mEditText.requestFocus();
                inputMethodManager.showSoftInput(mEditText, 0);
            });
            new Handler().postDelayed(() -> {
                changeLayoutNullParams(true);
            }, 200);
        }
    }


    private void hideSoftKeyBoard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null && mEditText != null) {
            inputMethodManager.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
        }
    }

    private void init() {
        View itemView = LayoutInflater.from(getContext()).inflate(R.layout.view_comment_panel, this, false);
        mEditText = itemView.findViewById(R.id.edit_text);
        mEditText.setOnTouchListener((v, event) -> {
            showSoftKeyBoard();
            return true;
        });

        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                sendView.setEnabled(s.length() > 0);
            }
        });


        sendView = itemView.findViewById(R.id.comment_send);
        sendView.setOnClickListener(v -> sendComment());

        mLayoutNull = itemView.findViewById(R.id.layout_null);
        mLayoutPanel = itemView.findViewById(R.id.layout_panel);
        addOnSoftKeyBoardVisibleListener((Activity) getContext(), this);
        addView(itemView);
    }

    private void changeLayoutNullParams(boolean isShowSoftKeyBoard) {
        LayoutParams params = (LayoutParams) mLayoutNull.getLayoutParams();
        if (isShowSoftKeyBoard) {
            params.weight = 1;
            params.height = 0;
            mLayoutNull.setLayoutParams(params);
        } else {
            params.weight = 0;
            params.height = mDisplayHeight;
            mLayoutNull.setLayoutParams(params);
        }
    }

    boolean visibleForLast = false;

    public void addOnSoftKeyBoardVisibleListener(Activity activity, final OnKeyBoardStateListener listener) {
        final View decorView = activity.getWindow().getDecorView();
        decorView.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            Rect rect = new Rect();
            decorView.getWindowVisibleDisplayFrame(rect);
            //计算出可见屏幕的高度
            int displayHight = rect.bottom - rect.top;
            //获得屏幕整体的高度
            int hight = decorView.getHeight();
            //获得键盘高度
            int keyboardHeight = hight - displayHight - Utils.calcStatusBarHeight(getContext());
            boolean visible = (double) displayHight / hight < 0.8;
            if (visible != visibleForLast) {
                listener.onSoftKeyBoardState(visible, keyboardHeight, displayHight - Utils.dp2px(48f));
            }
            visibleForLast = visible;
        });
    }


    @Override
    public void onSoftKeyBoardState(boolean visible, int keyboardHeight, int displayHeight) {
        if (visible) {
            mDisplayHeight = displayHeight;
        }
    }

    public void showCommentPanel(int position) {
        this.position = position;

        if (mLayoutPanel != null) {
            mLayoutPanel.setVisibility(VISIBLE);
        }
        showOrHideAnimation(true);
        showSoftKeyBoard();
    }

    private void showOrHideAnimation(final boolean isShow) {
        TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, isShow ? 1.0f : 0.0f,
                Animation.RELATIVE_TO_PARENT, isShow ? 0.0f : 1.0f);
        animation.setDuration(200);
        mLayoutPanel.startAnimation(animation);
    }

    public void dismiss() {
        showOrHideAnimation(false);
        if (mLayoutPanel != null) {
            mLayoutPanel.setVisibility(GONE);
        }
        hideSoftKeyBoard();
    }

    private void sendComment() {
        if (null != onSendCommentListener) {
            CharSequence text = mEditText.getText();
            if (null != text && text.length() > 0) {
                // todo: send text
                onSendCommentListener.onSendComment(text.toString(), position);
                dismiss();
            } else {
                // should not be here.
            }
        }
    }

    public void setOnSendCommentListener(OnSendCommentListener onSendCommentListener) {
        this.onSendCommentListener = onSendCommentListener;
    }

    private OnSendCommentListener onSendCommentListener;
    private int position;
    public interface OnSendCommentListener {
        void onSendComment(String comment, int position);
    }
}
