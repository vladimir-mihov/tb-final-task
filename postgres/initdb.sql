create table images(
	id serial not null,
	title varchar(128) not null,
	image varchar(256) not null,
	constraint pk_id primary key(id)
);

