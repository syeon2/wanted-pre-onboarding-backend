drop table if exists member;
drop table if exists post;
drop table if exists comment;

create table member
(
    id         bigint unsigned primary key auto_increment,
    email      varchar(255) unique not null,
    password   char(64)            not null,
    created_at timestamp           not null,
    updated_at timestamp           not null
);

create table post
(
    id         bigint unsigned primary key auto_increment,
    title      varchar(255)   not null,
    view_count integer unsigned not null,
    content    varchar(10000) not null,
    user_id    bigint unsigned not null,
    created_at timestamp      not null,
    updated_at timestamp      not null
);

create table comment
(
    id         bigint unsigned primary key auto_increment,
    reply      varchar(500) not null,
    created_at timestamp    not null,
    updated_at timestamp    not null,
    post_id    bigint unsigned not null,
    member_id  bigint unsigned not null
);
