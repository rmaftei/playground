var passport = require("passport");

var config = function(app, pool) {

	var _passport = passport;

	app.use(passport.initialize());
	app.use(passport.session());

	passport.serializeUser(function(user, done) {
		console.log("serializeUser");

		done(null, user);
	});

	passport.deserializeUser(function(user, done) {
		console.log("deserializeUser");

		done(null, user);
	});

	require("./strategies/local.strategy")(pool);
};

module.exports = config;