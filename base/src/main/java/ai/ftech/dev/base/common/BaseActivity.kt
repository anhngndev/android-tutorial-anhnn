package ai.ftech.dev.base.common

import ai.ftech.dev.base.R
import ai.ftech.dev.base.common.transition.ScreenTransitionManageImp
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.InflateException
import android.view.View
import android.view.ViewTreeObserver
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.AnimRes
import androidx.annotation.AnimatorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentManager

abstract class BaseActivity<DB : ViewDataBinding> : AppCompatActivity(), BaseView<ActivityConfig> {

    companion object {
        private val TAG = BaseActivity::class.java.simpleName
    }

    /**
     * Binding view
     */
    protected lateinit var binding: DB

    /**
     * Quản lý điều hướng màn hình
     */
    private lateinit var screenManager: ScreenTransitionManageImp

    /**
     * Cấp quyền
     */
    private var permissionListener: PermissionListener? = null
    private val launcher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { map ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                map.forEach { (k, v) ->
                    if (v) {
                        permissionListener?.onAllow()
                    } else {
                        if (!shouldShowRequestPermissionRationale(k)) {
                            permissionListener?.onNeverAskAgain()
                            return@registerForActivityResult
                        }
                    }
                    permissionListener?.onDenied()
                }
            }
        }

    /**
     * Safe action
     */
    private var safeAction = false
    private var waitingAction: (() -> Unit)? = null

    /**
     * Bàn phím
     */
    private var keyboardGlobalListener: ViewTreeObserver.OnGlobalLayoutListener? = null

    init {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        onPrepareInitView()
        if (getConfig().isOnlyPortraitScreen()) {
            setPortraitScreen()
        }
        super.onCreate(savedInstanceState)
        try {
            if (getConfig().isFixSingleTask()) {
                if (!isTaskRoot) {
                    finish()
                    return
                }
            }
            binding = DataBindingUtil.setContentView(this, getConfig().getLayoutResource())
            binding.lifecycleOwner = this
            screenManager = ScreenTransitionManageImp(supportFragmentManager)
            onInitBinding()
            onInitView()
            onViewClick()
            onObserverViewModel()
        } catch (e: InflateException) {
            e.printStackTrace()
            Log.e(TAG, "${e.message}")
        } catch (e: Resources.NotFoundException) {
            e.printStackTrace()
            Log.e(TAG, "${e.message}")
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "${e.message}")
        }
    }

    override fun onResume() {
        super.onResume()
        safeAction = true
        if (waitingAction != null) {
            waitingAction?.invoke()
            waitingAction = null
        }
    }

    override fun onPause() {
        safeAction = false
        super.onPause()
    }

    /**
     * Điều hướng đến màn hình
     *
     * @param fragment instance of fragment
     * @param isReplace replace or add fragment
     * @param keepToBackStack add to backstack
     */
    open fun navigateTo(
        fragment: BaseFragment<*>,
        isReplace: Boolean = true,
        keepToBackStack: Boolean = true,
        @AnimatorRes @AnimRes enter: Int = R.anim.slide_enter_left_to_right,
        @AnimatorRes @AnimRes exist: Int = R.anim.slide_exit_right_to_left,
        @AnimatorRes @AnimRes popEnter: Int = R.anim.slide_pop_enter_right_to_left,
        @AnimatorRes @AnimRes popExit: Int = R.anim.slide_pop_exit_left_to_right
    ) {
        if (getConfig().getContainerId() == LAYOUT_INVALID) {
            throw IllegalArgumentException("Cần phải gán container id để replace fragment")
        }
        screenManager.transitionTo(
            fragment,
            getConfig().getContainerId(),
            isReplace,
            keepToBackStack,
            enter,
            exist,
            popEnter,
            popExit
        )
    }

    /**
     * Quay trở lại màn hình trước đó
     * Hiện tại chỗ này chưa tối ưu , sẽ cập nhật để tránh xảy ra bug trong thời gian tới
     *
     * @param tag tag của màn hình trong backstack
     */
    fun backScreen(tag: String?) {
        if (tag == null) {
            supportFragmentManager.popBackStack()
        } else {
            supportFragmentManager.popBackStack(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
    }

    /**
     * Đợi 1 hành động khi đang xử lý ở background
     *
     * @param action cho phép hành động xảy ra
     */
    fun doSafeAction(action: () -> Unit) {
        if (safeAction) {
            action.invoke()
        } else {
            waitingAction = action
        }
    }

    /**
     * Xử lý khi cần cấp quyền
     *
     * @param permissions danh sách các quyền
     * @param listener callback
     */
    fun doRequestPermission(
        permissions: Array<String>,
        listener: PermissionListener
    ) {
        this.permissionListener = listener
        if (checkPermission(permissions)) {
            this.permissionListener?.onAllow()
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                launcher.launch(permissions)
            }
        }
    }

    /**
     * require softInputMode != SOFT_INPUT_ADJUST_NOTHING
     */
    fun setKeyboardVisibilityListener(parent: View, listener: (Boolean) -> Unit) {
        if (keyboardGlobalListener != null) {
            removeKeyboardVisibilityListener(parent)
        }
        keyboardGlobalListener = object : ViewTreeObserver.OnGlobalLayoutListener {
            private var wasShown: Boolean? = null

            override fun onGlobalLayout() {
                val defaultKeyboardHeightDP = 148
                val rect = Rect()
                val estimatedKeyboardHeight = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    defaultKeyboardHeightDP.toFloat(),
                    parent.resources.displayMetrics
                ).toInt()
                parent.getWindowVisibleDisplayFrame(rect)
                val heightDiff = parent.rootView.height - (rect.bottom - rect.top)
                val isShown = heightDiff >= estimatedKeyboardHeight
                if (wasShown == isShown) {
                    return
                } else {
                    wasShown = isShown
                    listener.invoke(isShown)
                }
            }
        }
        parent.viewTreeObserver.addOnGlobalLayoutListener(keyboardGlobalListener)
    }

    fun removeKeyboardVisibilityListener(parent: View) {
        if (keyboardGlobalListener != null) {
            parent.viewTreeObserver.removeOnGlobalLayoutListener(keyboardGlobalListener)
            keyboardGlobalListener = null
        }
    }

    /**
     * Kiểm tra cấp quyền
     *
     * @param permissions danh sách quyền cần cấp
     * @return trả về true nếu tất cả quyền đã được cấp , false nếu ít nhất 1 quyền chưa được cấp
     */
    private fun checkPermission(permissions: Array<String>): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            permissions.forEach {
                if (checkSelfPermission(it) == PackageManager.PERMISSION_GRANTED) {
                    return true
                }
            }
        }
        return false
    }

    private fun setPortraitScreen() {
        try {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "${e.message}")
        }
    }

    interface PermissionListener {
        fun onAllow()
        fun onDenied()
        fun onNeverAskAgain()
    }
}
