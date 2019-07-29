package org.example.test;

import java.io.File;
import java.nio.file.Paths;

import org.apache.commons.lang.SystemUtils;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

public class MetadataExtractorTest {

    public static void main(String[] args) {
        String userHome = SystemUtils.getUserHome().getAbsolutePath();
        File file = Paths.get(userHome, "Downloads", "xiaodu_1_mask_05_28.mp4").toFile();

        try {
            Metadata metadata = ImageMetadataReader.readMetadata(file);

            print(metadata, "Using ImageMetadataReader");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void print(Metadata metadata, String method) {
        System.out.println();
        System.out.println("-------------------------------------------------");
        System.out.print(' ');
        System.out.print(method);
        System.out.println("-------------------------------------------------");
        System.out.println();

        //
        // A Metadata object contains multiple Directory objects
        //
        for (Directory directory : metadata.getDirectories()) {

            //
            // Each Directory stores values in Tag objects
            //
            for (Tag tag : directory.getTags()) {
                System.out.println(tag);
            }

            //
            // Each Directory may also contain error messages
            //
            for (String error : directory.getErrors()) {
                System.err.println("ERROR: " + error);
            }
        }
    }

}
