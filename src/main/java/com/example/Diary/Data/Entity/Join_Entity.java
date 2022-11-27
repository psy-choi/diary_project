package com.example.Diary.Data.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "member")
public class Join_Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long number;

    @Column(unique = true, nullable = false)
    private String ID;

    @Column(nullable = false)
    private String Password;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;



}
