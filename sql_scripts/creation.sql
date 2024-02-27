
INSERT INTO formail.region (name)
VALUES ('Aegean');


INSERT INTO formail.region (name)
VALUES ('Marmara');


INSERT INTO formail.city (name)
VALUES ('İzmir');

INSERT INTO formail.city (name)
VALUES ('Aydın');

INSERT INTO formail.town (name, region_id , city_id)
VALUES ('Urla', 1, 1);


INSERT INTO formail.company_type  (name)
VALUES ('Public');


INSERT INTO formail.company (name, short_name , company_type_id , address_street , address_town)
VALUES ('Delta Akıllı Şirketler A.Ş.', 'ABC', 1, 'Gülbahce', 1);

INSERT INTO formail.department_type  (name)
VALUES ('Development');

INSERT INTO formail.user_role  (name)
VALUES ('ROLE_ADMIN');

INSERT INTO formail.user_role  (name)
VALUES ('ROLE_USER');

INSERT INTO formail.department (name, company_id , department_type_id , address_street , address_town)
VALUES ('IT', 1, 1, 'Gulbahce Teknopark', 1);

INSERT INTO formail.user (user_role_id , department_id , name, surname, email , password, is_active)
VALUES (1, 1, 'Ceren', 'Çağlayan', 'cer79cag@gmail.com', '', 1);