
--- Il faut initialisé les roles !? sinon comment tu dois créer un utilisateur
CREATE DATABASE Ahmini;
USE Ahmini;
insert into roles(id,name) values (1,'ROLE_AMBASSADEUR');
insert into roles(id,name) values (1,'ROLE_PARRAINEUR');

-- J'ai approuvé l'utilisateur pour le test, sinon comment tu fais pour approuvé le compte ?
-- Il manque une fonctionnalité ?

update users set status='APPROUVE';
