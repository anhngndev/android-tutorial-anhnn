package ai.ftech.dev.base.common

import ai.ftech.dev.base.R
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.activity.OnBackPressedCallback
import androidx.annotation.AnimRes
import androidx.annotation.AnimatorRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

abstract class BaseFragment<DB : ViewDataBinding> : Fragment(), BaseView<FragmentConfig> {

    companion object {
        private val TAG = BaseFragment::class.java.simpleName
    }

    /**
     * Instance base activity
     */
    private val baseActivity by lazy {
        requireActivity() as BaseActivity<*>
    }

    /**
     * Binding view
     */
    protected lateinit var binding: DB

    /**
     * Inflate layout root
     */
    private lateinit var myInflater: LayoutInflater

    /**
     * Manager transition fragment by Fragment Manager
     */
    private lateinit var callback: OnBackPressedCallback

    init {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onPrepareInitView()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (!::myInflater.isInitialized) {
            myInflater = LayoutInflater.from(requireActivity())
        }
        binding =
            DataBindingUtil.inflate(myInflater, getConfig().getLayoutResource(), container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        onInitBinding()
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        if (getConfig().isHandleBackPressByFragment()) {
            setBackPressedDispatcher()
        }
        setHasOptionsMenu(getConfig().isAttachMenuToFragment())
        onInitView()
        onViewClick()
        onObserverViewModel()
    }

    override fun onPrepareInitView() {
        getConfig().setupStatusBar().let {
            setStatusColor(it.color, it.isDarkText)
        }
    }

    /**
     * Xử lý nút back thiết bị
     */
    open fun onBackPressed(tag: String? = null) {
//        backScreenByNavigationComponent()
        backScreenByFragmentManager(tag)
    }

    /**
     * Điều hướng đến màn hình mới
     *
     * @param actionId R.id.xxx - action navigate in navigation graph
     * @param args pass bundle with navigation component
     */
    fun navigateTo(
        actionId: Int,
        args: Bundle? = null
    ) {
        try {
            with(findNavController()) {
                currentDestination?.getAction(actionId)?.let {
                    if (currentDestination?.id != currentDestination?.getAction(actionId)?.destinationId) {
                        navigate(actionId, args)
                    }
                }
            }
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
            Log.e(TAG, "${e.message}")
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "${e.message}")
        }
    }

    /**
     * Back trở về 1 trong những màn hình trước
     *
     * @param actionId R.id.xxx - id của fragment muốn điều hướng
     * @param inclusive giữ lại backstack
     */
    fun popBackStack(actionId: Int, inclusive: Boolean = false) {
        try {
            findNavController().popBackStack(actionId, inclusive)
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
            Log.e(TAG, "${e.message}")
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "${e.message}")
        }
    }

    fun navigateTo(
        fragment: BaseFragment<*>,
        isReplace: Boolean = true,
        keepToBackStack: Boolean = true,
        @AnimatorRes @AnimRes enter: Int = R.anim.slide_enter_left_to_right,
        @AnimatorRes @AnimRes exist: Int = R.anim.slide_exit_right_to_left,
        @AnimatorRes @AnimRes popEnter: Int = R.anim.slide_pop_enter_right_to_left,
        @AnimatorRes @AnimRes popExit: Int = R.anim.slide_pop_exit_left_to_right
    ) {
        baseActivity.navigateTo(
            fragment,
            isReplace,
            keepToBackStack,
            enter,
            exist,
            popEnter,
            popExit
        )
    }

    private fun backScreenByFragmentManager(tag: String? = null) {
        baseActivity.backScreen(tag)
    }

    private fun backScreenByNavigationComponent() {
        try {
            findNavController().navigateUp()
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
            Log.e(TAG, "${e.message}")
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "${e.message}")
        }
    }

    private fun setBackPressedDispatcher() {
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onBackPressed()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    fun setStatusColor(color: Int = Color.BLACK, isDarkText: Boolean = true) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val window = activity?.window
            window?.let { w ->
                w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                var newUiVisibility = w.decorView.systemUiVisibility
                newUiVisibility = if (isDarkText) {
                    newUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                } else {
                    newUiVisibility and (View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR).inv()
                }
                w.decorView.systemUiVisibility = newUiVisibility
                w.statusBarColor = color
            }
        }
    }

    fun setFullScreenWithStatusBar(isFull: Boolean) {
        activity?.window?.apply {
            if (isFull) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    decorView.systemUiVisibility =
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                }

                if (Build.VERSION.SDK_INT in Build.VERSION_CODES.KITKAT..Build.VERSION_CODES.LOLLIPOP) {
                    addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                    statusBarColor = Color.TRANSPARENT
                }

            } else {
                addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
            }
        }
    }

    fun setFullScreen(isFull: Boolean) {
        activity?.window?.apply {
            if (isFull) {
                setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN
                )
            } else {
                clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
            }
        }
    }
}
