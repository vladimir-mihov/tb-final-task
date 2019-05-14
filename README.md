# Final task

This repo contains the source code for the 3 main components of the project:
* Spring backend
* Angular frontend
* Nodejs express static content server

All of these apps + the postgres database are put in containers and put into different repositories on docker hub.

# Installation

For installation you are going to need docker and docker-compose.

It is as simple as running

```
docker-compose up -d
```

# Stopping the containers

You have 2 options.

Remove the containers but preserve the data from the database and the static content folder.
```
docker-compose down
```

Remove the containers and the persistent content.
```
docker-compose down -v
```

# Uninstallation

Remove every trace of the project
```
docker-compose down -v --rmi all
```

Or
```
sudo rm -rf /
```
