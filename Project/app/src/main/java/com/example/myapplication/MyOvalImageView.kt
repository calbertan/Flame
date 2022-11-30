package com.example.myapplication

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.RelativeLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.graphics.drawable.toBitmap

class MyOvalImageView: RelativeLayout {

    var rightBottomRadius: Int
    var leftBottomRadius: Int
    var rightTopRadius: Int
    var leftTopRadius: Int
    var radius = 0

    constructor(context: Context): this(context, null)
    constructor(context: Context, attributeSet: AttributeSet?): this(context, attributeSet, 0)
    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int)
            :super(context, attributeSet, defStyleAttr) {
        val typeArray = context.obtainStyledAttributes(attributeSet, R.styleable.RoundView)
        val defaultRadius = 0
        radius = typeArray.getDimensionPixelOffset(R.styleable.RoundView_radius, defaultRadius)
        leftTopRadius = typeArray.getDimensionPixelOffset(
            R.styleable.RoundView_leftTopRadius,
            defaultRadius
        )
        rightTopRadius = typeArray.getDimensionPixelOffset(
            R.styleable.RoundView_rightTopRadius,
            defaultRadius
        )
        leftBottomRadius = typeArray.getDimensionPixelOffset(
            R.styleable.RoundView_leftBottomRadius,
            defaultRadius
        )
        rightBottomRadius = typeArray.getDimensionPixelOffset(
            R.styleable.RoundView_rightBottomRadius,
            defaultRadius
        )
        if (radius != 0) {
            if (leftTopRadius == 0) {
                leftTopRadius = radius
            }
            if (rightTopRadius == 0) {
                rightTopRadius = radius
            }
            if (leftBottomRadius == 0) {
                leftBottomRadius = radius
            }
            if (rightBottomRadius == 0) {
                rightBottomRadius = radius
            }
        }
        typeArray.recycle()
    }

    override fun onDraw(canvas: Canvas?) {
        // 图片宽高大于圆角宽高， 获取圆角的宽高
        // 横向长度
        val maxLeft = Math.max(leftTopRadius, leftBottomRadius)
        val maxRight = Math.max(rightTopRadius, rightBottomRadius)
        val minWidth = maxLeft + maxRight
        // 竖纵向长度
        val maxTop = Math.max(leftTopRadius, rightTopRadius)
        val maxBottom = Math.max(leftBottomRadius, rightBottomRadius)
        val minHeight = maxTop + maxBottom
        if (width > minWidth && height > minHeight) {
            val path = Path()

            path.moveTo(leftTopRadius.toFloat(), 0F)

            path.lineTo((width - rightTopRadius).toFloat(), 0F)
            path.quadTo(width.toFloat(), 0F, width.toFloat(), rightTopRadius.toFloat())

            path.lineTo(width.toFloat(), (height - rightBottomRadius).toFloat())
            path.quadTo(
                width.toFloat(),
                height.toFloat(),
                (width - rightBottomRadius).toFloat(),
                height.toFloat()
            )

            path.lineTo(leftBottomRadius.toFloat(), height.toFloat())
            path.quadTo(0F, height.toFloat(), 0F, (height - leftBottomRadius).toFloat())

            path.lineTo(0F, leftTopRadius.toFloat())
            path.quadTo(0F, 0F, leftTopRadius.toFloat(), 0F)

            canvas!!.clipPath(path)
        }
        super.onDraw(canvas)
    }

}