package src.wrapperutil.viewmodel

import android.graphics.drawable.Drawable
import android.view.View
import androidx.lifecycle.MutableLiveData

abstract class ToolBarPVM : ParentVM() {

    var mToolBarTitle: String? = ""
    var mRightButtonText: String? = ""
    var mNextButton: String? = ""
    var mShareToolbarText: String? = ""
    var totalConnection: Int? = 0
    var lSearchBoxText: String? = ""
    var mDeepLinkData: String? = ""
    var lDeleteButtonText: String? = ""
    var lDraftButtonText: String? = ""
    var lClearAllText: String? = ""
    var mDiscardList: ArrayList<Any>? = null
    var deleteButtonVisiblity: MutableLiveData<Boolean>? = MutableLiveData()
    var isToEnableSearchByDefault: Boolean? = true
    var genericDrawable: Drawable? = null
    var moreIconDrawable: Drawable? = null
    var mShareButtonVisiiblity: Boolean = false
    var mFollowButtonText: String? = ""
    var noSpeakerText: String = ""

    var mShareChannelIcon: Boolean = false
    var mShareEventIcon: Boolean = false
    var mCalendarEventIcon: Boolean = false
    var mMoreChannelIcon: Boolean = false
    var mFilterChannelIcon: Boolean = false
    var mClearAllTextlVisible: Boolean = false
    var mDropDownArrowVisible: Boolean = false

    var showFilterChannelCount = false

    var textWithImageText: String? = ""

    init {
        TAG = ToolBarPVM::class.java.simpleName
        lDeleteButtonText = "Delete"
    }

    open fun typeOnSearch(text: CharSequence) {
    }

    open fun navigateToProfile() {
    }

    open fun searchIconClick() {
    }

    open fun deleteText() {
    }

    open fun rightButtonClick() {
    }

    open fun searchBarClick() {
    }

    open fun nextButtonToolBarClick() {
    }

    open fun draftButtonClick() {
    }

    open fun clearAllClick() {
    }

    open fun filterImgClick() {
    }

    open fun genericIconClick(view: View?) {
    }

    open fun bookmarkButtonClick(view: View?) {
    }

    open fun shareButtonClickJournal(view: View?) {
    }

    open fun moreIconClick(view: View?) {
    }

    open fun shareButtonToolBarClick() {
    }

    open fun imageButtonLeftToSearchClick() {
    }

    open fun searchImgClick() {}

    open fun shareIconChannelToolbarClick() {
    }

    open fun followButtonToolbarClick() {
    }

    open fun shareIconEventToolbarClick() {
    }

    open fun calendarIconEventDetailToolbarClick() {
    }

    open fun moreIconChannelToolbarClick(view: View) {
    }

    open fun filterIconChannelToolbarClick() {
    }

    open fun dropDownLayoutClick() {
    }

    open fun eventVaultClick() {
    }

    open fun eventCalendarClick() {
    }

    open fun eventSearchClick() {
    }

    open fun chatClick() {
    }

    open fun toolBarInfoClick() {
    }

    open fun eventfilterClick() {
    }

    open fun notificationClick() {
    }

    open fun homeSearchClick() {
    }

    open fun genericFirstButtonClick() {
    }

    open fun genericSecondButtonClick() {
    }

    open fun genericMiddleButtonClick() {
    }

    open fun genericImageWithTextButtonClick() {
    }

    open fun comparePreviousAndSelectedList(): Boolean {
        return false
    }

    open fun skipButtonClick() {
    }

    open fun eventBookmarkClick() {
    }

    open fun eventQuestionMarkClick() {
    }
}
