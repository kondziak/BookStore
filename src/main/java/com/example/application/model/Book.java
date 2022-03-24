package com.example.application.model;

import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, name = "author")
    private String author;

    @Column(nullable = false, name = "title")
    private String title;

    @Column(nullable = false, name = "publisher")
    private String publisher;

    @Column(nullable = false, name = "publication_date")
    private String publicationDate;

    @Column(nullable = false, name = "category")
    private String category;

    @Column(nullable = false, name = "language")
    private String language;

    @Column(nullable = false, name = "number_of_pages")
    private Integer numberOfPages;

    @Column(nullable = false, name = "price")
    private Double price;

    @Column(nullable = false, name = "description", columnDefinition = "text")
    private String description;

    @Column(nullable = false, name = "in_stock")
    private Integer inStock;

    @Transient
    private MultipartFile book_image;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MultipartFile getBook_image() {
        return book_image;
    }

    public void setBook_image(MultipartFile book_image) {
        this.book_image = book_image;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    public Integer getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(Integer numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public Integer getInStock() {
        return inStock;
    }

    public void setInStock(Integer inStock) {
        this.inStock = inStock;
    }
}
