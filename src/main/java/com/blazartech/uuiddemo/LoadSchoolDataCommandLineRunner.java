/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.blazartech.uuiddemo;

import com.blazartech.uuiddemo.data.SchoolData;
import com.blazartech.uuiddemo.data.SchoolDataRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 *
 * @author aar1069
 */
@Component
@Order(2)
@Slf4j
public class LoadSchoolDataCommandLineRunner implements CommandLineRunner {

    @Autowired
    private SchoolDataRepository repo;
    
    @Override
    public void run(String... args) throws Exception {
        log.info("adding school data");
        
        List<SchoolData> schools = List.of(
                new SchoolData("Western Maryland College", "MD", "college"),
                new SchoolData("Brandeis University", "MA", "university")
        );
        
        schools.stream()
                .peek(s -> log.info("saving school {}", s))
                .map(s -> repo.save(s))
                .forEach(s -> log.info("saved school {}", s));
    }
    
}
