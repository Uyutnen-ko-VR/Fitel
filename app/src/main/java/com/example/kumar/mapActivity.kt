package com.company.fitel

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Paint.Align
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.WindowManager
import android.widget.Toast
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.*
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider
import java.util.*


class mapActivity : Activity(), ClusterListener, ClusterTapListener {
    private lateinit var mapView: MapView
    private val CLUSTER_CENTERS = Arrays.asList(
        Point(55.756, 37.618),
        Point(59.956, 30.313),
        Point(56.838, 60.597),
        Point(43.117, 131.900),
        Point(56.852, 53.204)
    )

    inner class TextImageProvider(private val text: String) : ImageProvider() {
        override fun getId(): String {
            return "text_$text"
        }

        override fun getImage(): Bitmap {
            val metrics = DisplayMetrics()
            val manager = getSystemService(WINDOW_SERVICE) as WindowManager
            manager.defaultDisplay.getMetrics(metrics)
            val textPaint = Paint()
            textPaint.textSize = FONT_SIZE * metrics.density
            textPaint.textAlign = Paint.Align.CENTER
            textPaint.style = Paint.Style.FILL
            textPaint.isAntiAlias = true
            val widthF = textPaint.measureText(text)
            val textMetrics = textPaint.fontMetrics
            val heightF = Math.abs(textMetrics.bottom) + Math.abs(textMetrics.top)
            val textRadius =
                Math.sqrt((widthF * widthF + heightF * heightF).toDouble()).toFloat() / 2
            val internalRadius = textRadius + MARGIN_SIZE * metrics.density
            val externalRadius = internalRadius + STROKE_SIZE * metrics.density
            val width = (2 * externalRadius + 0.5).toInt()
            val bitmap = Bitmap.createBitmap(width, width, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            val backgroundPaint = Paint()
            backgroundPaint.isAntiAlias = true
            backgroundPaint.color = Color.RED
            canvas.drawCircle(
                (width / 2).toFloat(),
                (width / 2).toFloat(),
                externalRadius,
                backgroundPaint
            )
            backgroundPaint.color = Color.WHITE
            canvas.drawCircle(
                (width / 2).toFloat(),
                (width / 2).toFloat(),
                internalRadius,
                backgroundPaint
            )
            canvas.drawText(
                text,
                (
                        width / 2).toFloat(),
                width / 2 - (textMetrics.ascent + textMetrics.descent) / 2,
                textPaint
            )
            return bitmap
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MapKitFactory.setApiKey("f9d7fda4-2961-4c93-a925-f20e5078c5c9")
        setContentView(R.layout.activity_map)

        mapView = findViewById(R.id.mapview)
        mapView.map.move(
            CameraPosition(
                CLUSTER_CENTERS[0], 15f, 0f, 0f
            )
        )
        val imageProvider = ImageProvider.fromResource(
            this@mapActivity, R.drawable.search_result
        )

        // Note that application must retain strong references to both
        // cluster listener and cluster tap listener
        val clusterizedCollection =
            mapView.map.mapObjects.addClusterizedPlacemarkCollection(this)
        val points = createPoints(PLACEMARKS_NUMBER)
        clusterizedCollection.addPlacemarks(points, imageProvider, IconStyle())

        // Placemarks won't be displayed until this method is called. It must be also called
        // to force clusters update after collection change
        clusterizedCollection.clusterPlacemarks(60.0, 15)
    }

    override fun onStop() {
        mapView.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        mapView.onStart()
    }

    override fun onClusterAdded(cluster: Cluster) {
        // We setup cluster appearance and tap handler in this method
        cluster.appearance.setIcon(
            TextImageProvider(Integer.toString(cluster.size))
        )
        cluster.addClusterTapListener(this)
    }

    override fun onClusterTap(cluster: Cluster): Boolean {
        Toast.makeText(
            applicationContext,
            String.format("Fuck you", cluster.size),
            Toast.LENGTH_SHORT
        ).show()

        // We return true to notify map that the tap was handled and shouldn't be
        // propagated further.
        return true
    }

    private fun createPoints(count: Int): List<Point> {
        val points = ArrayList<Point>()
        val random = Random()
        for (i in 0 until count) {
            val clusterCenter = CLUSTER_CENTERS[random.nextInt(CLUSTER_CENTERS.size)]
            val latitude = clusterCenter.latitude + Math.random() - 0.5
            val longitude = clusterCenter.longitude + Math.random() - 0.5
            points.add(Point(latitude, longitude))
        }
        return points
    }

    companion object {
        private const val FONT_SIZE = 15f
        private const val MARGIN_SIZE = 3f
        private const val STROKE_SIZE = 3f
        private const val PLACEMARKS_NUMBER = 2000
    }
}