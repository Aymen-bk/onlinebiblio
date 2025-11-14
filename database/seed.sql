-- Seed Data for Online Library
USE online_library;

-- Insert Admin User (password: admin123)

INSERT INTO users (username, email, password, full_name, role) VALUES
('admin', 'admin@library.com', '240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9', 'System Administrator', 'ADMIN');

-- Insert Sample Users (password: user123)
INSERT INTO users (username, email, password, full_name, role) VALUES
('john_doe', 'john@example.com', '6ca13d52ca70c883e0f0bb101e425a89e8624de51db2d2392593af6a84118090', 'John Doe', 'USER'),
('jane_smith', 'jane@example.com', '6ca13d52ca70c883e0f0bb101e425a89e8624de51db2d2392593af6a84118090', 'Jane Smith', 'USER'),
('bob_wilson', 'bob@example.com', '6ca13d52ca70c883e0f0bb101e425a89e8624de51db2d2392593af6a84118090', 'Bob Wilson', 'USER');

-- Insert Sample Books
INSERT INTO books (title, author, isbn, category, description, publisher, published_year, pages, language, quantity, available_quantity, cover_image) VALUES
('The Great Gatsby', 'F. Scott Fitzgerald', '978-0743273565', 'Fiction', 'A classic American novel set in the Jazz Age, exploring themes of wealth, love, and the American Dream.', 'Scribner', 1925, 180, 'English', 5, 5, 'https://covers.openlibrary.org/b/isbn/9780743273565-L.jpg'),
('To Kill a Mockingbird', 'Harper Lee', '978-0061120084', 'Fiction', 'A gripping tale of racial injustice and childhood innocence in the American South.', 'Harper Perennial', 1960, 324, 'English', 4, 4, 'https://covers.openlibrary.org/b/isbn/9780061120084-L.jpg'),
('1984', 'George Orwell', '978-0451524935', 'Science Fiction', 'A dystopian social science fiction novel and cautionary tale about totalitarianism.', 'Signet Classic', 1949, 328, 'English', 6, 6, 'https://covers.openlibrary.org/b/isbn/9780451524935-L.jpg'),
('Pride and Prejudice', 'Jane Austen', '978-0141439518', 'Romance', 'A romantic novel of manners that critiques the British landed gentry at the end of the 18th century.', 'Penguin Classics', 1813, 432, 'English', 3, 3, 'https://covers.openlibrary.org/b/isbn/9780141439518-L.jpg'),
('The Catcher in the Rye', 'J.D. Salinger', '978-0316769174', 'Fiction', 'A story about teenage rebellion and alienation narrated by Holden Caulfield.', 'Little, Brown and Company', 1951, 277, 'English', 4, 4, 'https://covers.openlibrary.org/b/isbn/9780316769174-L.jpg'),
('Harry Potter and the Sorcerer''s Stone', 'J.K. Rowling', '978-0439708180', 'Fantasy', 'The first novel in the Harry Potter series, following a young wizard''s journey.', 'Scholastic', 1997, 309, 'English', 8, 8, 'https://covers.openlibrary.org/b/isbn/9780439708180-L.jpg'),
('The Hobbit', 'J.R.R. Tolkien', '978-0547928227', 'Fantasy', 'A fantasy novel about the quest of home-loving Bilbo Baggins.', 'Houghton Mifflin', 1937, 310, 'English', 5, 5, 'https://covers.openlibrary.org/b/isbn/9780547928227-L.jpg'),
('Brave New World', 'Aldous Huxley', '978-0060850524', 'Science Fiction', 'A dystopian novel set in a futuristic World State of genetically modified citizens.', 'Harper Perennial', 1932, 288, 'English', 4, 4, 'https://covers.openlibrary.org/b/isbn/9780060850524-L.jpg'),
('The Lord of the Rings', 'J.R.R. Tolkien', '978-0544003415', 'Fantasy', 'An epic high-fantasy novel following the quest to destroy the One Ring.', 'Houghton Mifflin', 1954, 1178, 'English', 3, 3, 'https://covers.openlibrary.org/b/isbn/9780544003415-L.jpg'),
('Animal Farm', 'George Orwell', '978-0451526342', 'Fiction', 'An allegorical novella reflecting events leading up to the Russian Revolution.', 'Signet Classic', 1945, 141, 'English', 6, 6, 'https://covers.openlibrary.org/b/isbn/9780451526342-L.jpg'),
('The Chronicles of Narnia', 'C.S. Lewis', '978-0066238500', 'Fantasy', 'A series of seven fantasy novels set in the magical land of Narnia.', 'HarperCollins', 1950, 767, 'English', 4, 4, 'https://covers.openlibrary.org/b/isbn/9780066238500-L.jpg'),
('Moby-Dick', 'Herman Melville', '978-0142437247', 'Adventure', 'The narrative of Captain Ahab''s obsessive quest for the white whale.', 'Penguin Classics', 1851, 654, 'English', 3, 3, 'https://covers.openlibrary.org/b/isbn/9780142437247-L.jpg'),
('The Odyssey', 'Homer', '978-0140268867', 'Epic', 'An ancient Greek epic poem attributed to Homer, following Odysseus'' journey home.', 'Penguin Classics', -800, 541, 'English', 4, 4, 'https://covers.openlibrary.org/b/isbn/9780140268867-L.jpg'),
('Jane Eyre', 'Charlotte Brontë', '978-0141441146', 'Romance', 'A novel following the experiences of its eponymous heroine, including her growth to adulthood.', 'Penguin Classics', 1847, 624, 'English', 3, 3, 'https://covers.openlibrary.org/b/isbn/9780141441146-L.jpg'),
('Wuthering Heights', 'Emily Brontë', '978-0141439556', 'Romance', 'A tale of passion and revenge set on the Yorkshire moors.', 'Penguin Classics', 1847, 416, 'English', 3, 3, 'https://covers.openlibrary.org/b/isbn/9780141439556-L.jpg'),
('The Picture of Dorian Gray', 'Oscar Wilde', '978-0141439570', 'Fiction', 'A philosophical novel about a young man who sells his soul for eternal youth.', 'Penguin Classics', 1890, 254, 'English', 4, 4, 'https://covers.openlibrary.org/b/isbn/9780141439570-L.jpg'),
('Frankenstein', 'Mary Shelley', '978-0141439471', 'Horror', 'A Gothic novel about a scientist who creates a sapient creature in an unorthodox experiment.', 'Penguin Classics', 1818, 280, 'English', 5, 5, 'https://covers.openlibrary.org/b/isbn/9780141439471-L.jpg'),
('Dracula', 'Bram Stoker', '978-0141439846', 'Horror', 'An epistolary novel about the vampire Count Dracula''s attempt to move to England.', 'Penguin Classics', 1897, 488, 'English', 4, 4, 'https://covers.openlibrary.org/b/isbn/9780141439846-L.jpg'),
('The Adventures of Sherlock Holmes', 'Arthur Conan Doyle', '978-0141034355', 'Mystery', 'A collection of twelve short stories featuring the famous detective.', 'Penguin Classics', 1892, 307, 'English', 5, 5, 'https://covers.openlibrary.org/b/isbn/9780141034355-L.jpg'),
('The Alchemist', 'Paulo Coelho', '978-0062315007', 'Fiction', 'A philosophical book about following your dreams and listening to your heart.', 'HarperOne', 1988, 208, 'English', 6, 6, 'https://covers.openlibrary.org/b/isbn/9780062315007-L.jpg');

-- Insert Sample Borrowings
INSERT INTO borrowings (user_id, book_id, borrow_date, due_date, status) VALUES
(2, 1, DATE_SUB(CURDATE(), INTERVAL 5 DAY), DATE_ADD(CURDATE(), INTERVAL 9 DAY), 'BORROWED'),
(2, 3, DATE_SUB(CURDATE(), INTERVAL 3 DAY), DATE_ADD(CURDATE(), INTERVAL 11 DAY), 'BORROWED'),
(3, 6, DATE_SUB(CURDATE(), INTERVAL 10 DAY), DATE_SUB(CURDATE(), INTERVAL 3 DAY), 'RETURNED'),
(4, 10, DATE_SUB(CURDATE(), INTERVAL 7 DAY), DATE_ADD(CURDATE(), INTERVAL 7 DAY), 'BORROWED');

-- Update available quantities based on borrowings
UPDATE books SET available_quantity = available_quantity - 1 WHERE id IN (1, 3);

-- Insert Sample Reviews
INSERT INTO reviews (user_id, book_id, rating, review_text) VALUES
(2, 6, 5, 'An absolutely magical journey! This book captured my imagination from the first page.'),
(3, 6, 5, 'A timeless classic that both children and adults can enjoy. Highly recommended!'),
(4, 10, 4, 'A powerful allegory that remains relevant today. Orwell''s writing is sharp and insightful.');
