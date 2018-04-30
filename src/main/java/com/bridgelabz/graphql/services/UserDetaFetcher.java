package com.bridgelabz.graphql.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bridgelabz.graphql.models.User;
import com.bridgelabz.graphql.repositories.ArticleRepository;
import com.bridgelabz.graphql.repositories.UserReposiroty;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

@Component
public class UserDetaFetcher implements DataFetcher<Object> {

	@Autowired
	private UserReposiroty userRepository;
	
	@Autowired
	private ArticleRepository articleRepository;
	
	@Override
	public Object get(DataFetchingEnvironment env) {
		if (env.getFieldDefinition().getName().equals("user")) {
			return userRepository.findById(env.getArgument("id")).get();
		} else if (env.getFieldDefinition().getName().equals("userbyname")) {
			return userRepository.findByName(env.getArgument("name"));
		} else if (env.getFieldDefinition().getName().equals("allusers")) {
			return userRepository.findAll();
		} else if (env.getFieldDefinition().getName().equals("articles")) {
			User user = new User();
			user.setId(env.getArgument("id"));
			return articleRepository.findByUserId(user);
		} else {
			throw new RuntimeException("Could not find query name");
		}
	}

}
