Create or replace function random_text(length integer) returns text as
$$
declare
  chars text[] := '{ chillhub , lorem ipsum , pasabugen , getaway , service , platform , lozol , akala mo talaga eh ,
 itigil mo kalokohan mo , makulet ka? , kanban , pain , high like the buildings in makati , Itoy pagpapalipas ng gabi sa tabi ng mambabarang. 
Kaya dudukutin ko na agad yung isang mata mo laglag din yung kabila. Lalagariin ka nang braso putol din yung kaliwa. 
Sasaksakin ng mababaw sa leeg butas din yaong batok. Mamartilyuhin ng isa sa ulo pero dalawa yaong dadagok. 
Babarilin ng isa sa dibdib dalawang bala yung papasok Palulunukin ng isang granada pero dalawa yaong sasabog. 
O bakit doble lagi yung resulta sa sinasabi mo na kulang? Hindi kaya ikaw din yung ginagamit na manika habang ikaw yung kinukulam?!}';
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

--select random_text(50);
--select random_name(3);

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
	"desc" varchar(10000),
	coins integer not null default 0,
	avatar_img varchar(50),
	name_style smallint,
	last_login_date date not null default current_date,
	del boolean not null default false
);

truncate table usr cascade;

do $$
begin
for r in 1..100000 loop
insert into usr("name", pass,"desc",coins,avatar_img,name_style,last_login_date,del) 
values(
		random_name(3),
		encode(gen_random_bytes(4),'base64'),
		random_text(10),
		cast( ((floor(random()*(5000-1+1))+1)+random()) as integer ),
		encode(gen_random_bytes(4),'base64'),
		cast( ((floor(random()*(30-1+1))+1)+random()) as integer ),
		current_date,
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
	usr_id bigint ,
	constraint usr_fk foreign key(usr_id) references usr(id),
	"name" varchar(100),
	body varchar(30000),
	category_type smallint not null default 1,
	ups int not null default 1,
	del boolean not null default false
);

truncate table memoir cascade;

do $$
begin
	for i in 1..100000 loop
		for j in 1..10 loop
			insert into memoir(place_id,usr_id,"name",body,category_type,ups,del) 
			values(
					i,
					i,
					random_name(10),
					random_text(15),
					cast( ((floor(random()*(5-1+1))+1)+random()) as integer ),
					cast( ((floor(random()*(50-1+1))+1)+random()) as integer ),
					false
			);
		end loop;
	end loop;
end;
$$;


