package com.bookstore.admin.portal.service;

import java.util.List;

import com.bookstore.admin.portal.entity.Book;

public interface BookService {

	Book save(Book book);

	List<Book> findAll();

	Book findOne(Long id);
	
	void removeOne(Long id);

}
