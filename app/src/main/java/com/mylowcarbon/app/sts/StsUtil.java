package com.mylowcarbon.app.sts;

import android.util.Log;

import com.mylowcarbon.app.model.ModelDao;
import com.mylowcarbon.app.model.StsInfo;

/**
 *
 */
public class StsUtil {
   private static final String TAG = "StsPresenter";
   public static StsInfo mStsInfo;

   public static void init(){

      mStsInfo = ModelDao.getInstance().getmStsInfo();
       Log.e(TAG, "StsUtil init  mStsInfo==null: "+(mStsInfo==null));

       if (mStsInfo == null ){
         Log.e(TAG, "StsUtil init  mStsInfo==null: ");
         new StsPresenter().getSts();
      }

   }

}
