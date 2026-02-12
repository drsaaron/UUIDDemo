/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.blazartech.uuiddemo;

import com.blazartech.uuiddemo.data.PersonData;
import com.blazartech.uuiddemo.data.PersonDataRepository;
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
@Slf4j
@Order(1)
public class LoadPersonDataCommandLineRunner implements CommandLineRunner {

    @Autowired
    private PersonDataRepository repo;
    
    @Override
    public void run(String... args) throws Exception {
        log.info("loading data");
        
        List<PersonData> people = List.of(
                new PersonData("Scott", "Aaron", 35),
                new PersonData("Eddie", "van Halen", 60)
        );
        
        people.stream()
                .peek(p -> log.info("saving person {}", p))
                .map(p -> repo.save(p))
                .forEach(p -> log.info("saved person = {}", p));
    }
    
}
