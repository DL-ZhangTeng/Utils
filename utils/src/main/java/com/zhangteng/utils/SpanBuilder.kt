package com.zhangteng.utils

import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ImageSpan

class SpanBuilder(
    text: CharSequence? = "",
    vararg spans: Any = arrayOf(),
    flags: Int = Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
) : SpannableStringBuilder(text) {
    init {
        for (span in spans) {
            setSpan(span, 0, length, flags)
        }
    }

    /**
     * Add the text.
     *
     * @return this `SpanBuilder`.
     */
    fun appendText(
        text: CharSequence,
    ): SpanBuilder {
        append(text)
        return this
    }

    /**
     * Add the ImageSpan to the start of the text.
     *
     * @return this `SpanBuilder`.
     */
    fun appendSpan(
        text: CharSequence,
        imageSpan: ImageSpan?,
        flags: Int = Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    ): SpanBuilder {
        append(text)
        setSpan(imageSpan, length - text.length, length - text.length + 1, flags)
        return this
    }

    /**
     * Appends the character sequence `text` and spans `spans` over the appended part.
     *
     * @param text  the character sequence to append.
     * @param spans the object or objects to be spanned over the appended text.
     * @return this `SpanBuilder`.
     */
    fun appendSpan(
        text: CharSequence,
        vararg spans: Any,
        flags: Int = Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    ): SpanBuilder {
        append(text)
        for (span in spans) {
            setSpan(span, length - text.length, length, flags)
        }
        return this
    }

    /**
     * Sets a span object to all appearances of specified text in the spannable.
     * A new instance of a span object must be provided for each iteration
     * because it can't be reused.
     *
     * @param textToSpan Case-sensitive text to span in the current spannable.
     * @param span       the object or objects to be spanned over the appended text.
     * @return `SpanBuilder`.
     */
    fun findAndSpan(
        textToSpan: CharSequence,
        span: Any?,
        flags: Int = Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    ): SpanBuilder {
        var lastIndex = 0
        while (lastIndex != -1) {
            lastIndex = toString().indexOf(textToSpan.toString(), lastIndex)
            if (lastIndex != -1) {
                setSpan(span, lastIndex, lastIndex + textToSpan.length, flags)
                lastIndex += textToSpan.length
            }
        }
        return this
    }

    /**
     * Mark the specified range of text with the specified object.
     * The flags determine how the span will behave when text is
     * inserted at the start or end of the span's range.
     */
    fun findAndSpan(
        span: Any,
        start: Int,
        end: Int,
        flags: Int = Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    ): SpanBuilder {
        setSpan(span, start, end, flags)
        return this
    }
}