package ai.ftech.dev.base.common

import ai.ftech.dev.base.R
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.view.*
import android.view.animation.AnimationUtils
import android.view.animation.BounceInterpolator
import android.widget.RelativeLayout
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

abstract class BaseDialog<BD : ViewDataBinding> : DialogFragment(), BaseView<DialogConfig> {

    /**
     * Binding view
     */
    protected lateinit var binding: BD

    /**
     * Listener when dismiss dialog
     */
    private val dismissListener: DialogInterface.OnDismissListener? = null
    private var needDismissOnResume = false
    private val handlerClose: Handler? = null
    private val runnableClose = Runnable {
        try {
            dismiss()
        } catch (e: IllegalStateException) {
            needDismissOnResume = true
        }
    }

    init {

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            getConfig().getLayoutResource(),
            container,
            false
        )
        binding.lifecycleOwner = viewLifecycleOwner
        onInitBinding()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        onPrepareInitView()
        super.onViewCreated(view, savedInstanceState)
        initAnimation()
        val background: View = view.findViewById(getConfig().getBackgroundId())
        background.setOnClickListener {
            if (getConfig().screen().isDismissByTouchOutSide) {
                dismissDialog()
            }
        }
        onInitView()
        onViewClick()
    }

    override fun onResume() {
        super.onResume()
        if (needDismissOnResume) {
            needDismissOnResume = false
            dismiss()
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val layout = RelativeLayout(requireActivity())
        layout.apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }
        val dialog = object : Dialog(requireContext()) {
            override fun onBackPressed() {
                if (getConfig().screen().isDismissByOnBackPressed) {
                    dismissDialog()
                }
            }
        }
        dialog.apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(layout)
            val window = dialog.window
            window?.let { w ->
                w.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                val wlp = w.attributes
                if (getConfig().screen().isFullWidth) {
                    wlp.width = WindowManager.LayoutParams.MATCH_PARENT
                }
                if (getConfig().screen().isFullHeight) {
                    wlp.height = WindowManager.LayoutParams.MATCH_PARENT
                }
                if (getConfig().screen().mode == DialogScreen.DIALOG_MODE.BOTTOM) {
                    wlp.gravity = Gravity.BOTTOM
                }
            }
        }
        configDialog(dialog)
        return dialog
    }

    override fun onDismiss(dialog: DialogInterface) {
        handlerClose?.removeCallbacks(runnableClose)
        super.onDismiss(dialog)
    }

    override fun dismiss() {
        super.dismiss()
        dismissListener?.onDismiss(dialog)
    }

    /**
     * Root layout of dialog
     */
    open fun getRootViewGroup(): ViewGroup? {
        return binding.root as? ViewGroup
    }

    /**
     * Show dialog
     *
     * @param fm should use childFragmentManager
     * @param tag should pass class simple name of dialog <xxx::class.java.simpleName>
     */
    fun showDialog(fm: FragmentManager, tag: String?) {
        if (!this.isAdded) {
            show(fm, tag)
        }
    }

    /**
     * Dismiss dialog
     *
     */
    fun dismissDialog() {
        if (this.isAdded) {
            dismiss()
        }
    }

    private fun configDialog(dialog: Dialog) {
        dialog.setCanceledOnTouchOutside(getConfig().screen().isDismissByTouchOutSide)
    }

    private fun animateDialog(viewGroup: ViewGroup) {
        when (getConfig().screen().mode) {
            DialogScreen.DIALOG_MODE.BOTTOM -> {
                viewGroup.startAnimation(
                    AnimationUtils.loadAnimation(
                        context,
                        R.anim.anim_slide_from_bottom
                    )
                )
            }
            else -> {
                val set = AnimatorSet()
                val animatorX = ObjectAnimator.ofFloat(viewGroup, ViewGroup.SCALE_X, 0.7f, 1f)
                val animatorY = ObjectAnimator.ofFloat(viewGroup, ViewGroup.SCALE_Y, 0.7f, 1f)
                set.playTogether(animatorX, animatorY)
                set.interpolator = BounceInterpolator()
                set.duration = 500
                set.start()
            }
        }
    }

    private fun initAnimation() {
        getRootViewGroup()?.let {
            animateDialog(it)
        }
    }
}
