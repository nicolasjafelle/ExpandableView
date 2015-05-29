package com.example.expandableview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.expandable.view.ExpandableView;

public class MainActivity extends AppCompatActivity {

    private ExpandableView topExpandableView;
    private ExpandableView middleExpandableView;

    private ExpandableView expandableViewLevel1;
    private ExpandableView expandableViewLevel2;
    private ExpandableView expandableViewLevel3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        topExpandableView = (ExpandableView) findViewById(R.id.activity_main_top_expandable_view);
        middleExpandableView = (ExpandableView) findViewById(R.id.activity_main_middle_expandable_view);

        expandableViewLevel1 = new ExpandableView(this);
        expandableViewLevel2 = new ExpandableView(this);
        expandableViewLevel3 = new ExpandableView(this);

        createTopExpandableView();
        createMiddleExpandableView();

        createInnerExpandableViewLevel1();
        createInnerExpandableViewLevel2();
        createInnerExpandableViewLevel3();

        expandableViewLevel1.setOutsideContentLayout(topExpandableView.getContentLayout());
        expandableViewLevel2.setOutsideContentLayout(topExpandableView.getContentLayout(), expandableViewLevel1.getContentLayout());
        expandableViewLevel3.setOutsideContentLayout(topExpandableView.getContentLayout(), expandableViewLevel1.getContentLayout(), expandableViewLevel2.getContentLayout());

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
        topExpandableView.addContentView(expandableViewLevel1);
    }

    private void createMiddleExpandableView() {
        String[] androidVersionNameList = getResources().getStringArray(R.array.android_version_names);

        middleExpandableView.fillData(R.drawable.ic_android, getString(R.string.android_names), false);
        middleExpandableView.setVisibleLayoutHeight(getResources().getDimensionPixelSize(R.dimen.new_visible_height));

        addContentView(middleExpandableView, androidVersionNameList, false);
    }

    private void createInnerExpandableViewLevel1() {
        String[] androidVersionNumberList = getResources().getStringArray(R.array.android_version_numbers);

        expandableViewLevel1.setBackgroundResource(android.R.color.holo_blue_light);
        expandableViewLevel1.fillData(R.drawable.ic_android, getString(R.string.android_codes), false);
        addContentView(expandableViewLevel1, androidVersionNumberList, false);
        expandableViewLevel1.addContentView(expandableViewLevel2);
    }

    private void createInnerExpandableViewLevel2() {
        String[] androidVersionNameList = getResources().getStringArray(R.array.android_version_names);

        expandableViewLevel2.setBackgroundResource(android.R.color.holo_green_light);
        expandableViewLevel2.fillData(R.drawable.ic_android, getString(R.string.android_names), false);
        addContentView(expandableViewLevel2, androidVersionNameList, false);
        expandableViewLevel2.addContentView(expandableViewLevel3);
    }

    private void createInnerExpandableViewLevel3() {
        String[] androidVersionNumberList = getResources().getStringArray(R.array.android_version_numbers);

        expandableViewLevel3.setBackgroundResource(android.R.color.holo_blue_light);
        expandableViewLevel3.fillData(R.drawable.ic_android, getString(R.string.android_codes), false);
        addContentView(expandableViewLevel3, androidVersionNumberList, false);
    }


}