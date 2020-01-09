package com.example.test.ui.tutor


import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.core.net.toUri
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.test.Class.Course
//import com.example.test.CourseContent

import com.example.test.R
import com.example.test.ui.course.CourseContent
import com.example.test.ui.course.CourseViewModel
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.course_list_layout.*
import kotlinx.android.synthetic.main.course_list_layout.view.*
import kotlinx.android.synthetic.main.fragment_tutor_course.view.*

/**
 * A simple [Fragment] subclass.
 */
class TutorCourse : Fragment() {


    lateinit var mRecylerView : RecyclerView
    lateinit var mDatabase : DatabaseReference
    private lateinit var courseViewModel: CourseViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_tutor_course, container, false)

        view.btnAddCourse.setOnClickListener {
            val intent = Intent(activity, AddCourse ::class.java)
            startActivity(intent)
        }

        mRecylerView = view.findViewById(R.id.tutor_course_recycleView)
        mRecylerView.setLayoutManager(GridLayoutManager(context, 2))
        mDatabase = FirebaseDatabase.getInstance().getReference("Course")

        logRecyclerView()

        return view
    }

    private fun logRecyclerView() {

        var FirebaseRecyclerAdapter = object : FirebaseRecyclerAdapter<Course, CourseViewHolder>(

            Course::class.java,
            R.layout.course_list_layout,
            CourseViewHolder::class.java,
            mDatabase
        ) {

            override fun populateViewHolder(viewHolder : CourseViewHolder?, model: Course?, position:Int) {
                viewHolder?.itemView?.test?.text = model?.title
                viewHolder?.itemView?.courseID?.text = model?.id
                Picasso.with(context).load(model?.image?.toUri()).into(viewHolder?.itemView?.courseImg)

            }
        }

        mRecylerView.adapter = FirebaseRecyclerAdapter
    }

    class CourseViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {


        init {

            itemView!!.setOnClickListener{

                //val intent = Intent(itemView.context, CourseContent::class.java)
                //intent.putExtra("title", itemView.test.text)
                //intent.putExtra("id", itemView.courseID.text)
                //itemView.context.startActivity(intent)

                var mDatabase2 : DatabaseReference

                val listItems = arrayOf("View Course", "Delete Course")

                val builder = AlertDialog.Builder(itemView.context)

                builder.setTitle("What do you want to perform")

                builder.setSingleChoiceItems(listItems,-1){dialog: DialogInterface?, i: Int ->

                    if(i == 0)
                    {
                        val intent = Intent(itemView.context, CourseContent::class.java)
                        intent.putExtra("title", itemView.test.text)
                        intent.putExtra("id", itemView.courseID.text)
                        itemView.context.startActivity(intent)
                    }

                    if(i == 1)
                    {
                        val delBuilder = AlertDialog.Builder(itemView.context, R.style.AlertDialogCustom)
                        delBuilder.setCancelable(true)

                        delBuilder.setTitle("Delete Course")
                        delBuilder.setMessage("Are you sure want to Delete ?")

                        delBuilder.setNegativeButton("No", DialogInterface.OnClickListener { dialog2, i ->
                            dialog?.cancel()
                            dialog2.cancel()
                        })

                        delBuilder.setPositiveButton("Yes", DialogInterface.OnClickListener { dialog2, i ->

                            // This is Delete course

                            val id = itemView.courseID.text.toString()

                             mDatabase2 = FirebaseDatabase.getInstance().getReference("Course")
                             mDatabase2.child(id).removeValue()
                            dialog2.cancel()
                            dialog?.cancel()
                        })

                        val dialog = delBuilder.create()
                        dialog.show()
                    }


                }

                val dialog = builder.create()
                dialog.show()
            }
        }
    }


}
