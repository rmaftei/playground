var passport = require("passport");
var LocalStrategy = require("passport-local").Strategy;

var userRepository = require("../../repository/userRepository")();

module.exports = function(pool) {
	passport.use(new LocalStrategy({
		usernameField: "username",
		passwordFied: "password"
	}, function(username, password, doneAuth) {

        userRepository.findByUsername(username, function(user) {
            if(!user) {
                doneAuth(null, false);
            } else {
                if(user.password !== password) {
                    doneAuth(null, false);
                } else {
                    doneAuth(null, user);
                }
            }
        });
	}));
};