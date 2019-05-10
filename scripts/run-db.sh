docker run --name vlado-pgsql \
	-p 5432:5432 \
	-e POSTGRES_PASSWORD=vladovlado \
	-v pgdata:/var/lib/postgresql/data \
	-d --rm postgres
