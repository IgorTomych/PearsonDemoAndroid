package itomy.ch.pearson.info

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import com.squareup.picasso.Picasso
import itomy.ch.pearson.R
import itomy.ch.pearson.databinding.ItemCourseBinding
import itomy.ch.pearson.model.Course

class CoursesAdapter(private val courses: List<Course>, var listener: OnItemClickListener) : RecyclerView.Adapter<CoursesAdapter.ItemHolder>() {

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_course
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, viewType, parent, false)
        return ItemHolder(binding as ItemCourseBinding)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val course = courses[holder.adapterPosition]
        holder.binding.course = course
        Picasso.with(holder.itemView.context).load(course.thumbnail).resize(400, 300).into(holder.imageView)
        holder.itemView.setOnClickListener { v ->
            listener.onItemClickListener(v, holder.layoutPosition)
        }
    }

    override fun getItemCount(): Int = courses.size

    inner class ItemHolder(val binding: ItemCourseBinding) : RecyclerView.ViewHolder(binding.root) {
        val imageView: ImageView = binding.imageView
    }

    interface OnItemClickListener {
        fun onItemClickListener(v: View, pos: Int)
    }
}