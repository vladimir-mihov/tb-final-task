# Final task

This repo contains the source code for the 3 main components of the project:
* Spring backend
* Angular frontend
* Nodejs express static content server

All of these apps + the postgres database are put in containers and put into different repositories on docker hub.

# Running the containers

For installation you are going to need docker and docker-compose.

First put your local IP in the docker-compose.yml file under `services > spring > environment > HOST_IP` and then run

```bash
docker-compose up -d
```

# Accessing the app

The app is available on localhost:80 in the browser

# Stopping the containers

You have 2 options.

Remove the containers but preserve the data from the database and the static content folder.

```bash
docker-compose down
```

Remove the containers and the persistent content.

```bash
docker-compose down -v
```

# Uninstallation

Remove every trace of the project

```bash
docker-compose down -v --rmi all
```

Or

```bash
sudo rm -rf /
```
