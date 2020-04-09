package com.bookstore.admin.portal.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.bookstore.admin.portal.entity.Book;
import com.bookstore.admin.portal.service.BookService;


@Controller
@RequestMapping("/book")
public class BookController {

	@Autowired
	private BookService bookService;

	/** Adding the new book **/
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addBook(Model model) {
		Book book = new Book();
		model.addAttribute("book", book);
		return "addBook";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addBookPost(@ModelAttribute("book") Book book, HttpServletRequest request) {
		
		bookService.save(book);
		
		MultipartFile bookImage = book.getBookImage();
		System.out.println("bookImage======"+ bookImage);

		try {
			byte[] bytes = bookImage.getBytes();
			String name = book.getTitle() + ".jpg";
			File imgFile = new File("src/main/resources/static/image/book/"+name);
			imgFile.getParentFile().mkdirs(); // Will create parent directories if not exists
			imgFile.createNewFile();
			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(imgFile, false));
			stream.write(bytes);
			stream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:bookList";
	}
		
	/** Update the book **/
	@RequestMapping("/updateBook")
	public String updateBook(@RequestParam("id") Long id, Model model) {
		Book book = bookService.findOne(id);
		model.addAttribute("book", book);
		
		return "updateBook";
	}
	
	@RequestMapping(value="/updateBook", method=RequestMethod.POST)
	public String updateBookPost(@ModelAttribute("book") Book book, HttpServletRequest request) {
		
		bookService.save(book);
		
		MultipartFile bookImage = book.getBookImage();
		
		if(!bookImage.isEmpty()) {
			try {
				byte[] bytes = bookImage.getBytes();
				String name = book.getTitle() + ".jpg";			
				File imgFile = new File("src/main/resources/static/image/book/"+name);
				imgFile.getParentFile().mkdirs(); // Will create parent directories if not exists
				imgFile.createNewFile();
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(imgFile, false));
				stream.write(bytes);
				stream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return "redirect:bookList";
	}
	
	/** fetching the book List **/
	@RequestMapping("/bookList")
	public String bookList(Model model) {
		List<Book> bookList = bookService.findAll();
		model.addAttribute("bookList", bookList);		
		return "bookList";
		
	}
	/** fetching the single book by Id **/
	@RequestMapping("/bookInfo")
	public String bookInfo(@RequestParam("id") Long id, Model model) {
		Book book = bookService.findOne(id);
		model.addAttribute("book", book);
		return "bookInfo";
	}
	
	/** Deleting the single book by Id **/
	@RequestMapping(value="/remove", method=RequestMethod.POST)
	public String remove(
			@ModelAttribute("id") String id, Model model
			) {
		bookService.removeOne(Long.parseLong(id.substring(8)));
		List<Book> bookList = bookService.findAll();
		model.addAttribute("bookList", bookList);
		
		return "redirect:/book/bookList";
	}
	
	/** Deleting the list of book**/
	@RequestMapping(value="/book/removeList", method=RequestMethod.POST)
	public String removeList(@RequestBody ArrayList<String> bookIdList, Model model){
		for (String id : bookIdList) {
			String bookId =id.substring(8);
			bookService.removeOne(Long.parseLong(bookId));
		}
		
		return "delete success";
	}

}
