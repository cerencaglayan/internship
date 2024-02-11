
INSERT INTO region (name)
VALUES ('Aegean');


INSERT INTO region (name)
VALUES ('Marmara');


INSERT INTO city (name)
VALUES ('İzmir');

INSERT INTO city (name)
VALUES ('Aydın');

INSERT INTO town (name, region_id , city_id)
VALUES ('Urla', 1, 1);


INSERT INTO company_type  (name)
VALUES ('Public');


INSERT INTO company (name, short_name , company_type_id , address_street , address_town)
VALUES ('Delta Akıllı Şirketler A.Ş.', 'ABC', 1, 'Gülbahce', 1);

INSERT INTO department_type  (name)
VALUES ('Development');

INSERT INTO user_role  (name)
VALUES ('ROLE_ADMIN');

INSERT INTO user_role  (name)
VALUES ('ROLE_USER');

INSERT INTO department (name, company_id , department_type_id , address_street , address_town)
VALUES ('IT', 1, 1, 'Gulbahce Teknopark', 1);

INSERT INTO user (user_role_id , department_id , name, surname, email , password, is_active)
VALUES (1, 1, 'Ceren', 'Çağlayan', 'cer79cag@gmail.com', 'aaaa', 1);

