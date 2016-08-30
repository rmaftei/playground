var pool = require("./database.postgres");

var bookRepository = function() {

	var getIndex = function(callback) {
		pool.connect(function(err, client, done) {
                if(err) {
                    console.error("error", err);
                }

                client.query("SELECT * FROM carti", function(err, result) {
                    if(err) {
                        console.error("error", err);
                    }

                    done();

                    callback(result.rows);
                });
            });
	};

	var getById = function(id, callback) {
            pool.connect(function(err, client, done) {
                if(err) {
                    console.error("error", err);
                }

                client.query("SELECT * FROM carti WHERE id=$1", [id], function(err, result) {
                    if(err) {
                        console.error("error", err);
                    }

                    done();

                    if(result.rows.length === 0) {
                        callback(null);
                    } else {
                        callback(result.rows[0]);
                    }
                });
            });
	};

	return {
		getIndex: getIndex,
		getById: getById
	};
};

module.exports = bookRepository;