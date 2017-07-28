drop table if exists `user`;

create table user (
	id bigint generated by default as identity,
	user_name varchar(64) not null,
	age bigint null,
    primary key (id)
);