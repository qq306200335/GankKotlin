package com.kzcpm.commonview.chart

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import com.xiaobai.libutils.common.DensityUtils
import com.xiaobai.libview.R


/**
 * 通用折线图view
 *
 * @author baiyunfei on 2015/12/15
 * email 306200335@qq.com
 */
class LineChartView : View {

    //折线路径
    private val brokenPathList = mutableListOf<Path>()

    //折线画笔
    private val brokenPaint = Paint()
    //坐标画笔
    private val straightPaint = Paint()
    //刻度线画笔
    private val scalePaint = Paint()
    //x坐标文字画笔
    private val xStraightTextPaint = Paint()
    //y坐标文字画笔
    private val yStraightTextPaint = Paint()
    //点画笔
    private val dottedPaint = Paint()
    //文字画笔
    private val textPaint = Paint()

    private val mPaint = Paint()

    //初始化间隔大小
    private val marginLittle = DensityUtils.dip2px(context, 4f).toFloat()
    private val marginShort = DensityUtils.dip2px(context, 8f).toFloat()
    private val marginMedium = DensityUtils.dip2px(context, 12f).toFloat()
    private val marginMiddle = DensityUtils.dip2px(context, 16f).toFloat()
    private val marginLong = DensityUtils.dip2px(context, 24f).toFloat()
    private val marginLarge = DensityUtils.dip2px(context, 32f).toFloat()
    private val marginBig = DensityUtils.dip2px(context, 40f).toFloat()

    /**
     * 折线颜色，粗细，线上字体颜色，大小
     */
    private var brokenColor = Color.BLACK
    private var brokenSize = DensityUtils.dip2px(context, 2f)
    private var brokenTextColor = Color.BLACK
    private var brokenTextSize = DensityUtils.sp2px(context, 12f)

    /**
     * 坐标颜色，粗细，线上字体颜色，大小
     */
    private var straightColor = Color.BLACK
    private var straightSize = DensityUtils.dip2px(context, 0.5f)
    private var xStraightTextColor = Color.BLACK
    private var yStraightTextColor = Color.BLACK
    private var xStraightTextSize = DensityUtils.sp2px(context, 12f)
    private var yStraightTextSize = DensityUtils.sp2px(context, 12f)

    /**
     * 折线图上的点颜色，大小
     */
    private var dottedColor = Color.BLACK
    private var dottedSize = DensityUtils.dip2px(context, 2f)

    /**
     * 原点和结束点的x,y坐标位置
     */
    private var xStartPoint: Float = 0f
    private var yStartPoint: Float = 0f
    private var xEndPoint: Float = 0f
    private var yEndPoint: Float = 0f
    /**
     * x,y坐标的长度
     */
    private var xStraightLength: Float = 0f
    private var yStraightLength: Float = 0f

    /**
     * 颜色，粗细，线上字体大小
     */
    private var xStrokeWidth = DensityUtils.dip2px(context, 1f)
    private var xTextSize = DensityUtils.sp2px(context, 12f)

    /**
     * Y轴最小值,刻度范围和刻度间隔
     */
    private var minValue: Int = 0
    private var valueSize = 5
    private var spaceValue = 5

    var title = ""

    /**
     * 坐标点集合
     */
    private val scorePoints = mutableListOf<MutableList<Point>>()

    /**
     * 纵坐标值集合
     */
    private val yValues = mutableListOf<MutableList<Float>>()
    /**
     * 横坐标值集合
     */
    private val xValueList = mutableListOf<String>()

    //值颜色列表
    val typeValueList = mutableListOf<String>()
    val typeColorList = mutableListOf<Int>()

    private var n = 0

    //是否显示折线图上的点
    var isShowPoint = false
    //是否显示折线图上的点
    var isShowScale = false

    //是否点击了图上的点
    private var isActionDown = false
    //点击了图上的点的位置
    private var selectPoint = -1

    private var iOnTouchClickListener: IOnTouchClickListener? = null

    constructor(context: Context) : super(context) {
        initPaint()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initViewAttribute(attrs!!)
        initPaint()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initViewAttribute(attrs!!)
        initPaint()
    }

    /**
     * 初始化自定义属性值
     */
    private fun initViewAttribute(attrs: AttributeSet) {

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.LineChartView)

        brokenColor = typedArray.getColor(R.styleable.LineChartView_brokenColor, Color.BLACK)
        brokenSize = typedArray.getDimensionPixelSize(
            R.styleable.LineChartView_brokenSize,
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                2f,
                resources.displayMetrics
            ).toInt()
        )
        brokenTextColor =
                typedArray.getColor(R.styleable.LineChartView_brokenTextColor, Color.BLACK)
        brokenTextSize = typedArray.getDimensionPixelSize(
            R.styleable.LineChartView_brokenTextSize,
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP,
                12f,
                resources.displayMetrics
            ).toInt()
        )

        straightColor = typedArray.getColor(R.styleable.LineChartView_straightColor, Color.BLACK)
        straightSize = typedArray.getDimensionPixelSize(
            R.styleable.LineChartView_straightSize,
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                0.5f,
                resources.displayMetrics
            ).toInt()
        )
        xStraightTextColor =
                typedArray.getColor(R.styleable.LineChartView_xStraightTextColor, Color.BLACK)
        yStraightTextColor =
                typedArray.getColor(R.styleable.LineChartView_yStraightTextColor, Color.BLACK)
        xStraightTextSize = typedArray.getDimensionPixelSize(
            R.styleable.LineChartView_xStraightTextSize,
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP,
                12f,
                resources.displayMetrics
            ).toInt()
        )
        yStraightTextSize = typedArray.getDimensionPixelSize(
            R.styleable.LineChartView_yStraightTextSize,
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP,
                12f,
                resources.displayMetrics
            ).toInt()
        )

        dottedColor = typedArray.getColor(R.styleable.LineChartView_dottedColor, Color.BLACK)
        dottedSize = typedArray.getDimensionPixelSize(
            R.styleable.LineChartView_dottedSize,
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                2f,
                resources.displayMetrics
            ).toInt()
        )

        typedArray.recycle()
    }

    /**
     * 初始化各种画笔
     */
    private fun initPaint() {
        //初始化Paint
        brokenPaint.isAntiAlias = true
        brokenPaint.style = Paint.Style.STROKE
        brokenPaint.strokeWidth = brokenSize.toFloat()
        brokenPaint.color = brokenColor
        brokenPaint.strokeCap = Paint.Cap.ROUND

        straightPaint.isAntiAlias = true
        straightPaint.style = Paint.Style.STROKE
        straightPaint.strokeWidth = straightSize.toFloat()
        straightPaint.color = straightColor
        straightPaint.strokeCap = Paint.Cap.ROUND

        scalePaint.isAntiAlias = true
        scalePaint.style = Paint.Style.STROKE
        scalePaint.strokeWidth = straightSize.toFloat()
        scalePaint.color = straightColor
        val floatArray = floatArrayOf(10f, 10f)
        val pathEffect: PathEffect = DashPathEffect(floatArray, 1f)
        scalePaint.pathEffect = pathEffect

        xStraightTextPaint.isAntiAlias = true
        xStraightTextPaint.textAlign = Paint.Align.CENTER
        xStraightTextPaint.style = Paint.Style.FILL
        xStraightTextPaint.color = xStraightTextColor
        xStraightTextPaint.strokeCap = Paint.Cap.ROUND
        xStraightTextPaint.textSize = xStraightTextSize.toFloat()

        yStraightTextPaint.isAntiAlias = true
        yStraightTextPaint.textAlign = Paint.Align.CENTER
        yStraightTextPaint.style = Paint.Style.FILL
        yStraightTextPaint.color = yStraightTextColor
        yStraightTextPaint.strokeCap = Paint.Cap.ROUND
        yStraightTextPaint.textSize = yStraightTextSize.toFloat()

        dottedPaint.isAntiAlias = true
        dottedPaint.style = Paint.Style.FILL
        dottedPaint.strokeWidth = dottedSize.toFloat()
        dottedPaint.color = dottedColor
        dottedPaint.strokeCap = Paint.Cap.ROUND

        textPaint.isAntiAlias = true
        textPaint.textAlign = Paint.Align.CENTER
        textPaint.style = Paint.Style.FILL
        textPaint.color = brokenTextColor
        textPaint.textSize = brokenTextSize.toFloat()

        mPaint.isAntiAlias = true
        textPaint.textAlign = Paint.Align.CENTER
        textPaint.style = Paint.Style.FILL
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        xStartPoint = marginBig
        yStartPoint = height - marginLarge
        xEndPoint = width - marginLarge
        yEndPoint = marginBig

        xStraightLength = xEndPoint - xStartPoint
        yStraightLength = yStartPoint - yEndPoint
    }

    /**
     * view在创建的时候执行这个函数
     */
    public override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (yValues.size == 0) {
            return
        }

        setLayerType(LAYER_TYPE_SOFTWARE, null)

        drawXStraight(canvas)
        drawYStraight(canvas)

        drawOther(canvas)
        drawLine(canvas)
        drawPoint(canvas)
    }

    /**
     * 绘制X坐标
     */
    private fun drawXStraight(canvas: Canvas) {

        //画X轴
        canvas.drawLine(
            xStartPoint, yStartPoint,
            xEndPoint, yStartPoint, straightPaint
        )
        //添加X坐标的刻度
        for (i in xValueList.indices) {

            if (i == 0) {
                //添加文字
                canvas.drawText(
                    xValueList[i],
                    xStartPoint,
                    yStartPoint + marginMiddle, xStraightTextPaint
                )
            }
            if (i == xValueList.size - 1) {
                //添加文字
                canvas.drawText(
                    xValueList[i],
                    xEndPoint - marginShort,
                    yStartPoint + marginMiddle, xStraightTextPaint
                )
            }
        }
    }

    /**
     * 绘制Y坐标
     */
    private fun drawYStraight(canvas: Canvas) {

        //画Y轴
        canvas.drawLine(
            xStartPoint,
            yEndPoint - marginShort,
            xStartPoint,
            yStartPoint,
            straightPaint
        )

        //拿到字符串的宽度
        val titleWidth = yStraightTextPaint.measureText(title)

        canvas.drawText(
            title,
            xStartPoint + marginShort + titleWidth / 2,
            yEndPoint - marginShort,
            yStraightTextPaint
        )

        //添加Y刻度和文字
        for (i in 0..valueSize) {

            val y = yStartPoint - yStraightLength / valueSize * i

            //刻度大小
            if (i % spaceValue == 0) {

                val yValue = (minValue + i).toString()

                //拿到字符串的宽度
                val yValueWidth = yStraightTextPaint.measureText(yValue)

                canvas.drawText(
                    yValue,
                    xStartPoint - yValueWidth / 2 - marginShort,
                    y + marginLittle,
                    yStraightTextPaint
                )

                //刻度线
                canvas.drawLine(xStartPoint, y, xEndPoint, y, scalePaint)
            }

            //刻度
            if (isShowScale && i % (spaceValue / 5) == 0) {
                canvas.drawLine(xStartPoint, y, xStartPoint - marginLittle, y, straightPaint)
            }
        }
    }

    /**
     * 绘制右上角类型内容
     */
    private fun drawOther(canvas: Canvas) {

        //有多条数据则绘制
        if (typeValueList.size > 1) {

            //倒序
            typeValueList.reverse()
            typeColorList.reverse()

            mPaint.strokeWidth = xStrokeWidth.toFloat()
            mPaint.textSize = xTextSize.toFloat()

            //绘制颜色对应类型
            var beforeWidth = width - marginLong

            for (i in typeValueList.indices) {

                //绘制字体的颜色
                mPaint.color = Color.BLACK

                //拿到字符串的宽度
                val stringWidth = mPaint.measureText(typeValueList[i])

                val xWidth = beforeWidth - stringWidth

                beforeWidth = xWidth - marginMiddle - marginLittle

                canvas.drawText(typeValueList[i], xWidth, marginLarge, mPaint)

                //绘制方块的颜色
                if (typeColorList.size > i) {
                    mPaint.color = typeColorList[i]
                }

                canvas.drawCircle(
                    xWidth - marginShort,
                    marginLong + marginLittle,
                    DensityUtils.dip2px(context, 5f).toFloat(),
                    mPaint
                )
            }

            //撤销倒序
            typeValueList.reverse()
            typeColorList.reverse()
        }
    }

    /**
     * 折线绘制
     */
    private fun drawLine(canvas: Canvas) {

        brokenPathList.clear()

        for (i in scorePoints.indices) {

            val pointList = scorePoints[i]

            val path = Path()
            path.reset()

            path.moveTo(pointList[0].x.toFloat(), pointList[0].y.toFloat())

            for (j in pointList.indices) {
                path.lineTo(pointList[j].x.toFloat(), pointList[j].y.toFloat())
            }

            brokenPathList.add(path)

            //有多种颜色，则根据size替换颜色
            if (typeColorList.size > i) {
                brokenPaint.color = typeColorList[i]
            }
            canvas.drawPath(path, brokenPaint)
        }
    }

    /**
     * 点绘制
     */
    private fun drawPoint(canvas: Canvas) {

        for (i in scorePoints.indices) {

            val pointList = scorePoints[i]

            for (j in pointList.indices) {

                //如果是被选中的点
                if (j == selectPoint) {

                    canvas.drawCircle(
                        pointList[j].x.toFloat(),
                        pointList[j].y.toFloat(),
                        DensityUtils.dip2px(context, 4f).toFloat(),
                        dottedPaint
                    )

                    //如果是点击或滑动的时候
                    if (isActionDown) {

                        //绘制竖线
                        mPaint.color = dottedColor
                        canvas.drawLine(
                            pointList[j].x.toFloat(),
                            yStartPoint,
                            pointList[j].x.toFloat(),
                            yEndPoint,
                            mPaint
                        )

                        //添加背景
                        mPaint.color = Color.rgb(230, 230, 251)
                        canvas.drawRect(0f, 0f, width.toFloat(), marginLarge, mPaint)

                        drawTypeValue(canvas, j)
                    }
                }

                //如果需要显示点的话
                if (isShowPoint) {
                    canvas.drawCircle(
                        pointList[j].x.toFloat(),
                        pointList[j].y.toFloat(),
                        DensityUtils.dip2px(context, 3f).toFloat(),
                        dottedPaint
                    )
                }
            }
        }
    }

    /**
     * 绘制其它内容
     */
    private fun drawTypeValue(canvas: Canvas, position: Int) {

        //有数据则绘制
        if (typeValueList.size > 0) {

            //倒序
            typeValueList.reverse()
            yValues.reverse()

            mPaint.strokeWidth = xStrokeWidth.toFloat()
            mPaint.textSize = xTextSize.toFloat()

            var beforeWidth = width - marginMiddle

            for (i in typeValueList.indices) {

                //绘制字体的颜色
                mPaint.color = Color.BLACK

                //拿到字符串的宽度
                val valueWidth =
                    mPaint.measureText(typeValueList[i] + "：" + yValues[i][position].toInt())

                val xValueWidth = beforeWidth - valueWidth

                beforeWidth = xValueWidth - marginLittle

                canvas.drawText(
                    typeValueList[i] + "：" + yValues[i][position].toInt(),
                    xValueWidth,
                    marginLong - marginLittle,
                    mPaint
                )
            }
        }

        //撤销倒序
        typeValueList.reverse()
        yValues.reverse()

        //拿到字符串的宽度
        val stringWidth = mPaint.measureText(xValueList[position])
        val xWidth = stringWidth / 2
        canvas.drawText(
            xValueList[position],
            marginMiddle,
            marginLong - marginLittle,
            mPaint
        )
    }

    /**
     * 设置横坐标值集合
     *
     * @param valueList 值集合
     */
    fun setXValueList(valueList: List<String>) {
        xValueList.clear()
        xValueList.addAll(valueList)
    }

    /**
     * 设置纵坐标值集合
     *
     * @param valueList 值集合
     */
    fun setYValueList(valueList: MutableList<Float>) {
        yValues.clear()
        yValues.add(valueList)
        initValue()
    }

    /**
     * 设置纵坐标值集合
     *
     * @param values 值集合
     */
    fun setYValues(values: MutableList<MutableList<Float>>) {
        yValues.clear()
        yValues.addAll(values)
        initValue()
    }

    /**
     * 初始化Value
     */
    private fun initValue() {

        //没有数据则不绘制
        if (yValues.size == 0) {
            return
        }

        val allValueList = mutableListOf<Float>()

        for (valueList in yValues) {
            allValueList.addAll(valueList)
        }

        var maxValue = ChartUtil.getMaxValue(allValueList, spaceValue)
        minValue = ChartUtil.getMinValue(allValueList, spaceValue)
        //获取集合中的最大最小值的差值
        valueSize = maxValue - minValue

        val level1 = 50
        val level2 = 100
        val level3 = 500
        val level4 = 1000
        val level5 = 5000
        val level6 = 10000

        spaceValue = 1

        //设置刻度值间隔
        when {
            valueSize <= level1 -> spaceValue = 5
            valueSize <= level2 -> spaceValue = 10
            valueSize <= level3 -> spaceValue = 50
            valueSize <= level4 -> spaceValue = 100
            valueSize <= level5 -> spaceValue = 500
            valueSize <= level6 -> spaceValue = 1000
        }

        maxValue = ChartUtil.getMaxValue(allValueList, spaceValue)
        minValue = ChartUtil.getMinValue(allValueList, spaceValue)

        if (maxValue == minValue) {
            maxValue += 5
        }
        //获取集合中的最大最小值的差值
        valueSize = maxValue - minValue

        initData()
    }

    /**
     * 初始化数据，将数据转换成point点集合
     */
    private fun initData() {

        //没有数据则不绘制
        if (yValues.size == 0) {
            return
        }

        scorePoints.clear()

        for (i in yValues.indices) {

            val pointList = mutableListOf<Point>()

            for (j in yValues[i].indices) {

                val point = Point()
                point.x = (xStartPoint + xStraightLength / (yValues[i].size - 1) * j).toInt()
                point.y = (yStartPoint - yStraightLength / valueSize * yValues[i][j]).toInt()

                pointList.add(point)
            }

            scorePoints.add(pointList)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {

        this.parent.requestDisallowInterceptTouchEvent(true)
        //一旦底层View收到touch的action后调用这个方法那么父层View就不会再调用onInterceptTouchEvent了，也无法截获以后的action，这个事件被消费了

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                //触摸(ACTION_DOWN操作)，滑动(ACTION_MOVE操作)和抬起(ACTION_UP)}
                onActionUpEvent(event)
                this.parent.requestDisallowInterceptTouchEvent(false)
            }
            MotionEvent.ACTION_MOVE -> {
                //触摸(ACTION_DOWN操作)，滑动(ACTION_MOVE操作)和抬起(ACTION_UP)}
                onActionUpEvent(event)
                this.parent.requestDisallowInterceptTouchEvent(false)
            }
            MotionEvent.ACTION_UP -> {
                isActionDown = false
                invalidate()
                this.parent.requestDisallowInterceptTouchEvent(false)

                if (iOnTouchClickListener != null) {
                    iOnTouchClickListener!!.onTouchUp(selectPoint)
                }
            }
            MotionEvent.ACTION_CANCEL -> {
                isActionDown = false
                invalidate()
                this.parent.requestDisallowInterceptTouchEvent(false)
                if (iOnTouchClickListener != null) {
                    iOnTouchClickListener!!.onTouchUp(selectPoint)
                }
            }
        }
        return true
    }

    private fun onActionUpEvent(event: MotionEvent) {

        if (scorePoints.size == 0) {
            return
        }

        //判断是否是指定的触摸区域
        for (i in scorePoints[0].indices) {

            if (event.x > scorePoints[0][i].x - marginShort && event.x < scorePoints[0][i].x + marginShort) {

                selectPoint = i
                isActionDown = true
                invalidate()

                if (iOnTouchClickListener != null) {
                    iOnTouchClickListener!!.onTouchDown(selectPoint)
                }
            }
        }
    }

    /**
     * 设置回调接口
     */
    fun setIOnItemClickListener(iOnTouchClickListener: IOnTouchClickListener) {
        this.iOnTouchClickListener = iOnTouchClickListener
    }

    /**
     * 选择点击回调接口
     */
    interface IOnTouchClickListener {

        /**
         * 手指点击
         *
         * @param position 对应的位置
         */
        fun onTouchDown(position: Int)

        /**
         * 手指抬起
         *
         * @param position 对应的位置
         */
        fun onTouchUp(position: Int)
    }
}