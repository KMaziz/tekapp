package com.college.collegeconnect.datamodels;

import java.util.ArrayList;

public class doc {
    public String nom;
    public String description;
    public String branch;
    public String id;

    public doc(String nom, String description, String branch, String id) {
        this.nom = nom;
        this.description = description;
        this.branch = branch;
        this.id = id;
    }

    public doc(String nom, String description, String branch) {
        this.nom = nom;
        this.description = description;
        this.branch = branch;
    }
}
