@file:Suppress("unused")

package academy.appdev.contextextensions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Point
import android.graphics.Typeface
import android.support.v4.content.ContextCompat
import android.util.Log
import org.jetbrains.anko.windowManager

/**
 * Created by stas on 2/1/19.
 */

private val Context.screenSizePoint: Point
    get() = Point().apply { windowManager.defaultDisplay.getSize(this) }

/**
 * Screen width in pixels
 */
val Context.screenWidth: Int
    get() = screenSizePoint.x

/**
 * Screen height in pixels
 */
val Context.screenHeight: Int
    get() = screenSizePoint.y

/**
 *  replace font
 */

fun Context.overrideFont(defaultFontNameToOverride: String, customFontFileNameInAssets: String) {
    try {
        val customFontTypeface = Typeface.createFromAsset(assets, customFontFileNameInAssets)
        val defaultFontTypefaceField = Typeface::class.java.getDeclaredField(defaultFontNameToOverride)
        defaultFontTypefaceField.isAccessible = true
        defaultFontTypefaceField.set(null, customFontTypeface)
    } catch (e: Exception) {
        Log.e(
            "overrideFont",
            "Can not set custom font $customFontFileNameInAssets instead of $defaultFontNameToOverride"
        )
    }
}

fun Context.isPermissionGranted(permission: String): Boolean =
    ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED

fun Activity.replaceActivity(intent: Intent) {
    startActivity(intent)
    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    finish()
}
