package com.ncl.nclcustomerservice.adapter;


/**
 * The type File details.
 */
public class FileDetails {
    /**
     * The File name.
     */
   public String fileName, /**
     * The File path.
     */
    filePath,/**
     * The File path.
     */
    filePathS3, /**
     * The File type.
     */
    fileType, /**
     * The Size.
     */
    size;

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

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
