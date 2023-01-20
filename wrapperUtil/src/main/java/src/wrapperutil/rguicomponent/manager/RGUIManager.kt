package src.wrapperutil.rguicomponent.manager

import android.content.Context

interface OnRGUINavigationListener {

    fun navigateToDeclineMeeting(context: Context, id: String)
}

object RGUIManager {
    private val TAG = RGUIManager::class.java.simpleName

    var onRGNavigationListener: OnRGUINavigationListener? = null

    fun initRGUI(onRGNavigationListener: OnRGUINavigationListener) {
        this.onRGNavigationListener = onRGNavigationListener
    }

//    fun getMRDetailFragment(meetingId: String?,isForViewPager:Boolean=false): Fragment {
//        var model = DCMRConnectModel()
//        model.id = meetingId!!
//        val dcMrMeetingDetailsFragment = DCMRMeetingDetailsFragment()
//        dcMrMeetingDetailsFragment.initData(model,meetingId,isForViewPager)
//        return dcMrMeetingDetailsFragment
//    }
//
//
}
