var http = require("http");
var xml2js = require("xml2js");
var parser = xml2js.Parser({explicitArray: false});

var goodreadsService = function() {
	var getBookById = function(id, callback) {

		

		callback(null, "Book description");
	};

	return {
		getBookById: getBookById
	};
};

module.exports = goodreadsService;