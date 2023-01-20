package src.wrapperutil.rguicomponent.interfaces

interface RGGetOpenFragmentResponse {

    fun sendAcceptDeclineMeetingData(data: Any, pos: Int, type: String)

    fun sendCancelMeetingData(data: Any, pos: Int, type: String, productType: Int)
}
