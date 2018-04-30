package com.bridgelabz.graphql.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bridgelabz.graphql.models.Article;
import com.bridgelabz.graphql.models.User;

public interface ArticleRepository extends JpaRepository<Article, Integer> {

	@Query("from Article where user=:user")
	List<Article> findByUserId(@Param("user") User user);
}
