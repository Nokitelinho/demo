package com.ibsplc.neoicargo.devops.utils.ecrsync.parse;

import com.ibsplc.neoicargo.devops.utils.ecrsync.ImageEntry;

import java.util.Arrays;

/**
 * @author Binu K <binu.kurian@ibsplc.com>
 */
/*
 *  Revision History
 *  Revision 	Date      	      Author			Description
 *  0.1			June 03, 2021 	  Binu K			First draft
 */
public class BomEntry {

    String artifactId;
    String image;

    public String getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "BomEntry{" +
                "artifactId='" + artifactId + '\'' +
                ", image='" + image + '\'' +
                '}';
    }

    public ImageEntry toImageEntry(){
        var split = image.split(":");
        return new ImageEntry(split[0],split[1]);
    }


}
