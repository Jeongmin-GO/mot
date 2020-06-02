package com.example.mot.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.mot.R
import com.example.mot.data.Item
import com.example.mot.db.entity.Menu
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_menu.*


/**
 * A simple [Fragment] subclass.
 */
class MenuFragment : Fragment() {

    private lateinit var list: MutableList<Item>
    private val mainAdapter: MainAdapter by lazy {
        MainAdapter { clickEventCallback(it) }
    }
    private val db = FirebaseFirestore.getInstance()
    private val menuVM: MenuViewModel by lazy {
        ViewModelProvider(this, MenuViewModel.Factory(activity!!.application)).get(MenuViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_menu, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initRecyclerAdapter()
        getMenu()
        searchMenu()
    }

    private fun initRecyclerAdapter() {
        /*어댑터 생성후 어떤 데이터(arraylist)와 어떤 recyclerview를 쓸 것인지 설정*/
        recycler.apply {
            adapter = mainAdapter
            layoutManager = LinearLayoutManager(activity?.application?.applicationContext)
            setHasFixedSize(true)
        }
    }

    private fun getMenu() {
        menuVM.getAllMenu().observe(this, Observer<MutableList<Menu>> {
            it?.let {
                mainAdapter.setData(it)
            }
        })
    }

    private fun clickEventCallback(position: Int) {}

    private fun searchMenu() {
        svMenu.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                Log.e("Fragment", query)
                return false
            }

            override fun onQueryTextChange(new: String?): Boolean {
                return false
            }
        })
    }
}
