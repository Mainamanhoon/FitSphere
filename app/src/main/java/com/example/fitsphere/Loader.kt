package com.example.fitsphere

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager

class Loader(context: Context) : Dialog(context) {

    init {
        val params: WindowManager.LayoutParams = window!!.attributes
        params.gravity = Gravity.CENTER
        window!!.attributes = params
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setTitle(null)
        setCancelable(false)
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_layout, null)
        setContentView(view)
    }
}
