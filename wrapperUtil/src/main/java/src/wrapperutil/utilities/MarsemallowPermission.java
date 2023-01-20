package src.wrapperutil.utilities;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;


public class MarsemallowPermission {
	private Context _cotext;
	public static final int REQUEST_APP_SETTINGS = 168;
	public static final int REQUEST_WRITE_STORAGE = 112;


	public MarsemallowPermission(Context _cotext) {
		this._cotext = _cotext;
	}

	public static boolean isPermissionDialogOpen(Context context) {

		try {
			ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
			ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
			if ("com.android.packageinstaller.permission.ui.GrantPermissionsActivity".equals(cn.getClassName()))
				return true;

		} catch (Exception ex) {

		}
		return false;
	}


	public Boolean callForPermission(String[] requiredPermissions) {

		if (Build.VERSION.SDK_INT > 22 && !hasPermissions(requiredPermissions)) {
			ActivityCompat.requestPermissions(((Activity) _cotext),
					requiredPermissions,
					REQUEST_WRITE_STORAGE);
			return false;
		} else
			return true;

	}

	/*public Boolean callForPermissionFromFragment(Activity activity, String[] requiredPermissions){

		if (Build.VERSION.SDK_INT > 22 && !hasPermissions(requiredPermissions)) {
			requestPermissions(
					requiredPermissions,
					REQUEST_WRITE_STORAGE);
			return false;
		}else
			return true;

	}*/

	public boolean hasPermissions(@NonNull String... permissions) {
		for (String permission : permissions)
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
				if (PackageManager.PERMISSION_GRANTED != _cotext.checkSelfPermission(permission))
					return false;
			}
		return true;
	}


	private void goToSettings() {
		Intent myAppSettings = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
				Uri.parse("package:" + _cotext.getPackageName()));
		myAppSettings.addCategory(Intent.CATEGORY_DEFAULT);
		myAppSettings.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		((Activity) _cotext).startActivityForResult(myAppSettings, REQUEST_APP_SETTINGS);
	}


/*
	public void openDialogForSetting(final Context context, int title, int msg, final boolean isOnBackPressEnable) {


		new android.app.AlertDialog.Builder(_cotext)
				.setTitle(title)
				.setMessage(msg)
				.setCancelable(true)
				.setPositiveButton(context.getResources().getString(R.string.setting),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int whichButton) {
								// do something...
								goToSettings();
							}
						}
				)
				.setNegativeButton(context.getResources().getString(R.string.cancel),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int whichButton) {
								dialog.dismiss();
								if (isOnBackPressEnable) {
									if (_cotext instanceof Activity) {
										((Activity) _cotext).onBackPressed();
									}
								}


							}
						}
				).show();

	}
*/

	public void openDialogForSetting(Context context, String title, String msg) {

		new AlertDialog.Builder(_cotext)
				.setTitle(title)
				.setMessage(msg)
				.setCancelable(true)
				.setPositiveButton("Setting",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int whichButton) {
								// do something...
								goToSettings();
							}
						}
				)
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int whichButton) {
								dialog.dismiss();
							}
						}
				).show();

	}


	public boolean onlyCheckPermissions(Context context, String[] requiredPermissions) {


		boolean permissionGranted = false;

		for (int i = 0; i < requiredPermissions.length; i++) {
			int res = context.checkCallingOrSelfPermission(requiredPermissions[i]);
			permissionGranted = (res == PackageManager.PERMISSION_GRANTED);
		}

		return permissionGranted;
	}


}


/*
	public class MarsemallowPermission
{
	private Context _cotext;
	public static final int REQUEST_APP_SETTINGS = 168;
	public static final int REQUEST_CALL_BACK = 112;


	public MarsemallowPermission(Context _cotext){
		this._cotext = _cotext;
	}


	public Boolean callForPermission(String[] requiredPermissions){

		if (Build.VERSION.SDK_INT > 22 && !hasPermissions(requiredPermissions)) {
			ActivityCompat.requestPermissions(((Activity) _cotext),
					requiredPermissions,
					REQUEST_CALL_BACK);
			return false;
		}else
			return true;

	}

	public boolean hasPermissions(@NonNull String... permissions) {
		for (String permission : permissions)
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
				if (PackageManager.PERMISSION_GRANTED != _cotext.checkSelfPermission(permission))
					return false;
			}
		return true;
	}


	private void goToSettings() {
		Intent myAppSettings = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
				Uri.parse("package:" + _cotext.getPackageName()));
		myAppSettings.addCategory(Intent.CATEGORY_DEFAULT);
		myAppSettings.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		((Activity) _cotext).startActivityForResult(myAppSettings, REQUEST_APP_SETTINGS);
	}


	public void openDialogForSetting(int title, int msg){

		new AlertDialog.Builder(_cotext)
				.setTitle(title)
				.setMessage(msg)
				.setCancelable(true)
				.setPositiveButton("Setting",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int whichButton) {
								// do something...
								goToSettings();
							}
						}
				)
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int whichButton) {
								dialog.dismiss();
							}
						}
				).show();

	}

	public void openDialogForSetting(String  title, String msg){

		new AlertDialog.Builder(_cotext)
				.setTitle(title)
				.setMessage(msg)
				.setCancelable(true)
				.setPositiveButton("Setting",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int whichButton) {
								// do something...
								goToSettings();
							}
						}
				)
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int whichButton) {
								dialog.dismiss();
							}
						}
				).show();

	}






}
*/
