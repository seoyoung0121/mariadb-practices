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

-- insert
insert into member values (null, 'kickscar@gmail.com', password('1234'), '김서영', '개발팀', null);

select * from member;

insert into member(id, email, name, password, dept)
values(null, 'kickscat@gmail.com', '김서', password('1234'), '개발팀');

-- update
update member set email='kickscar1@gmail.com', password=password('234')
where id=2;

-- delete
delete from member
where id=2;

-- transaction(tx)
select id, email from member;

select @@autocommit; -- 1
insert into member values(null, 'kickscar2@gmail.com', '김서영2', password('123'), 'r개발팀', null);

-- tx: begin
set autocommit=0;

insert into member values(null, 'kickscar2@gmail.com', '김서영4', password('123'), 'r개발팀', null);

-- tx: end
commit;
-- rollback;
rollback;