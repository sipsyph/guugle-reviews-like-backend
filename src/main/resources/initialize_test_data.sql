---------------------------------------------------------------------------------------------------------
--PLACE
drop table if exists place cascade;

create table place
( 
	id serial constraint place_pk primary key,
	display_name varchar(200),
	"name" varchar(200),
	coordinates_x decimal(15,2) not null default 0,
	coordinates_y decimal(15,2) not null default 0, 
	avg_rating decimal(15,2) not null default 0,
	memoirs_amount integer not null default 0, 
	engages_amount integer not null default 0
);

create extension if not exists pgcrypto;

truncate table place cascade;

do $$
begin
for r in 1..100000 loop
insert into place(display_name,"name", coordinates_x,coordinates_y, 
avg_rating, memoirs_amount, engages_amount) 
values(
		encode(gen_random_bytes(30),'base64'),
		encode(gen_random_bytes(30),'base64'),
		cast( ((floor(random()*(500000-1000+1))+10)+random()) as decimal(15,2) ),
		cast( ((floor(random()*(500000-1000+1))+10)+random()) as decimal(15,2) ),
		cast( ((floor(random()*(5-1+1))+1)+random()) as decimal(3,2) ),
		50,
		75
);
end loop;
end;
$$;
---------------------------------------------------------------------------------------------------------
--USR
drop table if exists usr cascade;

create table usr
( 
	id serial constraint usr_pk primary key,
	"name" varchar(50),
	pass varchar(50),
	"desc" varchar(200),
	del boolean not null default false
);

truncate table usr cascade;

do $$
begin
for r in 1..100000 loop
insert into usr("name", pass,"desc",del) 
values(
		encode(gen_random_bytes(25),'base64'),
		encode(gen_random_bytes(25),'base64'),
		encode(gen_random_bytes(100),'base64'),
		false
);
end loop;
end;
$$;

----------------------------------------------------------------------------------------------
--MEMOIR
drop table if exists memoir cascade;

create table memoir
( 
	id serial constraint memoir_pk primary key,
	place_id bigint ,
	constraint place_fk foreign key(place_id) references place(id),
	"name" varchar(50),
	body varchar(30000),
	category_type smallint not null default 1,
	ups int not null default 1,
	downs int not null default 0,
	del boolean not null default false
);

truncate table memoir cascade;

do $$
begin
	for i in 1..100000 loop
		for j in 1..10 loop
			insert into memoir(place_id,"name",body,category_type,ups,downs,del) 
			values(
					i,
					encode(gen_random_bytes(25),'base64'),
					encode(gen_random_bytes(1000),'base64'),
					cast( ((floor(random()*(5-1+1))+1)+random()) as integer ),
					cast( ((floor(random()*(50-1+1))+1)+random()) as integer ),
					cast( ((floor(random()*(50-1+1))+1)+random()) as integer ),
					false
			);
		end loop;
	end loop;
end;
$$;


