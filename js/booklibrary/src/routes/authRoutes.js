var express = require("express");

var uuidGenerator = require("node-uuid");

var passport = require("passport");

var authRouter = express.Router();

var userRepository = require("../repository/userRepository");


var pathAfterLogin = "/books";

var router = function(pool) {
	authRouter.route("/signUp")
		.post(function(req, res) {

			var signUpData = req.body;
			
			userRepository.insert(signUpData, function(saved) {
				if(saved) {
					req.login(req.body, function() {
	                	res.redirect(pathAfterLogin);
	                });
				} else {
					res.redirect("/");
				}	
			});
		});

	authRouter.route("/signIn")
		.post(passport.authenticate("local", {
			failureRedirect: "/"
		}), function(req, res) {
			res.redirect(pathAfterLogin);
		});

	authRouter.route("/profile")
		.all(function(req, res, next) {

			if(!req.user) {
				res.redirect("/");
			}

			next();
		})
		.get(function(req, res) {
			res.json(req.user);
		});

	return authRouter;
};

module.exports = router;