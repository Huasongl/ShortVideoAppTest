package com.example.test608.utils;

import android.content.ComponentName;

import com.example.test608.model.Destination;

import java.util.HashMap;

import androidx.fragment.app.FragmentActivity;
import androidx.navigation.ActivityNavigator;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.NavGraphNavigator;
import androidx.navigation.NavigatorProvider;
import androidx.navigation.fragment.FragmentNavigator;

public class NavGraphBuilder {
    public static void build(NavController controller, FragmentActivity activity,int containerId){
        NavigatorProvider provider=controller.getNavigatorProvider();
//        FragmentNavigator fragmentNavigator=provider.getNavigator(FragmentNavigator.class);
        FixFragmentNavigator fixFragmentNavigator=new FixFragmentNavigator(activity,activity.getSupportFragmentManager(),containerId);
        provider.addNavigator(fixFragmentNavigator);

        ActivityNavigator activityNavigator=provider.getNavigator(ActivityNavigator.class);

        NavGraph navGraph=new NavGraph(new NavGraphNavigator(provider));

        HashMap<String, Destination> destConfig=AppConfig.getsDestConfig();

        for(Destination value:destConfig.values()){
            if(value.Fragment){
                FragmentNavigator.Destination destination=fixFragmentNavigator.createDestination();
                destination.setClassName(value.className);
                destination.setId(value.id);
                destination.addDeepLink(value.pageUrl);
                navGraph.addDestination(destination);
            }else {
                ActivityNavigator.Destination destination=activityNavigator.createDestination();
                destination.setId(value.id);
                destination.setComponentName(new ComponentName(AppGlobals.getsApplication().getPackageName(),value.className));
                destination.addDeepLink(value.pageUrl);
                navGraph.addDestination(destination);
            }
            if(value.asStarter){
                navGraph.setStartDestination(value.id);
            }
        }
        controller.setGraph(navGraph);
    }
}
