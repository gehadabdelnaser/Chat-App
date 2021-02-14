package com.gehad.base

import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import androidx.fragment.app.Fragment

open class BaseFragment :Fragment() {

    lateinit var activity: BaseActivity<*,*>
    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity=context as BaseActivity<*,*>
    }

    fun showMessage(title:String?,message:String,
                    posActionName:String?,
                    posAction:DialogInterface.OnClickListener?,
                    negActionName:String?,
                    negAction:DialogInterface.OnClickListener?,
                    isCancelable:Boolean){

      activity.showMessage(title, message, posActionName,
          posAction, negActionName, negAction, isCancelable)
    }

    fun showMessage(title:Int?,message:Int,
                    posActionName:Int?,
                    posAction:DialogInterface.OnClickListener?,
                    negActionName:Int?,
                    negAction:DialogInterface.OnClickListener?,
                    isCancelable:Boolean){

        activity.showMessage(title, message, posActionName,
            posAction, negActionName, negAction, isCancelable)
    }
    fun hideLoadingDialog(){
        activity.hideLoadingDialog()
    }
    fun showLoadingDialog(loadingMessage:String?): ProgressDialog?{
        return activity.showLoadingDialog(loadingMessage)
    }
}