package com.malicia.mrg.assistant.photo.repertoire.file;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.Tag;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.malicia.mrg.assistant.photo.repertoire.Photo;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WorkWithFile {

    public static List<Path> getAllFilesFromFolderAndSubFolderWithType(String rootDir, List<String> authorizedExtensions) throws IOException {
        Path rootPath = Paths.get(rootDir);
        List<Path> matchingFiles = new ArrayList<>();

        // Walk through the directory tree
        Files.walkFileTree(rootPath, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                // Check if the file has an authorized extension
                String fileName = file.getFileName().toString();
                String extension = getFileExtension(fileName);

                if (authorizedExtensions.contains(extension)) {
                    matchingFiles.add(file);
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                // Handle errors, such as permissions, here if needed
                return FileVisitResult.CONTINUE;
            }
        });

        return matchingFiles;
    }

    public static List<Photo> convertPathsToPhotos(List<Path> paths) throws IOException {
        List<Photo> photos = new ArrayList<>();

        for (Path path : paths) {
            // Create a new Photo object
            Photo photo = new Photo();
            photo.setPath(path.toString());

            // Extract filename and extension
            String filename = path.getFileName().toString();
            String extension = getFileExtension(filename);
            photo.setFilename(filename);
            photo.setExtension(extension);

            // Get file creation date (from filesystem)
            photo.setCreatedDate(getFileCreatedDate(path));

            // Try to extract EXIF date (only if the file is an image)
            if (extension.equalsIgnoreCase("ARW") || extension.equalsIgnoreCase("jpg") || extension.equalsIgnoreCase("jpeg") || extension.equalsIgnoreCase("png")) {
                photo.setExifDate(getExifDate(path));
            }

            photos.add(photo);
        }

        return photos;
    }

    // Helper method to get the file extension
    public static String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex == -1) {
            return ""; // No extension found
        }
        return fileName.substring(dotIndex + 1).toLowerCase(); // Get extension and convert to lowercase
    }

    // Helper method to get file creation date
    private static String getFileCreatedDate(Path path) throws IOException {
        try {
            FileTime fileTime = (FileTime) Files.getAttribute(path, "creationTime");
            Date date = new Date(fileTime.toMillis());
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        } catch (IOException e) {
            return "Unknown"; // If creation date can't be fetched
        }
    }

    // Helper method to get EXIF date from an image file
    private static String getExifDate(Path path) {
        try {
            // Read EXIF data if the file is an image
            File file = path.toFile();
            Metadata metadata = ImageMetadataReader.readMetadata(file);

            // Get the EXIF directory
            ExifIFD0Directory directory = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);

            if (directory != null) {
                // Access EXIF DateTimeOriginal tag directly (tag ID 0x9003)
                String exifDate = directory.getString(ExifIFD0Directory.TAG_DATETIME);

                if (exifDate != null) {
                    return exifDate; // Return the EXIF datetime
                }
            }
        } catch (Exception e) {
            // If no EXIF data or error, return "Unknown"
            e.printStackTrace();
        }
        return "Unknown";
    }

    public static void putIntoJsonFile(List<Photo> expectedList, String jsonDest) {
        // Create an ObjectMapper instance
        ObjectMapper objectMapper = new ObjectMapper();
        // Enable pretty print
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        // Write the object to a file (output.json)
        File file = new File(jsonDest);
        try {
            objectMapper.writeValue(file, expectedList);  // This writes the JSON to the file
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}


