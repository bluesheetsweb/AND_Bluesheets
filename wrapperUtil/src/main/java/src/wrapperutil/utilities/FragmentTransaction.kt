package src.wrapperutil.utilities

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import src.wrapperutil.model.ModelFlow
import src.wrapperutil.uicomponent.DCFragment
import java.util.*
import kotlin.collections.LinkedHashMap
import kotlin.collections.set

object FragmentTransaction {
    private val listData = LinkedHashMap<Int, ModelFlow>()
    private val listFragments = HashMap<Int, List<DCFragment>>()
    private val TAG = FragmentTransaction::class.java.simpleName
    private var currentAddedFragment: String? = ""
    private var mCurrentActivityType: Int = -1
    private var mCurrentFragment: Int = -1

    fun initParentFrameForActivity(type: Int, data: ModelFlow) {
        listData[type] = data
        mCurrentActivityType = type
    }

    fun initParentFrameForFragment(type: Int, data: ModelFlow) {
        listData[type] = data
        mCurrentFragment = type
    }

    fun isParentInitalize(type: Int): Boolean {
        if (listData[type] == null) {
            return false
        } else {
            return true
        }
    }

    fun getNumberOfSameActivityInBackStatck(type: Int): Int {
        var count = 0
        for (entry in listData.entries) {
            val key = entry.key
            val value = entry.value
            Log.e(TAG, "getNumberOfSameActivityInBackStatck" + value)

            if (key == type) {
                count = count.inc()
            }
        }

        Log.e(TAG, "getNumberOfSameActivityInBackStatck count" + count)
        return count
    }

    fun getCurrentActivityType(): Int {
        Log.e(TAG, "getCurrentActivityType" + mCurrentActivityType)
        return mCurrentActivityType
    }

    fun getCurrentFragmentType(): Int {
        return mCurrentFragment
    }

    fun removeParentFrame(type: Int) {
        Log.e(TAG, "removeParentFrame type" + type)
        if (listData[type] != null) {
            listData.remove(type)

            var lastKey: Int = 0

            for (entry in listData.entries) {
                lastKey = entry.key
            }

            Log.e(TAG, "lastKey" + lastKey)

            if (lastKey != 0)
                mCurrentActivityType = lastKey
        }
    }

    fun add(
        type: Int,
        fragment: androidx.fragment.app.Fragment,
        isToAddBack: Boolean = false,
        bundle: Bundle? = null,
        isCommitAllowingStateLoss: Boolean = false
    ) {
        try {
            Log.e(TAG, "add fragment called")
            if (listData[type] == null) {
                Log.e(TAG, "No Parent Attached to DCFlowOrganizer")
                return
            }
            val fm = listData[type]!!.fragmentManager
            val ft = fm.beginTransaction()
// TODO Animation
//            ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left)
            fragment.arguments = bundle
            ft.add(listData[type]!!.frameLayout.id, fragment)
            if (isToAddBack) {
                Log.e(TAG, "add ${fragment.javaClass.simpleName}")
                ft.addToBackStack(fragment.javaClass.simpleName)
            } else
                clearBackStack(type)

            if (isCommitAllowingStateLoss) {
                ft.commitAllowingStateLoss()
            } else {
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        ft.commitNow()
                    } else {
                        ft.commit()
                        fm.executePendingTransactions()
                    }
                } catch (e: Throwable) {
                    e.printStackTrace()
                    try {
                        ft.commitAllowingStateLoss()
                    } catch (e: Throwable) {
                        e.printStackTrace()
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "add ex" + e.message)
        }
    }

/*
    fun addWithListner(type: Int,context:Activity, fragment: Fragment, isToAddBack: Boolean = false, bundle: Bundle? = null) {
        try {
            if (listData[type] == null) {
                Log.e(TAG, "No Parent Attached to DCFlowOrganizer")
                return
            }
            val fm = listData[type]!!.fragmentManager
            fm.addOnBackStackChangedListener(getListener(type))
            val ft = fm.beginTransaction()
            fragment.arguments = bundle
            ft.add(listData[type]!!.frameLayout.id, fragment)
            if (isToAddBack) {
                Log.e(TAG, "add ${fragment.javaClass.simpleName}")
                ft.addToBackStack(fragment.javaClass.simpleName)
            } else
                clearBackStack(type)
            ft.commit()
        } catch (e: Exception) {
            Log.e(TAG, "add ex" + e.message)
        }
    }
*/

    fun addWithoutBundleArgs(
        type: Int,
        fragment: androidx.fragment.app.Fragment,
        isToAddBack: Boolean = false
    ) {
        try {
            if (listData[type] == null) {
                Log.e(TAG, "No Parent Attached to DCFlowOrganizer")
                return
            }
            val fm = listData[type]!!.fragmentManager
            val ft = fm.beginTransaction()
            ft.add(listData[type]!!.frameLayout.id, fragment)
            if (isToAddBack) {
                Log.e(TAG, "addWithoutBundleArgs ${fragment.javaClass.simpleName}")
                ft.addToBackStack(fragment.javaClass.simpleName)
            } else
                clearBackStack(type)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                ft.commitNow()
            } else {
                ft.commit()
                fm.executePendingTransactions()
            }
        } catch (e: Exception) {
        }
    }

//    private fun addFragmentToList(type: Int, fragment: Fragment) {
//        var fragmentsList = listFragments.get(type)
//
//        if (fragmentsList == null)
//            fragmentsList = ArrayList()
//
//        fragmentsList!!.add(fragment)
//
//        listFragments.put(type, fragmentsList)
//    }

    fun replace(
        type: Int,
        fragment: androidx.fragment.app.Fragment,
        isToAddBack: Boolean = false,
        bundle: Bundle? = null,
        isCommitAllowingStateLoss: Boolean = false
    ) {
        try {
            Log.e(TAG, "replace called")
            if (listData[type] == null) {
                Log.e(TAG, "No Parent Attached to DCFlowOrganizer")
                return
            }
            val fm = listData[type]!!.fragmentManager
            val ft = fm.beginTransaction()
            fragment.arguments = bundle
            ft.replace(listData[type]!!.frameLayout.id, fragment)
            if (isToAddBack) {
                Log.e(TAG, "replace ${fragment.javaClass.simpleName}")
                ft.addToBackStack(fragment.javaClass.simpleName)
            } else
                clearBackStack(type)

            if (isCommitAllowingStateLoss) {
                ft.commitAllowingStateLoss()
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    ft.commitNow()
                } else {
                    ft.commit()
                    fm.executePendingTransactions()
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "ex", e)
        }
    }

    fun replaceWithoutBundleArgs(
        type: Int,
        fragment: androidx.fragment.app.Fragment,
        isToAddBack: Boolean = false
    ) {
        try {
            Log.e(TAG, "replaceWithoutBundleArgs")
            if (listData[type] == null) {
                Log.e(TAG, "No Parent Attached to DCFlowOrganizer")
                return
            }
            val fm = listData[type]!!.fragmentManager
            val ft = fm.beginTransaction()
            ft.replace(listData[type]!!.frameLayout.id, fragment)
            if (isToAddBack) {
                Log.e(TAG, "replaceWithoutBundleArgs ${fragment.javaClass.simpleName}")
                ft.addToBackStack(fragment.javaClass.simpleName)
            } else
                clearBackStack(type)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                ft.commitNow()
            } else {
                ft.commit()
                fm.executePendingTransactions()
            }
        } catch (e: Exception) {
            Log.e(TAG, "ex", e)
        }
    }

    fun getFragmentManager(type: Int): androidx.fragment.app.FragmentManager? {
        try {
            return listData[type]!!.fragmentManager
        } catch (ex: Exception) {
            return null
        }
    }

    fun hasNoMoreBacks(type: Int): Boolean {
        val fm = listData[type]?.fragmentManager
        val count = fm?.backStackEntryCount
        return when (count?.compareTo(1)) {
            -1 -> {
                true
            }
            else -> false
        }
    }

    fun clearBackStack(type: Int) {
        val fm = listData[type]!!.fragmentManager
        for (i in 0 until fm.backStackEntryCount) {
            fm.popBackStack()
        }
    }

    fun popUpBackToMain(type: Int) {
        val fm = listData[type]!!.fragmentManager
        val size = fm.backStackEntryCount
        for (i in 0 until size) {
            fm.popBackStack()
        }
    }

    fun popUpBackTo(type: Int, skipNoOfFragment: Int=1) {
        val fm = listData[type]?.fragmentManager
        if (fm == null)
            return
        var size = fm?.backStackEntryCount
        if (skipNoOfFragment > size!!)
            return
        else
            size = skipNoOfFragment
        for (i in 0 until size) {
            fm?.popBackStackImmediate()
        }
    }

    fun removeFragment(type: Int, fragment: Fragment) {
        try {
            Log.e(TAG, "removeFragment called")
            val fm = listData[type]?.fragmentManager
            if (fm == null)
                return

            fm.beginTransaction().remove(fragment).commitAllowingStateLoss()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    fun getCurrentFragment(type: Int?): androidx.fragment.app.Fragment? {

        var fragment: androidx.fragment.app.Fragment? = null

        Log.e(TAG, "listData[type]?.frameLayout" + listData[type]?.frameLayout)
        Log.e(TAG, "type" + type)
        if (listData[type]?.frameLayout != null)
            fragment =
                listData[type]?.fragmentManager?.findFragmentById(listData[type]?.frameLayout!!.id)

        // var fragment=fm.fragments[fm.fragments.size - 1]
        // return fragment
        return fragment
    }

    fun popUpBackTo(type: Int, fragment: androidx.fragment.app.Fragment) {
        val fm = listData[type]!!.fragmentManager
        fm.popBackStack(
            fragment.javaClass.name,
            androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
    }

    fun isCurrentFragment(type: Int?, fragment: androidx.fragment.app.Fragment?): Boolean {

        var currentFragment: androidx.fragment.app.Fragment? = null

        if (listData[type]?.frameLayout != null)
            currentFragment =
                listData[type]?.fragmentManager?.findFragmentById(listData[type]?.frameLayout!!.id)

        // var fragment=fm.fragments[fm.fragments.size - 1]
        // return fragment
        var areEqual = fragment?.javaClass.toString().equals(currentFragment?.javaClass.toString())
        Log.e(TAG, "fragment?.equals(currentFragment)!! $areEqual")
        return areEqual
    }

    private fun getListener(type: Int?): androidx.fragment.app.FragmentManager.OnBackStackChangedListener {

        Log.e(TAG, "getListner called" + type)
        return object : androidx.fragment.app.FragmentManager.OnBackStackChangedListener {
            override fun onBackStackChanged() {
                val manager = listData[type]!!.fragmentManager
                Log.e(TAG, "onBackStackChanged called")
                if (manager != null) {
                    val currFrag =
                        manager!!.findFragmentById(listData[type]!!.frameLayout.id) as DCFragment

                    currFrag!!.onResume()
                }
            }
        }
    }
}
