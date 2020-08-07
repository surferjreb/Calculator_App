package com.SoftCoreInc.calculator

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
//the import below allows you to use the name of the container(i.e.- button0..button9)
//Kotlin adds a casing function, calls findViewbyID,
import kotlinx.android.synthetic.main.activity_main.*


private const val STATE_PENDING_OPERATION = "PendingOperation"
private const val STATE_OPERAND1 = "Operand1"
private const val STATE_OPERAND1_STORED = "Operand1_Stored"

//Test
private const val ACTUAL_VALUE = "NewNumber"

class MainActivity : AppCompatActivity() {
//    private lateinit var result: EditText
//    private lateinit var newNumber: EditText

    // "lateinit" allows variables to be declared at a later point
//    private val displayOperation by lazy(LazyThreadSafetyMode.NONE) { findViewById<TextView>(R.id.operation) }

    //variable to hold the operands and type of calculations
    private var operand1: Double? = null
    private var operand2: Double = 0.0
    private var pendingOperation = "="

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        result = findViewById(R.id.result)
//        this.newNumber.setText("0")

//        // Data input buttons(numerical values)
//        val button0: Button = findViewById(R.id.button0)
//        val button1: Button = findViewById(R.id.button1)
//        val button2: Button = findViewById(R.id.button2)
//        val button3: Button = findViewById(R.id.button3)
//        val button4: Button = findViewById(R.id.button4)
//        val button5: Button = findViewById(R.id.button5)
//        val button6: Button = findViewById(R.id.button6)
//        val button7: Button = findViewById(R.id.button7)
//        val button8: Button = findViewById(R.id.button8)
//        val button9: Button = findViewById(R.id.button9)
//        val buttonDot: Button = findViewById(R.id.period)
//
//        //operation buttons
//        val buttonEquals = findViewById<Button>(R.id.equals)
//        val buttonPlus = findViewById<Button>(R.id.plus)
//        val buttonMinus = findViewById<Button>(R.id.minus)
//        val buttonTimes = findViewById<Button>(R.id.multiply)
//        val buttonDivide = findViewById<Button>(R.id.divide)
//        //clear input and output fields
//        val buttonClear = findViewById<Button>(R.id.clearScreen)

        //listener for calculator buttons
        val listener = View.OnClickListener { v ->
            val b = v as Button
            newNumber.append(b.text)
        }

        button0.setOnClickListener(listener)
        button1.setOnClickListener(listener)
        button2.setOnClickListener(listener)
        button3.setOnClickListener(listener)
        button4.setOnClickListener(listener)
        button5.setOnClickListener(listener)
        button6.setOnClickListener(listener)
        button7.setOnClickListener(listener)
        button8.setOnClickListener(listener)
        button9.setOnClickListener(listener)
        period.setOnClickListener(listener)

        //listener for operations
        val opListener = View.OnClickListener { v ->
            val op = (v as Button).text.toString()
            try {
                val value = newNumber.text.toString().toDouble()
                performOperation(value, op)
            } catch (e: NumberFormatException) {
                newNumber.setText("")
            }
            pendingOperation = op
            operation.text = pendingOperation

        }
        //listener for negate button
        val negateListener = View.OnClickListener { v ->
            val a = (v as Button).text.toString()
            var newNum: Float
            if (a == "NEG") {
                Log.d(ACTUAL_VALUE, "newNumber: ${newNumber.text.toString()}")
                newNum = if (newNumber.text.toString() == "") {
                    (1).toFloat()
                } else {
                    (newNumber.text.toString().toDouble().toFloat())
                }
                newNumber.setText(mult(newNum).toString())
                Log.d(ACTUAL_VALUE, "it Should be negative")

            }
        }
        //assign listener to buttons
        negate.setOnClickListener(negateListener)
        equals.setOnClickListener(opListener)
        plus.setOnClickListener(opListener)
        minus.setOnClickListener(opListener)
        multiply.setOnClickListener(opListener)
        divide.setOnClickListener(opListener)

    }

    private fun performOperation(value: Double, operation: String) {
        if (operand1 == null) {
            operand1 = value
        } else {

            if (pendingOperation == "=") {
                pendingOperation = operation
            }

            when (pendingOperation) {
                "=" -> operand1 = value
                "/" -> {
                    operand1 = if (value == 0.0) {
                        Double.NaN  // handle attempt to divide by zero
                    } else {
                        operand1!! / value
                    }
                }
                "*" -> operand1 = operand1!! * value
                "-" -> operand1 = operand1!! - value
                "+" -> operand1 = operand1!! + value
            }
        }

//        displayOperation.text = operation
        result.setText(this.operand1.toString())
        newNumber.setText("")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (operand1 != null) {
            outState.putDouble(STATE_OPERAND1, operand1!!)
            outState.putBoolean(STATE_OPERAND1_STORED, true)
        }
        outState.putString(STATE_PENDING_OPERATION, pendingOperation)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        operand1 = if (savedInstanceState.getBoolean(STATE_OPERAND1_STORED, false)) {
            savedInstanceState.getDouble(STATE_OPERAND1)
        } else {
            null
        }

        pendingOperation = savedInstanceState.getString(STATE_PENDING_OPERATION).toString()
        operation.text = pendingOperation

    }
}

private fun mult(d: Float): Double {
    return d * -1.0
}
