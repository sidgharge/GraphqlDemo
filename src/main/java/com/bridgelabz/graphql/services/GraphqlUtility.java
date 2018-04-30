package com.bridgelabz.graphql.services;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;

@Component
public class GraphqlUtility {

	@Value("classpath:models.graphqls")
	private Resource resource;

	@Autowired
	private UserDetaFetcher datafetcher;

	//@PostConstruct
	public GraphQL createGraphqlObject() throws IOException {
		File schemas = resource.getFile();
		TypeDefinitionRegistry registry = new SchemaParser().parse(schemas);
		RuntimeWiring wiring = buildRuntimeWiring();
		GraphQLSchema schema = new SchemaGenerator()
				.makeExecutableSchema(registry, wiring);
		return GraphQL.newGraphQL(schema).build();
	}

	private RuntimeWiring buildRuntimeWiring() {
		return RuntimeWiring.newRuntimeWiring()
				.type("Query", typewiring -> typewiring.dataFetcher("user", datafetcher)
														.dataFetcher("userbyname", datafetcher)
														.dataFetcher("allusers", datafetcher)
														.dataFetcher("articles", datafetcher)).build();
	}
}
