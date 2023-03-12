package com.bluesheets.ui.common

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import src.wrapperutil.utilities.FileUtils
import src.wrapperutil.utilities.Toaster
import java.io.File
import java.io.FileOutputStream

/**
 * Reference for generics: https://kotlinlang.org/docs/reference/generics.html
 * Basically BaseActivity will take any class that extends BaseViewModel
 */
/**
 * This class don't need the @AndroidEntryPoint annotation because this is abstract.
 */
abstract class BaseActivity : AppCompatActivity(), OnBackPressListener {

    var TAG = BaseActivity::class.java.simpleName

    private var isForCamera = true

    companion object {
        private const val PERMISSION_ALL = 102
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (GlobalData.screenHeight == 0) {
            GlobalData.screenHeight = Resources.getSystem().displayMetrics.heightPixels
            GlobalData.screenWidth = Resources.getSystem().displayMetrics.widthPixels
        }
    }

    fun showMessage(message: String) =
        Toaster.show(this@BaseActivity, message)

    fun showMessage(@StringRes resId: Int) = showMessage(getString(resId))

    fun checkForAllPermission(isForCamera: Boolean = true) {
        val mPermission = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
        )
        this.isForCamera = isForCamera
        if (!hasPermissions(*mPermission)) {
            ActivityCompat.requestPermissions(this, mPermission, PERMISSION_ALL)
        } else {
            runOnUiThread {
                if (isForCamera) {
                    openCamera()
                } else {
                    openGallery()
                }
            }
        }
    }

    private fun hasPermissions(vararg permissions: String): Boolean =
        permissions.all {
            ActivityCompat.checkSelfPermission(
                this@BaseActivity,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }

    override fun onBackPressed() {
        if (isBackPress()) {
            if (supportFragmentManager.backStackEntryCount > 0)
                supportFragmentManager.popBackStackImmediate()
            else super.onBackPressed()
        }
    }

    var galleryResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // There are no request codes

                if (result.data?.clipData != null) {
                    val count: Int = result.data?.clipData!!
                        .getItemCount() // evaluate the count before the for loop --- otherwise, the count is evaluated every loop.
                    var listImages: MutableList<String> = mutableListOf()
                    for (i in 0 until count) {
                        val imageUri: Uri = result.data?.clipData!!.getItemAt(i).getUri()
                        val selectedImagePath = FileUtils.getFilePath(this, imageUri)
                        listImages.add(selectedImagePath!!)
                        // do something with the image (save it to some directory or whatever you need to do with it here)
                    }
                    GlobalData.cameraGalleryListener?.onReceivedFromGallery(listImages)
                } else {
                    val selectedImageUri: Uri? = result.data?.data
                    if (selectedImageUri != null) {
                        val selectedImagePath = FileUtils.getFilePath(this, selectedImageUri)
                        GlobalData.cameraGalleryListener?.onReceivedFromGallery(selectedImagePath)
                    }
                }
            }
        }

    fun openGallery() {

        if (Build.VERSION.SDK_INT <= 19) {
            val i = Intent()
            i.type = "image/*"
            i.action = Intent.ACTION_GET_CONTENT
            i.addCategory(Intent.CATEGORY_OPENABLE)
            i.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            try {
                galleryResultLauncher.launch(i)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else if (Build.VERSION.SDK_INT > 19) {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            try {
                galleryResultLauncher.launch(intent)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    var cameraResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // There are no request codes
                val data: Intent? = result.data
                if (data?.extras != null) {
                    val extras: Bundle = data.extras!!
                    val bmp = extras["data"] as Bitmap?
                    if (bmp != null) {
                        saveImage(bmp)
                    }
                }
            }
        }

    fun openCamera() {

        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(
            MediaStore.EXTRA_OUTPUT,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI.toString()
        )

        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            try {
                cameraResultLauncher.launch(cameraIntent)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun saveImage(finalBitmap: Bitmap): String {
        var localImagePath = ""
        val t = Thread {
            val root = getExternalFilesDir(null)
            val myDir = File("$root/Captured Images/")
            if (!myDir.exists()) myDir.mkdirs()
            val fname = "/image-" + System.currentTimeMillis() + ".jpg"
            val file = File(myDir, fname)
            try {
                val out = FileOutputStream(file)
                finalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
                out.flush()
                out.close()
                localImagePath = myDir.toString() + fname
                runOnUiThread {
                    GlobalData.cameraGalleryListener?.onReceivedFromCamera(localImagePath)
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
        t.start()
        return localImagePath
    }

    open fun getRealPathFromURI(uri: Uri?): String? {
        if (uri == null) {
            return null
        }
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor: Cursor? =
            getContentResolver().query(uri, projection, null, null, null)
        if (cursor != null) {
            val column_index: Int = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            return cursor.getString(column_index)
        }
        return uri.path
    }
}
