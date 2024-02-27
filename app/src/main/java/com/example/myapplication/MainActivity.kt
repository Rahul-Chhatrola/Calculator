package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var workingsTV: TextView
    private lateinit var resultTV: TextView
    private lateinit var buttonAC: Button
    private lateinit var buttonDel: Button
    private lateinit var buttonDivision: Button
    private lateinit var buttonOne: Button
    private lateinit var buttonTwo: Button
    private lateinit var buttonThree: Button
    private lateinit var buttonFour: Button
    private lateinit var buttonFive: Button
    private lateinit var buttonSix: Button
    private lateinit var buttonSeven: Button
    private lateinit var buttonEight: Button
    private lateinit var buttonNine: Button
    private lateinit var buttonZero: Button
    private lateinit var buttonMultiply: Button
    private lateinit var buttonPlus: Button
    private lateinit var buttonMinus: Button
    private lateinit var buttonDot: Button
    private lateinit var buttonEqualsTo: Button


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        workingsTV = findViewById(R.id.workingsTV)
        resultTV = findViewById(R.id.resultTV)
        buttonOne = findViewById(R.id.buttonOne)
        buttonTwo = findViewById(R.id.buttonTwo)
        buttonThree = findViewById(R.id.buttonThree)
        buttonFour = findViewById(R.id.buttonFour)
        buttonFive = findViewById(R.id.buttonFive)
        buttonSix = findViewById(R.id.buttonSix)
        buttonSeven = findViewById(R.id.buttonSeven)
        buttonEight = findViewById(R.id.buttonEight)
        buttonNine = findViewById(R.id.buttonNine)
        buttonZero = findViewById(R.id.buttonZero)
        buttonAC = findViewById(R.id.buttonAC)
        buttonDel = findViewById(R.id.buttonDel)
        buttonDivision = findViewById(R.id.buttonDivision)
        buttonMultiply = findViewById(R.id.buttonMultiply)
        buttonPlus = findViewById(R.id.buttonPlus)
        buttonMinus = findViewById(R.id.buttonMinus)
        buttonDot = findViewById(R.id.buttonDot)
        buttonEqualsTo = findViewById(R.id.buttonEqualsTo)

        buttonAC.setOnClickListener {
            workingsTV.text = ""
            resultTV.text = ""
        }

        buttonOne.setOnClickListener {
            workingsTV.append("1")
        }
        buttonTwo.setOnClickListener {
            workingsTV.append("2")
        }
        buttonThree.setOnClickListener {
            workingsTV.append("3")
        }
        buttonFour.setOnClickListener {
            workingsTV.append("4")
        }
        buttonFive.setOnClickListener {
            workingsTV.append("5")
        }
        buttonSix.setOnClickListener {
            workingsTV.append("6")
        }
        buttonSeven.setOnClickListener {
            workingsTV.append("7")
        }
        buttonEight.setOnClickListener {
            workingsTV.append("8")
        }
        buttonNine.setOnClickListener {
            workingsTV.append("9")
        }
        buttonZero.setOnClickListener {
            workingsTV.append("0")
        }
        buttonDot.setOnClickListener {
            workingsTV.append(".")
        }
        buttonDel.setOnClickListener {
            val length = workingsTV.length()
            if (length > 0) {
                workingsTV.text = workingsTV.text.subSequence(0, length-1)
            }
        }
        buttonPlus.setOnClickListener {
            workingsTV.append("+")
        }
        buttonMinus.setOnClickListener {
            workingsTV.append("_")
        }
        buttonDivision.setOnClickListener {
            workingsTV.append("/")
        }
        buttonMultiply.setOnClickListener {
            workingsTV.append("*")
        }
        buttonEqualsTo.setOnClickListener {
            workingsTV.text = calculateResults()
        }


    }

    private fun calculateResults(): String {
        val digitsOperators = digitsOperators()
        if(digitsOperators.isEmpty()) return ""

        val timesDivision = timesDivisionCalculate(digitsOperators)
        if(timesDivision.isEmpty()) return ""

        val result = addSubtractCalculate(timesDivision)
        return result.toString()
    }
    private fun addSubtractCalculate(passedList: MutableList<Any>): Float
    {
        var result = passedList[0] as Float

        for(i in passedList.indices)
        {
            if(passedList[i] is Char && i != passedList.lastIndex)
            {
                val operator = passedList[i]
                val nextDigit = passedList[i + 1] as Float
                if (operator == '+')
                    result += nextDigit
                if (operator == '-')
                    result -= nextDigit
            }
        }

        return result
    }
    private fun timesDivisionCalculate(passedList: MutableList<Any>): MutableList<Any>
    {
        var list = passedList
        while (list.contains('*') || list.contains('/'))
        {
            list = calcTimesDiv(list)
        }
        return list
    }
    private fun calcTimesDiv(passedList: MutableList<Any>): MutableList<Any>
    {
        val newList = mutableListOf<Any>()
        var restartIndex = passedList.size

        for(i in passedList.indices)
        {
            if(passedList[i] is Char && i != passedList.lastIndex && i < restartIndex)
            {
                val operator = passedList[i]
                val prevDigit = passedList[i - 1] as Float
                val nextDigit = passedList[i + 1] as Float
                when(operator)
                {
                    '*' ->
                    {
                        newList.add(prevDigit * nextDigit)
                        restartIndex = i + 1
                    }
                    '/' ->
                    {
                        newList.add(prevDigit / nextDigit)
                        restartIndex = i + 1
                    }
                    else ->
                    {
                        newList.add(prevDigit)
                        newList.add(operator)
                    }
                }
            }

            if(i > restartIndex)
                newList.add(passedList[i])
        }

        return newList
    }
    private fun digitsOperators(): MutableList<Any>
    {
        val list = mutableListOf<Any>()
        var currentDigit = ""
        for(character in workingsTV.text)
        {
            if(character.isDigit() || character == '.')
                currentDigit += character
            else
            {
                list.add(currentDigit.toFloat())
                currentDigit = ""
                list.add(character)
            }
        }

        if(currentDigit != "")
            list.add(currentDigit.toFloat())

        return list
    }

}