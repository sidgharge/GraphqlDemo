package com.bridgelabz.graphql.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bridgelabz.graphql.models.User;

public interface UserReposiroty extends JpaRepository<User, Integer> {

	User findByName(String name);
}
