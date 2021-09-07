package ai.ftech.dev.base.common.transition

import ai.ftech.dev.base.R
import androidx.annotation.AnimRes
import androidx.annotation.AnimatorRes
import androidx.fragment.app.Fragment

interface ScreenTransitionHelper {

    fun getScreenCount(): Int

    fun transitionTo(
        fragment: Fragment,
        containerId: Int,
        isReplace: Boolean = true,
        keepToBackStack: Boolean = true,
        @AnimatorRes @AnimRes enter: Int = R.anim.slide_enter_left_to_right,
        @AnimatorRes @AnimRes exist: Int = R.anim.slide_exit_right_to_left,
        @AnimatorRes @AnimRes popEnter: Int = R.anim.slide_pop_enter_right_to_left,
        @AnimatorRes @AnimRes popExit: Int = R.anim.slide_pop_exit_left_to_right
    )

    fun backScreen()

    fun backToActivityRoot()
}
