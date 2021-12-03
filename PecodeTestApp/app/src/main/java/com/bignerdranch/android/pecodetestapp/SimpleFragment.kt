package com.bignerdranch.android.pecodetestapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment

class SimpleFragment(val pNum: Int) : Fragment(), View.OnClickListener  {

    private lateinit var btnNotification: AppCompatButton
    private lateinit var pageNumber:TextView
    private lateinit var btnAdd: AppCompatButton
    private lateinit var btnRemove: AppCompatButton

    fun SimpleFragment() {
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_simple, container, false)
        initElems(view)
        click()
        pageNumber.text = pNum.toString()

        return view
    }

    private fun initElems(v: View?) {
        btnNotification = v!!.findViewById<AppCompatButton>(R.id.btn_notification)
        pageNumber = v!!.findViewById<TextView>(R.id.page_number)
        btnAdd = v!!.findViewById<AppCompatButton>(R.id.btn_add)
        btnRemove = v!!.findViewById<AppCompatButton>(R.id.btn_remove)
        if((activity as MainActivity?)!!.checkFragCount() <= 1){
            btnRemove.visibility = View.GONE
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_add -> {
                (activity as MainActivity?)!!.addFragment()
                if((activity as MainActivity?)!!.checkFragCount() > 1){
                    btnRemove.visibility = View.VISIBLE
                }
            }
            R.id.btn_remove -> {
                (activity as MainActivity?)!!.removeFragment()
                Log.d("checkFragCount", (activity as MainActivity?)!!.checkFragCount().toString())
                if((activity as MainActivity?)!!.checkFragCount() == 1){
                    btnRemove.visibility = View.GONE
                }
                (activity as MainActivity?)!!.updateUI()
            }
            R.id.btn_notification -> (activity as MainActivity?)!!.sendNotification()
        }
    }

    private fun click(){
        btnAdd.setOnClickListener(this)
        btnRemove.setOnClickListener(this)
        btnNotification.setOnClickListener(this)
    }
}