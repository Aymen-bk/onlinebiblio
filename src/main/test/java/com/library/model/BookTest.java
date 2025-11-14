package com.library.model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class BookTest {

    private Book book;

    @Before
    public void setUp() {
        book = new Book();
    }

    @Test
    public void testBookCreation() {
        book.setId(1);
        book.setTitle("1984");
        book.setAuthor("George Orwell");
        book.setIsbn("978-0451524935");
        book.setCategory("Dystopian");
        book.setQuantity(5);
        book.setAvailableQuantity(3);

        assertEquals("Book ID should be 1", 1, book.getId());
        assertEquals("Title should be 1984", "1984", book.getTitle());
        assertEquals("Author should be George Orwell", "George Orwell", book.getAuthor());
        assertEquals("ISBN should match", "978-0451524935", book.getIsbn());
        assertEquals("Category should be Dystopian", "Dystopian", book.getCategory());
        assertEquals("Quantity should be 5", 5, book.getQuantity());
        assertEquals("Available quantity should be 3", 3, book.getAvailableQuantity());
    }

    @Test
    public void testIsAvailable_ShouldReturnTrueWhenAvailableQuantityGreaterThanZero() {
        book.setAvailableQuantity(5);
        assertTrue("Book should be available when available quantity > 0", book.isAvailable());
    }

    @Test
    public void testIsAvailable_ShouldReturnFalseWhenAvailableQuantityIsZero() {
        book.setAvailableQuantity(0);
        assertFalse("Book should not be available when available quantity = 0", book.isAvailable());
    }

    @Test
    public void testIsAvailable_ShouldReturnFalseWhenAvailableQuantityIsNegative() {
        book.setAvailableQuantity(-1);
        assertFalse("Book should not be available when available quantity < 0", book.isAvailable());
    }

    @Test
    public void testBookProperties() {
        book.setTitle("Clean Code");
        book.setAuthor("Robert C. Martin");
        book.setPublisher("Prentice Hall");
        book.setPublishedYear(2008);
        book.setPages(464);
        book.setLanguage("English");
        book.setDescription("A Handbook of Agile Software Craftsmanship");

        assertEquals("Title should be Clean Code", "Clean Code", book.getTitle());
        assertEquals("Author should be Robert C. Martin", "Robert C. Martin", book.getAuthor());
        assertEquals("Publisher should be Prentice Hall", "Prentice Hall", book.getPublisher());
        assertEquals("Published year should be 2008", 2008, book.getPublishedYear());
        assertEquals("Pages should be 464", 464, book.getPages());
        assertEquals("Language should be English", "English", book.getLanguage());
        assertTrue("Description should contain keyword", book.getDescription().contains("Agile"));
    }

    @Test
    public void testCoverImageField() {
        String imageUrl = "/images/books/clean-code.jpg";
        book.setCoverImage(imageUrl);

        assertEquals("Cover image URL should match", imageUrl, book.getCoverImage());
    }
}
