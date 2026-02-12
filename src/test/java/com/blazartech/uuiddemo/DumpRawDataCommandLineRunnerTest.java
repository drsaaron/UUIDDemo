/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.blazartech.uuiddemo;

import java.nio.ByteBuffer;
import java.util.UUID;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 *
 * @author aar1069
 */
@ExtendWith(SpringExtension.class)
@Slf4j
@ContextConfiguration(classes = {
    DumpRawDataCommandLineRunnerTest.DumpRawDataCommandLineRunnerTestConfiguration.class
})
public class DumpRawDataCommandLineRunnerTest {
    
    @Configuration
    public static class DumpRawDataCommandLineRunnerTestConfiguration {
        
        @Bean
        public DumpRawDataCommandLineRunner instance() {
            return new DumpRawDataCommandLineRunner();
        }
    }
    
    @Autowired
    private DumpRawDataCommandLineRunner instance;
    
    @MockitoBean
    private DataSource dataSource;
    
    public DumpRawDataCommandLineRunnerTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getUuidFromByteArray method, of class DumpRawDataCommandLineRunner.
     */
    @Test
    public void testGetUuidFromByteArray() {
        log.info("getUuidFromByteArray");
        
        // start from a random UUID
        UUID originalUuid = UUID.randomUUID();
        
        // convert to byte array
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(originalUuid.getMostSignificantBits());
        bb.putLong(originalUuid.getLeastSignificantBits());
        byte[] uuidBytes = bb.array();
        
        // Convert the byte array back to a UUID
        UUID convertedUuid = instance.getUuidFromByteArray(uuidBytes);
        
        // new one should equal the old
        assertEquals(originalUuid, convertedUuid);
    }

    
}
