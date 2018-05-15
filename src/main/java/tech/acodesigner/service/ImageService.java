package tech.acodesigner.service;

/**
 *
 * @description
 * @author Burton
 * @date 2018/5/15 20:10
 * @param
 * @return
 *
 */
import tech.acodesigner.dto.OperationResult;
import tech.acodesigner.entity.Image;

import java.util.List;

public interface ImageService {

    public OperationResult saveImage(Image image);

    public List<Image> getImages(int imageType);

    public Image getImageById(int imageId);

    public OperationResult deleteImageById(int id);

    public OperationResult deleteImageByName(String name);

}
