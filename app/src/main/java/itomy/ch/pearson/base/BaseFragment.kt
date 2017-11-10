package itomy.ch.pearson.base

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class BaseFragment<in T : ViewDataBinding> : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val dataBinding = DataBindingUtil
                .inflate<T>(inflater, getContentView(), container, false)
        setupBindingData(dataBinding)
        return dataBinding.root
    }

    @LayoutRes
    protected abstract fun getContentView(): Int

    open fun setupBindingData(viewBinding: T) {
        styleActionBar(getActionBar())
    }

    protected open fun styleActionBar(actionBar: ActionBar?) {

    }

    protected fun getActionBar(): ActionBar? {
        var bar: ActionBar? = null
        val activity = getBaseActivity()
        if (activity != null) {
            bar = activity.supportActionBar
        }
        return bar
    }

    fun getBaseActivity(): BaseActivity<*>? {
        var bActivity: BaseActivity<*>? = null
        val activity = activity
        if (activity != null && activity is BaseActivity<*>) {
            bActivity = activity
        }
        return bActivity
    }
}