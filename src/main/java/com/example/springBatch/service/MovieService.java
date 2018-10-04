package com.example.springBatch.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.springBatch.model.MovieDetails;
import com.example.springBatch.repository.MovieRepository;

@Component
public class MovieService {

	@Autowired
	private MovieRepository movieRepo;

	public MovieRepository getMovieRepo() {
		return movieRepo;
	}

	public void setMovieRepo(MovieRepository movieRepo) {
		this.movieRepo = movieRepo;
	}
	
	
	public MovieDetails saveEntity(MovieDetails m) {
		return movieRepo.save(m);
	}
	
	public List<MovieDetails> saveAll (List<MovieDetails> toSave){
		return movieRepo.saveAll(toSave);
	}
}
