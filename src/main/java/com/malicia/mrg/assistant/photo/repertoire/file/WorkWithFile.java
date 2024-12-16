package com.malicia.mrg.assistant.photo.repertoire.file;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.malicia.mrg.assistant.photo.repertoire.Photo;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import static com.drew.metadata.exif.ExifDirectoryBase.TAG_DATETIME;

public class WorkWithFile {

    private WorkWithFile() {
    }

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

    public static List<Photo> convertPathsToPhotos(String pathToScan, List<Path> paths) throws IOException {
        List<Photo> photos = new ArrayList<>();

        for (Path path : paths) {
            // Create a new Photo object
            Photo photo = new Photo();
            photo.setPath(path.toString());

            photo.setRelativeToPath(getNormalizedPath(path.toString()).replace(getNormalizedPath(pathToScan), ""));

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

            File imgFile = new File(String.valueOf(path));
            try {
                // Load the image file
                BufferedImage originalImage = ImageIO.read(imgFile);

                if (originalImage != null) {
                    // Set the dimensions for the thumbnail
                    int thumbnailWidth = 100; // Set desired thumbnail width
                    int thumbnailHeight = 100; // Set desired thumbnail height

                    // Create a scaled instance (thumbnail)
                    Image thumbnail = originalImage.getScaledInstance(thumbnailWidth, thumbnailHeight, Image.SCALE_SMOOTH);

                    // Optionally, you can create a BufferedImage for the thumbnail if you need it in BufferedImage format
                    BufferedImage bufferedThumbnail = new BufferedImage(thumbnailWidth, thumbnailHeight, BufferedImage.TYPE_INT_ARGB);
                    bufferedThumbnail.getGraphics().drawImage(thumbnail, 0, 0, null);

                    String base64EncodedThumbnail = encodeImageToBase64(bufferedThumbnail);

                    photo.setThumbnail("data:image/png;base64,"+base64EncodedThumbnail);
                }

            } catch (IOException e) {
                System.out.println("Error reading the image file: " + e.getMessage());
            }

            photos.add(photo);
        }

        return photos;
    }

    public static String getNormalizedPath(String pathToScan) {
        Path pathScan = Paths.get(pathToScan);
        Path normalizedPath = pathScan.normalize();
        return normalizedPath.toString();
    }

    public static String sanitizeFileName(String input) {
        // Replace invalid characters for Windows and Unix-based systems
        String sanitized = input.replaceAll("[\\\\/:*?\"<>|]", "_");

        // Remove leading and trailing spaces or dots (optional)
        sanitized = sanitized.replaceAll("^[\\.\\s]+$|^[\\.\\s]+$", "");

        // Optionally, you can limit the length of the file name (max 255 chars for most systems)
        if (sanitized.length() > 255) {
            sanitized = sanitized.substring(0, 255);
        }

        return sanitized;
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
                String exifDate = directory.getString(TAG_DATETIME);

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

    public static void putIntoJsonFile(Object expectedList, String jsonDest) {
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

    public static boolean moveFileWithTimestamp(String sourcePathStr, String destinationPathStr, boolean dryRun) throws IOException {
        Path sourcePath = Paths.get(sourcePathStr);
        Path destinationPath = Paths.get(destinationPathStr);

        // Check if source file exists
        if (!Files.exists(sourcePath)) {
            throw new IOException("Source file does not exist: " + sourcePathStr);
        }

        // Ensure the destination directory exists
        Files.createDirectories(destinationPath.getParent());

        if (!dryRun) {
            // Move the file
            Files.move(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);

            // Retrieve the last modified time of the source file
            BasicFileAttributes attrs = Files.readAttributes(sourcePath, BasicFileAttributes.class);
            FileTime lastModifiedTime = attrs.lastModifiedTime();

            // Set the last modified time of the destination file to match the source file
            Files.setLastModifiedTime(destinationPath, lastModifiedTime);
        }

        return true;
    }

    // Helper method to encode an image to Base64
    private static String encodeImageToBase64(BufferedImage image) {
        try {
            // Convert BufferedImage to byte array
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(image, "PNG", byteArrayOutputStream);
            byte[] imageBytes = byteArrayOutputStream.toByteArray();

            // Encode the byte array to Base64
            return Base64.getEncoder().encodeToString(imageBytes);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}


