package com.example.mot

import android.util.Log
import com.example.mot.data.Item
import com.example.mot.data.TransRepo
import com.example.mot.network.TransServiceApiProvider
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//private fun getTransWord(w: String) {
//    val getTransResponse : Call<TransRepo> = TransServiceApiProvider.getTransService().getTransWord(
//        TransServiceApiProvider.SERVICE_KEY,null,null,"AND",
//        TransServiceApiProvider.APP_NAME, "json","B", null,
//        null,"KOR", w)
//
//    getTransResponse.enqueue(object : Callback<TransRepo> {
//        override fun onFailure(call: Call<TransRepo>, t: Throwable) {
//            Log.e("MainActivity", "Fail")
//        }
//
//        override fun onResponse(call: Call<TransRepo>, response: Response<TransRepo>) {
//            when(response.isSuccessful){
//                true -> {
//                    Log.e("MainActivity", "Success")
//                    list = response.body()?.response?.body?.items?.item!!
//                    mainAdapter.setData(list)
//                }
//                false-> {}
//            }
//        }
//    })
//}
//
//private fun getDataAllTest() {
//    db.collection("/menu/category/gimbap")
//        .get()
//        .addOnSuccessListener { result ->
//            for (document in result) {
//                Log.d("firebase", "${document.id} => ${document.data}")
//            }
//        }
//        .addOnFailureListener { exception ->
//            Log.w("firebase", "Error getting documents.", exception)
//        }
//}
//
//private fun getDataTest() {
//    db.collection("/menu/category/gimbap").document("gimbap")
//        .get()
//        .addOnSuccessListener { document->
//            if (document != null) {
//                val d = document.toObject(Item::class.java)
//                Log.d("firebase", "DocumentSnapshot data: ${d?.dicChb}")
//            } else {
//                Log.d("firebase", "No such document")
//            }
//        }
//        .addOnFailureListener { exception ->
//            Log.d("firebase", "get failed with ", exception)
//        }
//}
