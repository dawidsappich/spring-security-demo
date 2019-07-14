drop table if exists users;

create table users (
    id varchar (50) not null,
    username varchar(50) not null,
    password varchar(500) not null,
    primary key (id), unique(username)
)