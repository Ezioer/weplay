package com.example.weile.materialdesignexa.widget;

import java.io.File;

import android.content.Context;
import android.os.Environment;

import com.example.weile.materialdesignexa.MyApplication;
import com.imnjh.imagepicker.util.FileUtil;

/**
 * Created by Martin on 2017/1/17.
 */
public class InnerCache extends Cache {

  private File innerCache;

  public InnerCache() {
    innerCache = getCacheDirCreateIfNotExist();
  }

  private File getCacheDirCreateIfNotExist() {
    File cachePath = new File(getInnerCacheDir(MyApplication.mContext));
    if (!cachePath.isDirectory()) {
      try {
        cachePath.mkdirs();
      } catch (Exception e) {
        e.printStackTrace();
      }
      try {
        new File(cachePath+"file").createNewFile();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return cachePath;
  }

  private String getInnerCacheDir(Context context) {
    String cachePath;
    if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
        || !Environment.isExternalStorageRemovable()) {
      cachePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath();
    } else {
      cachePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath();
    }
    return cachePath;
  }


  public boolean exist(String fileName) {
    String path = innerCache + File.separator + fileName;
    return FileUtil.exist(path);
  }

  @Override
  public String getAbsolutePath(String fileName) {
    return getDirectory().getAbsolutePath() + File.separator + fileName;
  }

  @Override
  public File getDirectory() {
    return getCacheDirCreateIfNotExist();
  }

  @Override
  public boolean deleteCacheItem(String fileName) {
    return true;
    /*String filePath = getAbsolutePath(fileName);
    return FileUtil.deleteFile(filePath);*/
  }
}
