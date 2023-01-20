package src.wrapperutil.rguicomponent.aleartDialog

// import kotlinx.android.synthetic.main.rg_declinepopup_dialog.view.*
// import kotlinx.android.synthetic.main.rg_declinepopup_dialog.view.btn_yes
// import kotlinx.android.synthetic.main.rg_declinepopup_dialog.view.iv_cancel
// import kotlinx.android.synthetic.main.rg_declinepopup_dialog.view.tv_heading
// import kotlinx.android.synthetic.main.rg_error_dialog.view.*
// import kotlinx.android.synthetic.main.rg_recordingpopup_dialog.view.*
//
// object RGAlertDialogUtil {
//
//    fun showErrorDialog(
//            title: String?,
//            buttonText: String?
//    ): AlertDialog? {
//
//
//        val builder = AlertDialog.Builder(
//                ContextThemeWrapper(
//                        mActivity,
//                        android.R.style.ThemeOverlay_Material_Dialog
//                )
//        )
//
//        var customLoadingDialog = builder.create()
//        customLoadingDialog.setCanceledOnTouchOutside(false)
//        customLoadingDialog.setCancelable(false)
//        val inflater = mActivity.layoutInflater
//        val dialogView = inflater.inflate(R.layout.rg_error_dialog, null)
//        customLoadingDialog.setView(dialogView)
//
//        customLoadingDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        customLoadingDialog.show()
//
//        dialogView.error_title.text = title
//        dialogView.okay_btn.text = buttonText
//        dialogView.okay_btn.isEnabled = true
//
//        dialogView.cross_image.setOnClickListener {
//            customLoadingDialog.dismiss()
//        }
//
//        dialogView.okay_btn.setOnClickListener {
//            customLoadingDialog.dismiss()
//        }
//        try {
//            val lp = WindowManager.LayoutParams()
//            lp.copyFrom(customLoadingDialog.window?.attributes)
//            lp.width = WindowManager.LayoutParams.MATCH_PARENT
//            lp.height = WindowManager.LayoutParams.WRAP_CONTENT
//            customLoadingDialog.show()
//            customLoadingDialog.window?.attributes = lp
//        } catch (ex: Exception) {
//            ex.printStackTrace()
//        }
//
//        return customLoadingDialog
//    }
//
//
//    fun showDeclinePopupDialog(
//            heading: String?,
//            subheading: String,
//            yes: String?,
//            no: String,
//            id: String,
//            rgGetOpenFragmentResponse: RGGetOpenFragmentResponse?,
//            pos: Int
//    ): AlertDialog? {
//
//
//        val builder = AlertDialog.Builder(
//                ContextThemeWrapper(
//                        mActivity,
//                        android.R.style.ThemeOverlay_Material_Dialog
//                )
//        )
//
//        var customDialog = builder.create()
//        customDialog.setCanceledOnTouchOutside(false)
//        customDialog.setCancelable(false)
//        val inflater = mActivity.layoutInflater
//        val dialogView = inflater.inflate(R.layout.rg_declinepopup_dialog, null)
//        customDialog.setView(dialogView)
//
//        customDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        customDialog.show()
//
//        dialogView.tv_heading.text = heading
//        dialogView.tv_subheading.text = subheading
//        dialogView.btn_yes.text = yes
//        dialogView.btn_no.text = no
//
//        dialogView.btn_yes.setOnClickListener {
//            customDialog.dismiss()
//            rgGetOpenFragmentResponse?.sendAcceptDeclineMeetingData(id, pos, "decline")
//        }
//
//        dialogView.btn_no.setOnClickListener {
//            customDialog.dismiss()
//        }
//
//        dialogView.iv_cancel.setOnClickListener {
//            customDialog.dismiss()
//        }
//
//        try {
//            val lp = WindowManager.LayoutParams()
//            lp.copyFrom(customDialog.window?.attributes)
//            lp.width = WindowManager.LayoutParams.MATCH_PARENT
//            lp.height = WindowManager.LayoutParams.WRAP_CONTENT
//            customDialog.show()
//            customDialog.window?.attributes = lp
//        } catch (ex: Exception) {
//            ex.printStackTrace()
//        }
//        return customDialog
//    }
//
//
//    fun showCancelPopupDialog(
//            heading: String?,
//            subheading: String,
//            yes: String?,
//            no: String,
//            id: String,
//            productType: Int,
//            rgGetOpenFragmentResponse: RGGetOpenFragmentResponse?,
//            pos: Int
//    ): AlertDialog? {
//
//
//        val builder = AlertDialog.Builder(
//                ContextThemeWrapper(
//                        mActivity,
//                        android.R.style.ThemeOverlay_Material_Dialog
//                )
//        )
//
//        var customDialog = builder.create()
//        customDialog.setCanceledOnTouchOutside(false)
//        customDialog.setCancelable(false)
//        val inflater = mActivity.layoutInflater
//        val dialogView = inflater.inflate(R.layout.rg_declinepopup_dialog, null)
//        customDialog.setView(dialogView)
//
//        customDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        customDialog.show()
//
//        dialogView.tv_heading.text = heading
//        dialogView.tv_subheading.text = subheading
//        dialogView.btn_yes.text = yes
//        dialogView.btn_no.text = no
//
//        dialogView.btn_yes.setOnClickListener {
//            customDialog.dismiss()
//            rgGetOpenFragmentResponse?.sendCancelMeetingData(id, pos, "cancel", productType)
//        }
//
//        dialogView.btn_no.setOnClickListener {
//            customDialog.dismiss()
//        }
//
//        dialogView.iv_cancel.setOnClickListener {
//            customDialog.dismiss()
//        }
//
//        try {
//            val lp = WindowManager.LayoutParams()
//            lp.copyFrom(customDialog.window?.attributes)
//            lp.width = WindowManager.LayoutParams.MATCH_PARENT
//            lp.height = WindowManager.LayoutParams.WRAP_CONTENT
//            customDialog.show()
//            customDialog.window?.attributes = lp
//        } catch (ex: Exception) {
//            ex.printStackTrace()
//        }
//        return customDialog
//    }
//
//
//    fun showPopupDialog(
//            onYesNoClickListener: OnYesNoClickListener,
//            headingText :String ="Do you want to leave this meeting ?",
//            descriptionText :String ="By clicking Yes you will leave this meeting.",
//            yesButtonText :String ="yes",
//            noButtonText :String ="No"
//    ): AlertDialog? {
//
//
//        val builder = AlertDialog.Builder(
//                ContextThemeWrapper(
//                        mActivity,
//                        android.R.style.ThemeOverlay_Material_Dialog
//                )
//        )
//
//        var customDialog = builder.create()
//        customDialog.setCanceledOnTouchOutside(false)
//        customDialog.setCancelable(false)
//        val inflater = mActivity.layoutInflater
//        val dialogView = inflater.inflate(R.layout.rg_declinepopup_dialog, null)
//        customDialog.setView(dialogView)
//
//        customDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        customDialog.show()
//
//        dialogView.tv_heading.text = headingText
//        dialogView.tv_subheading.text = descriptionText
//        dialogView.btn_yes.text = yesButtonText
//        dialogView.btn_no.text = noButtonText
//
//        dialogView.btn_yes.setOnClickListener {
//            customDialog.dismiss()
//            onYesNoClickListener.onYesClick("")
//        }
//
//        dialogView.btn_no.setOnClickListener {
//            customDialog.dismiss()
//        }
//
//        dialogView.iv_cancel.setOnClickListener {
//            customDialog.dismiss()
//        }
//
//        try {
//            val lp = WindowManager.LayoutParams()
//            lp.copyFrom(customDialog.window?.attributes)
//            lp.width = WindowManager.LayoutParams.MATCH_PARENT
//            lp.height = WindowManager.LayoutParams.WRAP_CONTENT
//            customDialog.show()
//            customDialog.window?.attributes = lp
//        } catch (ex: Exception) {
//            ex.printStackTrace()
//        }
//        return customDialog
//    }
//
//
//    fun showPopupDialogRecording(
//            heading: String?,
//            buttonYes: String
//    ): AlertDialog? {
//
//        val builder = AlertDialog.Builder(
//                ContextThemeWrapper(
//                        mActivity,
//                        android.R.style.ThemeOverlay_Material_Dialog
//                )
//        )
//
//        var customDialog = builder.create()
//        customDialog.setCanceledOnTouchOutside(false)
//        customDialog.setCancelable(false)
//        val inflater = mActivity.layoutInflater
//        val dialogView = inflater.inflate(R.layout.rg_recordingpopup_dialog, null)
//        customDialog.setView(dialogView)
//
//        customDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        customDialog.show()
//
//        dialogView.tv_heading.text = heading
//        dialogView.btn_ok.text = buttonYes
//
//        dialogView.btn_ok.setOnClickListener {
//            customDialog.dismiss()
//        }
//
//        dialogView.iv_cancel.setOnClickListener {
//            customDialog.dismiss()
//        }
//
//        try {
//            val lp = WindowManager.LayoutParams()
//            lp.copyFrom(customDialog.window?.attributes)
//            lp.width = WindowManager.LayoutParams.MATCH_PARENT
//            lp.height = WindowManager.LayoutParams.WRAP_CONTENT
//            customDialog.show()
//            customDialog.window?.attributes = lp
//        } catch (ex: Exception) {
//            ex.printStackTrace()
//        }
//        return customDialog
//    }
// }
