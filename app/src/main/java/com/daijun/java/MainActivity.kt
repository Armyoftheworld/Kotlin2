package com.daijun.java

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        GlobalScope.launch(Dispatchers.IO) {
            repeat(10) {
                kotlinx.coroutines.delay(1000L)
                println("this is in GlobalScope.launch, ${Thread.currentThread()}")
            }
        }
    }
}
