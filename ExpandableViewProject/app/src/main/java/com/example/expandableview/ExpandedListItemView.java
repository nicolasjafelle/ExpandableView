package com.example.expandableview;

import android.content.Context;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ExpandedListItemView extends RelativeLayout {

    private RelativeLayout mRoot;

    private TextView mText;

    private AppCompatCheckBox mCheckbox;

    private View mViewSeparator;

    public ExpandedListItemView(Context context) {
        super(context);
        init();
    }

    public ExpandedListItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ExpandedListItemView(Context context, AttributeSet attrs,
                                int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.expandable_list_item_view, this);

        mRoot = (RelativeLayout) findViewById(R.id.expandable_list_item_view_root);
        mText = (TextView) findViewById(R.id.expandable_list_item_view_text);
        mCheckbox = (AppCompatCheckBox) findViewById(R.id.expandable_list_item_view_checkbox);
        mViewSeparator = findViewById(R.id.expandable_list_item_view_separator);

        this.mRoot.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mCheckbox.setChecked(!mCheckbox.isChecked());
            }
        });
    }

    public void setText(String text, boolean showCheckbox) {
        this.mText.setText(text);
        if (!showCheckbox) {
            this.mCheckbox.setVisibility(View.GONE);
            this.mViewSeparator.setVisibility(View.GONE);
        }
    }


}
