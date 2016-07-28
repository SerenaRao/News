package com.neteasenews.common.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.neteasenews.R;
import com.squareup.picasso.Picasso;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author Yuan
 * @time 2016/7/22  17:20
 * @desc ${TODD}
 */
public class ImageUtil {

    public static final File IMG_DIR = new File(Environment.getExternalStorageDirectory().toString() + "/Netease/");

    /**
     * 加载图片
     *
     * @param v   要插入的ImageView
     * @param url 图片的url
     */
    public static void loadImg(ImageView v, String url) {
        Picasso.with(v.getContext())
                .load(url)
                .placeholder(R.drawable.place_holder)
                .error(R.drawable.place_holder)
                .into(v);
    }

    public static void loadImgFromLocal(ImageView v, String imgUrl) {
        String imgName = MD5Util.toMD5(imgUrl);
        File file = new File(IMG_DIR, imgName);
        Picasso.with(v.getContext())
                .load(file)
                .placeholder(R.drawable.place_holder)
                .error(R.drawable.place_holder)
                .into(v);
    }

    public static void saveImg2Disk(@NonNull String imgUrl) {
        String imgName = MD5Util.toMD5(imgUrl);
        try {
            if (!exists(imgUrl)) {
                HttpURLConnection conn = (HttpURLConnection) new URL(imgUrl).openConnection();
                Bitmap bitmap = BitmapFactory.decodeStream(conn.getInputStream());
                File myCaptureFile = new File(IMG_DIR, imgName);
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                bos.flush();
                bos.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveImg2Disk(@NonNull InputStream in, String imgUrl) throws IOException {
        String imgName = MD5Util.toMD5(imgUrl);
        File file = new File(IMG_DIR, imgName);
        if (file != null && file.exists())
            file.delete();

        FileOutputStream out = new FileOutputStream(file);
        byte[] buffer = new byte[1024 * 128];
        int len = -1;
        while ((len = in.read(buffer)) != -1) {
            out.write(buffer, 0, len);
        }
        out.flush();
        out.close();
        in.close();
    }

    public static boolean exists(@NonNull String imgUrl) {
        String imgName = MD5Util.toMD5(imgUrl);
        File file = new File(IMG_DIR, imgName);
        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();
        if (file == null || !file.exists()) {
            return false;
        }
        String path = file.getAbsolutePath();
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        return bitmap != null;
    }
}
