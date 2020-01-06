package com.dan.yousuanshi.utils;

import android.os.Parcel;
import android.os.Parcelable;

public class FileItem implements Parcelable {
    public int filePic;
    public String fileName= "s";
    public String filePath;
    public String fileModifiedTime = "s";
    public Long size;
    private int fileType = 0;//1为txt,2为doc,3为excel,4为ppt
    private boolean isChecked = false;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public int getFileType() {
        return fileType;
    }

    public void setFileType(int fileType) {
        this.fileType = fileType;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public int getFilePic() {
        return filePic;
    }

    public void setFilePic(int filePic) {
        this.filePic = filePic;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileModifiedTime() {
        return fileModifiedTime;
    }

    public void setFileModifiedTime(String fileModifiedTime) {
        this.fileModifiedTime = fileModifiedTime;
    }

    public FileItem() {
    }

    public FileItem(int filePic, String fileName, String filePath,
                    String fileModifiedTime, Long size) {
        super();
        this.filePic = filePic;
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileModifiedTime = fileModifiedTime;
        this.size = size;
    }

    public FileItem(int filePic, String filePath,Long size) {
        super();
        this.filePic = filePic;
        this.filePath = filePath;
        this.size = size;
    }

    @Override
    public String toString() {
        return "FileItem [filePic=" + filePic + ", fileName=" + fileName
                + ", filePath=" + filePath + ", fileModifiedTime="
                + fileModifiedTime + "]";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.filePic);
        dest.writeString(this.fileName);
        dest.writeString(this.filePath);
        dest.writeString(this.fileModifiedTime);
        dest.writeValue(this.size);
        dest.writeInt(this.fileType);
    }

    protected FileItem(Parcel in) {
        this.filePic = in.readInt();
        this.fileName = in.readString();
        this.filePath = in.readString();
        this.fileModifiedTime = in.readString();
        this.size = (Long) in.readValue(Long.class.getClassLoader());
        this.fileType = in.readInt();
    }

    public static final Parcelable.Creator<FileItem> CREATOR = new Parcelable.Creator<FileItem>() {
        @Override
        public FileItem createFromParcel(Parcel source) {
            return new FileItem(source);
        }

        @Override
        public FileItem[] newArray(int size) {
            return new FileItem[size];
        }
    };
}
