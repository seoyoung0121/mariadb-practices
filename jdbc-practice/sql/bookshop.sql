

CREATE TABLE `book` (
                             id	int	NOT NULL auto_increment,
                             title	varchar(100)	NOT NULL,
                             status	enum('대여중', '재고있음')	default '재고있음' NOT NULL,
                             author_id	int	NOT NULL,
                             PRIMARY KEY (id),
                             foreign key (author_id) references author(`id`)
);

enum('재고있음',' 대여중');

CREATE TABLE `author` (
                        `id`	int	NOT NULL auto_increment,
                        `name`	varchar(45)	NOT NULL,
                        PRIMARY KEY (id)
);

desc author

