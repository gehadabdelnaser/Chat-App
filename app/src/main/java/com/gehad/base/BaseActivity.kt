package com.gehad.base

import android.app.ProgressDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.gehad.chat.R

abstract class BaseActivity<T:ViewDataBinding , V:BaseViewModel<*>>:AppCompatActivity() {

    lateinit var activity:AppCompatActivity
    lateinit var viewDataBinding: T
    lateinit var viewModel: V

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity=this

        viewDataBinding=DataBindingUtil.setContentView(this,getLayoutId())
        viewModel=generateViewModel()
        viewModel.showMessage.observe(this, {
            if(it !=null){
            showMessage("",it,getString(R.string.ok),
                { dialogInterface, _ ->
                    dialogInterface.dismiss()},
                null,null,true)
            }
        })

        viewModel.showLoading.observe(this, {
            if (it){
                showLoadingDialog(getString(R.string.loading))
            }else{
                hideLoadingDialog()
            }
        })
    }

    abstract fun getLayoutId():Int
    abstract fun generateViewModel():V

    fun showMessage(title:String?,message:String,
                    posActionName:String?,
                    posAction:DialogInterface.OnClickListener?,
                    negActionName:String?,
                    negAction:DialogInterface.OnClickListener?,
                    isCancelable:Boolean){

        val dailog=AlertDialog.Builder(this)
        dailog.setTitle(title)
        dailog.setMessage(message)
        dailog.setPositiveButton(posActionName,posAction)
        dailog.setNegativeButton(negActionName,negAction)
        dailog.setCancelable(isCancelable)
        dailog.show()
    }

    fun showMessage(title:Int?,message:Int,
                    posActionName:Int?,
                    posAction:DialogInterface.OnClickListener?,
                    negActionName:Int?,
                    negAction:DialogInterface.OnClickListener?,
                    isCancelable:Boolean){

        val dailog=AlertDialog.Builder(this)
        if(title!=null )
            dailog.setTitle(title)
        dailog.setMessage(message)
        if(posActionName!=null)
            dailog.setPositiveButton(posActionName,posAction)
        if(negActionName!=null)
            dailog.setNegativeButton(negActionName,negAction)
        dailog.setCancelable(isCancelable)
        dailog.show()
    }

    fun hideLoadingDialog(){
        progressDialog?.dismiss()

    }
    private var progressDialog: ProgressDialog? =null

    fun showLoadingDialog(loadingMessage:String?):ProgressDialog?{
        progressDialog= ProgressDialog(this)
        progressDialog?.setMessage(loadingMessage)
        progressDialog?.setCancelable(false)
        progressDialog?.show()
        return progressDialog
    }
}