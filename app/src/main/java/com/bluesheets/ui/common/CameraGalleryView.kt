package com.bluesheets.ui.common

import android.view.LayoutInflater
import com.bluesheets.databinding.DialogCameraGalleryBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import src.wrapperutil.utilities.WrapperConstant
import src.wrapperutil.utilities.WrapperEnumAnnotation

object CameraGalleryView {

    fun openDialog(
        baseActivity: BaseActivity,
        listener: OnCameraGalleryListener
    ) {
        GlobalData.cameraGalleryListener = object : OnCameraGalleryListener {
            override fun onReceivedFromCamera(imagePath: String?) {
                listener.onReceivedFromCamera(imagePath)
            }

            override fun onReceivedFromGallery(imagePath: String?) {
                listener.onReceivedFromGallery(imagePath)
            }

            override fun onReceivedFromGallery(multipleImagesList: MutableList<String>) {
                listener.onReceivedFromGallery(multipleImagesList)
            }
        }

        val dialog = BottomSheetDialog(baseActivity)
        var binding = DialogCameraGalleryBinding.inflate(LayoutInflater.from(baseActivity))
        dialog.setContentView(binding.root)
        var isCameraSelected = true

        binding.radioCamera.setOnClickListener {
            isCameraSelected = true
            binding.radioCamera.isChecked = true
            binding.radioGallery.isChecked = false
            binding.textViewCamera.updateMode(WrapperEnumAnnotation(WrapperConstant.TEXT_MODE_SUB_HEADING_BOLD))
            binding.textViewGallery.updateMode(WrapperEnumAnnotation(WrapperConstant.TEXT_MODE_SUB_HEADING_REGULAR))
        }

        binding.radioGallery.setOnClickListener {
            isCameraSelected = false
            binding.radioCamera.isChecked = false
            binding.radioGallery.isChecked = true
            binding.textViewGallery.updateMode(WrapperEnumAnnotation(WrapperConstant.TEXT_MODE_SUB_HEADING_BOLD))
            binding.textViewCamera.updateMode(WrapperEnumAnnotation(WrapperConstant.TEXT_MODE_SUB_HEADING_REGULAR))
        }

        binding.textViewCamera.setOnClickListener {
            binding.radioCamera.callOnClick()
        }

        binding.textViewGallery.setOnClickListener {
            binding.radioGallery.callOnClick()
        }

        when (isCameraSelected) {
            true -> {
                binding.radioCamera.callOnClick()
            }
            else -> {
                binding.radioGallery.callOnClick()
            }
        }

        binding.imageCross.setOnClickListener {
            dialog.dismiss()
        }

        binding.btnDone.setOnClickListener {
            if (isCameraSelected) {
                baseActivity.checkForAllPermission(isForCamera = true)
            } else {
                baseActivity.checkForAllPermission(isForCamera = false)
            }
            dialog.dismiss()
        }

        dialog.show()
    }
}
