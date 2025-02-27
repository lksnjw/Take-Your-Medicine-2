package com.example.TakeYourMedicine

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.TakeYourMedicine.viewmodel.SignUp

class Landing : AppCompatActivity() {
    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)

        handler.postDelayed({
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
            finish()
        }, 2000L)
    }
}