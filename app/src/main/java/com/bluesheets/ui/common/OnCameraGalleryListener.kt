package com.bluesheets.ui.common
interface OnCameraGalleryListener {

    fun onReceivedFromCamera(imagePath: String?)

    fun onReceivedFromGallery(imagePath: String?)

    fun onReceivedFromGallery(multipleImagesList: MutableList<String>)
}
