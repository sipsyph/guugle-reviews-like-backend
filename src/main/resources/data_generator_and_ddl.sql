Create or replace function random_text(length integer) returns text as
$$
declare
  chars text[] := '{ chillhub , lorem ipsum , pasabugen , getaway , service , platform , lozol , akala mo talaga eh ,
 itigil mo kalokohan mo , makulet ka? , kanban , pain , high like the buildings in makati , Itoy pagpapalipas ng gabi sa tabi ng mambabarang, 
Kaya dudukutin ko na agad yung isang mata mo laglag din yung kabila, Lalagariin ka nang braso putol din yung kaliwa, 
Sasaksakin ng mababaw sa leeg butas din yaong batok, Mamartilyuhin ng isa sa ulo pero dalawa yaong dadagok, 
Babarilin ng isa sa dibdib dalawang bala yung papasok, Palulunukin ng isang granada pero dalawa yaong sasabog, 
O bakit doble lagi yung resulta sa sinasabi mo na kulang?, Hindi kaya ikaw din yung ginagamit na manika habang ikaw yung kinukulam?! }';
  result text := '';
  i integer := 0;
begin
  if length < 0 then
    raise exception 'Given length cannot be less than 0';
  end if;
  for i in 1..length loop
    result := result || chars[1+random()*(array_length(chars, 1)-1)];
  end loop;
  return result;
end;
$$ language plpgsql;

Create or replace function random_place_name(length integer) returns text as
$$
declare
  chars text[] := '{ SM Bacoor , Mcdonalds , Jollibee , Congrats BBM , Antoks , B-Side , 
  SM Dasma , Lozol Dosmo , Time Ka Na Computer Shop , Dasmarinas , Cavite City , Bacoor , 
  Aguinaldo Highway , Center of Information Technology in Cavite , 2x Na Yan! , Memoirea  }';
  result text := '';
  i integer := 0;
begin
  if length < 0 then
    raise exception 'Given length cannot be less than 0';
  end if;
  for i in 1..length loop
    result := result || chars[1+random()*(array_length(chars, 1)-1)];
  end loop;
  return result;
end;
$$ language plpgsql;

Create or replace function random_name(length integer) returns text as
$$
declare
  chars text[] := '{ Syph , Mateo , Manny , Trevor , Steve , Ash , Michael , Jordan , RJ , Yang , Ada , Lovelace , Einstein , 
Albert , Adolf , Winston , Ann , Alaric , Satoshi , Nakamoto , Gilfoyle , Dinesh , Richard , Hendricks , Russ , Bill , Gates }';
  result text := '';
  i integer := 0;
begin
  if length < 0 then
    raise exception 'Given length cannot be less than 0';
  end if;
  for i in 1..length loop
    result := result || chars[1+random()*(array_length(chars, 1)-1)] || ' ';
  end loop;
  return result;
end;
$$ language plpgsql;

CREATE OR REPLACE FUNCTION random_unique_array_of_int(max_arr_len integer, min integer, max integer) RETURNS integer[] AS $BODY$
begin
	return (
		SELECT ARRAY_AGG( a.n ) FROM (    
			SELECT ROUND(RANDOM()*(max - min) + min)::INT n FROM GENERATE_SERIES(min,max) 
			GROUP BY 1 LIMIT max_arr_len
		) a
	);
end
$BODY$ LANGUAGE plpgsql;

--select random_text(50);
--select random_name(3);
--select encode(gen_random_bytes(30),'base64') 
--select cast(current_date - (random() * (interval '360 days')) + '1 days' as date);
--select random_unique_array_of_int(10, 1, 20);

---------------------------------------------------------------------------------------------------------
--PLACE
drop table if exists place cascade;

create table place
( 
	id serial constraint place_pk primary key,
	address varchar(400),
	"name" varchar(200),
	coordinates_x decimal(15,2) not null default 0,
	coordinates_y decimal(15,2) not null default 0
);

create extension if not exists pgcrypto;

truncate table place cascade;

do $$
begin
for r in 1..10000 loop
insert into place(address,"name", coordinates_x,coordinates_y) 
values(
		random_place_name(4),
		random_place_name(2),
		cast( ((floor(random()*(500000-1000+1))+10)+random()) as decimal(15,2) ),
		cast( ((floor(random()*(500000-1000+1))+10)+random()) as decimal(15,2) )
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
	constraint constraint_name unique("name"),
	pass varchar(50),
	"desc" varchar(10000),
	usr_type SMALLINT NOT NULL DEFAULT 1,
	is_premium boolean not null default false,
	coins integer not null default 0,
	avatar_img varchar(50),
	name_style smallint,
	last_login_date date not null default current_date,
	created_date date not null default current_date,
	del boolean not null default false
);

truncate table usr cascade;

do $$
begin
for r in 1..10000 loop
insert into usr("name", pass,"desc",usr_type,is_premium,coins,avatar_img,name_style,last_login_date,created_date,del) 
values(
		random_name(3) || r,
		encode(gen_random_bytes(4),'base64'),
		random_text(10),
		cast( ((floor(random()*(5-1+1))+1)+random()) as integer ),
		false,
		cast( ((floor(random()*(5000-1+1))+1)+random()) as integer ),
		encode(gen_random_bytes(4),'base64'),
		cast( ((floor(random()*(30-1+1))+1)+random()) as integer ),
		cast(current_date - (random() * (interval '360 days')) + '1 days' as date),
		cast(current_date - (random() * (interval '360 days')) + '1 days' as date),
		false
);
end loop;
end;
$$;

----------------------------------------------------------------------------------------------
--MEMOIR2 WITH NO DESCRIPTION DERIVED FROM MEMOIR_DESC TABLE
drop table if exists memoir2 cascade;

create table memoir2
( 
	id serial constraint memoir2_pk primary key,
	place_id bigint ,
	constraint place_fk foreign key(place_id) references place(id),
	usr_id bigint ,
	constraint usr_fk foreign key(usr_id) references usr(id),
	"name" varchar(100),
	body varchar(30000),
	category_type smallint not null default 1,
	people_traffic_type smallint not null default 1,
	created_date date not null default current_date,
	ups int not null default 1,
	downs int not null default 0,
	del boolean not null default false
);

create index memoir2_place_id_idx
on memoir2(place_id);

--create index memoir_category_type_idx
--on memoir(category_type);
--
--create index memoir_desc_type_idx
--on memoir(desc_type);
--
--create index memoir_people_traffic_type_idx
--on memoir(people_traffic_type);

--create index memoir_name_idx
--on memoir(name);
--
--create index memoir_body_idx
--on memoir(body);

truncate table memoir2 cascade;

--Total time (ms)	88744  on 100k records
--Execute time (ms)	1315677 on 1m records June 6 2022
--Execute time (ms)	1934703 on 1m records June 11 2022
--Execute time (ms)	1270497
do $$
begin
	for i in 1..10000 loop
		for j in 1..5 loop
			insert into memoir2(place_id,usr_id,"name",body,category_type,people_traffic_type,created_date,ups,downs,del) 
			values(
					i,
					i,
					random_name(10),
					random_text(15),
					cast( ((floor(random()*(5-1+1))+1)+random()) as integer ),
					cast( ((floor(random()*(5-1+1))+1)+random()) as integer ),
					cast(current_date - (random() * (interval '360 days')) + '1 days' as date),
					cast( ((floor(random()*(50-1+1))+1)+random()) as integer ),
					cast( ((floor(random()*(50-1+1))+1)+random()) as integer ),
					false
			);
		end loop;
	end loop;
end;
$$;


----------------------------------------------------------------------------------------------
--MEMOIR DESC
drop table if exists memoir_desc cascade;

create table memoir_desc
( 
	id serial constraint memoir_desc_pk primary key,
	memoir_id bigint ,
	constraint place_fk foreign key(memoir_id) references memoir2(id),
	desc_type smallint not null default 1
);

create index memoir_desc_memoir_id_idx
on memoir_desc(memoir_id);

--create index memoir_desc_type_idx
--on memoir_desc(desc_type);
--
--drop index memoir_desc_type_idx;

create index memoir_id_and_desc_type_idx
on memoir_desc(memoir_id, desc_type);


truncate table memoir_desc cascade;

insert into memoir_desc(memoir_id,desc_type) 
select m.id,unnest(random_unique_array_of_int(7,1,10)) from memoir2 m;


--MEMOIR WITH DESC_TYPE AS AN INT ARRAY
----------------------------------------------------------------------------------------------
--MEMOIR
drop table if exists memoir cascade;

create table memoir
( 
	id serial constraint memoir_pk primary key,
	place_id bigint ,
	constraint place_fk foreign key(place_id) references place(id),
	usr_id bigint ,
	constraint usr_fk foreign key(usr_id) references usr(id),
	"name" varchar(100),
	body varchar(30000),
	category_type smallint not null default 1,
	desc_type integer ARRAY not null default array[]::integer[],
	people_traffic_type smallint not null default 1,
	created_date date not null default current_date,
	ups int not null default 1,
	downs int not null default 0,
	del boolean not null default false
);

create index memoir_place_id_idx
on memoir(place_id);

create index memoir_desc_type_idx
on memoir(desc_type);



truncate table memoir cascade;

--Total time (ms)	88744  on 100k records
--Execute time (ms)	1315677 on 1m records
do $$
begin
	for i in 1..10000 loop
		for j in 1..5 loop
			insert into memoir(place_id,usr_id,"name",body,category_type,desc_type,people_traffic_type,created_date,ups,downs,del) 
			values(
					i,
					i,
					random_name(10),
					random_text(15),
					cast( ((floor(random()*(5-1+1))+1)+random()) as integer ),
					random_unique_array_of_int(10,1,20),
					cast( ((floor(random()*(5-1+1))+1)+random()) as integer ),
					cast(current_date - (random() * (interval '360 days')) + '1 days' as date),
					cast( ((floor(random()*(50-1+1))+1)+random()) as integer ),
					cast( ((floor(random()*(50-1+1))+1)+random()) as integer ),
					false
			);
		end loop;
	end loop;
end;
$$;


----------------------------------------------------------------------------------------------
--COMMENT
drop table if exists "comment" cascade;

create table "comment"
( 
	id serial constraint comment_pk primary key,
	usr_id bigint ,
	constraint usr_fk foreign key(usr_id) references usr(id),
	memoir_id bigint ,
	constraint memoir_fk foreign key(memoir_id) references memoir(id),
	parent_comment_id bigint ,
	constraint parent_comment_fk foreign key(parent_comment_id) references comment(id),
	body varchar(20000),
	created_date date not null default current_date,
	ups int not null default 1,
	downs int not null default 0,
	del boolean not null default false
);

create index comment_usr_id_idx
on "comment"(usr_id);

create index comment_parent_comment_id_idx
on "comment"(parent_comment_id);

create index comment_memoir_id_idx
on "comment"(memoir_id);

create index comment_upvotes_idx
on "comment"((ups-downs));

truncate table "comment" cascade;

do $$
begin
	for j in 1..5 loop
		insert into "comment"(usr_id,memoir_id,parent_comment_id,body,created_date,ups,downs,del) 
		select m.usr_id,
		m.id,
		random_comment_in_memoir.id,
		random_text(9),
		cast(current_date - (random() * (interval '360 days')) + '1 days' as date),
		cast( ((floor(random()*(50-1+1))+1)+random()) as integer ),
		cast( ((floor(random()*(50-1+1))+1)+random()) as integer ),
		false
		from memoir m
		left join (
			select count(c.memoir_id) as comments_count, c.memoir_id  from "comment" c
			group by c.memoir_id
		) as comments_count_per_memoir on comments_count_per_memoir.memoir_id = m.id 
		left join lateral ( 
			select c.id from "comment" c where c.memoir_id = m.id 
			offset floor(random() * comments_count_per_memoir.comments_count)
			fetch first 1 rows only
		) as random_comment_in_memoir on true;
	end loop;
end;
$$;




