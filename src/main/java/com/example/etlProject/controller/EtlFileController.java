package com.example.etlProject.controller;


import com.example.etlProject.service.EtlFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
public class EtlFileController {

    @Autowired
    EtlFileService etlFileService;

    @PostMapping("/upload")
    @ResponseBody
    public ResponseEntity<String> uploadFile(@PathVariable MultipartFile file) throws Exception {
        if(etlFileService.validateFile(file)){
            return etlFileService.processFile(file);
        }
        else{
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("error parsing file");
        }
    }

    @DeleteMapping("/delete/{empId}")
    public ResponseEntity<String> deleteEmployeeRecord(@PathVariable("empId") Long empId){

        return etlFileService.deleteEmployeeById(empId);
    }
}
