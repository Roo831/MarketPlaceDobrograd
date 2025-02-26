package com.poptsov.marketplace.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.Objects;

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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Banned banned = (Banned) o;
        return Objects.equals(id, banned.id) && Objects.equals(descriptionOfBan, banned.descriptionOfBan) && Objects.equals(banDate, banned.banDate) && Objects.equals(user, banned.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, descriptionOfBan, banDate, user);
    }
}