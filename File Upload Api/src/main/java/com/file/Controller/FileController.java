package com.file.Controller;

import com.file.Services.FileService;
import com.file.model.FileResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/file")
public class FileController {


    @Value("${project.image}")
    private String path;

    @Autowired
    private FileService fileService;

    @PostMapping("/save")
    public ResponseEntity<FileResponse> SaveFileToProjctFolder(@RequestParam("file")MultipartFile file){

        String filename = file.getOriginalFilename();

        try {
            String file1 = fileService.SaveImageInProjectFile(path, file);
            return new ResponseEntity<>(new FileResponse(file1,"File save successfully !!!"),HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(new FileResponse(null,"Failed to save File !!!"),HttpStatus.INTERNAL_SERVER_ERROR);

        }



    }

    @GetMapping(value = "/get/{ImageName}",produces = MediaType.IMAGE_JPEG_VALUE)
    public void displayImage(@PathVariable("ImageName") String file , HttpServletResponse response) throws IOException {

        InputStream displayFile = this.fileService.DisplayFile(path, file);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(displayFile,response.getOutputStream());

    }


}
