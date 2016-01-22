-- CREATE DATABASE news_maker DEFAULT CHARACTER SET utf8;

CREATE TABLE rss_master (
id  int,
name VARCHAR(256),
url VARCHAR(256),
memo VARCHAR(256),
lang VARCHAR(10),
credit_at TIMESTAMP,
PRIMARY KEY (id)
);

CREATE TABLE rss_seed (
id int,
rss_id int,
url VARCHAR(256),
memo VARCHAR(256),
credit_at TIMESTAMP,
PRIMARY KEY (id)
);

CREATE TABLE news(
id int,
rss_seed_id int,
title VARCHAR(256),
body TEXT,
memo VARCHAR(256),
lang VARCHAR(256),
credit_at TIMESTAMP,
PRIMARY KEY (id)
);
