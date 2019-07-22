package ru.skillbranch.devintensive

import android.graphics.Color
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import ru.skillbranch.devintensive.extensions.hideKeyboard
import ru.skillbranch.devintensive.extensions.isKeyboardOpen
import ru.skillbranch.devintensive.models.Bender

class MainActivity : AppCompatActivity(), View.OnClickListener, TextView.OnEditorActionListener {

    private val statusKey:String = "STATUS"
    private val questionKey:String = "QUESTION"
    private val countErrorKey:String = "COUNT_ERROR"

    lateinit var benderImage: ImageView
    lateinit var textTxt: TextView
    lateinit var messageEt: EditText
    lateinit var sendBtn: ImageView

    lateinit var benderObj: Bender


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //benderImage = findViewById(R.id.iv_bender)
        benderImage = iv_bender
        textTxt = tv_text
        messageEt = et_message
        sendBtn = iv_send

        val status = savedInstanceState?.getString(statusKey)?: Bender.Status.NORMAL.name
        val question = savedInstanceState?.getString(questionKey)?: Bender.Question.NAME.name
        val countErr = savedInstanceState?.getInt(countErrorKey)?: 0
        benderObj = Bender(Bender.Status.valueOf(status), Bender.Question.valueOf(question),countErr)

        Log.d("M_MainActivity", "onCreate $status $question")
        val(r, g, b) = benderObj.status.color
        benderImage.setColorFilter(Color.rgb(r,g,b), PorterDuff.Mode.MULTIPLY)

        textTxt.text = benderObj.askQuestion()
        sendBtn.setOnClickListener(this)
        messageEt.setOnEditorActionListener(this)
    }

    
    override fun onRestart() {
        super.onRestart()
        Log.d("M_MainActivity", "onRestart")
    }

    override fun onStart() {
        super.onStart()
        Log.d("M_MainActivity", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("M_MainActivity", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("M_MainActivity", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("M_MainActivity", "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("M_MainActivity", "onDestroy")
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

        outState?.putString(statusKey, benderObj.status.name)
        outState?.putString(questionKey, benderObj.question.name)
        outState?.putInt(countErrorKey, benderObj.countError)
        Log.d("M_MainActivity", "onSaveInstanceState ${benderObj.status.name} ${benderObj.question.name}")
    }

    override fun onClick(v: View?) {
        if(v?.id == R.id.iv_send){
            Apply()
            Log.d("M_MainActivity", "onClick")
            Log.d("M_MainActivity", "is keyboard open: ${this.isKeyboardOpen()}")
        }
    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            Apply()
            hideKeyboard()
            Log.d("M_MainActivity", "onEditorAction")
        }
        return true
    }

    private fun Apply()
    {
        val (phrase, color) = benderObj.listenAnswer(messageEt.text.toString())
        messageEt.setText("")
        val(r,g,b) = color
        benderImage.setColorFilter(Color.rgb(r,g,b), PorterDuff.Mode.MULTIPLY)
        textTxt.text = phrase
    }

}


