package com.example.Diary.DiaryData.Entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import com.example.Diary.Data.Entity.Join_Entity;

@Getter
@Setter
@Entity
@Table(name = "diary")
public class Diary_Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long numbers;

    @Column(unique = true, nullable = false)
    private String date;

    @Column(nullable = false)
    private String Diary;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user")
    private Join_Entity userID;

}
