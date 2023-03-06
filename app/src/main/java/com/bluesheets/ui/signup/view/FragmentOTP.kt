package com.bluesheets.ui.signup.view

import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bluesheets.databinding.FragmentOtpBinding
import com.bluesheets.databinding.FragmentSignInBinding
import com.bluesheets.ui.signup.viewmodel.OtpViewModel
import com.bluesheets.ui.signup.viewmodel.SignInViewModel
import com.bluesheets.utils.AppUtils
import src.wrapperutil.utilities.*

class FragmentOTP : Fragment() {

    private var binding: FragmentOtpBinding? = null
    private lateinit var viewModel: OtpViewModel
    var otp1: String? = ""
    var otp2: String? = ""
    var otp3: String? = ""
    var otp4: String? = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOtpBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(OtpViewModel::class.java)
        binding?.viewModel = viewModel
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding?.enterOtp1?.setBackgroundColor(Color.parseColor(ColorPicker.SEPERATOR_COLOR))
        binding?.enterOtp2?.setBackgroundColor(Color.parseColor(ColorPicker.SEPERATOR_COLOR))
        binding?.enterOtp3?.setBackgroundColor(Color.parseColor(ColorPicker.SEPERATOR_COLOR))
        binding?.enterOtp4?.setBackgroundColor(Color.parseColor(ColorPicker.SEPERATOR_COLOR))

        binding?.enterOtp1?.addTextChangedListener(object : GenericTextWatcher(
            binding!!.enterOtp1,
            binding!!.enterOtp2,
            binding!!.enterOtp3,
            binding!!.enterOtp1,
            binding!!.enterOtp2
        ) {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                otp1 = s.toString()
                enableDisableButton()
            }
        })
        binding?.enterOtp2?.addTextChangedListener(object : GenericTextWatcher(
            binding!!.enterOtp1,
            binding!!.enterOtp2,
            binding!!.enterOtp3,
            binding!!.enterOtp2,
            binding!!.enterOtp3
        ) {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                otp2 = s.toString()
                enableDisableButton()
            }
        })
        binding?.enterOtp3?.addTextChangedListener(object : GenericTextWatcher(
            binding!!.enterOtp1,
            binding!!.enterOtp2,
            binding!!.enterOtp3,
            binding!!.enterOtp3,
            binding!!.enterOtp4
        ) {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                otp3 = s.toString()
                enableDisableButton()
            }
        })
        binding?.enterOtp4?.addTextChangedListener(object : GenericTextWatcher(
            binding!!.enterOtp1, binding!!.enterOtp2, binding!!.enterOtp3, binding!!.enterOtp4, null
        ) {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                otp4 = s.toString()
                enableDisableButton()
            }
        })

        // GenericKeyEvent here works for deleting the element and to switch back to previous EditText
        // first parameter is the current EditText and second parameter is previous EditText
        binding?.enterOtp1?.setOnKeyListener(
            GenericKeyEvent(
                binding!!.enterOtp1, binding!!.enterOtp1, null
            )
        )
        binding?.enterOtp2?.setOnKeyListener(
            GenericKeyEvent(
                binding!!.enterOtp1, binding!!.enterOtp2, binding!!.enterOtp1
            )
        )
        binding?.enterOtp3?.setOnKeyListener(
            GenericKeyEvent(
                binding!!.enterOtp1, binding!!.enterOtp3, binding!!.enterOtp2
            )
        )
        binding?.enterOtp4?.setOnKeyListener(
            GenericKeyEvent(
                binding!!.enterOtp1, binding!!.enterOtp4, binding!!.enterOtp3
            )
        )
    }

    fun enableDisableButton() {
        if (isOtpFilled()) {
            binding?.buttonVerify?.updateMode(WrapperEnumAnnotation(WrapperConstant.BUTTON_MODE_PRIMARY))
        } else {
            binding?.buttonVerify?.updateMode(WrapperEnumAnnotation(WrapperConstant.BUTTON_MODE_DISABLED))
        }
    }

    private fun isOtpFilled() =
        (AppUtils.isValidString(otp1) && AppUtils.isValidString(otp2) && AppUtils.isValidString(otp3) && AppUtils.isValidString(
            otp4
        ))

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}