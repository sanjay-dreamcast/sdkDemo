package com.diy.smartframesdk

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.Calendar

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.graphics.Rect
import android.util.Log
import android.view.MotionEvent
import android.view.WindowManager
import android.widget.EditText
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.Locale

abstract class BaseActivity : AppCompatActivity() {

    protected fun init() {
        initArguments()
        initViews()
        setupListener()
        loadData()
    }
    protected val context: Context
        get() = this
    protected interface OptionClickedListener {
        fun onBackBtnPressed()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    abstract fun initArguments()
    abstract fun initViews()
    abstract fun setupListener()
    abstract fun loadData()
    protected fun showToast(msg:String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }











}