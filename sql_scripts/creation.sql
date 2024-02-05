
INSERT INTO region (name)
VALUES ('Aegean');


INSERT INTO region (name)
VALUES ('Marmara');


INSERT INTO City (name)
VALUES ('İzmir');

INSERT INTO City (name)
VALUES ('Aydın');

INSERT INTO Town (name, region_id , city_id)
VALUES ('Urla', 1, 1);


INSERT INTO company_type  (name)
VALUES ('Public');


INSERT INTO Company (name, short_name , company_type_id , address_street , address_town)
VALUES ('Delta Akıllı Şirketler A.Ş.', 'ABC', 1, 'Gülbahce', 1);

INSERT INTO department_type  (name)
VALUES ('Development');

INSERT INTO user_role  (name)
VALUES ('ADMIN');

INSERT INTO user_role  (name)
VALUES ('USER');

INSERT INTO Department (name, company_id , department_type_id , address_street , address_town)
VALUES ('IT', 1, 1, 'Gulbahce Teknopark', 1);

INSERT INTO User (user_role_id , department_id , name, surname, email , password, is_active)
VALUES (1, 1, 'Ceren', 'Çağlayan', 'cer79cag@gmail.com', 'aaaa', 1);




