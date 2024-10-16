package com.swhwang.mysql_connect.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Data
public class User {
    @Id
    private String username;
    private String password;
}
