package itomy.ch.pearson.info

import android.content.Context
import android.content.Intent
import android.widget.RadioGroup
import itomy.ch.pearson.R
import itomy.ch.pearson.base.BaseActivity
import itomy.ch.pearson.databinding.ActivityInfoBinding

class InfoActivity : BaseActivity<ActivityInfoBinding>(), RadioGroup.OnCheckedChangeListener {

    private lateinit var viewBinding: ActivityInfoBinding

    companion object {
        fun getLaunchIntent(context: Context): Intent = Intent(context, InfoActivity::class.java)
    }

    override fun getContentView(): Int = R.layout.activity_info

    override fun setupBindingData(viewBinding: ActivityInfoBinding) {
        super.setupBindingData(viewBinding)
        this.viewBinding = viewBinding
        viewBinding.checkedChangeCallback = this
        startMyCoursesFragment()
    }

    override fun onCheckedChanged(p0: RadioGroup?, p1: Int) {
        when (p1) {
            R.id.radioButtonHome -> startMyCoursesFragment()
            R.id.radioButtonProfile -> startMyAccountFragment()
        }
    }

    private fun startMyCoursesFragment() {
        startFragment(MyCoursesFragment.getInstance(), false)
    }

    private fun startMyAccountFragment() {
        startFragment(MyAccountFragment.getInstance(), false)
    }
}