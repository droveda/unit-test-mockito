INSERT INTO users (user_id, email, enabled, username, role, password) VALUES (1,	'fulano@gmail.com',	1,	'fulano',	'USER',	'$2a$10$t9YqA1YRWuZNrxQbfdTpLusVh1XXZHs3El.ilbte7EdHjpB9232x6');
INSERT INTO users (user_id, email, enabled, username, role, password) VALUES (2,	'ciclano@gmail.com',	1,	'ciclano',	'USER',	'$2a$10$t9YqA1YRWuZNrxQbfdTpLusVh1XXZHs3El.ilbte7EdHjpB9232x6');
INSERT INTO users (user_id, email, enabled, username, role, password) VALUES (3,	'beltrano@gmail.com',	1,	'beltrano',	'USER',	'$2a$10$t9YqA1YRWuZNrxQbfdTpLusVh1XXZHs3El.ilbte7EdHjpB9232x6');

INSERT INTO leilao (id, data_abertura, nome, valor_inicial, usuario_user_id, fechado) VALUES (1,	'2020-08-03',	'Tablet Xpto 3',	5.00,	1, false), (2,	'2020-08-03',	'Computador Z3',	500.00,	3, false);

INSERT INTO lance (id, data, valor, leilao_id, usuario_user_id) VALUES (1,	'2020-08-04',	10.00,	1,	3), (2,	'2020-08-04',	15.00,	1,	2);