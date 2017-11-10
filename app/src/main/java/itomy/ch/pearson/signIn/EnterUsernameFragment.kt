package itomy.ch.pearson.signIn

import android.support.v4.app.Fragment
import android.view.View
import android.widget.TextView
import itomy.ch.pearson.R
import itomy.ch.pearson.base.BaseFragment
import itomy.ch.pearson.databinding.FragmentEnterUsernameBinding

class EnterUsernameFragment : BaseFragment<FragmentEnterUsernameBinding>(), View.OnClickListener {

    private lateinit var viewBinding: FragmentEnterUsernameBinding

    companion object {
        fun getInstance(): Fragment = EnterUsernameFragment()
    }

    override fun getContentView(): Int = R.layout.fragment_enter_username

    override fun setupBindingData(viewBinding: FragmentEnterUsernameBinding) {
        super.setupBindingData(viewBinding)
        this.viewBinding = viewBinding
        viewBinding.continueCallback = this
        getBaseActivity()?.getToolbar()?.findViewById<TextView>(R.id.backTextView)?.visibility = View.INVISIBLE
    }

    override fun onClick(p0: View?) {
        if (p0?.id == R.id.continueButton) {
            startSignInFragment()
        }
    }

    private fun startSignInFragment() {
        getBaseActivity()?.startFragment(SignInFragment.getInstance(viewBinding.usernameEditText.text.toString()), true)
    }
}