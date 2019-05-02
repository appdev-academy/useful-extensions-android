@file:Suppress("unused")

package academy.appdev.viewextensions

import android.graphics.Typeface
import android.support.v7.widget.AppCompatTextView
import android.text.InputType
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.TextSwitcher
import android.widget.TextView

/**
 * Created by stas on 2/1/19.
 */


/**
 * Configure textSwitcher with animation
 * @param animationDuration animation duration in millis. Equals 300 by default
 * @param init closure extension for AppCompatTextView
 */
fun TextSwitcher.prepare(animationDuration: Long = 300, init: AppCompatTextView.() -> Unit) {
    inAnimation = AnimationUtils.loadAnimation(context, android.R.anim.fade_in).apply { duration = animationDuration }
    outAnimation = AnimationUtils.loadAnimation(context, android.R.anim.fade_out).apply { duration = animationDuration }
    setFactory {
        AppCompatTextView(context).apply {
            init()
        }
    }
}

/**
 * Capitalize first letter
 */
fun EditText.capitalizeFirst() {
    inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_SENTENCES
}

/**
 * Set text typeface
 * @param path Path to asset
 */
fun TextView.setFont(path: String) {
    typeface = Typeface.createFromAsset(context.assets, path)
}
