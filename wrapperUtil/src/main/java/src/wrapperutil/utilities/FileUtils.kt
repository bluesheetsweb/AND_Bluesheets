

package src.wrapperutil.utilities

import android.content.Context
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Log
import java.io.*
import java.text.SimpleDateFormat
import java.util.*
import android.provider.DocumentsContract

import android.content.ContentUris
import android.database.Cursor

import android.os.Build
import java.lang.Integer.min


object FileUtils {

    internal val TAG = "FileUtils"
    internal val TempFolder = "/Files/Temp"

    fun isEmptyString(text: String?): Boolean {

        if (text == null)
            return true

        return !(text != null && !text.trim { it <= ' ' }.isEmpty() && text.trim { it <= ' ' } != "null")
    }

    // type = 2 (video), 1 - pdf , 3 - image, 4 - audio.
    fun getTempFileFromUri(context: Context, uri: Uri, type: Int, file_name: String?): File? {
        var file_name = file_name
        var inStr: InputStream? = null
        var out: OutputStream? = null
        if (isEmptyString(file_name))
            file_name = getFilename(context, uri)

        val tempFile = createTempFile(context, type, file_name)
        try {
            inStr = context.contentResolver.openInputStream(uri)
            // open the output-file:
            out = FileOutputStream(tempFile!!)
            // copy the content:
            val buffer = ByteArray(1024)
            var len: Int = inStr!!.read(buffer)
            while (len != -1) {
                out.write(buffer, 0, len)
            }
            return tempFile
            // Contents are copied!
        } catch (ex: Exception) {
            Log.e(TAG, "writePDFToSdCard: error in main try" + ex.message)
            return null
        } finally {
            try {
                inStr?.close()
                out?.close()
            } catch (e: IOException) {
                e.printStackTrace()
                Log.e(TAG, "writePDFToSdCard: error while close " + e.message)
            }
        }
    }

    // type = 2 (video), 1 - pdf , 3 - image.
    fun getTempFilePathFromUri(context: Context, uri: Uri, type: Int, file_name: String?): String? {
        var file_name = file_name
        var inStr: InputStream? = null
        var out: OutputStream? = null
        if (isEmptyString(file_name))
            file_name = getFilename(context, uri)
        val tempFile = createTempFile(context, type, file_name)
        try {
            inStr = context.contentResolver.openInputStream(uri)
            // open the output-file:
            out = FileOutputStream(tempFile!!)
            // copy the content:
            val buffer = ByteArray(1024)
            var len: Int = inStr!!.read(buffer)
            while (len != -1) {
                out.write(buffer, 0, len)
            }
            return tempFile.absolutePath
            // Contents are copied!
        } catch (ex: Exception) {
            Log.e(TAG, "writePDFToSdCard: error in main try" + ex.message)
            return null
        } finally {
            try {
                inStr?.close()
                out?.close()
            } catch (e: IOException) {
                e.printStackTrace()
                Log.e(TAG, "writePDFToSdCard: error while close " + e.message)
            }
        }
    }

    fun clearTempFolder(context: Context) {
        val mediaStorageDir = File(
            Environment.getExternalStorageDirectory().toString() +
                "/Android/data/" +
                context.applicationContext.packageName +
                TempFolder
        )
        if (mediaStorageDir.isDirectory) {
            val children = mediaStorageDir.list()
            for (i in children.indices) {
                File(mediaStorageDir, children[i]).delete()
            }
        }
    }

    fun createTempFile(context: Context, type: Int, file_name: String?): File? {
        var mImageName = ""
        val mediaStorageDir = File(
            Environment.getExternalStorageDirectory().toString() +
                "/Android/data/" +
                context.applicationContext.packageName +
                TempFolder
        )
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null
            }
        }
        val timeStamp = SimpleDateFormat("ddMMyyyy_HHmmss").format(Date())

        if (isEmptyString(file_name)) {
            if (type == 1)
                mImageName = "$timeStamp.pdf"
            else if (type == 2)
                mImageName = "$timeStamp.mp4"
            else if (type == 3)
                mImageName = "$timeStamp.jpeg"
            else if (type == 4)
                mImageName = "$timeStamp.mp3"
        } else
            mImageName = file_name!!

        return File(mediaStorageDir.path + File.separator + mImageName)
    }

    fun getFilename(context: Context, uri: Uri): String? {
        var fileName: String? = ""
        try {
            var filePathUri = uri
            if (uri.scheme!!.toString().compareTo("content") == 0) {
                val cursor = context.contentResolver.query(uri, null, null, null, null)
                if (cursor!!.moveToFirst()) {
                    val column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA) // Instead of "MediaStore.Images.MediaDb.DATA" can be used "_data"
                    filePathUri = Uri.parse(cursor.getString(column_index))
                    fileName = filePathUri.lastPathSegment!!.toString()
                }
            } else if (uri.scheme!!.compareTo("file") == 0) {
                fileName = filePathUri.lastPathSegment!!.toString()
            } else {
                fileName = fileName + "_" + filePathUri.lastPathSegment
            }
            return fileName
        } catch (ex: Exception) {
            Log.e(TAG, " ex " + ex.message)
        }

        try {
            if (uri.scheme == "content") {
                val cursor = context.contentResolver.query(uri, null, null, null, null)
                try {
                    if (cursor != null && cursor.moveToFirst()) {
                        fileName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                    }
                } finally {
                    cursor!!.close()
                }
            }
            if (fileName == null) {
                fileName = uri.path
                val cut = fileName!!.lastIndexOf('/')
                if (cut != -1) {
                    fileName = fileName.substring(cut + 1)
                }
            }
        } catch (ex: Exception) {
            Log.e(TAG, "getFilename: " + ex.message)
        }

        return fileName
    }

    /************ end of pdf posting work  */

    fun getFileNameFromFullPath(actualpath: String): String {

        return try {
            val lastSepratedValue = actualpath.substring(actualpath.lastIndexOf(File.separator) + 1)
            Log.e(TAG, "getFileNameFromFullPath lastSepratedValue$lastSepratedValue")
            val lastPos = lastSepratedValue.indexOf("?")
            Log.e(TAG, "getFileNameFromFullPath INDEXOF$lastPos")
            var finalName = ""
            finalName = if (lastPos > 0) {
                lastSepratedValue.substring(0, lastPos)
            } else {
                lastSepratedValue
            }
            Log.e(TAG, "getFileNameFromFullPath finalName$finalName")
            finalName
        } catch (ex: java.lang.Exception) {
            ""
        }
    }

    fun getLastFolderOfFile(actualpath: String): String {
        try {
            var folderPath = actualpath.substring(0, actualpath.lastIndexOf(File.separator))
            return folderPath.substring(folderPath.lastIndexOf(File.separator) + 1)
        } catch (ex: Exception) {
            return ""
        }
    }

    fun getRelativePathFromUrl(actualpath: String): String {
        var actualpath = actualpath

        try {
            if (actualpath.contains("http://"))
                actualpath = actualpath.substring(7)
            else if (actualpath.contains("https://"))
                actualpath = actualpath.substring(8)
            val relativeIndex = actualpath.indexOf(File.separator)
            return actualpath.substring(relativeIndex + 1)
        } catch (ex: Exception) {
            return ""
        }
    }

    /**
     * Gets the extension of a file name, like ".png" or ".jpg".
     *
     * @param uri
     * @return Extension including the dot("."); "" if there is no extension;
     * null if uri was null.
     */
    fun getExtension(uri: String?): String? {
        if (uri == null) {
            return null
        }

        val dot = uri.lastIndexOf(".")
        return if (dot >= 0) {
            uri.substring(dot)
        } else {
            // No extension.
            ""
        }
    }

    fun isFileTypePDF(uri: String?): Boolean {
        val fileName = getFileNameFromFullPath(uri!!)
        Log.e(TAG, "isFileTypePDF fileName" + fileName)
        var extention = getExtension(fileName)
        Log.e(TAG, "isFileTypePDF extention" + extention)
        return (extention?.equals(".pdf", ignoreCase = true)!!)
    }

    fun isFileTypeGif(uri: String?): Boolean? {
        val fileName = getFileNameFromFullPath(uri!!)
        Log.e(TAG, "isFileTypeGif fileName" + fileName)
        var extention = getExtension(fileName)
        Log.e(TAG, "isFileTypeGif extention" + extention)
        return (extention?.equals(".gif", ignoreCase = true)!!)
    }

    fun isFileTypeHls(uri: String?): Boolean? {
        val fileName = getFileNameFromFullPath(uri!!)
        Log.e(TAG, "isFileTypeHls fileName" + fileName)
        var extention = getExtension(fileName)
        Log.e(TAG, "isFileTypeHls extention" + extention)
        return (extention?.contains(".m3u8", ignoreCase = true)!!)
    }

    fun isFileTypeVideo(uri: String?): Boolean {
        val fileName = getFileNameFromFullPath(uri!!)
        Log.e(TAG, "isFileTypeVideo fileName" + fileName)
        var extention = getExtension(fileName)
        Log.e(TAG, "isFileTypeVideo extention" + extention)
        return ((extention?.contains(".m3u8", ignoreCase = true)!!) || (extention.contains(".mp4", ignoreCase = true)))
    }

    fun getOutputFileName(context: Context, fileName: String, extention: String): String {
        var fileName = fileName
        val resourceFolder = File(
            Environment.getExternalStorageDirectory().toString() +
                "/Android/data/" +
                context.applicationContext.packageName +
                "/Audio/Output"
        )
        if (!resourceFolder.exists()) {
            if (!resourceFolder.mkdirs()) {
                return ""
            }
        }
        //        outputFileName = fileName + ".mp3";
        fileName = resourceFolder.path + fileName
        Log.d("filename", fileName)
        //        Log.d("outputFileName", outputFileName);
        try {
            if (!File(fileName).exists())
                File(fileName).createNewFile()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return fileName
    }

    fun copyFile(Copy_sourceLocation: File, Paste_Target_Location: File): String? {
        try {
            // make sure the target file exists
            if (Copy_sourceLocation.exists()) {

                val inStr = FileInputStream(Copy_sourceLocation)
                val out = FileOutputStream(
                    Paste_Target_Location
                )

                // Copy the bits from instream to outstream
                val buf = ByteArray(1024)
                var len: Int = inStr.read(buf)
                while (len > 0) {
                    out.write(buf, 0, len)
                }
                inStr.close()
                out.close()

                Log.i("copyFile", "Copy file successful.")
                return ""
            } else {
                Log.i("copyFile", "Copy file failed. Source file missing.")
                return null
            }
        } catch (e: NullPointerException) {
            Log.i("copyFile", "" + e)

            return null
        } catch (e: Exception) {
            Log.i("copyFile", "" + e)
            return null
        }

        // return null;
    }

    fun checkIsUrl(url: String): Boolean {
        return when (
            !isEmptyString(url) && url.trim().toLowerCase().startsWith("http://") ||
                !isEmptyString(url) && url.trim().toLowerCase().startsWith("https://")
        ) {
            true -> true
            false -> false
        }
    }

    fun getLocalPath(url: String): String {
        var filePath = url
        try {
            var path = Uri.parse(url).path
            var localFile = File(path)
            if (localFile.exists())
                filePath = localFile.path
        } catch (t: Throwable) {
            t.printStackTrace()
        }
        return filePath
    }

//    fun readJsonFromAsset(mContext: Context, fileName: String, class2: Class<*>): Class<*>? {
//
//        //  var path = language + ".txt"
//        var json: String? = null
//        try {
//            val ins = mContext.assets.open(fileName)
//            val size = ins.available()
//            val buffer = ByteArray(size)
//            ins.read(buffer)
//            ins.close()
//            json = String(buffer, charset("UTF-8"))
//            val gson = Gson()
//            // val jsonArray = JSONArray(json)
//            // val jsonString = jsonArray.toString()
//            val temp_obj = gson.fromJson(json, class2::class.java)
//            return temp_obj
//
//        } catch (ex: IOException) {
//            ex.printStackTrace()
//        }
//
//        return null
//
//    }

    fun getDownloadedFileIfExist(context: Context?, url: String?, searchingFolder: String?): String? {
        try {
            if (!isEmptyString(url)) {
                var fileName = getFileNameFromFullPath(url!!)
                var folder = File(context?.filesDir.toString() + searchingFolder)
                if (folder.exists()) {
                    var filePath = "${folder.path}${File.separator}$fileName"
                    if (File(filePath).exists())
                        return filePath
                }
            }
        } catch (e: Throwable) {
            e.printStackTrace()
        }
        return ""
    }

    fun deleteAllFiles(context: Context?, searchingFolder: String?) {
        try {
            if (!isEmptyString(searchingFolder)) {
                var folder = File(context?.filesDir.toString() + searchingFolder)
                if (folder.exists()) {
                    deleteRecursive(folder)
                }
            }
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    fun getFolderSize(dir: File): String {
        var bytes = getFolderSizeInLong(dir)
        if (bytes < 1024) return "$bytes B"
        val exp = (Math.log(bytes.toDouble()) / Math.log(1024.0)).toInt()
        val pre = "KMGTPE"[exp - 1] + ""
        return String.format("%.1f %sB", bytes / Math.pow(1024.0, exp.toDouble()), pre)
    }

    fun getFolderSizeInLong(dir: File): Long {
        if (dir.exists()) {
            var result: Long = 0
            val fileList = dir.listFiles()
            for (i in fileList.indices) {
                // Recursive call if it's a directory
                if (fileList[i].isDirectory) {
                    result += getFolderSizeInLong(fileList[i])
                } else {
                    // Sum the file size in bytes
                    result += fileList[i].length()
                }
            }
            return result // return the file size
        }
        return 0
    }

    fun deleteRecursive(fileOrDirectory: File) {
        if (fileOrDirectory.isDirectory)
            for (child in fileOrDirectory.listFiles())
                deleteRecursive(child)

        fileOrDirectory.delete()
    }


    fun getPathFromUri(context: Context, uri: Uri): String? {
        val isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT

        // DocumentProvide
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":").toTypedArray()
                val type = split[0]
                if ("primary".equals(type, ignoreCase = true)) {
                    return Environment.getExternalStorageDirectory().toString() + "/" + split[1]
                }

                // TODO handle non-primary volumes
            } else if (isDownloadsDocument(uri)) {
                val id = DocumentsContract.getDocumentId(uri)
                val contentUri = ContentUris.withAppendedId(
                    Uri.parse("content://downloads/public_downloads"), java.lang.Long.valueOf(id)
                )
                return getDataColumn(context, contentUri, null, null)
            } else if (isMediaDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":").toTypedArray()
                val type = split[0]
                var contentUri: Uri? = null
                if ("image" == type) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                } else if ("video" == type) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                } else if ("audio" == type) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                }
                val selection = "_id=?"
                val selectionArgs = arrayOf(
                    split[1]
                )
                return getDataColumn(context, contentUri, selection, selectionArgs)
            }
        } else if ("content".equals(uri.scheme, ignoreCase = true)) {

            // Return the remote address
            return if (isGooglePhotosUri(uri)) uri.lastPathSegment else getDataColumn(
                context,
                uri,
                null,
                null
            )
        } else if ("file".equals(uri.scheme, ignoreCase = true)) {
            return uri.path
        }
        return null
    }

    fun getDataColumn(
        context: Context, uri: Uri?, selection: String?,
        selectionArgs: Array<String>?
    ): String? {
        var cursor: Cursor? = null
        val column = "_data"
        val projection = arrayOf(
            column
        )
        try {
            cursor = context.contentResolver.query(
                uri!!, projection, selection, selectionArgs,
                null
            )
            if (cursor != null && cursor.moveToFirst()) {
                val index: Int = cursor.getColumnIndexOrThrow(column)
                return cursor.getString(index)
            }
        } finally {
            if (cursor != null) cursor.close()
        }
        return null
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    fun isExternalStorageDocument(uri: Uri): Boolean {
        return "com.android.externalstorage.documents" == uri.authority
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    fun isDownloadsDocument(uri: Uri): Boolean {
        return "com.android.providers.downloads.documents" == uri.authority
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    fun isMediaDocument(uri: Uri): Boolean {
        return "com.android.providers.media.documents" == uri.authority
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    fun isGooglePhotosUri(uri: Uri): Boolean {
        return "com.google.android.apps.photos.content" == uri.authority
    }


    fun getFilePath(context: Context, contentUri: Uri): String? {
        try {
            val filePathColumn = arrayOf(
                MediaStore.Files.FileColumns._ID,
                MediaStore.Files.FileColumns.TITLE,
                MediaStore.Files.FileColumns.SIZE,
                MediaStore.Files.FileColumns.DATE_ADDED,
                MediaStore.Files.FileColumns.DISPLAY_NAME,
            )

            val returnCursor = contentUri.let { context.contentResolver.query(it, filePathColumn, null, null, null) }

            if (returnCursor != null) {

                returnCursor.moveToFirst()
                val nameIndex = returnCursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME)
                val name = returnCursor.getString(nameIndex)
                val file = File(context.cacheDir, name)
                val inputStream = context.contentResolver.openInputStream(contentUri)
                val outputStream = FileOutputStream(file)
                var read: Int
                val maxBufferSize = 1 * 1024 * 1024
                val bytesAvailable = inputStream!!.available()

                val bufferSize = min(bytesAvailable, maxBufferSize)
                val buffers = ByteArray(bufferSize)

                while (inputStream.read(buffers).also { read = it } != -1) {
                    outputStream.write(buffers, 0, read)
                }

                inputStream.close()
                outputStream.close()
                return file.absolutePath
            }
            else
            {
                Log.d("","returnCursor is null")
                return null
            }
        }
        catch (e: Exception) {
            Log.d("","exception caught at getFilePath(): $e")
            return null
        }
    }

    //    public static String getMimeTypefromUri(Uri uri) {
    //        String mimeType = null;
    //        if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
    //            ContentResolver cr = ImageProcess.context.getContentResolver();
    //            mimeType = cr.getType(uri);
    //        } else {
    //            String fileExtension = MimeTypeMap.getFileExtensionFromUrl(uri
    //                    .toString());
    //            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(
    //                    fileExtension.toLowerCase());
    //        }
    //        return mimeType;
    //    }
}
