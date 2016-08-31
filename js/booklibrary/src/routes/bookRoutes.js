var express = require("express");

var booksRouter = express.Router();

var goodreadService = require("../services/goodreadsService")();

var router = function(nav) {

    var bookController = require("../controllers/bookController")(goodreadService, nav);
    
    booksRouter.use(function(req, res, next) {
        if(!req.user) {
            res.redirect("/");
        }

        next();
    });

    booksRouter.route("/")
        .get(bookController.getIndex);

    booksRouter.route("/:id")
        .all()
        .get(bookController.getById);

        return booksRouter;
};


module.exports = router;
