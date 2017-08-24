package iii_conventions

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {
    override fun compareTo(other: MyDate): Int {
        if (year != other.year) {
            return year - other.year
        }
        if (month != other.month) {
            return month - other.month
        }
        return dayOfMonth - other.dayOfMonth
    }

    operator fun rangeTo(other: MyDate): DateRange = DateRange(this, other)

    operator fun plus(timeInterval: TimeInterval): MyDate = addTimeIntervals(timeInterval, 1)

    operator fun plus(repeatedTimeInterval: RepeatedTimeInterval): MyDate = addTimeIntervals(repeatedTimeInterval.timeInterval, repeatedTimeInterval.n)
}

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR;

    operator fun times(n: Int): RepeatedTimeInterval {
        return RepeatedTimeInterval(this, n)
    }
}

class RepeatedTimeInterval(val timeInterval: TimeInterval, val n: Int)

class DateRange(val start: MyDate, val endInclusive: MyDate) {
    operator fun iterator() = DateIterator(this)
}

class DateIterator(private val dateRange: DateRange) : Iterator<MyDate> {
    var current = dateRange.start

    override fun next(): MyDate {
        val result = current
        current = current.nextDay()
        return result
    }

    override fun hasNext(): Boolean {
        return current <= dateRange.endInclusive
    }
}