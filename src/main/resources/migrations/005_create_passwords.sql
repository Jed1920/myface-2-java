create table passwords 
(
	id 				int unsigned not null auto_increment,
	user_id			int unsigned not null,
    hash_password	varchar(140) not null,
    salt			varchar(140) not null,
    
    primary key (id),
    foreign key (user_id) references users(id)
);
SELECT * FROM passwords;
 drop table passwords;
