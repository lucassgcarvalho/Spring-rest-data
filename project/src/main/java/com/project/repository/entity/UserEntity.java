package com.project.repository.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
public class UserEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_id")
    private Long id;

    @NotNull
    @Size(min = 2, max = 255)
    private String name;

    private Long phoneNumber;

    @Embedded
    private Address address;

    @Column(unique = true)
    @NotNull
    @Email
    private String email;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "owner")
    private List<BookEntity> listBookEntity;

    @OneToMany(mappedBy = "author")
    private List<BookEntity> booksAuthored;

    public Long getResourceId() {
        return id;
    }

}