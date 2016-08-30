var pool = require("./database.postgres");

var userRepository = function() {
	var insert = function(signUpData, callback) {

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

                        callback(false);
                    }

                    done();

                    callback(true);
            });
        });     
	};

	var findByUsername = function(username, callback) {
		pool.connect(function(err, client, done) {
            if(err) {
                console.error("error", err);
            }

            client.query("SELECT * FROM utilizatori WHERE username = $1", 
            	[username], 
            	function(err, result) {
                    console.log("search after " + username);
	                if(err) {
	                    console.error("error", err);

                        callback(null);
	                }

	                if(result.rows.length === 0) {
                        console.log("User not found");

                        callback(null);
                    } else {
                        callback(result.rows[0]);
                    }
            });
        });
	};

    return {
        insert: insert,
        findByUsername: findByUsername
    };
};

module.exports = userRepository;