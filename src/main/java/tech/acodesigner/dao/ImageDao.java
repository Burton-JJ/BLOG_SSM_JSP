package tech.acodesigner.dao;

import tech.acodesigner.entity.Article;
import tech.acodesigner.entity.Image;

import java.util.List;
/**
 *
 * @description
 * @author Burton
 * @date 2018/5/11 15:14
 * @param
 * @return
 *
 */
public interface ImageDao {

    public int saveImage(Image image);

    public Image getImageById(int imageId);

    public Image getImageByName(String imageName );

    public List<Image> getImageByType(int imageType);

    public int deleteImageById(int imageId);

    public int deleteImageByName(String imageName);



}
