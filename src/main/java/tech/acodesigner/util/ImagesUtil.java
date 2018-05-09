package tech.acodesigner.util;

import java.io.File;

/**
 * Created by 77239 on 2017/4/4/0004.
 */
public class ImagesUtil {

    public static String[] getImages(String path) {
        File imagesFolder = new File(path);
        String[] images = imagesFolder.list();
        return images;
        //输出文件夹名+文件名 若是底层文件夹 则输出文件名
    }

}
