var express = require("express");

var booksRouter = express.Router();

var router = function(nav) {

    var bookController = require("../controllers/bookController")(undefined, nav);
    
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
