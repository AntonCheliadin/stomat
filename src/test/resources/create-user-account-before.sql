delete from user_account_role;
delete from user_account;

insert into user_account(id, first_name, last_name, password, active, email) values
(1, 'first', 'admin', '$2a$08$fNUHI3FnO3cbT6VAcClJOOsIq93f2101ud2RAKiZFAh7Y2h.oFRzC', true, 'testEmail1@test.com'),
(2, 'second', 'user', '$2a$08$fNUHI3FnO3cbT6VAcClJOOsIq93f2101ud2RAKiZFAh7Y2h.oFRzC', true, 'testEmail2@test.com');

insert into user_account_role(user_account_id, roles) values
(1, 'ADMIN'), (1, 'USER'),
(2, 'USER');
