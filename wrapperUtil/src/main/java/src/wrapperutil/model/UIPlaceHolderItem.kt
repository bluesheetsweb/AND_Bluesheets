package src.wrapperutil.model

class UIPlaceHolderItem {

    var title: String? = ""
    var msg: String? = ""
    var image: Int? = 0
    var okButtonTitle: String? = ""
    var cancelbuttonTitle: String? = ""
    var target: Any? = null
    var progressBarMainVisible: Boolean? = false
    var progrssBarTopVisible: Boolean = false
    var isToast: Boolean? = false
    var progressBarBottom: Boolean? = false

    fun setData(
        title: String?,
        msg: String?,
        okBtnTitle: String?,
        cancelBtnTitle: String?,
        img: Int?,
        progressBarMainVisible: Boolean?,
        isToast: Boolean?,
        progressBarBottom: Boolean? = false,
        progrssBarTopVisible: Boolean = false
    ): UIPlaceHolderItem {

        // var model = DCUIPlaceHolderItem()
        this.title = title
        this.msg = msg
        this.okButtonTitle = okBtnTitle
        this.cancelbuttonTitle = cancelBtnTitle
        this.image = img
        this.progressBarMainVisible = progressBarMainVisible
        this.isToast = isToast
        this.progressBarBottom = progressBarBottom
        this.progrssBarTopVisible = progrssBarTopVisible
        return this
    }
}
