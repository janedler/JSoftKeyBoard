package com.safe.keyboard.util;

import android.content.Context;

/**
 * 资源操作工具类
 * Created by janedler on 16/7/14.
 */
public final class RUtil {

    public static int layout(Context paramContext, String paramString){
        return paramContext.getResources().getIdentifier(paramString, "build/intermediates/exploded-aar/com.android.support/appcompat-v7/23.4.0/res/layout", paramContext.getPackageName());
    }

    public static int drawable(Context paramContext, String paramString){
        return paramContext.getResources().getIdentifier(paramString, "drawable", paramContext.getPackageName());
    }

    public static int id(Context paramContext, String paramString){
        return paramContext.getResources().getIdentifier(paramString, "id", paramContext.getPackageName());
    }

    public static int color(Context paramContext, String paramString){
        return paramContext.getResources().getIdentifier(paramString, "build/intermediates/exploded-aar/com.android.support/appcompat-v7/23.4.0/res/color", paramContext.getPackageName());
    }

    public static int style(Context paramContext, String paramString){
        return paramContext.getResources().getIdentifier(paramString, "style", paramContext.getPackageName());
    }


    public static int xml(Context paramContext, String paramString){
        return paramContext.getResources().getIdentifier(paramString, "xml", paramContext.getPackageName());
    }


}
