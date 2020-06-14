package com.example.mot.ui.ar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.example.mot.R
import com.google.ar.core.Anchor
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.rendering.Renderable
import com.google.ar.sceneform.rendering.ViewRenderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.TransformableNode

class ARActivity : AppCompatActivity() {

    private lateinit var arFragment : ArFragment
    private lateinit var anchor: Anchor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ar)

        init()
    }

    private fun init() {
        initARFragment()
        arTapListener()
    }

    private fun initARFragment() {
        arFragment = supportFragmentManager.findFragmentById(R.id.arFragment) as ArFragment
    }

    private fun arTapListener() = arFragment.setOnTapArPlaneListener { hitResult, plane, motionEvent ->
        anchor = hitResult.createAnchor()

        ViewRenderable.builder()
            .setView(this, R.layout.layout_food)
            .build()
            .thenAccept {
                it.isShadowCaster = false
                it.isShadowReceiver = false
                addModelToScence(it) }
            .exceptionally {
                val builder : AlertDialog.Builder = AlertDialog.Builder(this)
                builder.setMessage(it.localizedMessage)
                    .show()
                return@exceptionally null
            }
    }

    private fun addModelToScence(renderable: Renderable) {
        val anchorNode  = AnchorNode(anchor)
        val node  = TransformableNode(arFragment.transformationSystem)
        node.scaleController.maxScale = 0.2f
        node.scaleController.minScale = 0.1f
        node.setParent(anchorNode)
        node.renderable = renderable
        arFragment.arSceneView.scene.addChild(anchorNode)
        node.select()
    }
}
