package com.dev.marcellocamara.pgm.Helper;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/***
    marcellocamara@id.uff.br
            2019
***/

public class Permissions {

    public static final String[] PHOTO_PERMISSIONS = {
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public static final int CAMERA_REQUEST = 5, GALLERY_REQUEST = 10;

    public static boolean verify(Activity activity, String[] strings, int requestCode){
        if(Build.VERSION.SDK_INT >= 23){
            List<String> permissionsDenied = new ArrayList<>();
            for (String permission : strings){
                boolean allowed = ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED;
                if(!allowed){
                    permissionsDenied.add(permission);
                }
            }
            if(permissionsDenied.isEmpty()){
                return true;
            }else{
                String[] permissionsDeniedArrayStrings = new String[permissionsDenied.size()];
                permissionsDenied.toArray(permissionsDeniedArrayStrings);
                ActivityCompat.requestPermissions(activity, permissionsDeniedArrayStrings,requestCode);
                return false;
            }
        }else{
            return true;
        }
    }

}
