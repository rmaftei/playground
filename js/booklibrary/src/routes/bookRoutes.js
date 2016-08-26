var express = require("express");

var booksRouter = express.Router();

var router = function(nav, pool) {
    booksRouter.route("/")
        .get(function(req, res) {
            pool.connect(function(err, client, done) {
                if(err) {
                    console.error("error", err);
                }

                client.query("SELECT * FROM carti", function(err, result) {
                    if(err) {
                        console.error("error", err);
                    }

                    done();

                    res.render("books", {
                        title: "Books", 
                        nav: nav,
                        books: result.rows
                    });
                });
            });
        });


    booksRouter.route("/:id")
        .all(function(req, res, next) {
            var id = req.params.id;

            pool.connect(function(err, client, done) {
                if(err) {
                    console.error("error", err);
                }

                client.query("SELECT * FROM carti WHERE id=$1", [id], function(err, result) {
                    if(err) {
                        console.error("error", err);
                    }

                    done();

                    if(result.rows.length === 0) {
                        res.status(404).send("Book not found");
                    } else {
                        req.book = result.rows[0];
                        next();
                    }


                });
            });
        })
        .get(function(req, res) {
            res.render("book", {
                title: "Book", 
                nav: nav,
                book: req.book
            });
        });

        return booksRouter;
};


module.exports = router;
