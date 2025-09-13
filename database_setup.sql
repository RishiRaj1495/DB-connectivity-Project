CREATE DATABASE books;
USE books;

CREATE TABLE authors (
    authorID INT PRIMARY KEY,
    firstName VARCHAR(50),
    lastName VARCHAR(50)
);

CREATE TABLE titles (
    isbn VARCHAR(20) PRIMARY KEY,
    title VARCHAR(100),
    editionNumber INT
);

CREATE TABLE authorISBN (
    authorID INT,
    isbn VARCHAR(20),
    FOREIGN KEY (authorID) REFERENCES authors(authorID),
    FOREIGN KEY (isbn) REFERENCES titles(isbn)
);

-- Sample data from PDF examples
INSERT INTO authors (authorID, firstName, lastName) VALUES
(1, 'Paul', 'Deitel'), (2, 'Harvey', 'Deitel'), (3, 'Abbey', 'Deitel'),
(4, 'Dan', 'Quirk'), (5, 'Michael', 'Morgano');

INSERT INTO titles (isbn, title, editionNumber) VALUES
('0133406954', 'Internet & World Wide Web How to Program', 5),
('0133807800', 'Java How to Program', 10),
('0133570924', 'Java How to Program, Late Objects Version', 10);

INSERT INTO authorISBN (authorID, isbn) VALUES
(1, '0133406954'), (2, '0133406954'), (3, '0133406954'),
(1, '0133807800'), (2, '0133807800'),
(1, '0133570924');
