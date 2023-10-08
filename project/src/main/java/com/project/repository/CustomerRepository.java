package com.project.repository;

import com.project.repository.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "users", path = "users")
public interface CustomerRepository extends PagingAndSortingRepository<UserEntity, Long> {

    @Override
    @EntityGraph(attributePaths = {"listBookEntity", "booksAuthored"})
    Iterable<UserEntity> findAll(Sort sort);

    @RestResource(path = "email")
    Optional<UserEntity> findByEmailEqualsIgnoreCase(String email);

    @RestResource(path = "email-all")
    Page<List<UserEntity>> findByEmailContainsIgnoreCase(String email, Pageable pageable);

    @RestResource(path = "name")
    Page<List<UserEntity>> findByNameContainingIgnoreCase(String name, Pageable pageable);

    @Override
    @RestResource(exported = false)
    void deleteById(Long aLong);

    @Override
    @RestResource(exported = false)
    void delete(UserEntity entity);
}