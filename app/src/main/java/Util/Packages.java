package Util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by Administrator on 2017/7/25 0025.
 */

public final class Packages {
    public static String getVersionName(Context context){
        return getVersionName(context,context.getPackageName());
    }
    public static String getVersionName(Context context,String packagename){
        String version =null;
        try{
            PackageManager manager=context.getPackageManager();
            PackageInfo info=manager.getPackageInfo(packagename,0);
            version=info.versionName;
        }
        catch (PackageManager.NameNotFoundException e){
            e.printStackTrace();
        }
        return version;
    }

}
