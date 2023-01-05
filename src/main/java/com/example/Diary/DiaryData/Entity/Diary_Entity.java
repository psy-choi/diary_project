package com.example.Diary.DiaryData.Entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import com.example.Diary.Data.Entity.Join_Entity;

@Getter
@Setter
@Entity
@Table(name = "Diary")
public class Diary_Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long number;

    @Column(nullable = false)
    private String user;

    @Column(unique = true, nullable = false)
    private String date;

    @Column(nullable = false)
    private String Diary;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "number", insertable = false, updatable = false)
    private Join_Entity User_ID;

    /*public void setUser_ID(Join_Entity User_ID){
        this.User_ID = User_ID;
    }*/
}
