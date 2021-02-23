create sequence hibernate_sequence start with 1 increment by 1;
create table query (id bigint not null, query_text varchar(255), ss_user_id bigint, primary key (id));
create table ss_user (id bigint not null, spotify_id varchar(255), primary key (id));
alter table query add constraint FK9gh2irfcg0epgqiajqw10xkqw foreign key (ss_user_id) references ss_user;
