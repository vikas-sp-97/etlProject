package com.example.etlProject.service;

import com.example.etlProject.dto.User;
import com.example.etlProject.entity.Employee;
import com.example.etlProject.feign.DashboardInterface;
import com.example.etlProject.repository.EtlEmployeeRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;
import java.util.List;

@Service
public class EtlFileService {
    private static final String FILE_TYPE = "text/csv";

    @Autowired
    EtlEmployeeRepository etlEmployeeRepository;

    @Autowired
    DashboardInterface dashboardInterface;

    public boolean validateFile(MultipartFile file) {
        return FILE_TYPE.equalsIgnoreCase(file.getContentType());
    }

    public ResponseEntity<String> processFile(MultipartFile file) {
        if(!parsedDataFromFileSuccessfully(file)){
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Error parsing the file");
        }
        return ResponseEntity.status(HttpStatus.OK).body("file processed successfully");
    }

    private Boolean parsedDataFromFileSuccessfully(MultipartFile file) {
        try(BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));
            CSVParser parser = new CSVParser(br, CSVFormat.DEFAULT.builder()
                    .setSkipHeaderRecord(true)
                    .setHeader()
                    .build())){
            List<CSVRecord> records = parser.getRecords();
            int lineNumber = 0;
            for(CSVRecord record: records){
                lineNumber++;
                try{

                    Employee e = Employee.builder()
                            .firstName(record.get(0))
                            .lastName(record.get(1))
                            .dateOfBirth(Date.valueOf(record.get(2)))
                            .department(record.get(3))
                            .age(Integer.parseInt(record.get(4)))
                            .emailAddress(record.get(5))
                            .build();

                    processUserRegistry(e);

                    etlEmployeeRepository.save(e);
                }
                catch(Exception e){
                    System.out.println("Error parsing record at line number : "+lineNumber);
                    e.printStackTrace();
                }
            }
            System.out.println("No.of parsed record "+lineNumber);
            return true;

        }
        catch(IOException e){

            e.printStackTrace();
            return false;
        }
    }

    private void processUserRegistry(Employee e) {
        if(!e.getEmailAddress().isEmpty()){
            User user = User.builder()
                    .username(e.getEmailAddress())
                    .active("Active")
                    .build();

            ResponseEntity<?> res = dashboardInterface.registerClientUser(user);
            System.out.println(res.getBody());
        }
    }

    public ResponseEntity<String> deleteEmployeeById(Long empId) {

        if(etlEmployeeRepository.findById(empId).isPresent()){
            etlEmployeeRepository.deleteById(empId);
            return ResponseEntity.status(HttpStatus.OK).body("Entry deleted for empoyee id: "+empId);
        }
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("empoyee id: "+empId+ " Not present in DB.");
    }
}
