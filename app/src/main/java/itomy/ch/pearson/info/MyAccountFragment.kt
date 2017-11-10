package itomy.ch.pearson.info

import android.support.v4.app.Fragment
import android.support.v7.app.ActionBar
import android.view.View
import itomy.ch.pearson.R
import itomy.ch.pearson.base.BaseFragment
import itomy.ch.pearson.databinding.FragmentMyAccountBinding

class MyAccountFragment : BaseFragment<FragmentMyAccountBinding>(), View.OnClickListener {

    private lateinit var viewBinding: FragmentMyAccountBinding

    companion object {
        fun getInstance(): Fragment = MyAccountFragment()
    }

    override fun getContentView(): Int = R.layout.fragment_my_account

    override fun setupBindingData(viewBinding: FragmentMyAccountBinding) {
        super.setupBindingData(viewBinding)
        this.viewBinding = viewBinding
        viewBinding.signOutCallback = this
//        todo make query
//        viewBinding.user = User()
    }

    override fun onClick(p0: View?) {
        if (p0?.id == R.id.buttonSignOut) {
//            todo logout query + go to first activity

        }
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun styleActionBar(actionBar: ActionBar?) {
        super.styleActionBar(actionBar)
        getActionBar()?.title = getString(R.string.fragment_my_account_title)
    }
}