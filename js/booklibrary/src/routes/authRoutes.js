var express = require("express");

var uuidGenerator = require("node-uuid");

var passport = require("passport");

var authRouter = express.Router();

var router = function(pool) {
	authRouter.route("/signUp")
		.post(function(req, res) {

			var signUpData = req.body;
			
			pool.connect(function(err, client, done) {
	            if(err) {
	                console.error("error", err);
	            }

	            var generatedId = uuidGenerator.v4();

	            client.query("INSERT INTO utilizatori VALUES($1, $2, $3)", 
	            	[generatedId, signUpData.username, signUpData.password], 
	            	function(err, result) {
		                if(err) {
		                    console.error("error", err);
		                }

		                done();

	                    req.login(req.body, function() {
							res.redirect("/auth/profile");
						});
	            });
	        });		
			
		});

	authRouter.route("/signIn")
		.post(passport.authenticate("local", {
			failureRedirect: "/"
		}), function(req, res) {
			res.redirect("/auth/profile");
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