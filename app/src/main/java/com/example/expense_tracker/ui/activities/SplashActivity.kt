package com.example.expense_tracker.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.expense_tracker.databinding.ActivitySplashBinding
import com.example.expense_tracker.ui.MainActivity

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)


        supportActionBar?.hide()
//splash ekran için bir Thread oluşturuyoruz
        val background = object : Thread() {
            override fun run() {
                try {

                    Thread.sleep(6000)
                    val intent = Intent(baseContext, MainActivity::class.java)
                    startActivity(intent)
                }catch (e : Exception){
                    e.printStackTrace()
                }
            }
        }
        background.start()
    }
}