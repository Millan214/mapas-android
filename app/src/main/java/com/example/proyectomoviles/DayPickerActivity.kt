package com.example.proyectomoviles

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_day_picker.*
import java.util.*

class DayPickerActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{

    var day = 0; var month = 0; var year = 0; var hour = 0; var minute = 0
    var savedDay = 0; var savedMonth = 0; var savedYear = 0; var savedHour = 0; var savedMinute = 0
    val TAG = "TAG"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_day_picker)

        getDateTimeCalendar()

        mostrarPares()

        molestarPorConsola()

        dpa_btn_verificar.setOnClickListener {
            getDateTimeCalendar()
            DatePickerDialog(this,this,year,month,day).show()
            TimePickerDialog(this,this,hour,minute,true).show()
        }

        dpa_btn_entrar.setOnClickListener {
            if (allForZero()){
                val intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
            }else{
                Log.d("tag","Y:$savedYear Month:$savedMonth Day:$savedDay hour:$savedHour min:$savedMinute")
                Toast.makeText(this,"No estás verificado",Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun molestarPorConsola() {
        Thread{
            while (true){
                Log.i(TAG,"a")
                Thread.sleep(500)
            }
        }.start()
    }

    private fun mostrarPares() {
        val numerosPares = Array(10,{it*2})
        var todo = ""
        numerosPares.forEach {it:Int ->
            todo+=" $it "
        }
        tvEntrada.text = todo
    }

    private fun allForZero(): Boolean {
        if (1 == savedDay && 1 == savedMonth && 1900 == savedYear && 1 == savedHour && 1 == savedMinute){
            return true
        }
        return false
    }

    private fun getDateTimeCalendar() {
        val cal = Calendar.getInstance()
        day = cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.MONTH)
        year = cal.get(Calendar.YEAR)
        hour = cal.get(Calendar.HOUR)
        minute = cal.get(Calendar.MINUTE)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        savedDay = dayOfMonth
        savedMonth = month
        savedYear = year
        getDateTimeCalendar()
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        savedHour = hourOfDay
        savedMinute = minute
        getDateTimeCalendar()
    }
}