package com.example.test608.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MenuItem;

import com.example.test608.R;
import com.example.test608.model.BottomBar;
import com.example.test608.model.Destination;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;

import java.util.List;

public class AppBottomBar extends BottomNavigationView {
    private static int[] sIcons= new int[]{
            R.drawable.icon_tab_home,
            R.drawable.icon_tab_sofa,
            R.drawable.icon_tab_publish,
            R.drawable.icon_tab_find,
            R.drawable.icon_tab_mine
    };
    private BottomBar sBottomBar;

    public AppBottomBar(Context context){
        this(context,null);
    }
    public AppBottomBar(Context context, AttributeSet attrs){
        this(context,attrs,0);
    }
    @SuppressLint("RestrictedApi")
    public AppBottomBar(Context context,AttributeSet attrs,int defStyleAttr){
        super(context,attrs,defStyleAttr);

        sBottomBar=AppConfig.getsBottomBar();

        int[][] state= new int[2][];
        state[0] = new int[]{android.R.attr.state_selected};
        state[1] = new int[]{};

        int[] colors= new int[]{
                Color.parseColor(sBottomBar.activeColor),
                Color.parseColor(sBottomBar.inActiveColor)};

        ColorStateList stateList=new ColorStateList(state,colors);
        setItemTextColor(stateList);
        setItemIconTintList(stateList);

        setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);

        List<BottomBar.Tab> tabs = sBottomBar.tabs;
        for(BottomBar.Tab tab:tabs){
            if(!tab.enable){
                continue;
            }
            int itemId= getItemId(tab.pageUrl);
            if(itemId<0){
                continue;
            }
            MenuItem menuItem=getMenu().add(0,itemId,tab.index,tab.title);
            menuItem.setIcon(sIcons[tab.index]);
        }

        int index=0;
        for(BottomBar.Tab tab:sBottomBar.tabs) {
            if (!tab.enable) {
                continue;
            }
            int itemId = getItemId(tab.pageUrl);
            if (itemId < 0) {
                continue;
            }
            int iconSize = dp2Px(tab.size);
            BottomNavigationMenuView menuView = (BottomNavigationMenuView) getChildAt(0);
            BottomNavigationItemView itemView = (BottomNavigationItemView) menuView.getChildAt(index);
            itemView.setIconSize(iconSize);
            if (TextUtils.isEmpty(tab.title)) {
                int tintColor = TextUtils.isEmpty(tab.tintColor) ?
                        Color.parseColor("#ff678f") : Color.parseColor(tab.tintColor);
                itemView.setIconTintList(ColorStateList.valueOf(tintColor));
                itemView.setShifting(false);
            }
            index++;
        }
        if (sBottomBar.selectTab != 0) {
            BottomBar.Tab selectTab = sBottomBar.tabs.get(sBottomBar.selectTab);
            if (selectTab.enable) {
                int itemId = getItemId(selectTab.pageUrl);
                //这里需要延迟一下 再定位到默认选中的tab
                //因为 咱们需要等待内容区域,也就NavGraphBuilder解析数据并初始化完成，
                //否则会出现 底部按钮切换过去了，但内容区域还没切换过去
                post(() -> setSelectedItemId(itemId));
            }
        }
    }
    private int dp2Px(int dpValue){
        DisplayMetrics metrics = getContext().getResources().getDisplayMetrics();
        return (int)(metrics.density*dpValue +0.5f);
    }
    private int getItemId(String pageUrl){
        Destination destination=AppConfig.getsDestConfig().get(pageUrl);
        if (destination==null){
            return -1;
        }
        return destination.id;
    }
}
