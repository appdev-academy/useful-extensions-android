@file:Suppress("unused")

package academy.appdev.viewextensions

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.TimeInterpolator
import android.annotation.TargetApi
import android.os.Build
import android.support.v4.view.animation.FastOutSlowInInterpolator
import android.view.HapticFeedbackConstants
import android.view.View
import org.jetbrains.anko.backgroundResource
import java.util.*

/**
 * Created by stas on 2/1/19.
 */

/**
 * Get/Set views visibility.
 * true - VISIBLE
 * false - GONE
 */
var View.isVisible: Boolean
    get() = visibility == View.VISIBLE
    set(value) {
        visibility = if (value) View.VISIBLE else View.GONE
    }


/**
 * Get/Set views visibility.
 * true - VISIBLE
 * false - INVISIBLE
 */
var View.isVisibleOrInvisible: Boolean
    get() = visibility == View.VISIBLE
    set(value) {
        visibility = if (value) View.VISIBLE else View.INVISIBLE
    }

/**
 * Generate unique view id
 */
fun View.identify() {
    id = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
        View.generateViewId()
    } else {
        UUID.randomUUID().hashCode()
    }
}

fun View.disableClicks() {
    setOnTouchListener { _, _ ->
        true
    }
}

fun View.addRippleAnimation() {
    context.obtainStyledAttributes(intArrayOf(R.attr.selectableItemBackground)).apply {
        backgroundResource = getResourceId(0, 0)
        recycle()
    }
}

fun View.performHapticFeedback() = performHapticFeedback(
    HapticFeedbackConstants.VIRTUAL_KEY,
    HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING or HapticFeedbackConstants.FLAG_IGNORE_VIEW_SETTING
)

/**
 * Switch views visibility with animation
 * @param duration - animation duration in millis (default :200L)
 * @param interpolator - time interpolator (default : FastOutSlowInInterpolator)
 */
fun View.animateAppearance(
    duration: Long = 200L,
    interpolator: TimeInterpolator = FastOutSlowInInterpolator(),
    _isVisible: Boolean? = null
) {
    val wereVisible = isVisible
    if (wereVisible == _isVisible) {
        return
    }
    if (!wereVisible) {
        alpha = 0f
        isVisible = true
    }
    animate().apply {
        alpha(if (wereVisible) 0f else 1f)
        setDuration(duration)
        setInterpolator(interpolator)

        setListener(if (wereVisible) {
            object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    isVisible = false
                }
            }
        } else null)

    }.start()
}

