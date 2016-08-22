var express = require("express");

var authRouter = express.Router();

var router = function() {
	authRouter.route("/signUp")
		.post(function(req, res) {
			res.status(202).send(req.body);
		});

	return authRouter;
};

module.exports = router;