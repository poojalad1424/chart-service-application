package com.example.chartserviceapplication.adapter

import android.content.ContextWrapper
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

@BindingAdapter("mutableText")
fun setMutableText(view: TextView, text: MutableLiveData<String>?) {
    val parentActivity: FragmentActivity? = view.getParentActivity()
    if (parentActivity != null && text != null) {
        text.observe(parentActivity, Observer { value -> view.text = value ?: "" })
    }
}

@BindingAdapter("mutableDoubleText")
fun setMutableTextDouble(view: TextView, text: MutableLiveData<Double>?) {
    val parentActivity: FragmentActivity? = view.getParentActivity()
    if (parentActivity != null && text != null) {
        text.observe(parentActivity, Observer { value -> view.text = value.toString() ?: "" })
    }
}

fun View.getParentActivity(): FragmentActivity?{
    var context = this.context
    while (context is ContextWrapper) {
        if (context is FragmentActivity) {
            return context
        }
        context = context.baseContext
    }
    return null
}