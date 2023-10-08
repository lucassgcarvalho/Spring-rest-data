package com.project.repository.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Data
public class BookId implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long bookId;
    private Long author;

}