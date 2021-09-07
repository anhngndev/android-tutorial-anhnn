package ai.ftech.dev.base.common.transition

import androidx.annotation.AnimRes
import androidx.annotation.AnimatorRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class ScreenTransitionManageImp(
    private val fragmentManager: FragmentManager
) : ScreenTransitionHelper {

    override fun getScreenCount() = fragmentManager.backStackEntryCount

    override fun transitionTo(
        fragment: Fragment,
        containerId: Int,
        isReplace: Boolean,
        keepToBackStack: Boolean,
        @AnimatorRes @AnimRes enter: Int,
        @AnimatorRes @AnimRes exist: Int,
        @AnimatorRes @AnimRes popEnter: Int,
        @AnimatorRes @AnimRes popExit: Int
    ) {
        try {
            val tag = fragment::class.java.simpleName
            /*bundle?.let {
                fragment.arguments = it
            }*/
            fragmentManager.beginTransaction().apply {
                setCustomAnimations(enter, exist, popEnter, popExit)
                if (isReplace) {
                    replace(containerId, fragment, tag)
                } else {
                    add(containerId, fragment, tag)
                }
                if (keepToBackStack) {
                    addToBackStack(tag)
                }
                commit()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun backScreen() {
        fragmentManager.popBackStack()
    }

    override fun backToActivityRoot() {
        for (i in 0..fragmentManager.backStackEntryCount) {
            fragmentManager.popBackStack()
        }
    }

    fun getCurrentFragment(): Fragment? {
        val fragments = fragmentManager.fragments
        return fragments.lastOrNull()
    }
}
