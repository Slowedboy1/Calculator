package com.example.calculator_11

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {


    //ประกาศตัวแปร
    private lateinit var display: TextView //แสดงตัวเลข
    private var current = "" //ค่าปัจจุบัน
    private var operator = "" //เครื่องหมายที่เราเลือก +-*/
    private var firstNumber = 0.0 //เลข1
    private var secondNumber = 0.0 //เลข2
    private var result = 0.0
    private var isNewOperation = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        display = findViewById(R.id.txtDisplay)

        val numberButton = listOf(
            R.id.btn0,
            R.id.btn1,
            R.id.btn2,
            R.id.btn3,
            R.id.btn4,
            R.id.btn5,
            R.id.btn6,
            R.id.btn7,
            R.id.btn8,
            R.id.btn9,
        )

        for (id in numberButton) {
            val btn = findViewById<Button>(id)
            btn.setOnClickListener { numberClicked(btn.text.toString()) }
        }

        findViewById<Button>(R.id.btnPlus).setOnClickListener { operatorClicked("+") }
        findViewById<Button>(R.id.btnMinus).setOnClickListener { operatorClicked("-") }
        findViewById<Button>(R.id.btnx).setOnClickListener { operatorClicked("*") }
        findViewById<Button>(R.id.btnDiv).setOnClickListener { operatorClicked("/") }
        findViewById<Button>(R.id.btnMod).setOnClickListener {
            if (current.isNotEmpty()) {
                val value = current.toDouble() / 100.0
                current = value.toString()
                display.text = current
            }
        }

        //ปุ่มเครื่องหมายพิเศษใส่ไข่
        findViewById<Button>(R.id.btnEqual).setOnClickListener { calculaterResult() }
        findViewById<Button>(R.id.btnClear).setOnClickListener { clearAll() }
        findViewById<Button>(R.id.btnDp).setOnClickListener { addDot() }
        findViewById<Button>(R.id.btnmp).setOnClickListener { toggleSing() }
        findViewById<Button>(R.id.btnDelete).setOnClickListener { backSpace() }
    }
    private fun numberClicked(value : String){
        if (isNewOperation){//ถ้าเป้นการเริ่มใหม่ให้้ล้างหน้าจอก่อน
            display.text = ""
            isNewOperation = false
        }
        current += value // เพิ่มค่าตัวเลขที่ต่อท้าย
        display.text=current //ให้แสดงค่าบนหน้าจอ
    }
    //เมื่อกดเครือ่งหมาย
    private fun operatorClicked(op: String){
        if (current.isNotEmpty()){
            firstNumber = current.toDouble()
            operator = op
            current = ""
        }
    }
    //เมื่อกดปุ่ม = คำนวนรวม
    private fun calculaterResult() {
        if (current.isNotEmpty()) {
            secondNumber = current.toDouble()
            result = when (operator) {
                "+" -> firstNumber + secondNumber
                "-" -> firstNumber - secondNumber
                "*" -> firstNumber * secondNumber
                "/" -> if (secondNumber != 0.0) firstNumber / secondNumber else Double.NaN
                "%" -> if (secondNumber != 0.0) firstNumber / secondNumber else Double.NaN
                else -> 0.0
            }
            display.text = if (result.isNaN()) "can not devided by zero" else result.toString()
            current = result.toString()
            isNewOperation = true
        }
    }
    private fun clearAll(){
        current = ""
        operator = ""
        firstNumber = 0.0
        secondNumber = 0.0
        result = 0.0
        display.text = "0"
        isNewOperation = true

    }
    //เพิ่มจุด
    private  fun addDot(){
        if (!current.contains(".")){
            if(current.isEmpty()) current ="0"
            current += "."
            display.text = current
        }
    }
    //ปุ่มเครื่องหมาย
    private fun toggleSing(){
        if(current.isNotEmpty() && current != "0") {
            current = if (current.startsWith(".")){
                current.substring(1)
        }else {
            "-$current"
        }
        display.text = current
        }
    }
    //ปุ่มตัว
    private fun backSpace(){
        if (isNewOperation) return

        if (current.isNotEmpty()){
            current = current.dropLast( n = 1) //ตัดตัวสุดท้าย

            if (current == "-" || current.isEmpty()) {
                current = ""
                display.text = "0"
            }else{
                display.text = current
            }
        }
    }
}