package com.malicia.mrg.assistant.photo.repertoire;

public class Photo {
    private String thumbnail;
    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
    private String path;
    private String relativeToPath;
    private String filename;
    private String extension;
    private String createdDate;

    private String exifDate;

    // Getters and setters
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getExifDate() {
        return exifDate;
    }

    public void setExifDate(String exifDate) {
        this.exifDate = exifDate;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "path='" + path + '\'' +
                ", relativeToPath='" + relativeToPath + '\'' +
                ", filename='" + filename + '\'' +
                ", extension='" + extension + '\'' +
                ", createdDate='" + createdDate + '\'' +
                ", exifDate='" + exifDate + '\'' +
                '}';
    }

    public String getRelativeToPath() {
        return relativeToPath;
    }

    public void setRelativeToPath(String relativeToPath) {
        this.relativeToPath = relativeToPath;
    }
}
