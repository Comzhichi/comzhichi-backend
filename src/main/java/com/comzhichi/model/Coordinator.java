package com.comzhichi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "coordinators")
@Getter
@Setter
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "user_id")
public class Coordinator extends User {

    @Column(length = 100)
    private String department;
}