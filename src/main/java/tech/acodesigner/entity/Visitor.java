package tech.acodesigner.entity;

import java.util.Date;

/**
 * @program: Blog_SSM
 * @description: 访问量、访问者ip
 * @author: Burton_J
 * @create: 2018-05-14 01:20
 **/
public class Visitor {
    private int visitorId;
    private String visitorIp;
    private Date visitorTime;

    public int getVisitorId() {
        return visitorId;
    }

    public void setVisitorId(int visitorId) {
        this.visitorId = visitorId;
    }

    public String getVisitorIp() {
        return visitorIp;
    }

    public void setVisitorIp(String visitorIp) {
        this.visitorIp = visitorIp;
    }

    public Date getVisitorTime() {
        return visitorTime;
    }

    public void setVisitorTime(Date visitorTime) {
        this.visitorTime = visitorTime;
    }

    @Override
    public String toString() {
        return "Visitor{" +
                "visitorId=" + visitorId +
                ", visitorIp='" + visitorIp + '\'' +
                ", visitorTime='" + visitorTime + '\'' +
                '}';
    }
}
