var passport = require("passport");
var LocalStrategy = require("passport-local").Strategy;


module.exports = function(pool) {
	passport.use(new LocalStrategy({
		usernameField: "username",
		passwordFied: "password"
	}, function(username, password, doneAuth) {

		pool.connect(function(err, client, done) {
            if(err) {
                console.error("error", err);
            }

            client.query("SELECT * FROM utilizatori WHERE username = $1", 
            	[username], 
            	function(err, result) {
	                if(err) {
	                    console.error("error", err);
	                }

	                done();

                    if(result.rows.length === 0) {
                        doneAuth("Bad username/password - db", null);
                    } else {

                    	var user = result.rows[0];

                    	if(user.password === password) {
                    		doneAuth(null, user);	
                    	}

                        doneAuth(null, false);
                    }
            });
        });
	}));
};