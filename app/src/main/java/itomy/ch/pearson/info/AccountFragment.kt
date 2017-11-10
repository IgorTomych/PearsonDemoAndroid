package itomy.ch.pearson.info

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBar
import android.view.View
import android.widget.Toast
import itomy.ch.pearson.R
import itomy.ch.pearson.base.BaseFragment
import itomy.ch.pearson.databinding.FragmentAccountBinding
import itomy.ch.pearson.model.util.Status

class AccountFragment : BaseFragment<FragmentAccountBinding>(), View.OnClickListener {

    private lateinit var viewBinding: FragmentAccountBinding
    private val viewModel by lazy {
        ViewModelProviders.of(this, PearsonViewModelFactory(context!!)).get(CoursesViewModel::class.java)
    }
    private var logoutListener: LogoutListener? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        logoutListener = context as LogoutListener
    }

    companion object {
        fun getInstance(): Fragment = AccountFragment()
    }

    override fun getContentView(): Int = R.layout.fragment_account

    override fun setupBindingData(viewBinding: FragmentAccountBinding) {
        super.setupBindingData(viewBinding)
        this.viewBinding = viewBinding
        viewBinding.signOutCallback = this
        loadUser()
    }

    override fun onClick(p0: View?) {
        if (p0?.id == R.id.buttonSignOut) {
            viewModel.logout()
            logoutListener?.onLogout()
        }
    }

    override fun styleActionBar(actionBar: ActionBar?) {
        super.styleActionBar(actionBar)
        getActionBar()?.title = getString(R.string.fragment_my_account_title)
    }

    private fun loadUser() {
        viewModel.loadUser().observe(this, Observer {
            viewBinding.status = it?.status
            if (it?.status == Status.SUCCESS) {
                viewBinding.user = it.data
            } else if (it?.status == Status.ERROR) {
                val message = it.message
                if (message != null && message.contains("401")) {
                    viewModel.logout()
                    logoutListener?.onLogout()
                } else {
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == SIGN_IN_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            loadUser()
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}

interface LogoutListener {
    fun onLogout()
}
