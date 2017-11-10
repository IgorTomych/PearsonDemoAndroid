package itomy.ch.pearson.base

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import itomy.ch.pearson.R

abstract class BaseActivity<in T : ViewDataBinding> : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val contentViewId = getContentView()
        if (contentViewId > 0) {
            val dataBinding = DataBindingUtil.setContentView<T>(this, contentViewId)
            setupBindingData(dataBinding)
        }
        setupToolbar()
    }

    open fun getToolbar(): Toolbar? {
        return null
    }

    open fun setupBindingData(viewBinding: T) {}

    abstract fun getContentView(): Int

    fun startFragment(fragment: Fragment, isAddToBackStack: Boolean) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.containerFrameLayout, fragment)
        if (isAddToBackStack) {
            fragmentTransaction.addToBackStack(fragment.toString())
        }
        fragmentTransaction.commit()
    }

    private fun setupToolbar() {
        val toolbar = getToolbar()
        if (toolbar != null) {
            setSupportActionBar(toolbar)
        }
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.title = null
            setupActionBar(actionBar)
        }
    }

    open fun setupActionBar(actionBar: ActionBar) {

    }
}