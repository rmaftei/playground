var express = require("express");

var adminRouter = express.Router();

var uuidGenerator = require("node-uuid");

var router = function(nav, pool) {

	adminRouter.route("/addBooks")
        .post(function(req, res) {
			var newBook = req.body;

			pool.connect(function(err, client, done) {
                if(err) {
                    console.error("error", err);
                }

                var generatedId = uuidGenerator.v4();

                client.query("INSERT INTO carti VALUES($1, $2, $3, $4, $5)", 
                		[generatedId, newBook.title, newBook.author, newBook.genre, 0], function(err, result) {
                    if(err) {
                        errorHandler(err, res);
                    }

                    done();

                });

                client.query("SELECT * FROM carti WHERE id=$1", [generatedId], function(err, result) {
                    if(err) {
                        console.err("error", err);

                        res.status(501).send(err);
                    }

                    done();

                    res.send(result.rows[0]);

                });
            });

		});

	return adminRouter;
};

function errorHandler(err, res) {
	console.err("error", err);
	res.status(501).send(err);
}

module.exports = router;