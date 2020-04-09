package com.bookstore.admin.portal.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.admin.portal.entity.Book;
import com.bookstore.admin.portal.repository.BookRepository;



@Service
public class BookServiceImpl implements BookService{
	
	@Autowired
	private BookRepository bookRepository;
	
	public Book save(Book book) {
		return bookRepository.save(book);
	}
	
	public List<Book> findAll() {
		return (List<Book>) bookRepository.findAll();
	}
	
	public Book findOne(Long id) {
		Optional<Book> bookOpt =  bookRepository.findById(id);
		return bookOpt.get();
	}
	
	public void removeOne(Long id) {
		Optional<Book> book = bookRepository.findById(id);
		if(book.isPresent()) {
			bookRepository.delete(book.get());
		}			
	}
}
