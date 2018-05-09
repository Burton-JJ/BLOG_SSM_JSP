package tech.acodesigner.entity;

/**
 * @program: Blog_SSM
 * @description: 图片
 * @author: Burton_J
 * @create: 2018-05-07 23:27
 **/
public class Image {
    private int imageId;//自增
    private int imageType;//0为用户头像 1为首页文章背景图片 2（还未决定）可能为文章内容图像
    private String imageName;

    public Image() {
    }

    public Image(int imageType, String imageName) {
        this.imageType = imageType;
        this.imageName = imageName;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public int getImageType() {
        return imageType;
    }

    public void setImageType(int imageType) {
        this.imageType = imageType;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    @Override
    public String toString() {
        return "Image{" +
                "imageId=" + imageId +
                ", imageType=" + imageType +
                ", imageName='" + imageName + '\'' +
                '}';
    }
}
