/*
Copyright (c) 2012, Apps4Av Inc. (apps4av.com) 
All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

    * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
    *     * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
    *
    *     THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

package com.ds.avare.position;


import android.util.Log;

import com.ds.avare.gps.GpsParams;
import com.ds.avare.shapes.Tile;
import com.ds.avare.utils.BitmapHolder;

/**
 * A class that keeps lon/lat pair of what is shown.
 * @author zkhan
 *
 */
public class Origin {

    // latitude and longitude of center of screen
    private double mLonC;
    private double mLatC;
    // latitude and longitude of upper left of screen
    private double mLonL;
    private double mLatU;
    private double mZoom;
    private double mScale;

    /**
     * 
     */
    public Origin() {
        
    }
    
    /**
     * 
     * @param params
     * @param pan
     */
    public void update(Tile currentTile, int width, int height, GpsParams params, Pan pan, Scale scale) {
        if(currentTile == null) {
            return;
        }
        mScale = 1.0 / (scale.getScaleFactor() * scale.getMacroFactor());
        mZoom = currentTile.getZoom();
        mLatC = Epsg900913.getLatitudeOf(-pan.getMoveY() * mScale, params.getLatitude(), mZoom);
        mLonC = Epsg900913.getLongitudeOf(-pan.getMoveX() * mScale, params.getLongitude(), mZoom);
        mLatU = Epsg900913.getLatitudeOf(-(pan.getMoveY() + height / 2) * mScale, params.getLatitude(), mZoom);
        mLonL = Epsg900913.getLongitudeOf(-(pan.getMoveX() + width / 2) * mScale, params.getLongitude(), mZoom);
        Log.d("-------------", ""  + mLatU + " " + mLatC + " " + mScale);
    }

    /**
     * 
     * @return
     */
    public double getLongitudeOf(double of) {
        return Epsg900913.getLongitudeOf(of, mLonL, mZoom);
    }
    
    /**
     * 
     * @return
     */
    public double getLatitudeOf(double of) {
        return Epsg900913.getLatitudeOf(of, mLatU, mZoom);
    }

    /**
     * double The X offset on the screen of the given longitude
     */
    public double getOffsetX(double lon) {
        return Epsg900913.getOffsetX(mLonL, lon, mZoom);
    }

    /**
     * double The Y offset on the screen of the given latitude
     */
    public double getOffsetY(double lat) {
        return Epsg900913.getOffsetY(mLatU, lat, mZoom);
    }

    /**
     *
     * @return
     */
    public double getLongitudeCenter() {
        return mLonC;
    }

    /**
     *
     * @return
     */
    public double getLatitudeCenter() {
        return mLatC;
    }
}
