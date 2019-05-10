const express = require('express');
const app = express();

app.use(express.static('../back-end/static'));

app.listen(3333);
