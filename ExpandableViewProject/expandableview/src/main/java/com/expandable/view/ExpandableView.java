package com.expandable.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * ExpandableView is a View showing only a visible content and when clicked on it, it displays more content in a fashion way.
 * It can also handle inner ExpandableViews, one ExpandableView inside another and another.
 */
public class ExpandableView extends RelativeLayout {

    private static final int DURATION = 400;

    private float DEGREES;

    private TextView textView;

    private RelativeLayout clickableLayout;

    private LinearLayout contentLayout;

    private List<ViewGroup> outsideContentLayoutList;

    private int outsideContentHeight = 0;

    private ImageView leftIcon;

    private ImageView rightIcon;

    private ValueAnimator animator;
    private RotateAnimation rotateAnimator;

    public ExpandableView(Context context) {
        super(context);
        init();
    }

    public ExpandableView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ExpandableView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.expandable_view, this);

        outsideContentLayoutList = new ArrayList<>();

        textView = (TextView) findViewById(R.id.expandable_view_title);
        clickableLayout = (RelativeLayout) findViewById(R.id.expandable_view_clickable_content);
        contentLayout = (LinearLayout) findViewById(R.id.expandable_view_content_layout);
        leftIcon = (ImageView) findViewById(R.id.expandable_view_image);
        rightIcon = (ImageView) findViewById(R.id.expandable_view_right_icon);

        contentLayout.setVisibility(GONE);

        clickableLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (contentLayout.isShown()) {
                    collapse();
                } else {
                    expand();
                }
            }
        });


        //Add onPreDrawListener
        contentLayout.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        contentLayout.getViewTreeObserver().removeOnPreDrawListener(this);
                        contentLayout.setVisibility(View.GONE);

                        final int widthSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
                        final int heightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
                        contentLayout.measure(widthSpec, heightSpec);

                        animator = slideAnimator(0, contentLayout.getMeasuredHeight());
                        return true;
                    }
                });
    }

    /**
     * Set the new Height of the visible content layout.
     * @param newHeight
     */
    public void setVisibleLayoutHeight(int newHeight) {
        this.clickableLayout.getLayoutParams().height = newHeight;
    }

    /**
     * Set the parent's ViewGroup
     * @param outsideContentLayout
     */
    public void setOutsideContentLayout(ViewGroup outsideContentLayout) {
        this.outsideContentLayoutList.add(outsideContentLayout);
    }

    /**
     * Set the parent's ViewGroup, one or more than one.
     * @param params
     */
    public void setOutsideContentLayout(ViewGroup... params) {
        for (int i = 0; i < params.length; i++) {
            this.outsideContentLayoutList.add(params[i]);
        }
    }

    /**
     * Returns the Content LinearLayout, the LinearLayout that expands or collapse in a fashion way.
     * @return The Content LinearLayout
     */
    public LinearLayout getContentLayout() {
        return this.contentLayout;
    }

    /**
     * Add a view into the Content LinearLayout, the LinearLayout that expands or collapse in a fashion way.
     * @param newContentView
     */
    public void addContentView(View newContentView) {
        contentLayout.addView(newContentView);
        contentLayout.invalidate();
    }

    /**
     * Set the left drawable icon, the text to display, and if you want to use the chevron icon or plus icon.
     * @param leftResId - 0 for no left drawable.
     * @param text
     * @param useChevron - true to use the chevron icon, false to use the plus icon
     */
    public void fillData(int leftResId, String text, boolean useChevron) {
        textView.setText(text);

        if (leftResId == 0) {
            leftIcon.setVisibility(GONE);
        } else {
            leftIcon.setImageResource(leftResId);
        }

        if (useChevron) {
            DEGREES = -180;
            rightIcon.setImageResource(R.drawable.ic_expandable_view_chevron);
        } else {
            DEGREES = -225;
            rightIcon.setImageResource(R.drawable.ic_expandable_view_plus);
        }
    }

    /**
     * Set the left drawable icon, the text to display, and if you want to use the chevron icon or plus icon.
     * @param leftResId - 0 for no left drawable.
     * @param stringResId
     * @param useChevron - true to use the chevron icon, false to use the plus icon
     */
    public void fillData(int leftResId, int stringResId, boolean useChevron) {
        fillData(leftResId, getResources().getString(stringResId), useChevron);
    }

    /**
     * Set the left drawable icon, the text to display using the plus icon by default.
     * @param leftResId - 0 for no left drawable.
     * @param text
     */
    public void fillData(int leftResId, String text) {
        fillData(leftResId, text, false);
    }

    /**
     * Set the left drawable icon, the text to display using the plus icon by default.
     * @param leftResId - 0 for no left drawable.
     * @param stringResId
     */
    public void fillData(int leftResId, int stringResId) {
        fillData(leftResId, getResources().getString(stringResId), false);
    }


    /**
     * Expand animation to display the discoverable content.
     */
    public void expand() {
        //set Visible
        contentLayout.setVisibility(View.VISIBLE);
        int x = rightIcon.getMeasuredWidth() / 2;
        int y = rightIcon.getMeasuredHeight() / 2;

        rotateAnimator = new RotateAnimation(0f, DEGREES, x, y);
        rotateAnimator.setInterpolator(new LinearInterpolator());
        rotateAnimator.setRepeatCount(Animation.ABSOLUTE);
        rotateAnimator.setFillAfter(true);
        rotateAnimator.setDuration(DURATION);
        rightIcon.startAnimation(rotateAnimator);
        animator.start();
    }

    /**
     * Collapse animation to hide the discoverable content.
     */
    public void collapse() {
        int finalHeight = contentLayout.getHeight();

        int x = rightIcon.getMeasuredWidth() / 2;
        int y = rightIcon.getMeasuredHeight() / 2;

        rotateAnimator = new RotateAnimation(DEGREES, 0f, x, y);
        rotateAnimator.setInterpolator(new LinearInterpolator());
        rotateAnimator.setRepeatCount(Animation.ABSOLUTE);
        rotateAnimator.setFillAfter(true);
        rotateAnimator.setDuration(DURATION);

        ValueAnimator mAnimator = slideAnimator(finalHeight, 0);

        mAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animator) {
                contentLayout.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });

        rightIcon.startAnimation(rotateAnimator);
        mAnimator.start();
    }


    private ValueAnimator slideAnimator(int start, int end) {

        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.setDuration(DURATION);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int value = (Integer) valueAnimator.getAnimatedValue();

                ViewGroup.LayoutParams layoutParams = contentLayout.getLayoutParams();
                layoutParams.height = value;

                contentLayout.setLayoutParams(layoutParams);
                contentLayout.invalidate();

                if (outsideContentLayoutList != null && !outsideContentLayoutList.isEmpty()) {

                    for (ViewGroup outsideParam : outsideContentLayoutList) {
                        ViewGroup.LayoutParams params = outsideParam.getLayoutParams();

                        if (outsideContentHeight == 0) {
                            outsideContentHeight = params.height;
                        }

                        params.height = outsideContentHeight + value;
                        outsideParam.setLayoutParams(params);
                        outsideParam.invalidate();
                    }
                }
            }
        });
        return animator;
    }

}
