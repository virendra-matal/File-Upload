package com.file.Services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileService {

    public String SaveImageInProjectFile(String path, MultipartFile file) throws IOException {

        //get name
        String filename = file.getOriginalFilename();

        //Generating random name
        String randomId = UUID.randomUUID().toString();
        String newfilename = randomId.concat(filename.substring(filename.lastIndexOf(".")));

        //full path
        String fullpath = path + File.separator + newfilename;


        //checking fif path exist
        File file1 = new File(path);
        if (!file1.exists()){
            file1.mkdir();
        }

        //setting in folder
        Files.copy(file.getInputStream(), Paths.get(fullpath));

        return newfilename;
    }

    public InputStream DisplayFile(String path, String saveFile) throws FileNotFoundException {

        String saveFilePath=path+File.separator+saveFile;
        InputStream is=new FileInputStream(saveFilePath);
        //Image name will getfrom the database...(saveFile)
        return is;
    }
}
