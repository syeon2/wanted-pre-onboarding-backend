drop table if exists member;
drop table if exists post;
drop table if exists post_detail;

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
    title      varchar(255)     not null,
    view_count integer unsigned not null,
    user_id    bigint unsigned  not null,
    created_at timestamp        not null,
    updated_at timestamp        not null
);

create table post_detail
(
    post_id    bigint unsigned primary key,
    content    varchar(10000) not null,
    created_at timestamp      not null,
    updated_at timestamp      not null
);
