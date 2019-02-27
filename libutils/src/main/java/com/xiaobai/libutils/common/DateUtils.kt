package com.xiaobai.libutils.common

import android.annotation.SuppressLint
import java.text.DecimalFormat
import java.text.ParseException
import java.text.ParsePosition
import java.text.SimpleDateFormat
import java.util.*

/**
 * 获取时间工具类
 *
 * @author 来自网络 on 2015/12/15
 */
@SuppressLint("SimpleDateFormat")
object DateUtils {

    /**
     * 获取现在时间
     *
     * @return 返回短时间字符串格式yyyyMMdd
     */
    val date: String
        get() {
            val currentTime = Date()
            val formatter = SimpleDateFormat("yyyyMMdd")
            return formatter.format(currentTime)
        }

    /**
     * 获取现在时间
     *
     * @return 返回短时间字符串格式yyyy/MM/dd
     */
    val nowDate: String
        get() {
            val currentTime = Date()
            val formatter = SimpleDateFormat("yyyy/MM/dd")
            return formatter.format(currentTime)
        }

    /**
     * 获取现在时间
     *
     * @return 返回短时间字符串格式yyyy-MM-dd
     */
    val stringDate: String
        get() {
            val currentTime = Date()
            val formatter = SimpleDateFormat("yyyy-MM-dd")
            return formatter.format(currentTime)
        }

    /**
     * 获取现在时间
     *
     * @return 返回字符串格式 yyyyMMddHHmmss
     */
    val dateTime: String
        get() {
            val currentTime = Date()
            val formatter = SimpleDateFormat("yyyyMMddHHmmss")
            return formatter.format(currentTime)
        }

    /**
     * 获取现在时间
     *
     * @return 返回字符串格式 yyyy-MM-dd HH:mm:ss
     */
    val stringTime: String
        get() {
            val currentTime = Date()
            val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            return formatter.format(currentTime)
        }

    /**
     * 获取时间 小时:分;秒 HH:mm:ss
     *
     * @return 返回字符串格式 HH:mm:ss
     */
    val time: String
        get() {
            val formatter = SimpleDateFormat("HH:mm:ss")
            val currentTime = Date()
            return formatter.format(currentTime)
        }

    /**
     * 得到下个月具体时间还款日期的时间戳
     */
    val nextMonth: Long
        get() {
            val c = Calendar.getInstance()
            val month = c.get(Calendar.MONTH) + 2
            val year = c.get(Calendar.YEAR)
            val date = year.toString() + "-" + month + "-16 00:00:00"
            val time = strDateToLong(date)!!
            return time / 1000
        }

    /**
     * 得到当前时间的月份和年
     */
    val thisYearMonth: Int
        get() {
            val c = Calendar.getInstance()
            val sdf = SimpleDateFormat("yyyyMM")
            c.get(Calendar.YEAR)
            c.get(Calendar.MONTH)
            return Integer.parseInt(sdf.format(c.time))
        }

    /**
     * 获取当前时间戳
     */
    val longTime: Long
        get() = System.currentTimeMillis()

    /**
     * 获取指定日期的时间 小时:分 HH:mm
     *
     * @return 返回字符串格式 HH:mm
     */
    fun getTimeShort(date: Date): String {
        val formatter = SimpleDateFormat("HH:mm")
        return formatter.format(date)
    }

    /**
     * 将短时间格式字符串转换为时间 yyyy-MM-dd
     *
     * @param strDate 短时间字符串
     * @return 返回字符串格式 yyyy-MM-dd
     */
    fun strToDate(strDate: String): Date {
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        val pos = ParsePosition(0)
        return formatter.parse(strDate, pos)
    }

    /**
     * 将长时间格式时间转换为字符串 yyyy-MM-dd HH:mm:ss
     *
     * @param dateDate 长时间字符串
     * @return 返回字符串格式 yyyy-MM-dd HH:mm:ss
     */
    fun dateToStrLong(dateDate: Date): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return formatter.format(dateDate)
    }

    /**
     * 将短时间格式时间转换为字符串 yyyy-MM-dd
     *
     * @param dateDate 短时间字符串
     * @return 返回字符串格式 yyyy-MM-dd
     */
    fun dateToStr(dateDate: Date): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        return formatter.format(dateDate)
    }

    /**
     * 转换为字符串 yyyy-MM-dd
     *
     * @param year  年
     * @param month 月
     * @param day   天
     * @return 返回字符串格式 yyyy-MM-dd
     */
    fun intFormatToDateStr(year: Int, month: Int, day: Int): String {
        val parted = "00"
        val decimal = DecimalFormat(parted)
        return year.toString() + "-" +
                decimal.format(month.toLong()) + "-" + decimal.format(day.toLong())
    }

    /**
     * 将字符串日期转换为long类型
     *
     * @param date 字符串日期
     * @return long类型
     * @throws ParseException
     * @throws Exception
     */
    fun strDateToLong(date: String): Long? {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return try {
            val dt = sdf.parse(date)
            dt.time
        } catch (e: ParseException) {
            e.printStackTrace()
            null
        }

    }

    /**
     * 将10位数毫秒转换为详细时间
     */
    fun getDateTime(time: Long): String {

        val c = Calendar.getInstance()
        c.timeInMillis = time * 1000
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return sdf.format(c.time)

    }

    /**
     * 将10位数毫秒转换为日期
     */
    fun getDate(time: String): String {

        val length = 10

        if (time.length < length) {
            return "0"
        }
        val time0 = java.lang.Long.parseLong(time)
        val c = Calendar.getInstance()
        c.timeInMillis = time0 * 1000
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        return sdf.format(c.time)
    }

    /**
     * 将13位毫秒转换为日期
     */
    fun getLongDate(time: Long): String {
        val c = Calendar.getInstance()
        c.timeInMillis = time
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return sdf.format(c.time)
    }

    /**
     * 得到添加月份的时间
     *
     * @param month 月份时间
     * @return 返回字符串格式 yyyy-MM-dd HH:mm:ss
     */
    fun getAddMonth(month: Int): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val c = Calendar.getInstance()
        c.add(Calendar.MONTH, month)
        c.set(Calendar.DAY_OF_MONTH, 1)
        c.set(Calendar.HOUR_OF_DAY, 0)
        c.set(Calendar.MINUTE, 0)
        c.set(Calendar.SECOND, 0)
        return sdf.format(c.time)
    }

    /**
     * 得到添加月份的时间戳
     *
     * @param month 添加的月份
     * @return 时间戳
     */
    fun getMonthTime(month: Int): Long {
        val time = strDateToLong(getAddMonth(month))!!
        return time / 1000
    }

    /**
     * 返回13位月份时间戳
     *
     * @param month 月份
     * @return 时间戳
     */
    fun getMonthLongTime(month: Int): Long {
        return strDateToLong(getAddMonth(month))!!
    }

    /**
     * 根据时间戳得到月份和年
     *
     * @param time 时间戳
     * @return 返回字符串格式 yyyy年MM月
     */
    fun getCurrYearMonth(time: Long): String {
        val c = Calendar.getInstance()
        c.timeInMillis = time * 1000
        val sdf = SimpleDateFormat("yyyy年MM月")
        return sdf.format(c.time)
    }

    /**
     * 根据时间戳得到年月日
     *
     * @param time 时间戳
     * @return 返回字符串格式 yyyy年MM月dd日
     */
    fun getCurrYearMonthDay(time: Long): String {
        val c = Calendar.getInstance()
        c.timeInMillis = time * 1000
        val sdf = SimpleDateFormat("yyyy年MM月dd日")
        return sdf.format(c.time)
    }

    /**
     * 得到添加月份的月份
     *
     * @param month 月份
     * @return +1后的真实月份
     */
    fun getMonth(month: Int): String {
        val c = Calendar.getInstance()
        c.add(Calendar.MONTH, month)
        return (c.get(Calendar.MONTH) + 1).toString() + ""
    }

    /**
     * 得到添加月份的年月
     *
     * @param month 月份
     * @return 返回字符串格式 yyyy年MM月
     */
    fun getYearMonth(month: Int): String {
        val sdf = SimpleDateFormat("yyyy年MM月")
        val c = Calendar.getInstance()
        c.add(Calendar.MONTH, month)
        return sdf.format(c.time)
    }

    /**
     * 得到当前日期的去年的时间
     *
     * @return yyyy-MM-dd
     */
    fun getLastYear(calendar: Calendar): String {

        calendar.add(Calendar.YEAR, -3)
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        return formatter.format(calendar.time)
    }

    /**
     * 得到当前日期的所在周的周一
     *
     * @return yyyy-MM-dd
     */
    fun getMondayOfThisWeek(calendar: Calendar): String {

        var dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1

        if (dayOfWeek == 0) {
            dayOfWeek = 7
        }

        calendar.add(Calendar.DATE, -dayOfWeek + 1)
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        return formatter.format(calendar.time)
    }

    /**
     * 获得上周星期一的日期
     */
    fun getPreviousMonday(calendar: Calendar): String {

        var dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1

        if (dayOfWeek == 0) {
            dayOfWeek = 7
        }

        calendar.add(Calendar.DATE, -dayOfWeek + 1 - 7)
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        return formatter.format(calendar.time)
    }

    /**
     * 获得下周星期一的日期
     */
    fun getNextMonday(calendar: Calendar): String {

        var dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1

        if (dayOfWeek == 0) {
            dayOfWeek = 7
        }

        calendar.add(Calendar.DATE, -dayOfWeek + 1 + 7)
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        return formatter.format(calendar.time)
    }

    /**
     * 得到本周周日
     *
     * @return yyyy-MM-dd
     */
    fun getSundayOfThisWeek(calendar: Calendar): String {

        var dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1

        if (dayOfWeek == 0) {
            dayOfWeek = 7
        }

        calendar.add(Calendar.DATE, -dayOfWeek + 7)
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        return formatter.format(calendar.time)
    }

    /**
     * 得到当前日期的前七天日期
     *
     * @return yyyy-MM-dd
     */
    fun getDateBeforeWeek(date: String): String {

        val year = Integer.parseInt(date.substring(0, 4))
        val month = Integer.parseInt(date.substring(5, 7))
        val day = Integer.parseInt(date.substring(8, 10))

        val calendar = Calendar.getInstance()
        calendar.set(year, month - 1, day)

        calendar.add(Calendar.DAY_OF_YEAR, -7)
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        return formatter.format(calendar.time)
    }

    /**
     * 得到当前日期的后七天日期
     *
     * @return yyyy-MM-dd
     */
    fun getDateAfterWeek(date: String): String {

        val year = Integer.parseInt(date.substring(0, 4))
        val month = Integer.parseInt(date.substring(5, 7))
        val day = Integer.parseInt(date.substring(8, 10))

        val calendar = Calendar.getInstance()
        calendar.set(year, month - 1, day)


        calendar.add(Calendar.DAY_OF_YEAR, 7)
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        return formatter.format(calendar.time)
    }

    /**
     * 得到当前日期的后三天日期
     *
     * @return yyyy-MM-dd
     */
    fun getDateAfterThree(date: String): String {

        val year = Integer.parseInt(date.substring(0, 4))
        val month = Integer.parseInt(date.substring(5, 7))
        val day = Integer.parseInt(date.substring(8, 10))

        val calendar = Calendar.getInstance()
        calendar.set(year, month - 1, day)

        calendar.add(Calendar.DAY_OF_YEAR, 3)
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        return formatter.format(calendar.time)
    }

    /**
     * 得到当前日期的前一天日期
     *
     * @return yyyy-MM-dd
     */
    fun getBeforeDate(date: String): String {

        val year = Integer.parseInt(date.substring(0, 4))
        val month = Integer.parseInt(date.substring(5, 7))
        val day = Integer.parseInt(date.substring(8, 10))

        val calendar = Calendar.getInstance()
        calendar.set(year, month - 1, day)

        calendar.add(Calendar.DAY_OF_YEAR, -1)
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        return formatter.format(calendar.time)
    }

    /**
     * 得到当前日期的后一天日期
     *
     * @return yyyy-MM-dd
     */
    fun getAfterDate(date: String): String {

        val year = Integer.parseInt(date.substring(0, 4))
        val month = Integer.parseInt(date.substring(5, 7))
        val day = Integer.parseInt(date.substring(8, 10))

        val calendar = Calendar.getInstance()
        calendar.set(year, month - 1, day)

        calendar.add(Calendar.DAY_OF_YEAR, 1)
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        return formatter.format(calendar.time)
    }

    /**
     * 为单数加0
     *
     * @param number 数字
     * @return 加0后的字符串
     */
    fun getNumber(number: Int): String {

        var s = number.toString() + ""

        val n = 10

        //小于10加0
        if (number < n) {
            s = "0$s"
        }

        return s
    }

    /**
     * 设置20151212为2015-12-12
     *
     * @param number 数字
     * @return 转换后的字符串
     */
    fun setDate(number: Int): String {

        var date = number.toString() + ""

        val year = date.substring(0, 4)
        val month = date.substring(4, 6)
        val day = date.substring(6, date.length)

        date = "$year-$month-$day"

        return date
    }

    /**
     * 设置2015-12-12为20151212
     *
     * @param date 字符串日期
     * @return 数字日期
     */
    fun setDate(date: String): Int {

        return Integer.parseInt(date.replace("-", ""))
    }
}



