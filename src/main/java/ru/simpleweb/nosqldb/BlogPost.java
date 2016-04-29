package ru.simpleweb.nosqldb;

import org.mongojack.ObjectId;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BlogPost {
	private String id;

	@ObjectId
	@JsonProperty("_id")
	public String getId() {
		return id;
	}

	@ObjectId
	@JsonProperty("_id")
	public void setId(String id) {
		this.id = id;
	}
}