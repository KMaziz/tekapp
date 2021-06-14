package com.college.collegeconnect.datamodels;


public class Upload {

    public String name;
    public long timestamp;
    public String branch;
    public String url;
    public int download;

    public Upload() {
    }

    public Upload(String name, String branch,  int download, String url, long timestamp) {
        this.name = name;
        this.branch = branch;
        this.download = download;
        this.url = url;
        this.timestamp = timestamp;
    }


    public String getBranch() {
        return branch;
    }

    public int getDownload() {
        return download;
    }

    public String getName() {
        return name;
    }


    public String getUrl() {
        return url;
    }

    public long getTimestamp() {
        return timestamp;
    }

}
