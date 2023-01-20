package src.wrapperutil.uicomponent

import android.content.Context
import android.os.Build
import android.text.TextUtils
import android.webkit.WebResourceResponse
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.IOException
import java.util.*

class WVResourceMappingHelper private constructor(private val context: Context) {
    private var localAssetMapModelList: List<LocalAssetMapModel>? = null
    val overridableExtensions: List<String> = ArrayList(Arrays.asList("js", "css", "png", "jpg", "woff", "ttf", "eot", "ico"))

    private //        String pageData = null;
    //        try {
    //            pageData = ResourceAccessHelper.getJsonData(context, "web-assets/map.json");
    //        } catch (IOException e) {
    //        }
    //        if (pageData != null) {
    //            Type listType = new TypeToken<ArrayList<LocalAssetMapModel>>() {
    //            }.getType();
    //            localAssetMapModelList = new Gson().fromJson(pageData, listType);
    //        }
    //
    //        pageData = null;
    //        try {
    //            pageData = ResourceAccessHelper.getJsonData(context, "web-assets/fonts-map.json");
    //        } catch (IOException e) {
    //        }
    //        if (pageData != null) {
    //            Type listType = new TypeToken<ArrayList<LocalAssetMapModel>>() {
    //            }.getType();
    //            List<LocalAssetMapModel> fontsMap = new Gson().fromJson(pageData, listType);
    //            localAssetMapModelList.addAll(fontsMap);
    //        }
    val localAssetList: List<LocalAssetMapModel>
        get() = ArrayList()

    fun getLocalAssetPath(url: String): String? {
        if (TextUtils.isEmpty(url)) {
            return ""
        }
        if (localAssetMapModelList == null) {
            localAssetMapModelList = localAssetList
        }
        if (localAssetMapModelList != null && !localAssetMapModelList!!.isEmpty()) {
            for (localAssetMapModel in localAssetMapModelList!!) {
                if (localAssetMapModel.url == url) {
                    return localAssetMapModel.asset_url
                }
            }
        }
        return ""
    }

    fun getLocalFilePath(url: String): String {
        var localFilePath = ""
        val fileNameForUrl = getLocalFileNameForUrl(url)
        if (!TextUtils.isEmpty(fileNameForUrl) && fileExists(fileNameForUrl)) {
            localFilePath = getFileFullPath(fileNameForUrl)
        }
        return localFilePath
    }

    fun getLocalFileNameForUrl(url: String): String {
        var localFileName = ""
        val parts = url.split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        if (parts.size > 0) {
            localFileName = parts[parts.size - 1]
        }
        return localFileName
    }

    private fun fileExists(fileName: String): Boolean {
        val path = context
            .filesDir.toString() + "/cart/" + fileName
        return File(path).exists()
    }

    private fun getFileFullPath(relativePath: String): String {
        return context.filesDir.toString() + "/cart/" + relativePath
    }

    fun getFileExt(fileName: String): String {
        return fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length)
    }

    fun getMimeType(fileExtension: String): String {
        var mimeType = ""
        when (fileExtension) {
            "css" -> mimeType = "text/css"
            "js" -> mimeType = "text/javascript"
            "png" -> mimeType = "image/png"
            "jpg" -> mimeType = "image/jpeg"
            "ico" -> mimeType = "image/x-icon"
            "woff", "ttf", "eot" -> mimeType = "application/x-font-opentype"
        }
        return mimeType
    }

    @Throws(IOException::class)
    fun getWebResourceResponseFromAsset(assetPath: String, mimeType: String, encoding: String): WebResourceResponse {
        val inputStream = context.assets.open(assetPath)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val statusCode = 200
            val reasonPhase = "OK"
            val responseHeaders = HashMap<String, String>()
            responseHeaders["Access-Control-Allow-Origin"] = "*"
            return WebResourceResponse(mimeType, encoding, statusCode, reasonPhase, responseHeaders, inputStream)
        }
        return WebResourceResponse(mimeType, encoding, inputStream)
    }

    @Throws(FileNotFoundException::class)
    fun getWebResourceResponseFromFile(filePath: String, mimeType: String, encoding: String): WebResourceResponse {
        val file = File(filePath)
        val fileInputStream = FileInputStream(file)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val statusCode = 200
            val reasonPhase = "OK"
            val responseHeaders = HashMap<String, String>()
            responseHeaders["Access-Control-Allow-Origin"] = "*"
            return WebResourceResponse(mimeType, encoding, statusCode, reasonPhase, responseHeaders, fileInputStream)
        }
        return WebResourceResponse(mimeType, encoding, fileInputStream)
    }

    private inner class LocalAssetMapModel {
        internal var url: String? = null
        internal var asset_url: String? = null
    }

    companion object {
        private var instance: WVResourceMappingHelper? = null

        fun getInstance(context: Context): WVResourceMappingHelper {
            if (instance == null) {
                instance = WVResourceMappingHelper(context)
            }
            return instance as WVResourceMappingHelper
        }
    }
}
