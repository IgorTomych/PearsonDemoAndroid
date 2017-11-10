package itomy.ch.pearson.signIn

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import android.widget.TextView
import itomy.ch.pearson.R
import itomy.ch.pearson.base.BaseFragment
import itomy.ch.pearson.databinding.FragmentSignInBinding
import itomy.ch.pearson.info.InfoActivity

private const val USERNAME = "USERNAME_BUNDLE_KEY"

class SignInFragment : BaseFragment<FragmentSignInBinding>(), View.OnClickListener {

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
//            todo query login
            startActivity(InfoActivity.getLaunchIntent(activity!!))
        }
    }
}