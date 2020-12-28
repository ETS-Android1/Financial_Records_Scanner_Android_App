package com.example.btr490project;

import com.google.firebase.database.Exclude;

public class ImageUpload {

    private String imageUrl;
    private String imageCategory;
    private String imageKey;
    private String imageName;

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

    // We add image key to our file to have access to the images in the firebase
    // To not include the id to our object we use exclude before our functions

    @Exclude
    public String getImageKey() {
        return imageKey;
    }

    @Exclude
    public void setImageKey(String imageKey) {
        this.imageKey = imageKey;
    }

    @Exclude
    public String getImageName() {
        return imageName;
    }

    @Exclude
    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    @Exclude
    public void setImageNameFromUrl(String imageUrl) {
        setImageName(imageUrl.substring(imageUrl.indexOf('F') + 1, imageUrl.indexOf('?')));
    }

}
