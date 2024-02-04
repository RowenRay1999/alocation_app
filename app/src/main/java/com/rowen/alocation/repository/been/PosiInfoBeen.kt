package com.rowen.alocation.repository.been

import android.graphics.Bitmap
import com.baidu.mapapi.model.LatLng

open class PosiInfoBeen {
    var id: Int? = null
    var owner: Int? = null
    var date: Int? = null
    var star: Int? = null
    var info: Info? = null
    var location: LatLng? = null
    var comments: ArrayList<Int>? = null
    var images: ArrayList<Bitmap>? = null
    var share: Int? = null
    var pv: Int? = null
    var uv: Int? = null
    var extend: String? = null

    class Info {
        var tag: ArrayList<Int>? = null
        var description: String? = null
    }
}