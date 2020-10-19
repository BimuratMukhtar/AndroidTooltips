# AndroidTooltips
Add tooltip near any other view with ease
  
  
## Instructions  
  
1. Add a dependency to your app build.gradle  
```groovy  
dependencies {  
	implementation 'kz.opensource.tooltips:tooltips:1.0.0'
}  
```  
  
2. Create an HoverViewManager object  
```kotlin  
private lateinit var toolTipsManager: ToolTipsManager  
toolTipsManager = ToolTipsManager()
```  
  
3. Create your custom view to show, like this:  
```xml  
<?xml version="1.0" encoding="utf-8"?>  
<LinearLayout  
  xmlns:android="http://schemas.android.com/apk/res/android"  
  android:layout_width="match_parent"  
  android:layout_height="wrap_content"  
  android:orientation="vertical"  
  android:background="#383E46"  
  >  
  
 <TextView  android:id="@+id/tooltip_desc_text"  
  android:layout_width="match_parent"  
  android:layout_height="wrap_content"  
  android:textColor="@color/colorWhite"  
  android:text="@string/text_long"  
  />  
  
 <TextView  android:id="@+id/tooltip_ok_button"  
  android:layout_width="match_parent"  
  android:layout_height="wrap_content"  
  android:layout_marginTop="12dp"  
  android:gravity="end|center"  
  android:textColor="@color/colorWhite"  
  android:text="@string/text_ok"  
  />  
  
</LinearLayout> 
```  
Important: Do not use layout_margin  
  4. Use the ToolTip to construct your tooltip  
```kotlin  
val customView = layoutInflater.inflate(R.layout.custom_view, parentLayout, false)  
val customTooltip = ToolTip(  
	anchorView = anchorView,  
	rootView = parentLayout,  
	align = ToolTip.ALIGN_CENTER,  
	position = ToolTip.POSITION_BELOW,  
	backgroundColor = resources.getColor(R.color.colorOrange),  
	contentView = customView  
)
```  
'anchorView' here is the view which near it the tooltip will be shown and 'parentLayout' is the layout where the tooltip will be added to.  Prefer to pass in a layout which is higher in the xml tree as this will give the  
tooltip more visible space.  
  
5. Use ToolTipsManager to show the tooltip  
  
IMPORTANT: This must be called after the layout has been drawn  
You can override the 'onWindowFocusChanged()' of an Activity and show there, Start a delayed runnable from onStart() , React to user action or any other method that works for you  
```kotlin  
toolTipsManager.show(customTooltip) 
```  
  
Each tooltip is dismissable by clicking on it, if you want to dismiss an tooltip from code there are a few options, The most simple way is to do the following  
```kotlin  
toolTipsManager.findAndDismiss(anchorView)
```  
Where 'anchorView' is the same view we asked to position an tooltip near it  
  
If you want to react when tooltip has been dismissed, Implement ToolTipsManager.TipListener interface and use appropriate ToolTipsManager constructor  
```kotlin  
class TooltipActivity : AppCompatActivity(), TipListener, View.OnClickListener { 
.  
.  
	override fun onCreate(savedInstanceState: Bundle?) { 
		 toolTipsManager = ToolTipsManager(tipListener = this)
		 .
		 .
	}  
.  
.  
	override fun onTipDismissed(view: View?, anchorViewId: Int, byUser: Boolean) {
		// do something
	}
}
```  
  
### License  
```  
Copyright 2016 Tomer Goldstein  
  
Licensed under the Apache License, Version 2.0 (the "License");  
you may not use this file except in compliance with the License.  
You may obtain a copy of the License at  
  
 http://www.apache.org/licenses/LICENSE-2.0  
Unless required by applicable law or agreed to in writing, software  
distributed under the License is distributed on an "AS IS" BASIS,  
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  
See the License for the specific language governing permissions and  
limitations under the License.  
```