package itomy.ch.pearson.signIn

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v4.app.DialogFragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.Toolbar
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.Toast
import itomy.ch.pearson.R
import itomy.ch.pearson.base.BaseActivity
import itomy.ch.pearson.databinding.ActivitySignInBinding
import itomy.ch.pearson.dialogs.WarningDialog

class SignInActivity : BaseActivity<ActivitySignInBinding>() {
    private lateinit var viewBinding: ActivitySignInBinding

    override fun getContentView(): Int = R.layout.activity_sign_in

    companion object {
        fun getLaunchIntent(context: Context): Intent = Intent(context, SignInActivity::class.java)
    }

    override fun setupBindingData(viewBinding: ActivitySignInBinding) {
        super.setupBindingData(viewBinding)
        this.viewBinding = viewBinding
        startEnterUsernameFragment()
        setupTosLink()
    }

    override fun getToolbar(): Toolbar? {
        val signInToolbarBinding = viewBinding.signInToolbar
        val toolbar = signInToolbarBinding!!.signInToolbar
        signInToolbarBinding.backTextView.setOnClickListener({ onBackPressed() })
        return toolbar
    }

    private fun startEnterUsernameFragment() {
        startFragment(EnterUsernameFragment.getInstance(), false)
    }

    private fun setupTosLink() {
        val tosColor = ContextCompat.getColor(this, R.color.colorTosText)
        val tosText = getString(R.string.terms_of_service)
        val spannableString = SpannableString(String.format(getString(R.string.activity_sign_in_terms_of_service), tosText))

        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                onTosClick()
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.color = tosColor
                ds.isUnderlineText = false
            }
        }
        val end = spannableString.length - 1
        val start = end - tosText.length
        spannableString.setSpan(clickableSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        viewBinding.termsOfServiceTextView.text = spannableString
        viewBinding.termsOfServiceTextView.movementMethod = LinkMovementMethod.getInstance()
        viewBinding.termsOfServiceTextView.highlightColor = Color.TRANSPARENT
    }

    private fun onTosClick() {
        val dialogFragment: DialogFragment = WarningDialog.newInstance()
        dialogFragment.show(supportFragmentManager, WarningDialog.TAG)
    }
}