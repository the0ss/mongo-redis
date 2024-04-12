package com.theos.mongoredis.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Document(collection = "user")
@NoArgsConstructor
@AllArgsConstructor
public class User {
    
    @Id
    private String id;
    private String name;
}
