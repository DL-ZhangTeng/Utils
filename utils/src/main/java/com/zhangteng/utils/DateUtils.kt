package com.zhangteng.utils

import android.annotation.SuppressLint
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


/**
 * 英文简写如：12
 */
const val FORMAT_H: String = "HH"

/**
 * 英文简写如：12:01
 */
const val FORMAT_M: String = "mm"

/**
 * 英文简写如：12:01
 */
const val FORMAT_HM: String = "HH:mm"

/**
 * 英文简写如：1-12
 */
const val FORMAT_MD: String = "MM-dd"

/**
 * 英文简写如：1-12 12:01
 */
const val FORMAT_MDHM: String = "MM-dd HH:mm"

/**
 * 英文简写如：2016
 */
const val FORMAT_Y: String = "yyyy"

/**
 * 英文简写（默认）如：2016-12
 */
const val FORMAT_YM: String = "yyyy-MM"

/**
 * 英文简写（默认）如：2016-12-01
 */
const val FORMAT_YMD: String = "yyyy-MM-dd"

/**
 * 英文全称  如：2016-12-01 23
 */
const val FORMAT_YMDH: String = "yyyy-MM-dd HH"

/**
 * 英文全称  如：2016-12-01 23:15
 */
const val FORMAT_YMDHM: String = "yyyy-MM-dd HH:mm"

/**
 * 英文全称  如：2016-12-01 23:15:06
 */
const val FORMAT_YMDHMS: String = "yyyy-MM-dd HH:mm:ss"

/**
 * 精确到毫秒的完整时间    如：yyyy-MM-dd HH:mm:ss.S
 */
const val FORMAT_FULL: String = "yyyy-MM-dd HH:mm:ss.S"

/**
 * 中文简写  如：2016年
 */
const val FORMAT_Y_CN: String = "yyyy年"

/**
 * 中文简写  如：2016年12月
 */
const val FORMAT_YM_CN: String = "yyyy年MM月"

/**
 * 中文简写  如：2016年12月01日
 */
const val FORMAT_YMD_CN: String = "yyyy年MM月dd日"

/**
 * 中文简写  如：2016年12月01日  12时
 */
const val FORMAT_YMDH_CN: String = "yyyy年MM月dd日 HH时"

/**
 * 中文简写  如：2016年12月01日  12时12分
 */
const val FORMAT_YMDHM_CN: String = "yyyy年MM月dd日 HH时mm分"

/**
 * 中文全称  如：2016年12月01日  23时15分06秒
 */
const val FORMAT_YMDHMS_CN: String = "yyyy年MM月dd日  HH时mm分ss秒"

/**
 * 精确到毫秒的完整中文时间
 */
const val FORMAT_FULL_CN: String = "yyyy年MM月dd日  HH时mm分ss秒SSS毫秒"

/**
 * 中文简写  如：12月01日
 */
const val FORMAT_MD_CN: String = "MM月dd日"

/**
 * 格式化时间 时间毫秒
 * @return 返回时间String
 */
@SuppressLint("SimpleDateFormat")
fun Long?.getTimeStr(format: String = FORMAT_YMDHMS): String? {
    return if (this == null) null else SimpleDateFormat(format).format(this)
}

/**
 * 使用预设格式提取字符串日期
 *
 * @return 提取字符串的日期
 */
@SuppressLint("SimpleDateFormat")
fun String?.str2Date(pattern: String? = FORMAT_YMDHMS): Date? {
    val df = SimpleDateFormat(pattern)
    return try {
        if (this != null)
            df.parse(this)
        else
            null
    } catch (e: ParseException) {
        e.printStackTrace()
        null
    } catch (e: NullPointerException) {
        e.printStackTrace()
        null
    }
}

/**
 * 使用预设格式提取字符串日期
 *
 * @return 提取字符串的日期
 */
fun String?.str2Calendar(format: String? = FORMAT_YMDHMS): Calendar? {
    val date = str2Date(format) ?: return null
    val c = Calendar.getInstance()
    c.time = date
    return c
}

/**
 * 时间字符串转时间戳
 * @return 时间转换时间戳
 */
@SuppressLint("SimpleDateFormat")
fun String?.strToTime(pattern: String? = FORMAT_YMDHMS): Long {
    val dateFormat = SimpleDateFormat(pattern)
    var date = Date()
    try {
        date = dateFormat.parse(this!!)!!
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return date.time
}

/**
 * Calendar转时间字符串
 * @return 时间字符串
 */
@SuppressLint("SimpleDateFormat")
fun Calendar.calendar2Str(format: String = FORMAT_YMDHMS): String { // yyyy-MM-dd HH:mm:ss
    val sdf = SimpleDateFormat(format)
    return sdf.format(this)
}

/**
 * 在日期上增加数个整年
 *
 * @param n    要增加的年数
 * @return 增加数个整年
 */
fun Calendar.addYear(n: Int): Calendar {
    val calendar = this.clone() as Calendar
    calendar.add(Calendar.YEAR, n)
    return calendar
}

/**
 * 在日期上增加数个整月
 *
 * @param n    要增加的月数
 * @return 增加数个整月
 */
fun Calendar.addMonth(n: Int): Calendar {
    val calendar = this.clone() as Calendar
    calendar.add(Calendar.MONTH, n)
    return calendar
}

/**
 * 在日期上增加天数
 *
 * @param n    要增加的天数
 * @return 增加之后的天数
 */
fun Calendar.addDay(n: Int): Calendar {
    val calendar = this.clone() as Calendar
    calendar.add(Calendar.DATE, n)
    return calendar
}

/**
 * 获取距现在某一小时的时刻
 *
 * @param h      距现在的小时 例如：h=-1为上一个小时，h=1为下一个小时
 * @return 获取距现在某一小时的时刻
 */
fun Calendar.addHour(h: Int): Calendar {
    val calendar = this.clone() as Calendar
    calendar.time.time = calendar.time.time + h * 60 * 60 * 1000
    return calendar
}

/**
 * 获取某年0点时间戳
 */
fun Calendar.getYearZeroTimeInMillis(): Long {
    val calendar = this.clone() as Calendar
    calendar.add(Calendar.YEAR, 0)
    calendar.add(Calendar.DATE, 0)
    calendar.add(Calendar.MONTH, 0)
    calendar[Calendar.DAY_OF_YEAR] = 1
    calendar[Calendar.HOUR] = 0
    calendar[Calendar.MINUTE] = 0
    calendar[Calendar.SECOND] = 0
    return calendar.timeInMillis
}

/**
 * 获取某月0点时间戳
 */
fun Calendar.getMonthZeroTimeInMillis(): Long {
    val calendar = this.clone() as Calendar
    calendar.add(Calendar.YEAR, 0)
    calendar.add(Calendar.MONTH, 0)
    calendar[Calendar.DAY_OF_MONTH] = 1 // 设置为1号,当前日期既为本月第一天
    calendar[Calendar.HOUR_OF_DAY] = 0
    calendar[Calendar.MINUTE] = 0
    calendar[Calendar.SECOND] = 0
    return calendar.timeInMillis
}

/**
 * 获取某天0点时间戳
 */
fun Calendar.getDayZeroTimeInMillis(): Long {
    val calendar = this.clone() as Calendar
    calendar[Calendar.HOUR_OF_DAY] = 0
    calendar[Calendar.MINUTE] = 0
    calendar[Calendar.SECOND] = 0
    return calendar.timeInMillis
}

/**
 * 功能描述：返回年
 *
 * @return 返回年份
 */
fun Calendar.getYear(): Int {
    return get(Calendar.YEAR)
}

/**
 * 功能描述：返回月
 *
 * @return 返回月份
 */
fun Calendar.getMonth(): Int {
    return get(Calendar.MONTH) + 1
}

/**
 * 功能描述：返回日
 *
 * @return 返回日份
 */
fun Calendar.getDay(): Int {
    return get(Calendar.DAY_OF_MONTH)
}

/**
 * 返回星期
 *
 * @return 返回星期
 */
fun Calendar.getWeek(): Int {
    return get(Calendar.DAY_OF_WEEK)
}

/**
 * 获取星期几
 */
fun Calendar.getWeekStr(): String {
    return when (getWeek()) {
        1 -> "星期日"
        2 -> "星期一"
        3 -> "星期二"
        4 -> "星期三"
        5 -> "星期四"
        6 -> "星期五"
        7 -> "星期六"
        else -> ""
    }
}

/**
 * 功能描述：返回小时
 *
 * @return 返回小时
 */
fun Calendar.getHour(): Int {
    return get(Calendar.HOUR_OF_DAY)
}

/**
 * 功能描述：返回分
 *
 * @return 返回分钟
 */
fun Calendar.getMinute(): Int {
    return get(Calendar.MINUTE)
}

/**
 * 返回秒钟
 *
 * @return 返回秒钟
 */
fun Calendar.getSecond(): Int {
    return get(Calendar.SECOND)
}

/**
 * 按用户格式字符串距离今天的天数
 *
 * @param endDate   结束时间,默认获取到当前时间
 * @return 按用户格式字符串距离今天的天数
 */
fun Calendar.countDays(endDate: Calendar = Calendar.getInstance()): Int {
    val t = endDate.time.time
    val t1 = this.time.time
    return (t / 1000 - t1 / 1000).toInt() / 3600 / 24
}

/**
 * description: 按月返回所有日期
 *
 * @param endDate   结束时间,默认获取到当前时间
 */
fun Calendar.groupDateByMonth(endDate: Calendar = Calendar.getInstance()): MutableMap<String, Int> {
    val datesByMonth: MutableMap<String, Int> = HashMap()
    val currentDate = this.clone() as Calendar
    while (currentDate.before(endDate) || currentDate == endDate) {
        val monthKey: String =
            currentDate[Calendar.YEAR].toString() + "-" + (currentDate[Calendar.MONTH] + 1)
        datesByMonth[monthKey] = (datesByMonth[monthKey] ?: 0) + 1
        currentDate.add(Calendar.DAY_OF_MONTH, 1)
    }
    return datesByMonth
}