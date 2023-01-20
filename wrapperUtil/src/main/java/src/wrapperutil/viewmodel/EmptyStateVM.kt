package src.wrapperutil.viewmodel

import androidx.lifecycle.MutableLiveData
import src.wrapperutil.utilities.WrapperEnumAnnotation

class EmptyStateVM : ParentVM() {
    override fun getState(): MutableLiveData<WrapperEnumAnnotation> {
        return mProgressState
    }

    override fun onBackPressed() {
    }
}
