var passport = require("passport");

var config =function(app) {
	app.use(passport.initialize());
	app.use(passport.session());

	passport.serializeUser(function(user, done) {
		done(null, user.id);
	});

	passport.deserializeUser(function(userId, done) {
		done(null, user);
	});

	require("./strategies/local.strategy")();
};

module.exports = config;