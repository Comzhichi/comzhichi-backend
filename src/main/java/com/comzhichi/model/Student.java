package com.comzhichi.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "students")
@Getter
@Setter
@NoArgsConstructor

@PrimaryKeyJoinColumn(name = "user_id")
public class Student extends User {

    @Column(length = 50)
    private String level;

    @Column(length = 100)
    private String institution;
}