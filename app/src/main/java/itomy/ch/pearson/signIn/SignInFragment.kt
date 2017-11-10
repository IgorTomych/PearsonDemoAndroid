package itomy.ch.pearson.signIn

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import android.widget.TextView
import android.widget.Toast
import itomy.ch.pearson.R
import itomy.ch.pearson.base.BaseFragment
import itomy.ch.pearson.databinding.FragmentSignInBinding
import itomy.ch.pearson.info.CoursesViewModel
import itomy.ch.pearson.info.PearsonViewModelFactory
import itomy.ch.pearson.model.util.Status

private const val USERNAME = "USERNAME_BUNDLE_KEY"

class SignInFragment : BaseFragment<FragmentSignInBinding>(), View.OnClickListener {

    private val viewModel by lazy {
        ViewModelProviders.of(this, PearsonViewModelFactory(context!!)).get(CoursesViewModel::class.java)
    }
    private lateinit var viewBinding: FragmentSignInBinding

    companion object {
        fun getInstance(username: String): Fragment {
            val signInFragment = SignInFragment()
            val arguments = Bundle()
            arguments.putString(USERNAME, username)
            signInFragment.arguments = arguments
            return signInFragment
        }
    }

    override fun getContentView(): Int = R.layout.fragment_sign_in

    override fun setupBindingData(viewBinding: FragmentSignInBinding) {
        super.setupBindingData(viewBinding)
        this.viewBinding = viewBinding
        viewBinding.signInCallback = this
        viewBinding.username = arguments?.getString(USERNAME)
        getBaseActivity()?.getToolbar()?.findViewById<TextView>(R.id.backTextView)?.visibility = View.VISIBLE
    }

    override fun onClick(p0: View?) {
        if (p0?.id == R.id.signInButton) {
            val authenticate = viewModel.authenticate(viewBinding.username!!.trim(), viewBinding.passwordEditText.text.toString())
            authenticate.observe(this, Observer {
                viewBinding.status = it?.status
                if (it?.status == Status.SUCCESS) finishActivityWithSuccess()
                else if (it?.status == Status.ERROR) Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
            })
        }
    }

    private fun finishActivityWithSuccess() {
        activity?.run {
            setResult(Activity.RESULT_OK)
            finish()
        }
    }
}