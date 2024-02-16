package com.ibsplc.neoicargo.devops.utils.ecrsync;

import static com.ibsplc.neoicargo.devops.utils.ecrsync.CLIArgs.hasText;

/**
 * @author Binu K <binu.kurian@ibsplc.com>
 */
/*
 *  Revision History
 *  Revision 	Date      	      Author			Description
 *  0.1			June 03, 2021 	  Binu K			First draft
 */
public class ImageEntry {

    private String image;
    private String imageTag;

    public ImageEntry(String image, String imageTag) {
        this.image = image;
        this.imageTag = imageTag;
    }

    public String getImage() {
        return image;
    }

    public String getImage(String prefix) {
        if (hasText(prefix)) {
            return prefix + "/" + image;
        }
        return image;
    }

    public String getImage(String prefix, String imagePrefix) {
        imagePrefix = hasText(imagePrefix) ? imagePrefix : "";
        if (hasText(prefix))
            return prefix + "/" + imagePrefix + image;
        return imagePrefix + image;
    }


    public String getImageTag() {
        return imageTag;
    }


    @Override
    public String toString() {
        return "ImageEntry{" +
                "image='" + image + '\'' +
                ", imageTag='" + imageTag + '\'' +
                '}';
    }
}
