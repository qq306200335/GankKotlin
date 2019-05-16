package com.xiaobai.libview.chart

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.xiaobai.libutils.common.DensityUtils

/**
 * 通用环形图view
 * @author baiyunfei on 2019/2/28
 * email 306200335@qq.com
 */
class CircleChartView : View {

    private var circleX = 0f
    private var circleY = 0f
    private var radius = 0f

    private var arcLeft = 0f
    private var arcTop = 0f
    private var arcRight = 0f
    private var arcBottom = 0f
    private lateinit var arcRF0: RectF

    //环形画笔
    private val circlePaint = Paint()
    //文字画笔
    private val textPaint = Paint()

    /**
     * 类型字体颜色，大小
     */
    private var textColor = Color.BLACK
    private var textSize = DensityUtils.sp2px(context, 14f)

    /**
     * 值集合
     */
    private val valueList = mutableListOf<Int>()
    private val percentList = mutableListOf<Float>()

    //值颜色列表
    private val valueTypeList = mutableListOf<String>()
    private val colorTypeList = mutableListOf<Int>()

    //初始化间隔大小
    private val marginMiddle = DensityUtils.dip2px(context, 16f).toFloat()

    constructor(context: Context) : super(context) {
        initPaint()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initPaint()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initPaint()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        circleX = (w / 4).toFloat()
        circleY = (h / 2).toFloat()
        radius = (h * 3 / 8).toFloat()

        arcLeft = circleX - radius
        arcTop = circleY - radius
        arcRight = circleX + radius
        arcBottom = circleY + radius
        arcRF0 = RectF(arcLeft, arcTop, arcRight, arcBottom)
    }

    /**
     * 初始化各种画笔
     */
    private fun initPaint() {
        //初始化Paint
        textPaint.isAntiAlias = true
        textPaint.textAlign = Paint.Align.CENTER
        textPaint.style = Paint.Style.FILL
        textPaint.color = textColor
        textPaint.textSize = textSize.toFloat()

        circlePaint.isAntiAlias = true
        textPaint.color = Color.BLACK
    }

    /**
     * view在创建的时候执行这个函数
     */
    public override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (valueList.size == 0) {
            return
        }

        setLayerType(LAYER_TYPE_SOFTWARE, null)

        drawCircle(canvas)
        drawText(canvas)
    }

    /**
     * 绘制环形图
     */
    private fun drawCircle(canvas: Canvas) {

        //起始角度
        var startAngle = 0f
        var percentAngle: Float

        for (i in percentList.indices) {

            percentAngle = if (i == percentList.size - 1) {
                360f - startAngle
            } else {
                (Math.round(percentList[i] * 360 * 100) / 100).toFloat()
            }

            //分配颜色
            if (colorTypeList.size > i) {
                circlePaint.color = colorTypeList[i]
            }

            //在饼图中显示所占比例
            canvas.drawArc(arcRF0, startAngle, percentAngle, true, circlePaint)

            //下次的起始角度
            startAngle += percentAngle
        }

        //画圆心
        circlePaint.color = Color.WHITE
        canvas.drawCircle(circleX, circleY, radius * 3 / 4, circlePaint)
    }

    /**
     * 绘制类型文字
     */
    private fun drawText(canvas: Canvas) {

        if (valueTypeList.size == 0) {
            return
        }

        val beforeWidth = width.toFloat() / 2 + marginMiddle
        var beforeHeight: Float

        for (i in valueTypeList.indices) {

            beforeHeight = height.toFloat() / (valueTypeList.size + 1) * (i + 1)

            //绘制方块的颜色
            if (colorTypeList.size > i) {
                textPaint.color = colorTypeList[i]
            }

            canvas.drawCircle(
                beforeWidth,
                beforeHeight,
                DensityUtils.dip2px(context, 6f).toFloat(),
                textPaint
            )

            //绘制字体的颜色
            textPaint.color = Color.BLACK

            //拿到字符串的宽度
            val titleWidth = textPaint.measureText(valueTypeList[i])

            canvas.drawText(
                valueTypeList[i],
                beforeWidth + marginMiddle + titleWidth / 2,
                beforeHeight + DensityUtils.dip2px(context, 10f).toFloat() / 2,
                textPaint
            )
        }
    }

    /**
     * 设置值集合
     *
     * @param values 值集合
     */
    fun setValueTypes(values: MutableList<String>) {
        valueTypeList.clear()
        valueTypeList.addAll(values)
    }

    /**
     * 设置值集合
     *
     * @param values 值集合
     */
    fun setColorTypes(values: MutableList<Int>) {
        colorTypeList.clear()
        colorTypeList.addAll(values)
    }

    /**
     * 设置值集合
     *
     * @param values 值集合
     */
    fun setValues(values: MutableList<Int>) {
        valueList.clear()
        valueList.addAll(values)
        initValue()
    }

    /**
     * 初始化Value
     */
    private fun initValue() {

        //没有数据则不绘制
        if (valueList.size == 0) {
            return
        }

        var countValue = 0f
        //计算总值
        for (i in valueList) {
            countValue += i
        }

        percentList.clear()
        //计算百分百集合
        for (i in valueList) {
            val percent = i / countValue
            percentList.add(percent)
        }
    }
}