package itomy.ch.pearson.info

import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBar
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import itomy.ch.pearson.R
import itomy.ch.pearson.base.BaseFragment
import itomy.ch.pearson.databinding.FragmentMyCoursesBinding
import itomy.ch.pearson.dialogs.WarningDialog
import itomy.ch.pearson.model.Course

class MyCoursesFragment : BaseFragment<FragmentMyCoursesBinding>(), CoursesAdapter.OnItemClickListener {

    private lateinit var viewBinding: FragmentMyCoursesBinding

    companion object {
        fun getInstance(): Fragment = MyCoursesFragment()
    }

    override fun getContentView(): Int = R.layout.fragment_my_courses

    override fun setupBindingData(viewBinding: FragmentMyCoursesBinding) {
        super.setupBindingData(viewBinding)
        this.viewBinding = viewBinding

//        todo api request
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val layoutManager: RecyclerView.LayoutManager
        layoutManager = LinearLayoutManager(activity)
        viewBinding.recyclerView.layoutManager = layoutManager
        viewBinding.recyclerView.itemAnimator = DefaultItemAnimator()

//        todo delete mock
        val mutableList : MutableList<Course> = arrayListOf()
        val course1 = Course("1", "name", "http://www.planwallpaper.com/static/images/desktop-year-of-the-tiger-images-wallpaper.jpg", "subject")
        val course2 = Course("1", "name1", "http://www.planwallpaper.com/static/images/desktop-year-of-the-tiger-images-wallpaper.jpg",
                "subject1")
        val course3 = Course("1", "name2", "http://www.planwallpaper.com/static/images/desktop-year-of-the-tiger-images-wallpaper.jpg",
                "subject2")
        val course4 = Course("1", "name3", "http://www.planwallpaper.com/static/images/desktop-year-of-the-tiger-images-wallpaper.jpg",
                "subject3")
        mutableList.add(course1)
        mutableList.add(course2)
        mutableList.add(course3)
        mutableList.add(course4)
//
        
        viewBinding.recyclerView.adapter = CoursesAdapter(mutableList, this)
    }

    override fun styleActionBar(actionBar: ActionBar?) {
        super.styleActionBar(actionBar)
        getActionBar()?.title = getString(R.string.fragment_my_courses_title)
    }

    override fun onItemClickListener(v: View, pos: Int) {
        val dialogFragment: DialogFragment = WarningDialog.newInstance()
        dialogFragment.show(fragmentManager, WarningDialog.TAG)
    }
}