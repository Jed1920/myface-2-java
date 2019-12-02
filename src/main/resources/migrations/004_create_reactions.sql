create table reactions
(
	id 			int unsigned not null auto_increment,
	user_id		int unsigned not null,
    post_id		int unsigned not null,
    reaction	varchar(140) not null,
    timestamp   datetime not null,
    
    primary key (id),
    foreign key (user_id) references users(id),
    foreign key (post_id) references posts(id)
);

drop table reactions;

SELECT * FROM reactions;
SELECT * FROM posts;
SELECT LAST_INSERT_ID() FROM reactions;
SELECT MAX(id) FROM reactions;
SELECT * FROM reactions WHERE id = 4;
SELECT * FROM reactions WHERE user_id = 4 and post_id = 29;
UPDATE reactions SET reaction= "like",timestamp =2019-12-02 14:55:53.134  WHERE id = 5;