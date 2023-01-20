package src.wrapperutil.utilities

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import src.wrapperutil.listener.OnGlobalCallListener

object PicUtil {

    private const val GLIDE_TIMEOUT = 30000 // 60 seconds

    private val TAG = PicUtil::class.java.simpleName

    fun display160ProfileImgWithCenterCrop(
        context: Context?,
        url: String,
        imageView: ImageView,
        img: Int,
        listener: OnGlobalCallListener? = null
    ) {
        try {

            // changes on

            if (!isValidContextForGlide(context)) {
                imageView?.setImageResource(img)
                listener?.onError(Any())
                return
            }

            if (url.isNullOrBlank()) {
                imageView.setImageResource(img)
                listener?.onError(Any())
                return
            }

            Glide.with(context!!)
                .load(url)
                .fitCenter()
                .disallowHardwareConfig()
                .error(img)
                .centerCrop()
                .placeholder(img)
                //                                .timeout(GLIDE_TIMEOUT)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        listener?.onError(Any())
                        return true
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        Log.d(TAG, "OnResourceReady")
                        listener?.onSuccess(Any())
                        return false
                    }
                })
                .into(imageView)

/*
            if (FileUtils.checkIsUrl(url)) {
                CalculateImageResolution.getInstance().generate100RosulationUrl(url, imageView, object : CalculateImageResolution.OnGenerateUrl {
                    override fun onGenerateUrl(url: String) {
                        if (!isValidContextForGlide(context)) {
                            imageView?.setImageResource(img)
                            listener.onError(Any())
                            return
                        }
                        listener.onSuccess(Any())
                    }

                    override fun onFail() {
                        if (!isValidContextForGlide(context)) {
                            imageView?.setImageResource(img)
                            listener.onError(Any())
                            return
                        }
                        //  listener.onError(Any())

                        GlideApp.with(context!!)
                            .load(url)
                            .fitCenter()
                            .disallowHardwareConfig()
                            .error(img)
                            .centerCrop()
                            .placeholder(img)
                            //                                .timeout(GLIDE_TIMEOUT)
                            .diskCacheStrategy(DiskCacheStrategy.DATA)
                            .listener(object : RequestListener<Drawable> {
                                override fun onLoadFailed(p0: GlideException?, p1: Any?, p2: Target<Drawable>?, p3: Boolean): Boolean {
                                    listener.onError(Any())
                                    return true
                                }

                                override fun onResourceReady(p0: Drawable?, p1: Any?, p2: Target<Drawable>?, p3: DataSource?, p4: Boolean): Boolean {
                                    Log.d(TAG, "OnResourceReady")
                                    listener.onSuccess(Any())
                                    return false
                                }
                            })
                            .into(imageView)


                    }
                })
            } else {
                GlideApp.with(context!!)
                    .load(File(url)) // Uri of the picture
                    //.override(resize_img_width, resize_img_height)
                    .disallowHardwareConfig()
                    .error(img)
                    .centerCrop()
                    .placeholder(img)
                    //                        .timeout(GLIDE_TIMEOUT)
                    //  .override(SQUARE_MEDIUM_SIZE)
                    //  .transition(withCrossFade())
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
                    ////.transition(DrawableTransitionOptions.withCrossFade())
                    .into(imageView)

            }
*/
        } catch (ex: Exception) {

            Log.e(TAG, "displayImgWithDefault: " + ex.message)
        }
    }

    fun displayImageWithCenterCrop(
        context: Context?,
        url: String?,
        imageView: ImageView?,
        img: Int,
        listener: OnGlobalCallListener? = null
    ) {
        try {

            if (!isValidContextForGlide(context)) {
                imageView?.setImageResource(img)
                listener?.onError(Any())
                return
            }

            if (url.isNullOrBlank()) {
                imageView?.setImageResource(img)
                listener?.onError(Any())
                return
            }
            if (imageView != null)
                Glide.with(context!!)
                    .load(url)
                    .fitCenter()
                    .disallowHardwareConfig()
                    .error(img)
                    .centerCrop()
                    .placeholder(img)
                    //                                .timeout(GLIDE_TIMEOUT)
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            listener?.onError(Any())
                            return true
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            Log.d(TAG, "OnResourceReady")
                            listener?.onSuccess(Any())
                            return false
                        }
                    })
                    .into(imageView)

/*
            if (FileUtils.checkIsUrl(url)) {
                CalculateImageResolution.getInstance().generate100RosulationUrl(url, imageView, object : CalculateImageResolution.OnGenerateUrl {
                    override fun onGenerateUrl(url: String) {
                        if (!isValidContextForGlide(context)) {
                            imageView?.setImageResource(img)
                            listener.onError(Any())
                            return
                        }
                        listener.onSuccess(Any())
                    }

                    override fun onFail() {
                        if (!isValidContextForGlide(context)) {
                            imageView?.setImageResource(img)
                            listener.onError(Any())
                            return
                        }
                        //  listener.onError(Any())

                        GlideApp.with(context!!)
                            .load(url)
                            .fitCenter()
                            .disallowHardwareConfig()
                            .error(img)
                            .centerCrop()
                            .placeholder(img)
                            //                                .timeout(GLIDE_TIMEOUT)
                            .diskCacheStrategy(DiskCacheStrategy.DATA)
                            .listener(object : RequestListener<Drawable> {
                                override fun onLoadFailed(p0: GlideException?, p1: Any?, p2: Target<Drawable>?, p3: Boolean): Boolean {
                                    listener.onError(Any())
                                    return true
                                }

                                override fun onResourceReady(p0: Drawable?, p1: Any?, p2: Target<Drawable>?, p3: DataSource?, p4: Boolean): Boolean {
                                    Log.d(TAG, "OnResourceReady")
                                    listener.onSuccess(Any())
                                    return false
                                }
                            })
                            .into(imageView)


                    }
                })
            } else {
                GlideApp.with(context!!)
                    .load(File(url)) // Uri of the picture
                    //.override(resize_img_width, resize_img_height)
                    .disallowHardwareConfig()
                    .error(img)
                    .centerCrop()
                    .placeholder(img)
                    //                        .timeout(GLIDE_TIMEOUT)
                    //  .override(SQUARE_MEDIUM_SIZE)
                    //  .transition(withCrossFade())
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
                    ////.transition(DrawableTransitionOptions.withCrossFade())
                    .into(imageView)

            }
*/
        } catch (ex: Exception) {

            Log.e(TAG, "displayImgWithDefault: " + ex.message)
        }
    }

    fun downloadAndCacheImage(context: Context, url: String) {
        Glide.with(context)
            .load(url)
            .preload()
    }

    private fun isValidContextForGlide(context: Context?): Boolean {
        if (context == null) {
            return false
        }
        if (context is Activity) {
            val activity = context as Activity?
            if (activity!!.isDestroyed || activity.isFinishing) {
                return false
            }
        }
        return true
    }
}
