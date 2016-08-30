var Pool = require('pg-pool');

var pool = new Pool();

var config = {
	user:"jack",
	database: "carti",
	password:"",
	port: 5432,
	max: 10,
	ideTimeoutMillis: 30000
};

var pool = new Pool(config);

module.exports = pool;