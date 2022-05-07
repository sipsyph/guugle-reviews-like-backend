drop table if exists creature_classification cascade;

create table creature_classification
( 
	id serial constraint creature_classification_pk primary key,
	"name" varchar(200),
	body_mass_growth_rate decimal(15,8) not null default 0,
	body_mass_potential_inclusion_type smallint not null default 1, -- 1 daily, 2 weekly, 3 monthly, 4 quarterly, 5 semi annually, 6 annually
	body_mass_potential_compounding_type smallint not null default 1, -- 1 daily, 2 weekly, 3 monthly, 4 quarterly, 5 semi annually, 6 annually
	lifespan_min_in_days integer not null default 1,
	lifespan_max_in_days integer not null default 1
);

drop table if exists creature cascade;

create table creature
( 
	id serial constraint creature_pk primary key,
	"name" varchar(200),
	creature_classification_id bigint references creature_classification (id) on delete cascade,
	age_in_years integer not null default 0,
	body_mass decimal(15,8) not null default 0,
	body_mass_potential decimal(15,8) not null default 0,
	birth_date date not null default current_date,
	average_daily_body_mass decimal(15,8) not null default 0,
	last_body_mass_change_date date not null default current_date,
	last_body_mass_before_change decimal(15,8) not null default 0,
	accumulated_daily_body_mass decimal(15,8) not null default 0
);

drop table if exists creature_event;

create table creature_event
( 
	id serial constraint creature_event_pk primary key,
	event_description varchar(200),
	creature_id bigint references creature (id) on delete cascade,
	body_mass_change_amount decimal(15,8) not null default 0,
	takeaway_type smallint not null default 0,
	event_date date not null default current_date
);


truncate table creature_classification cascade;
-- 1 daily, 2 weekly, 3 monthly, 4 quarterly, 5 semi annually, 6 annually
INSERT INTO creature_classification
(
	"name", 
	body_mass_growth_rate, 
	body_mass_potential_inclusion_type, 
	body_mass_potential_compounding_type, 
	lifespan_min_in_days, 
	lifespan_max_in_days
)
VALUES(
	'SERAPHIMAE', 
	0.9, 
	1, 
	1,
	1, 
	2
);

INSERT INTO creature_classification
(
	"name", 
	body_mass_growth_rate, 
	body_mass_potential_inclusion_type, 
	body_mass_potential_compounding_type, 
	lifespan_min_in_days, 
	lifespan_max_in_days
)
VALUES(
	'CHERUBIMAEDS', 
	0.8, 
	3, 
	2,
	15, 
	45
);

INSERT INTO creature_classification
(
	"name", 
	body_mass_growth_rate, 
	body_mass_potential_inclusion_type, 
	body_mass_potential_compounding_type, 
	lifespan_min_in_days, 
	lifespan_max_in_days
)
VALUES(
	'MANTICORE', 
	1.5, 
	6, 
	4,
	15, 
	45
);

INSERT INTO creature_classification
(
	"name", 
	body_mass_growth_rate, 
	body_mass_potential_inclusion_type, 
	body_mass_potential_compounding_type, 
	lifespan_min_in_days, 
	lifespan_max_in_days
)
VALUES(
	'BEHEMOTH', 
	2, 
	1, 
	2,
	15, 
	45
);

INSERT INTO creature_classification
(
	"name", 
	body_mass_growth_rate, 
	body_mass_potential_inclusion_type, 
	body_mass_potential_compounding_type, 
	lifespan_min_in_days, 
	lifespan_max_in_days
)
VALUES(
	'LEVIATHAN', 
	2, 
	1, 
	4,
	15, 
	45
);


create extension if not exists pgcrypto;


truncate table creature cascade;

--creature classif 1
do $$
begin
for r in 1..10000 loop
insert into creature("name", creature_classification_id, age_in_years, body_mass, body_mass_potential, birth_date, 
average_daily_body_mass, last_body_mass_change_date, last_body_mass_before_change, accumulated_daily_body_mass) 
values(
		(select "name" from creature_classification cc where cc.id = 1) || '-' || encode(gen_random_bytes(20),'base64'),
		1,
		0,
		cast( ((floor(random()*(500000-1000+1))+10)+random()) as decimal(15,8) ),
		0,
		current_date,
		0,
		current_date,
		0,
		0
);
end loop;
end;
$$;

--creature classif 2
do $$
begin
for r in 1..10000 loop
insert into creature("name", creature_classification_id, age_in_years, body_mass, body_mass_potential, birth_date, 
average_daily_body_mass, last_body_mass_change_date, last_body_mass_before_change, accumulated_daily_body_mass) 
values(
		(select "name" from creature_classification cc where cc.id = 2) || '-' || encode(gen_random_bytes(20),'base64'),
		1,
		0,
		cast( ((floor(random()*(500000-1000+1))+10)+random()) as decimal(15,8) ),
		0,
		current_date,
		0,
		current_date,
		0,
		0
);
end loop;
end;
$$;

--creature classif 3
do $$
begin
for r in 1..10000 loop
insert into creature("name", creature_classification_id, age_in_years, body_mass, body_mass_potential, birth_date, 
average_daily_body_mass, last_body_mass_change_date, last_body_mass_before_change, accumulated_daily_body_mass) 
values(
		(select "name" from creature_classification cc where cc.id = 3) || '-' || encode(gen_random_bytes(20),'base64'),
		1,
		0,
		cast( ((floor(random()*(500000-1000+1))+10)+random()) as decimal(15,8) ),
		0,
		current_date,
		0,
		current_date,
		0,
		0
);
end loop;
end;
$$;

--creature classif 4
do $$
begin
for r in 1..10000 loop
insert into creature("name", creature_classification_id, age_in_years, body_mass, body_mass_potential, birth_date, 
average_daily_body_mass, last_body_mass_change_date, last_body_mass_before_change, accumulated_daily_body_mass) 
values(
		(select "name" from creature_classification cc where cc.id = 4) || '-' || encode(gen_random_bytes(20),'base64'),
		1,
		0,
		cast( ((floor(random()*(500000-1000+1))+10)+random()) as decimal(15,8) ),
		0,
		current_date,
		0,
		current_date,
		0,
		0
);
end loop;
end;
$$;

--creature classif 5
do $$
begin
for r in 1..10000 loop
insert into creature("name", creature_classification_id, age_in_years, body_mass, body_mass_potential, birth_date, 
average_daily_body_mass, last_body_mass_change_date, last_body_mass_before_change, accumulated_daily_body_mass) 
values(
		(select "name" from creature_classification cc where cc.id = 5) || '-' || encode(gen_random_bytes(20),'base64'),
		1,
		0,
		cast( ((floor(random()*(500000-1000+1))+10)+random()) as decimal(15,8) ),
		0,
		current_date,
		0,
		current_date,
		0,
		0
);
end loop;
end;
$$;



