package com.example.fragmentcallbackexample

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager

class MainActivity : AppCompatActivity() {

    var viewModel: MainViewModel ?= null

    // selection CallbackType.RECEIVER or CallbackType.VIEW_MODEL
    var activeCallBackType = CallbackType.RECEIVER

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = if(activeCallBackType == CallbackType.VIEW_MODEL) MainViewModel() else null

        addFragment(FragmentOne())
    }

    private fun addFragment(fragment: Fragment){
        val ft = supportFragmentManager.beginTransaction()
        ft.add(R.id.container, fragment).addToBackStack(fragment.tag).commit()
    }

}

fun Context.localBroadCastReceiver() = LocalBroadcastManager.getInstance(this)

enum class CallbackType{
    RECEIVER, VIEW_MODEL
}