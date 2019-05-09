insert into clinic (id, name)
values (nextval('hibernate_sequence'), 'test clinic');

insert into doctor(id, clinic_id, user_id, email)
values (12345678901,
        (select id from clinic where name = 'test clinic'),
        (select id from user_account where email = 'email@email.com'),
        'doctor@email.com');

insert into doctor_manager
values (12345678901, (select id from user_account where email = 'email@email.com'));