ExpandableView
==============

[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-ExpandableView-brightgreen.svg?style=flat)](http://android-arsenal.com/details/1/1898)

ExpandableView is a View showing only a content and when clicked on it, it displays more content in a fashion way. You can add views or viewgroups but remember that it will only display the content in a LinearLayout with vertical orientation.<br>

You can choose by default a "chevron" icon animation or a "plus" icon animation. The "always visible row" has a left icon, a text and finally a right icon which will be the animated one.

![ExpandableView-art](https://raw.githubusercontent.com/nicolasjafelle/ExpandableView/master/art/Example.gif)

Instructions - Maven Central
============

1. Add this library in your build.gradle:

```groovy
dependencies {
    compile 'coming soon...'
}
```

Instructions
============

1. Clone the git repo
2. Import the "ExpandableView" module into your Android-gradle project.
3. Add "ExpandableView" module in your settings.gradle
4. DONE


How to Use it
================

As any view in Android you can add it by code or by layout xml file but remember that if you want to change the default height of the visible content you need to use:
``` java
topExpandableView.setVisibleLayoutHeight(300);
topExpandableView.setVisibleLayoutHeight(getResources().getDimensionPixelSize(R.dimen.new_height));
```

Remember also to fill the information inside the visible content by using:
``` java
expandableView.fillData(R.drawable.ic_android, R.string.android_names, true);
//or
expandableView.fillData(R.drawable.ic_android, getString(R.string.android_names), true);
//or
expandableView.fillData(R.drawable.ic_android, R.string.android_names, true);
//or
expandableView.fillData(R.drawable.ic_android, getString(R.string.android_names));
//or
expandableView.fillData(R.drawable.ic_android, R.string.android_names);

//or
expandableView.fillData(0, R.string.android_names); // No drawable left by passing 0.
```

If you want to add content into the discoverable LinearLayout simple use:
``` java
expandableView.addContentView(itemView); // itemView could be a simple TextView or more complex custom views
```

The most relevant part of this ExpandableView is when you want to include an ExpandableView into another ExpandableView, you need to pass the parent's View hierarchy, like this:
``` java
expandableViewLevel1.setOutsideContentLayout(topExpandableView.getContentLayout()); // 1 Level
expandableViewLevel2.setOutsideContentLayout(topExpandableView.getContentLayout(), expandableViewLevel1.getContentLayout()); // 2 Levels
expandableViewLevel3.setOutsideContentLayout(topExpandableView.getContentLayout(), expandableViewLevel1.getContentLayout(), expandableViewLevel2.getContentLayout()); // 3 Levels
```

Also remember to use this package in your layout files: 

	<com.expandable.view.ExpandableView
		android:id="@+id/activity_main_top_expandable_view"
		android:layout_width="match_parent"
		android:layout_height="wrap_content"/>

You can also customize the TextView inside the visible Content Layout in this way:
	
	<!-- Use this style name to override the default style -->
	<style name="ExpandableView_TextView">
		<item name="android:textSize">18sp</item>
		<item name="android:textStyle">bold</item>
		<item name="android:textColor">@android:color/black</item>
	</style>


Developed By
================

* Nicolas Jafelle - <nicolasjafelle@gmail.com>


License
================

    Copyright 2015 Nicolas Jafelle

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
