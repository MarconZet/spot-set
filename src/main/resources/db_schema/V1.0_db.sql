create table query (id bigserial not null, query_text varchar(255), user_id bigint, primary key (id));
create table "user" (id bigserial not null, spotify_id varchar(255), primary key (id));
alter table query add constraint FK8u411btodd5iehn8xmpv5x5xg foreign key (user_id) references "user";