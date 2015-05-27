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

    public void setOutsideContentLayout(ViewGroup outsideContentLayout) {
        this.outsideContentLayoutList.add(outsideContentLayout);
    }

    public void setOutsideContentLayout(ViewGroup... params) {
        for (int i = 0; i < params.length; i++) {
            this.outsideContentLayoutList.add(params[i]);
        }
    }

    public LinearLayout getContentLayout() {
        return this.contentLayout;
    }

    public void addContentView(View newContentView) {
        contentLayout.addView(newContentView);
        contentLayout.invalidate();
    }

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

    public void fillData(int leftResId, int stringResId, boolean useChevron) {
        fillData(leftResId, getResources().getString(stringResId), useChevron);
    }

    public void fillData(int leftResId, String text) {
        fillData(leftResId, text, false);
    }

    public void fillData(int leftResId, int stringResId) {
        fillData(leftResId, getResources().getString(stringResId), false);
    }


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
