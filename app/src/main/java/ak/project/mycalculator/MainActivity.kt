package ak.project.mycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private var tvInput: TextView?=null
    var initialZero: Boolean=true
    var lastNumaric : Boolean=true
    var lastDot: Boolean=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvInput=findViewById(R.id.tvInput)

    }

    fun onDigit(view: View) {
        if (initialZero){
            tvInput?.text=""
            initialZero=false
        }
        tvInput?.append((view as Button).text)
        lastNumaric=true
    }
    fun onZero(view: View){
        if(!initialZero)
            tvInput?.append("0")
    }

    fun onClear(view: View) {
        tvInput?.text="0"
        initialZero=true
        lastNumaric=true
        lastDot=false
    }

    fun onDecimal(view: View) {
        if(lastNumaric && !lastDot){
            tvInput?.append(".")
            initialZero=false
            lastNumaric=false
            lastDot=true
        }
    }

    fun onOperator(view: View){
            tvInput?.text?.let {
                if (lastNumaric && !isOperatorAdded(it.toString()))
                    tvInput?.append((view as Button).text)
                lastNumaric= false
                lastDot=false
            }
    }
    fun onClickMinus(view: View)
    {
        if(initialZero){
            tvInput?.text= (view as Button).text
            initialZero=false
        }
        else onOperator(view)
    }

    private fun isOperatorAdded(value:String):Boolean{
        return if (value.startsWith("-")) false
            else{
                value.contains("/")
                        || value.contains("*")
                        || value.contains("+")
                        || value.contains("-")
            }
    }

    fun onEqual(view: View){
        if (lastNumaric)
        {
            var tvValue= tvInput?.text.toString()
            var prifix =""
            try {
                if (tvValue.startsWith("-")){
                    prifix="-"
                    tvValue=tvValue.substring(1)}
                if(tvValue.contains("-"))
                {
                    val splitValue = tvValue.split("-")
                    var one= splitValue[0]
                    var two = splitValue[1]
                    if (prifix.isNotEmpty()){
                        one = prifix+ one
                    }
                    tvInput?.text=removeAfterDot((one.toDouble()-two.toDouble()).toString())
                }

                if(tvValue.contains("+"))
                {
                    val splitValue = tvValue.split("+")
                    var one= splitValue[0]
                    var two = splitValue[1]
                    if (prifix.isNotEmpty()){
                        one = prifix+ one
                    }
                    tvInput?.text=removeAfterDot((one.toDouble()+two.toDouble()).toString())

                }

                if(tvValue.contains("*"))
                {
                    val splitValue = tvValue.split("*")
                    var one= splitValue[0]
                    var two = splitValue[1]
                    if (prifix.isNotEmpty()){
                        one = prifix+ one
                    }
                    tvInput?.text=removeAfterDot((one.toDouble()*two.toDouble()).toString())

                }

                if(tvValue.contains("/"))
                {
                    val splitValue = tvValue.split("/")
                    var one= splitValue[0]
                    var two = splitValue[1]
                    if (prifix.isNotEmpty()){
                        one = prifix+ one
                    }
                    tvInput?.text=removeAfterDot((one.toDouble()/two.toDouble()).toString())

                }
            }
            catch (e:ArithmeticException){
                e.printStackTrace()
            }            }
    }
    private fun removeAfterDot(result :String): String {
        var value =result
        if(result.contains(".0"))
            value= (result.substring(0,result.length-2))
        return value
    }

}