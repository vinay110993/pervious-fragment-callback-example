package com.example.fragmentcallbackexample

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.fragmentcallbackexample.databinding.FargmentTwoLayoutBinding

class FragmentTwo: Fragment(){

    private var binding: FargmentTwoLayoutBinding ?= null
    private var viewModel : MainViewModel ?= null

    private val isCallTypeViewModel : Boolean by lazy {
        (activity as? MainActivity)?.activeCallBackType == CallbackType.VIEW_MODEL
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fargment_two_layout, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = if(isCallTypeViewModel) (activity as? MainActivity)?.viewModel else null

        binding?.btnFragUpdate?.setOnClickListener {

            if(isCallTypeViewModel){
                viewModel?.updateString("Value updated via LiveData :)")
            } else {
                val intent= Intent().apply {
                    action = RECEIVER_FILTER
                    putExtra("data", "Value updated via Receiver :)")
                }
                context?.localBroadCastReceiver()?.sendBroadcast(intent)
            }
        }
    }

}