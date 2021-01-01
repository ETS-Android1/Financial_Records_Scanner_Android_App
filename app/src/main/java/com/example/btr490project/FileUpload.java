package com.example.btr490project;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;

public class FileUpload {

    private String fileName;
    private String startDate;
    private String endDate;
    private String fileCategory;
    private String fileUrl;
    private String fileKey;
    private String fileStatus;
    private ArrayList<String> selectedFiles;


    public FileUpload() {

    }

    public FileUpload(String fileName, String startDate, String endDate, String fileCategory, String fileUrl) {
        setFileName(fileName);
        setStartDate(startDate);
        setEndDate(endDate);
        setFileCategory(fileCategory);
        setFileUrl(fileUrl);
        setFileStatus(" ");
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getFileCategory() {
        return fileCategory;
    }

    public void setFileCategory(String fileCategory) {
        this.fileCategory = fileCategory;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    @Exclude
    public String getFileKey() {
        return fileKey;
    }

    @Exclude
    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }


    public String getFileStatus() {
        return fileStatus;
    }


    public void setFileStatus(String fileStatus) {
        this.fileStatus = fileStatus;
    }


}
