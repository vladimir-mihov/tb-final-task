
imageName := vladimirmihov/postgres-alpine

dataVolume := vlado-pgdata

containerName := vlado-pgsql

staticFolder := static

install:
	$(info 1. Creating a docker volume for the database)
	docker volume create $(dataVolume)

	$(info 2. Pulling docker image for the database)
	docker pull $(imageName)

	$(info 3. Installing node modules for the static content server)
	( cd static-content-server && exec npm install )

	$(info 4. Installing node modules for the front end)
	( cd front-end && exec npm install )

uninstall:
	$(info 1. Removing docker volume)
	docker volume rm $(dataVolume)

	$(info 2. Removing docker image)
	docker rmi $(imageName)

run-db:
	$(info Running docker container)
	docker run --name $(containerName) \
		-p 5432:5432 \
		-e POSTGRES_PASSWORD=vladovlado \
		-v $(dataVolume):/var/lib/postgresql/data \
		-d --rm $(imageName)

clean-db:
	$(info 1. Removing postgres container)
	docker rm -f $(containerName)

	$(info 2. Deleting postgres docker volume)
	docker volume rm $(dataVolume)

	$(info 3. Cleaning static folder)
	rm -f $(staticFolder)/*
