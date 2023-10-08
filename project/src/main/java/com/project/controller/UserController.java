package com.project.controller;

import com.project.repository.CustomerRepository;
import com.project.repository.entity.UserEntity;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@BasePathAwareController
public class UserController {

  private final CustomerRepository customerRepository;

  public UserController(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  @CrossOrigin(maxAge = 3600)
  @GetMapping(path = "/users/{id}")
  public ResponseEntity<RepresentationModel<?>> retrieve(@PathVariable Long id) {
    Optional<UserEntity> byId = customerRepository.findById(id);
    RepresentationModel<?> teste = RepresentationModel.of(byId)
            .add(Link.of("teste")
                    .withTitle("bunda")
            );
    return ResponseEntity.ok(teste);
    //return ResponseEntity.ok(RepresentationModel.of(byId));
  }

}