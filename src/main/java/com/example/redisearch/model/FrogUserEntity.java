package com.example.redisearch.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Table(name = "user_info")
public class FrogUserEntity {

    @Id
    private Long id;

    @Column(name = "user_name", updatable = false)
    private String username;
    @Column(name = "e_name", updatable = false)
    private String ename;
    @Column(name = "frog_id", updatable = false)
    private String frogId;

    @Override
    public String toString() {
        return "FrogUserEntity{" +
                "id=" + id +
                ", username='" + username + '\'' +
                '}';
    }
}
