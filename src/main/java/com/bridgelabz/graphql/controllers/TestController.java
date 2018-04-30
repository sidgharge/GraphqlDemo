package com.bridgelabz.graphql.controllers;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.graphql.models.Article;
import com.bridgelabz.graphql.models.User;
import com.bridgelabz.graphql.repositories.ArticleRepository;
import com.bridgelabz.graphql.repositories.UserReposiroty;
import com.bridgelabz.graphql.services.GraphqlUtility;

import graphql.ExecutionResult;
import graphql.GraphQL;

@RestController
public class TestController {

	private GraphQL graphQL;

	@Autowired
	private GraphqlUtility utility;
	
	@Autowired
	private UserReposiroty userReposiroty;
	
	@Autowired
	private ArticleRepository articleRepository;

	@PostConstruct
	public void createObject() throws IOException {
		graphQL = utility.createGraphqlObject();
	}

	@PostMapping(value = "/query")
	public ResponseEntity<Object> query(@RequestBody String query) {
		ExecutionResult result = graphQL.execute(query);
		System.out.println("errors: " + result.getErrors());
		return ResponseEntity.ok(result.getData());
	}
	
	@PostMapping(value = "/user")
	public void add(@RequestBody User user) {
		userReposiroty.save(user);
	}
	
	@PostMapping(value = "/article/{userid}")
	public void add(@RequestBody Article article, @PathVariable int userid) {
		User user = userReposiroty.findById(userid).get();
		article.setUser(user);
		articleRepository.save(article);
	}

}
