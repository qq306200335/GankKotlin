package com.kzcpm.commonview.chart

/**
 * 图表工具类
 *
 * @author baiyunfei on 2015/12/15
 * email 306200335@qq.com
 */
object ChartUtil {

    /**
     * 获取最大值
     *
     * @param valueList 值集合
     * @return 最大值
     */
    fun getMaxValue(valueList: List<Float>, space: Int): Int {

        var max = valueList[0]

        for (i in 1 until valueList.size) {
            if (max < valueList[i]) {
                max = valueList[i]
            }
        }

        var maxValue = max.toInt()

        if (maxValue == 0) {
            return 0
        } else if (maxValue > 0) {
            return if (maxValue % space == 0) {
                maxValue / space * space
            } else {
                (maxValue / space + 1) * space
            }
        } else {
            maxValue = -maxValue
            return if (maxValue % space == 0) {
                -(maxValue / space * space)
            } else {
                -(maxValue / space * space)
            }
        }
    }

    /**
     * 获取最小值
     *
     * @param valueList 值集合
     * @return 最小值
     */
    fun getMinValue(valueList: List<Float>, space: Int): Int {

        var min = valueList[0]

        for (i in 1 until valueList.size) {
            if (min > valueList[i]) {
                min = valueList[i]
            }
        }

        var minValue = min.toInt()

        //刻度 space
        when {
            minValue == 0 -> return 0
            minValue > 0 -> return if (minValue % space == 0) {
                minValue / space * space
            } else {
                minValue / space * space
            }
            else -> {
                minValue = -minValue

                return if (minValue % space == 0) {
                    -(minValue / space) * space
                } else {
                    -(minValue / space + 1) * space
                }
            }
        }
    }

    fun getValueSize(valueList: List<Float>): Int {

        var max = valueList[0]

        for (i in 1 until valueList.size) {
            if (max < valueList[i]) {
                max = valueList[i]
            }
        }

        var min = valueList[0]

        for (i in 1 until valueList.size) {
            if (min > valueList[i]) {
                min = valueList[i]
            }
        }

        return (max - min).toInt()
    }
}