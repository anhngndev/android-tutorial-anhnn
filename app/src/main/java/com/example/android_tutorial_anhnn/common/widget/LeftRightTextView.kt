package com.example.android_tutorial_anhnn.common.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.media.Image
import android.util.AttributeSet
import android.util.Log
import android.view.DragEvent
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.example.android_tutorial_anhnn.R
import com.example.android_tutorial_anhnn.databinding.LeftRightTextviewLayoutBinding

class LeftRightTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {
    private val TAG = "LeftRightTextView"

    private lateinit var view: View
    private lateinit var ivIconLeft: ImageView
    private lateinit var tvTitle: TextView
    private lateinit var ivIconRight: ImageView

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        Log.d(TAG, "onAttachedToWindow: ")
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        Log.d(TAG, "onMeasure: ")
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        Log.d(TAG, "onLayout: ")
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        Log.d(TAG, "draw: ")
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        Log.d(TAG, "onDraw: ")
    }
    init {
        initView()
        setupAttributes(attrs, 0)
    }

    fun setIconLeft(drawable: Drawable?) {
        ivIconLeft.setImageDrawable(drawable)
        requestLayout()
    }

    fun setTitle(title: String) {
        tvTitle.text = title
        requestLayout()
    }

    fun setIconRight(drawable: Drawable) {
        ivIconRight.setImageDrawable(drawable)
    }

    fun setIconRightOnClick(onClick : (v: View) -> Unit) {
        ivIconRight.setOnClickListener {
            onClick.invoke(it)
        }
    }

    private fun initView() {
        view = LayoutInflater.from(context).inflate(R.layout.left_right_textview_layout, this, true)
        ivIconLeft = findViewById(R.id.ivLeftRightTextViewIconLeft)
        tvTitle = findViewById(R.id.tvLeftRightTextViewTitle)
        ivIconRight = findViewById(R.id.ivLeftRightTextViewIconRight)

    }

    private fun setupAttributes(attrs: AttributeSet?, defStyle: Int) {
        val ta = context.theme.obtainStyledAttributes(attrs, R.styleable.LeftRightTextView, 0, 0)

        // set iconLeft
        val iconLeft = ta.getResourceId(R.styleable.LeftRightTextView_lrtv_icon_left, 0)
        ivIconLeft.setImageResource(iconLeft)

        // set title
        val titleContent = ta.getString(R.styleable.LeftRightTextView_lrtv_title_content)
        tvTitle.text = titleContent

        // set color
        val titleColor = ta.getColor(R.styleable.LeftRightTextView_lrtv_title_color, 0)
        tvTitle.setTextColor(titleColor)


        ta.recycle()
    }


}