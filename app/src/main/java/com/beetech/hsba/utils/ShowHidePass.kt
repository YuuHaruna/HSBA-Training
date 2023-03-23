package com.beetech.hsba.utils

import android.content.Context
import android.text.method.HideReturnsTransformationMethod
import android.widget.EditText
import android.widget.ImageButton
import androidx.core.content.ContextCompat
import com.beetech.hsba.R

fun showHidePass(context: Context, editText: EditText, imageButton: ImageButton) {
    when (editText.transformationMethod) {
        HideReturnsTransformationMethod.getInstance() -> {
            editText.transformationMethod = AsteriskPasswordTransformationMethod()
            imageButton.setImageDrawable(
                (ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_show_password
                ))
            )
        }

        else -> {
            editText.transformationMethod = HideReturnsTransformationMethod.getInstance()
            imageButton.setImageDrawable(
                (ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_hide_password
                ))
            )
        }
    }
}