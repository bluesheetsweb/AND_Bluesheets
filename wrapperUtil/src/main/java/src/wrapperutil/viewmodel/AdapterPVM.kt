package src.wrapperutil.viewmodel

import androidx.databinding.BaseObservable
import androidx.lifecycle.MutableLiveData
import src.wrapperutil.utilities.WrapperEnumAnnotation

abstract class AdapterPVM : BaseObservable() {

    var mProgressState = MutableLiveData<WrapperEnumAnnotation>()
    var TAG = AdapterPVM::class.java.simpleName
    abstract fun getState(): MutableLiveData<src.wrapperutil.utilities.WrapperEnumAnnotation>
}
