package com.gehad.chat.ui.register

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.gehad.chat.R
import com.gehad.base.BaseActivity
import com.gehad.chat.databinding.ActivityRegisterBinding
import com.gehad.chat.ui.home.HomeActivity

class RegisterActivity : BaseActivity<ActivityRegisterBinding,RegisterViewModel>(),Navigator{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewDataBinding.vm=viewModel
        viewModel.navigator=this
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_register
    }

    override fun generateViewModel(): RegisterViewModel {
        return ViewModelProvider(this).get(RegisterViewModel::class.java)
    }

    override fun openHomeActivity() {
        val intent=Intent(this,HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}
