/**
 * 
 */
package com.edhy.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.edhy.api.dto.Book;

/**
 * 
 */
@RestController
@RequestMapping("/api/books")
public class BooksController {

	List<Book> books = new ArrayList<>();

	public BooksController() {
		// TODO Auto-generated constructor stub
		books.addAll(List.of(new Book(1l, "one", "frank kaf"), new Book(2l, "two", "jhony"),
				new Book(3l, "three", "frank kaf"), new Book(4l, "four", "Daniels polosky"),
				new Book(5l, "five", "frank kaf"), new Book(6l, "six", "william blake"),
				new Book(5l, "nine", "kasenbasch"), new Book(6l, "six", "la sombra"),
				new Book(7l, "seven", "marquez")));
	}

	@GetMapping
	public List<Book> getBooks(@RequestParam(required = false) String name) {

		if (name == null) {
			return books;
		}

		return books.stream().filter(book -> book.getName().equalsIgnoreCase(name)).toList();
	}

	@PostMapping
	public void createBook(@RequestBody Book bookNew) {

		boolean isNew = books.stream().noneMatch(book -> book.getName().equalsIgnoreCase(bookNew.getName()));

		if (isNew) {
			books.add(bookNew);
		}

	}
}
