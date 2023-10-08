package com.project.repository.entity;

import lombok.Data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Data
@Entity
public class BookEntity {

    @EmbeddedId
    private BookId id;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private UserEntity owner;

    @NotNull
    private String nome;

    @NotNull
    private String description;

    @NotNull
    private String gender;

    @ManyToOne
    @JoinColumn(name = "author_id", updatable = false, insertable = false)
    @NotNull
    private UserEntity author;
}