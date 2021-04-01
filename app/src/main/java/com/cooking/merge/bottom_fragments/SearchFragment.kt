package com.cooking.merge.bottom_fragments

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cooking.merge.MainActivity
import com.cooking.merge.R
import com.cooking.merge.SearchActivity
import com.cooking.merge.adapters.Permissions.CAMERA_PERMISSIONS
import com.cooking.merge.adapters.RecyclerView_Adapter
import kotlinx.android.synthetic.main.fragment_search.*
import java.util.*
import kotlin.collections.ArrayList


class  SearchFragment : Fragment(){

    lateinit var adapter: RecyclerView_Adapter
    lateinit var fooddd: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View{
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        val searchIcon = food_search.findViewById<ImageView>(R.id.search_mag_icon)
        searchIcon.setColorFilter(Color.WHITE)

        val cancelIcon = food_search.findViewById<ImageView>(R.id.search_close_btn)
        cancelIcon.setColorFilter(Color.WHITE)

        val textView = food_search.findViewById<TextView>(R.id.search_src_text)
        textView.setTextColor(Color.WHITE)
        // If you want to change the color of the cursor, change the 'colorAccent' in colors.xml

        fooddd = view.findViewById(R.id.my_recycler_view)
        fooddd.layoutManager = LinearLayoutManager(fooddd.context)
        fooddd.setHasFixedSize(true)

        food_search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }

        })

        getListOfFoods()

        //Open Camera 點擊事件
        val Launch_Camera_btn = view.findViewById<View>(R.id.Launch_Camera_btn) as Button
        Launch_Camera_btn.setOnClickListener {

            //若MainActivity中要求的相機權限已經授權
            if ((activity as MainActivity).checkPermissions(CAMERA_PERMISSIONS[0])) {

                //進入Camera
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE)

            } else {
                //再次要求權限
                val intent = Intent(activity, SearchActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        }

        return view
    }

    private fun getListOfFoods() {
        val isoCountryCodes = arrayOf("A","B")
        val foodList = ArrayList<String>()
        for (i in isoCountryCodes) {
            val locale = Locale("", i)
            foodList.add("food") //更改名稱
        }
        adapter = RecyclerView_Adapter(foodList)
        fooddd.adapter = adapter
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        //若已經完成拍照並確認照片後
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {

            //設定一變數作為照片的變數
            //val takenImage = data?.extras?.get("data") as Bitmap
            //pictureView.setImageBitmap(takenImage)
        }
        else{
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    companion object {
        private const val CAMERA_REQUEST_CODE = 1114
    }

}