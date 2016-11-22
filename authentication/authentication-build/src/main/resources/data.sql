truncate schema public restart identity and commit no check
insert into User (id, username, password, failedLogins) 
values (0, 'John', 'Doe', 0);

insert into Role (id,role) values (0, 'admin');

--insert into user_roles (roles_id, user_id) values (0, 0);
--insert into roles_user (roles_id, user_id) values (0, 0);
