var connect = require('connect'),
    serveStatic = require('serve-static'),
    directory = __dirname + '/src/main/webapp';
var server = connect().
	use(serveStatic(directory)).
    listen(6060);

console.log("Server started and listen to http://127.0.0.1:6060/client");