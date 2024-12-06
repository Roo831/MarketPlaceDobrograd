package com.poptsov.marketplace.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "banned")
public class Banned {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "description_of_ban", nullable = false)
    private String descriptionOfBan;

    @Column(name = "ban_date", nullable = false)
    private Date banDate;

    @OneToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    public Banned (Integer id){
        this.id = id;
    }
}