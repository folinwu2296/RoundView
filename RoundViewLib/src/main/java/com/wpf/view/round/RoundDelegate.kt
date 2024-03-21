package com.wpf.view.round

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import android.graphics.drawable.StateListDrawable
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

open class RoundDelegate(private val view: View, private val context: Context, attrs:AttributeSet?) {
    
    private val mGdBackground:GradientDrawable = GradientDrawable()
    
    private val mGdBackgroundPress:GradientDrawable = GradientDrawable()
    
    private val mDefaultColor:Int = -1
    /**
     * 背景颜色
     */
    @ColorInt
    private var mBackgroundColor:Int =  mDefaultColor
    /**
     * 点击时的背景颜色
     */
    @ColorInt
    private var mBackgroundPressColor:Int = mDefaultColor
    /**
     * 圆角
     */
    private var mCornerRadius:Int = 0
    /**
     * 圆角-
     */
    private var mCornerRadiusTL:Int = 0
    /**
     * 圆角
     */
    private var mCornerRadiusTR:Int = 0
    /**
     * 圆角
     */
    private var mCornerRadiusBL:Int = 0
    /**
     * 圆角
     */
    private var mCornerRadiusBR:Int = 0
    
    /**
     * 边框大小
     */
    private var mStrokeWidth:Int = 0
    
    /**
     * 边框颜色
     */
    @ColorInt
    private var mStrokeColor:Int =  mDefaultColor
    
    /**
     * 边框点击颜色
     */
    @ColorInt
    private var mStrokePressColor:Int = mDefaultColor
    
    /**
     * 文本点击颜色
     */
    @ColorInt
    private var mTextPressColor:Int = mDefaultColor
    
    
    private var mIsRadiusHalfHeight:Boolean = false
    
    private var mIsWidthHeightEqual:Boolean = false
    
    private var mIsRippleEnable:Boolean = false
    
    private val radiusArr = FloatArray(8)
    
    init {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.RoundTextView)
        mBackgroundColor = ta.getColor(R.styleable.RoundTextView_rv_backgroundColor,mBackgroundColor)
        mBackgroundPressColor = ta.getColor(R.styleable.RoundTextView_rv_backgroundPressColor,mBackgroundColor)
        
        mCornerRadius = ta.getDimensionPixelSize(R.styleable.RoundTextView_rv_cornerRadius,0)
        mCornerRadiusBL = ta.getDimensionPixelSize(R.styleable.RoundTextView_rv_cornerRadius_BL,0)
        mCornerRadiusBR = ta.getDimensionPixelSize(R.styleable.RoundTextView_rv_cornerRadius_BR,0)
        mCornerRadiusTL = ta.getDimensionPixelSize(R.styleable.RoundTextView_rv_cornerRadius_TL,0)
        mCornerRadiusTR = ta.getDimensionPixelSize(R.styleable.RoundTextView_rv_cornerRadius_TR,0)
        
        mStrokeWidth = ta.getDimensionPixelSize(R.styleable.RoundTextView_rv_stroke_width,0)
        mStrokeColor = ta.getColor(R.styleable.RoundTextView_rv_stroke_color,mStrokeColor)
        mStrokePressColor = ta.getColor(R.styleable.RoundTextView_rv_stroke_pressColor, mStrokePressColor)
        mTextPressColor = ta.getColor(R.styleable.RoundTextView_rv_text_pressColor, mTextPressColor)
        
        mIsRadiusHalfHeight = ta.getBoolean(R.styleable.RoundTextView_rv_isRadiusHalfHeight,false)
        mIsWidthHeightEqual = ta.getBoolean(R.styleable.RoundTextView_rv_isWidthHeightEqual,false)
        mIsRippleEnable = ta.getBoolean(R.styleable.RoundTextView_rv_isRippleEnable,true)
    
        ta.recycle()
    }
    
     fun setBackgroundColor(@ColorInt backgroundColor: Int) {
        mBackgroundColor = backgroundColor
        setBgSelector()
    }
    
    fun setBackgroundColorRes(@ColorRes backgroundColor: Int) {
        mBackgroundColor = ContextCompat.getColor(context,backgroundColor)
        setBgSelector()
    }
    
     fun setBackgroundPressColor(@ColorInt backgroundPressColor: Int) {
        mBackgroundPressColor = backgroundPressColor
        setBgSelector()
    }
    
     fun setCornerRadius(cornerRadius: Int) {
        mCornerRadius = dp2px(cornerRadius)
        setBgSelector()
    }
    
    open fun setStrokeWidth(strokeWidth: Int) {
        mStrokeWidth = dp2px(strokeWidth)
        setBgSelector()
    }
    
     fun setStrokeColor(@ColorInt strokeColor: Int) {
        mStrokeColor = strokeColor
        setBgSelector()
    }
    
     fun setStrokePressColor(@ColorInt strokePressColor: Int) {
        mStrokePressColor = strokePressColor
        setBgSelector()
    }
    
    open fun setTextPressColor(@ColorInt textPressColor: Int) {
        mTextPressColor = textPressColor
        setBgSelector()
    }
    
     fun setIsRadiusHalfHeight(isRadiusHalfHeight: Boolean) {
        mIsRadiusHalfHeight = isRadiusHalfHeight
        setBgSelector()
    }
    
     fun setIsWidthHeightEqual(isWidthHeightEqual: Boolean) {
        mIsWidthHeightEqual = isWidthHeightEqual
        setBgSelector()
    }
    
     fun setCornerRadiusTL(cornerRadius_TL: Int) {
        mCornerRadiusTL = cornerRadius_TL
        setBgSelector()
    }
    
     fun setCornerRadiusTR(cornerRadius_TR: Int) {
        mCornerRadiusTR = cornerRadius_TR
        setBgSelector()
    }
    
     fun setCornerRadiusBL(cornerRadius_BL: Int) {
        mCornerRadiusBL = cornerRadius_BL
        setBgSelector()
    }
    
     fun setCornerRadiusBR(cornerRadius_BR: Int) {
        mCornerRadiusBR = cornerRadius_BR
        setBgSelector()
    }
    
    fun isWidthHeightEqual():Boolean{
        return mIsWidthHeightEqual
    }
    
    fun isRadiusHalfHeight():Boolean {
        return mIsRadiusHalfHeight
    }
    
    private fun dp2px(dp: Int): Int {
        val scale = context.resources.displayMetrics.density
        return (dp * scale + 0.5f).toInt()
    }
    
    protected fun sp2px(sp: Int): Int {
        val scale = context.resources.displayMetrics.scaledDensity
        return (sp * scale + 0.5f).toInt()
    }
    
    fun setBgSelector(){
        val bg = StateListDrawable()
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP && mIsRippleEnable){
            setDrawable(mGdBackground,mBackgroundColor,mStrokeColor)
            val rippleDrawable = RippleDrawable(getPressedColorSelector(mBackgroundColor,mBackgroundPressColor),mGdBackground,null)
            view.background = rippleDrawable
        }else{
            setDrawable(mGdBackground,mBackgroundColor,mStrokeColor)
            bg.addState(intArrayOf(-android.R.attr.state_pressed), mGdBackground)
            if (mBackgroundPressColor != mDefaultColor || mStrokePressColor != mDefaultColor){
                setDrawable(mGdBackgroundPress,if (mBackgroundPressColor== mDefaultColor){
                        mBackgroundColor
                    }else{
                        mBackgroundPressColor
                    },if (mStrokePressColor== mDefaultColor){
                        mStrokeColor
                    }else{
                        mStrokePressColor
                    })
                bg.addState(intArrayOf(android.R.attr.state_pressed),mGdBackgroundPress)
            }
            view.background = bg
        }
        
        if (view is TextView){
            if (mTextPressColor != mDefaultColor){
                val textColors:ColorStateList = view.textColors
                val colorStateList = ColorStateList(
                    arrayOf(
                        intArrayOf(-android.R.attr.state_pressed),
                        intArrayOf(android.R.attr.state_pressed)),
                    intArrayOf(textColors.defaultColor, mTextPressColor))
                view.setTextColor(colorStateList)
            }
        }
    }
    
    private fun setDrawable(gradientDrawable: GradientDrawable,color:Int,strokeColor:Int){
        gradientDrawable.setColor(color)
        if (mCornerRadiusTL>0 || mCornerRadiusTR>0 || mCornerRadiusBL>0 || mCornerRadiusBR>0){
            radiusArr[0] = mCornerRadiusTL.toFloat()
            radiusArr[1] = mCornerRadiusTL.toFloat()
    
            radiusArr[2] = mCornerRadiusTR.toFloat()
            radiusArr[3] = mCornerRadiusTR.toFloat()
    
            radiusArr[4] = mCornerRadiusBR.toFloat()
            radiusArr[5] = mCornerRadiusBR.toFloat()
    
            radiusArr[6] = mCornerRadiusBL.toFloat()
            radiusArr[7] = mCornerRadiusBL.toFloat()
            gradientDrawable.cornerRadii=radiusArr
           
        }else{
            gradientDrawable.cornerRadius=mCornerRadius.toFloat()
        }
        gradientDrawable.setStroke(mStrokeWidth,strokeColor)
    }
    
    private fun getPressedColorSelector(normalColor:Int,pressedColor:Int):ColorStateList{
        return ColorStateList(
            arrayOf(
                intArrayOf(android.R.attr.state_pressed),
                intArrayOf(android.R.attr.state_focused),
                intArrayOf(android.R.attr.state_activated),
                intArrayOf()),
            intArrayOf(
                pressedColor,
                pressedColor,
                pressedColor,
                normalColor
            )
        )
    }
}