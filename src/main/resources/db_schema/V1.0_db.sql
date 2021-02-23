create sequence hibernate_sequence start with 1 increment by 1;
create table query (id bigint not null, query_text varchar(255), user_id bigint, primary key (id));
create table "user" (id bigint not null, spotify_id varchar(255), primary key (id));
alter table query add constraint FK8u411btodd5iehn8xmpv5x5xg foreign key (user_id) references "user";
