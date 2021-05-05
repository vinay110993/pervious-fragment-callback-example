package com.example.fragmentcallbackexample

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.fragmentcallbackexample.databinding.FragmentOneLayoutBinding

const val RECEIVER_FILTER = "receiver"

class FragmentOne: Fragment(){

    private var binding: FragmentOneLayoutBinding ?= null
    private var viewModel : MainViewModel ?= null

    private val isCallTypeViewModel : Boolean by lazy {
        (activity as? MainActivity)?.activeCallBackType == CallbackType.VIEW_MODEL
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_one_layout, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.textview?.text = getString(R.string.fragment_one_text)

        binding?.btnFragTwo?.setOnClickListener {
            addFragment(FragmentTwo())
        }

        viewModel = if(isCallTypeViewModel) (activity as? MainActivity)?.viewModel else null

        viewModel?.observerOnString()?.observe(viewLifecycleOwner, Observer {
            binding?.textview?.text = it
        })
    }



    private fun addFragment(fragment: Fragment){
        val ft = activity?.supportFragmentManager?.beginTransaction()
        ft?.add(R.id.container, fragment)?.addToBackStack(fragment.tag)?.commit()
    }

    override fun onResume() {
        super.onResume()
        if(!isCallTypeViewModel) {
            context?.localBroadCastReceiver()?.registerReceiver(receiver, IntentFilter(RECEIVER_FILTER))
        }
    }

    override fun onPause() {
        super.onPause()
        context?.localBroadCastReceiver()?.unregisterReceiver(receiver)
    }

    private val receiver: BroadcastReceiver by lazy {
        object: BroadcastReceiver(){
            override fun onReceive(context: Context?, intent: Intent?) {
                val data = intent?.getStringExtra("data")
                binding?.textview?.text = data
            }
        }
    }

}