package tech.acodesigner.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.acodesigner.dao.ImageDao;
import tech.acodesigner.dto.OperationResult;
import tech.acodesigner.entity.Image;
import tech.acodesigner.service.ImageService;

import java.util.List;

/**
 * @program: Blog_SSM
 * @description:
 * @author: Burton_J
 * @create: 2018-05-08 00:28
 **/
@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageDao imageDao;



    @Override
    public OperationResult saveImage(Image image) {
        OperationResult or = new OperationResult();
        int result = imageDao.saveImage(image);
        if(result<=0){
            or.setSuccess(false);
            or.setInfo("图片插入失败");
        } else{
            or.setSuccess(true);
            or.setInfo("图片插入成功");
        }
        return or;
    }

    @Override
    public OperationResult deleteImageById(int id) {
        OperationResult or = new OperationResult();
        int result = imageDao.deleteImageById(id);
        if(result<=0){
            or.setSuccess(false);
            or.setInfo("删除失败 图片使用中");
        } else{
            or.setSuccess(true);
            or.setInfo("删除成功");
        }
        return or;
    }

    @Override
    public OperationResult deleteImageByName(String name) {
        OperationResult or = new OperationResult();
        int result = imageDao.deleteImageByName(name);
        if(result<=0){
            or.setSuccess(false);
            or.setInfo("删除失败");
        } else{
            or.setSuccess(true);
            or.setInfo("删除成功");
        }
        return or;
    }

    @Override
    public List<Image> getImages(int imageType) {
        return imageDao.getImageByType(imageType);
    }

    @Override
    public Image getImageById(int imageId) {
        imageDao.getImageById(imageId);
        return imageDao.getImageById(imageId);
    }
}
