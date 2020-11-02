package com.example.btr490project;

public class ImageUpload {

    private String imageUrl;
    private String imageCategory;

    public ImageUpload() {
    }

    public ImageUpload(String imageUrl, String imageCategory) {
        setImageUrl(imageUrl);
        setImageCategory(imageCategory);
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageCategory() {
        return imageCategory;
    }

    public void setImageCategory(String imageCategory) {
        this.imageCategory = imageCategory;
    }

}
