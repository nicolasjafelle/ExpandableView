package com.example.expandableview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.expandable.view.ExpandableView;

public class MainActivity extends AppCompatActivity {

    private ExpandableView topExpandableView;
    private ExpandableView middleExpandableView;

    private ExpandableView expandableView;
    private ExpandableView expandableView2;
    private ExpandableView expandableView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        topExpandableView = (ExpandableView) findViewById(R.id.activity_main_top_expandable_view);
        middleExpandableView = (ExpandableView) findViewById(R.id.activity_main_middle_expandable_view);

        expandableView = new ExpandableView(this);
        expandableView2 = new ExpandableView(this);
        expandableView3 = new ExpandableView(this);

        createTopExpandableView();
        createMiddleExpandableView();

        createInnerExpandableView();
        createInnerExpandableView2();
        createInnerExpandableView3();

        expandableView.setOutsideContentLayout(topExpandableView.getContentLayout());
        expandableView2.setOutsideContentLayout(topExpandableView.getContentLayout(), expandableView.getContentLayout());
        expandableView3.setOutsideContentLayout(topExpandableView.getContentLayout(), expandableView.getContentLayout(), expandableView2.getContentLayout());

    }

    public void addContentView(ExpandableView view, String[] stringList, boolean showCheckbox) {

        for (int i = 0; i < stringList.length; i++) {
            ExpandedListItemView itemView = new ExpandedListItemView(this);
            itemView.setText(stringList[i], showCheckbox);
            view.addContentView(itemView);
        }
    }

    private void createTopExpandableView() {
        String[] androidVersionNameList = getResources().getStringArray(R.array.android_version_names);

        topExpandableView.fillData(R.drawable.ic_android, getString(R.string.android_names), true);
        addContentView(topExpandableView, androidVersionNameList, true);
        topExpandableView.addContentView(expandableView);
    }

    private void createMiddleExpandableView() {
        String[] androidVersionNameList = getResources().getStringArray(R.array.android_version_names);

        middleExpandableView.fillData(R.drawable.ic_android, getString(R.string.android_names), false);
        addContentView(middleExpandableView, androidVersionNameList, false);
    }

    private void createInnerExpandableView() {
        String[] androidVersionNumberList = getResources().getStringArray(R.array.android_version_numbers);

        expandableView.setBackgroundResource(android.R.color.holo_blue_light);
        expandableView.fillData(R.drawable.ic_android, getString(R.string.android_numbers), false);
        addContentView(expandableView, androidVersionNumberList, false);
        expandableView.addContentView(expandableView2);
    }

    private void createInnerExpandableView2() {
        String[] androidVersionNameList = getResources().getStringArray(R.array.android_version_names);

        expandableView2.setBackgroundResource(android.R.color.holo_green_light);
        expandableView2.fillData(R.drawable.ic_android, getString(R.string.android_names), false);
        addContentView(expandableView2, androidVersionNameList, false);
        expandableView2.addContentView(expandableView3);
    }

    private void createInnerExpandableView3() {
        String[] androidVersionNumberList = getResources().getStringArray(R.array.android_version_numbers);

        expandableView3.setBackgroundResource(android.R.color.holo_blue_light);
        expandableView3.fillData(R.drawable.ic_android, getString(R.string.android_numbers), false);
        addContentView(expandableView3, androidVersionNumberList, false);
    }


}