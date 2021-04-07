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
    lateinit var hotlist_rv: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View{
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        val searchIcon = ingredients_search.findViewById<ImageView>(R.id.search_mag_icon)
        searchIcon.setColorFilter(Color.WHITE)

        val cancelIcon = ingredients_search.findViewById<ImageView>(R.id.search_close_btn)
        cancelIcon.setColorFilter(Color.WHITE)

        val textView = ingredients_search.findViewById<TextView>(R.id.search_src_text)
        textView.setTextColor(Color.WHITE)
        // If you want to change the color of the cursor, change the 'colorAccent' in colors.xml

        //製作熱門搜尋的recycler_view
        hotlist_rv = view.findViewById(R.id.hotlist_rv)
        hotlist_rv.layoutManager = LinearLayoutManager(hotlist_rv.context)
        hotlist_rv.setHasFixedSize(true)

        //做一個textListener，參數query為輸入字串
        ingredients_search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            //當使用者輸入（提交）一text
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            //當使用者修改輸入字串
            override fun onQueryTextChange(newText: String?): Boolean {

                //重置adapter並重新檢查輸入字串
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
        //設定熱門搜尋的選項
        val hotlist_items = arrayOf("快速晚餐","高麗菜","馬鈴薯","簡易家常菜","雞肉","超簡單甜點","家常菜 肉","減醣")
        val hotList = ArrayList<String>()
        for (i in hotlist_items) {
            hotList.add(i) //更改名稱
        }
        adapter = RecyclerView_Adapter(hotList)
        hotlist_rv.adapter = adapter
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