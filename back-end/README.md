# Backend for the final task

It is a simple REST API written in Java using Spring Boot.
It supports the following methods:
* GET /meme - returns all the images in the database
* POST /meme - saves the image on the filesystem and stores metadata in the database
* PUT /meme/id - changes a meme that has a certain id
* DELETE /meme/id - delete a meme that has a certain id