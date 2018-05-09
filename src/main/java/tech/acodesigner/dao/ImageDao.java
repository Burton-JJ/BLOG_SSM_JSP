package tech.acodesigner.dao;

import tech.acodesigner.entity.Article;
import tech.acodesigner.entity.Image;

import java.util.List;

/**
 * @program: Blog_SSM
 * @description: 图片
 * @author: Burton_J
 * @create: 2018-05-07 23:45
 **/
public interface ImageDao {

    public int saveImage(Image image);

    public Image getImageById(int imageId);

    public Image getImageByName(String imageName );

    public List<Image> getImageByType(int imageType);

    public int deleteImageById(int imageId);

    public int deleteImageByName(String imageName);



}
