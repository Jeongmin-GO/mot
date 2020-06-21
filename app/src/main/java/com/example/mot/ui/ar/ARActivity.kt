package com.example.mot.ui.ar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mot.R
import com.example.mot.db.entity.Menu
import com.example.mot.network.NaverApiResponse
import com.example.mot.network.NaverApi
import com.example.mot.unit.Language
import com.example.mot.unit.extension.TAG
import com.example.mot.viewmodel.MenuViewModel
import com.google.ar.core.Anchor
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.rendering.Renderable
import com.google.ar.sceneform.rendering.ViewRenderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.TransformableNode
import kotlinx.android.synthetic.main.activity_ar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ARActivity : AppCompatActivity() {
    val api = NaverApi.create()

    private lateinit var target: String
    private var id: Long = -1
    private lateinit var arFragment: ArFragment
    private lateinit var anchor: Anchor

    private val menuVM: MenuViewModel by lazy {
        ViewModelProvider(
            this,
            MenuViewModel.Factory(application)
        )
            .get(MenuViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ar)

        init()
    }

    private fun init() {
        showContent()
        initARFragment()
        if (!id.equals(-1)) arTapListener()
    }

    private fun initARFragment() {
        arFragment = supportFragmentManager.findFragmentById(R.id.arFragment) as ArFragment
    }

    private fun arTapListener() =
        arFragment.setOnTapArPlaneListener { hitResult, plane, motionEvent ->
            anchor = hitResult.createAnchor()

            ViewRenderable.builder()
                .setView(this, R.layout.layout_food)
                .setVerticalAlignment(ViewRenderable.VerticalAlignment.BOTTOM)
                .build()
                .thenAccept {
                    it.isShadowCaster = false
                    it.isShadowReceiver = false
                    it.view.setBackgroundResource(R.drawable.gukbap)
                    when(id.toInt()) {
                        1->  it.view.setBackgroundResource(R.drawable.kimbap)
                        7->  it.view.setBackgroundResource(R.drawable.gukbap)
                        10-> it.view.setBackgroundResource(R.drawable.rabbokki)
                    }
                    addModelToScence(it)
                }
                .exceptionally {
                    val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                    builder.setMessage(it.localizedMessage)
                        .show()
                    return@exceptionally null
                }
        }

    private fun addModelToScence(renderable: Renderable) {
        val anchorNode = AnchorNode(anchor)
        val node = TransformableNode(arFragment.transformationSystem)
        node.scaleController.maxScale = 0.2f
        node.scaleController.minScale = 0.1f
        node.setParent(anchorNode)
        node.renderable = renderable
        arFragment.arSceneView.scene.addChild(anchorNode)
        node.select()
    }

    private fun setLanguage(m: Menu): String? {
        return when (Language.langCode) {
            0 -> m.dicKor
            1 -> m.dicEn
            2 -> m.dicChb
            else -> m.dicJpe
        }
    }

    private fun showContent() {
        id = intent.getLongExtra("menuId", -1)
        if (id.equals(-1)) {
            //값이 안왔을때 처리
        } else {
            menuVM.getMenuById(id).observe(this, Observer {
                txtname.text = setLanguage(it)
                txtcontent.text = it.contents
                txtingredient.text =it.ingredients
                if(Language.langCode!=0)transferPapago(it.contents, it.ingredients)
            })
        }
    }

    private fun setApi() {
        when (Language.langCode) {
            1 -> target = "en"
            2 -> target = "zh-CN"
            3 -> target = "ja"
        }
    }

    private fun transferPapago(translatedcontext: String?, translatedIngre: String?) {
        setApi()

        val callPostTransferPapago = translatedcontext?.let {
            api.translatePapago("ko", target, it)
        }
        val callPostTransferInPapago = translatedIngre?.let {
            api.translatePapago("ko", target, it)
        }

        callPostTransferPapago?.enqueue(object : Callback<NaverApiResponse> {
            override fun onFailure(call: Call<NaverApiResponse>, t: Throwable) {
                Log.d(TAG, "실패 : $t")
            }

            override fun onResponse(
                call: Call<NaverApiResponse>,
                response: Response<NaverApiResponse>
            ) {
                txtcontent.text = response.body()?.message?.result?.translatedText
                Log.d(TAG, "성공 : ${response.raw()}")
            }
        })

        callPostTransferInPapago?.enqueue(object : Callback<NaverApiResponse> {
            override fun onFailure(call: Call<NaverApiResponse>, t: Throwable) {
                Log.d(TAG, "실패 : $t")
            }

            override fun onResponse(
                call: Call<NaverApiResponse>,
                response: Response<NaverApiResponse>
            ) {
                Log.d(TAG, "${response.code()}")
                txtingredient.text = response.body()?.message?.result?.translatedText
                Log.d(TAG, "성공 : ${response.raw()}")
            }

        })
    }
}

