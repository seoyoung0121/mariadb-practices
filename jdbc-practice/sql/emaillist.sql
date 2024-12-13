-- DDL DML 연습


drop table member;

create table member(
    id int not null auto_increment,
    email varchar(200) not null,
    password varchar(64) not null,
    name varchar(50) not null,
    department varchar(100),

    primary key (id)
);

desc member;

alter table member add column juminbunho char(13) not null;

alter table member drop juminbunho;

alter table member add column juminbunho char(13) not null after email;

alter table member change column department dept varchar(100) not null;

alter table member add column profile text;

alter table member drop juminbunho;


select * from department;


CREATE TABLE `emaillist` (
    `id`	int	NOT NULL auto_increment,
   `first_name`	varchar(45)	NOT NULL,
    `last_name`	varchar(45)	NOT NULL,
    `email`	varchar(200)	NOT NULL,
    PRIMARY KEY (id)
)
ENGINE = InnoDB;

desc emaillist;

insert into  emaillist values (null, '둘', '리', 'dooly@gmail.com');

select id, first_name, last_name
from emaillist
order by id desc;

delete from emaillist where id = 1;

