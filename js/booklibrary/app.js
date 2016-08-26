var express = require("express");
var bodyparser = require("body-parser");
var cookieParser = require("cookie-parser");
var passport = require("passport");
var session = require("express-session");

var port = process.env.PORT || 8080;

var app = express();

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

// pool.connect(function(err, client, done) {
// 	if(err) {
// 		console.error("error fetching client from pool", err);
// 	}

// 	client.query("SELECT $1::int AS number",["1"], function(err, result) {
// 		if(err) throw err;

// 		done();

// 		console.log(result.rows[0]);

// 		client.end(function(err) {
// 			if(err) throw err;
// 		});
// 	});
// });

// pool.on("error", function(err, client) {
// 	console.log("idle client error", err.message, err.stack);
// });

var nav = [{
            	link: "/books", text:"Books"
        	},{
            	link: "/authors", text:"Authors"
        	}];

var booksRouter = require("./src/routes/bookRoutes")(nav, pool);
var adminRouter = require("./src/routes/adminRoutes")(nav, pool);
var authRouter = require("./src/routes/authRoutes")(pool);


app.use(bodyparser.json());
app.use(bodyparser.urlencoded({
    extended: true
}));

app.use(express.static("public"));
app.use(cookieParser());
app.use(session({secret: "library"}));

require("./src/config/passport")(app, pool);

app.set("views", "./src/views");
app.set("view engine", "ejs");

app.use("/books", booksRouter);
app.use("/books/admin", adminRouter);
app.use("/auth", authRouter);

app.get("/", function(req, res) {
    res.render("index", {
            title: "Hello from EJS", 
            nav: nav
        });
});

app.listen(port, function(req, res) {
    console.log("running on port: " + port);
});
