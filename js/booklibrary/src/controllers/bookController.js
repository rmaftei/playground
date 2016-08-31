var bookRepository = require("../repository/bookRepository")();

var bookController = function(bookService, nav) {

	var getIndex = function(req, res) {
		bookRepository.getIndex(function(books) {
			res.render("books", {
            	title: "Books", 
                nav: nav,
                books: books
            }); 
		});
    };

	var getById = function(req, res, next) {
     	var id = req.params.id;

        bookRepository.getById(id, function(book) {
        	bookService.getBookById(book.isbn, function(error, description) {
        		
        		book.description = description;

	        	res.render("book", {
	                title: "Book", 
	                nav: nav,
	                book: book
	            });	
        	});
        });
    };

	return {
		getIndex: getIndex,
		getById: getById
	};
};

module.exports = bookController;