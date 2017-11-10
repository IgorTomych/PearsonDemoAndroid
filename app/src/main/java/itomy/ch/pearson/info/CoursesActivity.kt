package itomy.ch.pearson.info

import android.app.Activity
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.widget.RadioGroup
import itomy.ch.pearson.R
import itomy.ch.pearson.base.BaseActivity
import itomy.ch.pearson.databinding.ActivityInfoBinding
import itomy.ch.pearson.signIn.SignInActivity

const val SIGN_IN_REQUEST_CODE = 0

class CoursesActivity : BaseActivity<ActivityInfoBinding>(), RadioGroup.OnCheckedChangeListener, LogoutListener {

    private lateinit var viewBinding: ActivityInfoBinding
    private val viewModel: CoursesViewModel by lazy {
        ViewModelProviders.of(this, PearsonViewModelFactory(this)).get(CoursesViewModel::class.java)
    }

    companion object {
        fun getLaunchIntent(context: Context): Intent = Intent(context, CoursesActivity::class.java)
    }

    override fun getContentView(): Int = R.layout.activity_info

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!viewModel.isAuthorised()) {
            startAuthorisation()
        }
    }

    private fun startAuthorisation() {
        startActivityForResult(SignInActivity.getLaunchIntent(this), SIGN_IN_REQUEST_CODE)
    }

    override fun setupBindingData(viewBinding: ActivityInfoBinding) {
        super.setupBindingData(viewBinding)
        this.viewBinding = viewBinding
        viewBinding.checkedChangeCallback = this
        startCoursesFragment()
    }

    override fun onCheckedChanged(p0: RadioGroup?, p1: Int) {
        when (p1) {
            R.id.radioButtonHome -> startCoursesFragment()
            R.id.radioButtonProfile -> startAccountFragment()
        }
    }

    private fun startCoursesFragment() {
        startFragment(CoursesFragment.getInstance(), false)
    }

    private fun startAccountFragment() {
        startFragment(AccountFragment.getInstance(), false)
    }

    private fun getCurrentFragment(): Fragment? {
        return supportFragmentManager.findFragmentById(R.id.containerFrameLayout)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == SIGN_IN_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                val currentFragment = getCurrentFragment()
                currentFragment?.onActivityResult(requestCode, resultCode, data)
            } else if (resultCode == Activity.RESULT_CANCELED) {
                finish()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onLogout() {
        startAuthorisation()
    }
}