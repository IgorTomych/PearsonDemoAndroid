package itomy.ch.pearson.info

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBar
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import itomy.ch.pearson.R
import itomy.ch.pearson.base.BaseFragment
import itomy.ch.pearson.databinding.FragmentCoursesBinding
import itomy.ch.pearson.model.util.Status

class CoursesFragment : BaseFragment<FragmentCoursesBinding>() {

    private lateinit var viewBinding: FragmentCoursesBinding
    private lateinit var adapter: CoursesAdapter
    private lateinit var logoutListener: LogoutListener
    private val viewModel by lazy {
        ViewModelProviders.of(this, PearsonViewModelFactory(context!!)).get(CoursesViewModel::class.java)
    }

    companion object {
        fun getInstance(): Fragment = CoursesFragment()
    }

    override fun getContentView(): Int = R.layout.fragment_courses

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        logoutListener = context as LogoutListener
    }

    override fun setupBindingData(viewBinding: FragmentCoursesBinding) {
        super.setupBindingData(viewBinding)
        this.viewBinding = viewBinding
        setupRecyclerView()
        if (viewModel.isAuthorised()) {
            loadContent()
        }
    }

    private fun setupRecyclerView() {
        val layoutManager: RecyclerView.LayoutManager
        layoutManager = LinearLayoutManager(activity)
        viewBinding.recyclerView.layoutManager = layoutManager
        viewBinding.recyclerView.itemAnimator = DefaultItemAnimator()
        adapter = CoursesAdapter()
        viewBinding.recyclerView.adapter = adapter
    }

    override fun styleActionBar(actionBar: ActionBar?) {
        super.styleActionBar(actionBar)
        getActionBar()?.title = getString(R.string.fragment_my_courses_title)
    }

    private fun loadContent() {
        viewModel.loadCourses().observe(this, Observer {
            viewBinding.status = it?.status
            if (it?.status == Status.SUCCESS) adapter.setCourses(it.data)
            else if (it?.status == Status.ERROR) {
                val message = it.message
                if (message != null && message.contains("401")) {
                    viewModel.logout()
                    logoutListener.onLogout()
                } else {
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == SIGN_IN_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            loadContent()
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}