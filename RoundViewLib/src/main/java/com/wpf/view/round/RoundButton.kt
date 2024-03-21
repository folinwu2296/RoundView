package com.wpf.view.round

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatRadioButton
import kotlin.math.max

class RoundButton:AppCompatRadioButton {
    
    private lateinit var mDelegate: RoundDelegate
    
    constructor(context: Context) : super(context) {
        init(null)
    }
    
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs)
    }
    
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }
    
    
    private fun init(attrs: AttributeSet?) {
        mDelegate = RoundDelegate(this, context, attrs)
    }
    
    fun getRoundViewDelegate(): RoundDelegate {
        return mDelegate
    }
    
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (mDelegate.isWidthHeightEqual()&&width>0 && height>0){
            val max = max(width,height)
            val  measureSpec = MeasureSpec.makeMeasureSpec(max,MeasureSpec.EXACTLY)
            super.onMeasure(measureSpec, measureSpec)
            return
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }
    
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        if (mDelegate.isRadiusHalfHeight()){
            mDelegate.setCornerRadius(height/2)
        }else{
            mDelegate.setBgSelector()
        }
    }
}