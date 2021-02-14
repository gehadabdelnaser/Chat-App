package com.gehad.chat.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.gehad.chat.R
import com.gehad.base.BaseActivity
import com.gehad.chat.databinding.ActivityLoginBinding
import com.gehad.chat.ui.home.HomeActivity
import com.gehad.chat.ui.register.RegisterActivity

class LoginActivity : BaseActivity<ActivityLoginBinding,LoginViewModel>(),Navigator {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewDataBinding.vm=viewModel
        viewModel.navigator=this

        viewModel.authUser.observe(this, Observer {
            //start home activity
            startActivity(Intent(this,HomeActivity::class.java))
            finish()
        })

    }

    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }
    override fun generateViewModel(): LoginViewModel {
        return ViewModelProvider(this).get(LoginViewModel::class.java)
    }

    override fun openRegister() {
        startActivity(Intent(this,RegisterActivity::class.java))
    }
}
