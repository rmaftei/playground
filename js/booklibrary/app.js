var express = require("express");
var bodyparser = require("body-parser");
var cookieParser = require("cookie-parser");
var passport = require("passport");
var session = require("express-session");

var port = process.env.PORT || 8080;

var app = express();

var nav = [{
            	link: "/books", text:"Books"
        	},{
            	link: "/authors", text:"Authors"
        	}];

var booksRouter = require("./src/routes/bookRoutes")(nav);
var adminRouter = require("./src/routes/adminRoutes")(nav);
var authRouter = require("./src/routes/authRoutes")();

app.use(bodyparser.json());
app.use(bodyparser.urlencoded({
    extended: true
}));

app.use(express.static("public"));
app.use(cookieParser());
app.use(session({secret: "library"}));

require("./src/config/passport")(app);

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
