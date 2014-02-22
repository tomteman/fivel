// web.js
var express = require("express");
var logfmt = require("logfmt");
var path = require('path');
var http = require('http');
var app = express();

app.use(logfmt.requestLogger());

app.set('views', __dirname);
app.engine('html', require('ejs').renderFile);

// Everything in public will be accessible from '/'
app.use(express.static(path.join(__dirname, 'public')));

// Everything in 'FivelBackend' && 'FivelHTML' will be "mounted" in '/public'
app.use('/public', express.static(path.join(__dirname, 'FivelBackend')));
app.use('/public', express.static(path.join(__dirname, 'FivelHTML')));

app.get('*', function(req, res) {
  res.render('index.html');
});

app.get('/alive', function (req, res)
{
res.send('Hello World!');
    
});
var port = Number(process.env.PORT || 5000);

var server = http.createServer(app);
server.listen(port, function() {
  console.log("Listening on " + port);
});