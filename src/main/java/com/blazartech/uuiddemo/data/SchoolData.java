/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.blazartech.uuiddemo.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

/**
 *
 * @author aar1069
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
public class SchoolData {
    
    @Id
    @UuidGenerator
    @Column(length = 36)
    private String id;
    
    private String name;
    @Column(length = 2) private String state;
    private String type;
    
    public SchoolData(String name, String state, String type) {
        this.name = name;
        this.state = state;
        this.type = type;
    }
}
