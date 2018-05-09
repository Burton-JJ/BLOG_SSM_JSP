package tech.acodesigner.dto;

/**
 * Created by 77239 on 2017/4/1/0001.
 */
public class UserDto {

    private int userId;
    private String username;
    private int userType;
    private int imageId;
    private String imageName;

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }





    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", userType=" + userType +
                ", imageId=" + imageId +
                ", imageName='" + imageName + '\'' +
                '}';
    }
}
