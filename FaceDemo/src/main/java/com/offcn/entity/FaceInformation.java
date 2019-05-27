package com.offcn.entity;

/**
 * @ClassName: FaceInformation
 * @Description: TODO 类的描述
 * @author: 符纯涛
 * @date: 2019/5/27
 */

public class FaceInformation {
    private String imageData;

    @Override
    public String toString() {
        return "FaceInformation{" +
                "imageData='" + imageData + '\'' +
                '}';
    }

    public String getImageData() {
        return imageData;
    }

    public void setImageData(String imageData) {
        this.imageData = imageData;
    }
}
