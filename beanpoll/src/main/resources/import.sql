
-- User "beanpoll" is both an admin and a judge, and has password "beanpollpassword"
INSERT INTO User (id, admin, judge, name, salt, secret, team_id) VALUES ('UA', true, true, 'beanpoll', 'b97ebe9b-4db5-4dc4-8c31-c1d46df88d83', '$argon2id$v=19$m=1024,t=2,p=2$/bDvwgcKFx/SLUhz768+mQ$qdptwK3x7Z8u6/LBE9FjW0+IAZtVINmczJjEOGmhBUc', NULL);

-- User "beanadmin" is an admin but not a judge, and has password "beanadminpassword"
INSERT INTO User (id, admin, judge, name, salt, secret, team_id) VALUES ('UBA', true, false, 'beanadmin', 'fd981cdb-fe4e-42e6-893d-2935f21e95ab', '$argon2id$v=19$m=1024,t=2,p=2$5/afcBFwYdIw2Rof2wBTXg$KLlRoawxCr51fcesq+Q8YB65PRrHPqDQFX0WlO35b9s', NULL);

-- User "beanadmin" is a judge but not an admin, and has password "beanjudgepassword"
INSERT INTO User (id, admin, judge, name, salt, secret, team_id) VALUES ('UBJ', false, true, 'beanjudge', 'b7cb9d0b-bdd1-41f7-ba03-702462f129a3', '$argon2id$v=19$m=1024,t=2,p=2$6JmQTLgp2EwfJ/10htuLWw$vvn79tSS5RdaZSCZV9oaZybaDm5BcYXR3RDthUufH9U', NULL);

-- TestContest
INSERT INTO Contest (id, enabled, ends, name, running, starts) VALUES ('a51c930e-ea6a-4c27-8764-06ee921dca3b', false, '2018-05-31 18:00:00', 'TestContest', false, '2018-01-01 08:00:00');

-- TestTeam
INSERT INTO Team (id, flag, name, contest_id) VALUES ('744dce04-1126-4d65-a2f9-daebb8cfec5b', 'BONUSPOINTS', 'TestTeam', 'a51c930e-ea6a-4c27-8764-06ee921dca3b');

-- User "TestTeamUser" is a team user for TestTeam and has password "TestTeamTestTeam"
INSERT INTO User (id, admin, judge, name, salt, secret, team_id) VALUES ('f91405ca-5750-4845-82c5-c8c40abc81fb', false, false,'TestTeamUser', '8b8e089c-c093-4541-997a-7fa6d5efe202', '$argon2id$v=19$m=1024,t=2,p=2$qdiP4n1m7AS9/SM5L1XJ3g$vP/2EcD/jtS67d3RzKSR0QTORivPPSiC0MG9EGwkIIc', '744dce04-1126-4d65-a2f9-daebb8cfec5b');

-- TestResource
INSERT INTO Resource (id, address, name, port, scoring, type, weight, contest_id) VALUES ('d24ceab6-b371-42ee-9de5-9b94814300f3', 'httpbin.org', 'TestHTTP', 80, true, 'HTTP', 1, 'a51c930e-ea6a-4c27-8764-06ee921dca3b');

-- Parameters for TestResource
INSERT INTO Parameter VALUES ('b7e9aac3-990e-4f48-a513-6a49d9debcf8', 'HTTP_RESOLVER', NULL, 'd24ceab6-b371-42ee-9de5-9b94814300f3');

-- TestTask
INSERT INTO Task (id, starts, description, ends, name, pointValue, contest_id) VALUES ('d59f69a0-b789-40ca-a45d-d1c5bd8adceb', '2018-01-01 08:00:00', 'Upload a file.', '2018-05-31 18:00:00', 'TestTask', 100, 'a51c930e-ea6a-4c27-8764-06ee921dca3b');


-- Values below this line are for testing only
# INSERT INTO Contest (id, name, running) VALUES ('C0', 'Cyber Patriot', FALSE);
# INSERT INTO Contest (id, name, running) VALUES ('C1', 'CCDC', FALSE);
# INSERT INTO Contest (id, name, running) VALUES ('C2', 'CDX', FALSE);
# INSERT INTO Contest (id, name, running) VALUES ('C3', 'Panoply', FALSE);
# INSERT INTO Team (id, name, flag, contest_id) VALUES ('T0', 'Blue Man Group', 'Ready, Go!', 'C0');
# INSERT INTO User (id, name, salt, secret, admin, judge, team_id) VALUES ('T0U0', 'user0.0', 'judgebean', '$argon2id$v=19$m=1024,t=2,p=2$B6Lw0JZ5b+QqOmOba0ossQ$oYHSH76XD/H94adXaAIWR+c9/vAE09XZ6dFtAsjyXWA', FALSE, FALSE, 'T0');
# INSERT INTO User (id, name, salt, secret, admin, judge, team_id) VALUES ('T0U1', 'user0.1', 'judgebean', '$argon2id$v=19$m=1024,t=2,p=2$B6Lw0JZ5b+QqOmOba0ossQ$oYHSH76XD/H94adXaAIWR+c9/vAE09XZ6dFtAsjyXWA', FALSE, FALSE, 'T0');
# INSERT INTO Team (id, name, flag, contest_id) VALUES ('T1', 'Bala Morgab', 'BMG', 'C0');
# INSERT INTO User (id, name, salt, secret, admin, judge, team_id) VALUES ('T1U0', 'user1.0', 'judgebean', '$argon2id$v=19$m=1024,t=2,p=2$B6Lw0JZ5b+QqOmOba0ossQ$oYHSH76XD/H94adXaAIWR+c9/vAE09XZ6dFtAsjyXWA', FALSE, FALSE, 'T1');
# INSERT INTO User (id, name, salt, secret, admin, judge, team_id) VALUES ('T1U1', 'user1.1', 'judgebean', '$argon2id$v=19$m=1024,t=2,p=2$B6Lw0JZ5b+QqOmOba0ossQ$oYHSH76XD/H94adXaAIWR+c9/vAE09XZ6dFtAsjyXWA', FALSE, FALSE, 'T1');
# INSERT INTO Team (id, name, flag, contest_id) VALUES ('T2', 'General Motors Bicyclists', 'GMb', 'C1');
# INSERT INTO User (id, name, salt, secret, admin, judge, team_id) VALUES ('T2U0', 'user2.0', 'judgebean', '$argon2id$v=19$m=1024,t=2,p=2$B6Lw0JZ5b+QqOmOba0ossQ$oYHSH76XD/H94adXaAIWR+c9/vAE09XZ6dFtAsjyXWA', FALSE, FALSE, 'T2');
# INSERT INTO User (id, name, salt, secret, admin, judge, team_id) VALUES ('T2U1', 'user2.1', 'judgebean', '$argon2id$v=19$m=1024,t=2,p=2$B6Lw0JZ5b+QqOmOba0ossQ$oYHSH76XD/H94adXaAIWR+c9/vAE09XZ6dFtAsjyXWA', FALSE, FALSE, 'T2');
# INSERT INTO Team (id, name, flag, contest_id) VALUES ('T3', 'Greater Midwest Baseball', 'GMB', 'C1');
# INSERT INTO User (id, name, salt, secret, admin, judge, team_id) VALUES ('T3U0', 'user3.0', 'judgebean', '$argon2id$v=19$m=1024,t=2,p=2$B6Lw0JZ5b+QqOmOba0ossQ$oYHSH76XD/H94adXaAIWR+c9/vAE09XZ6dFtAsjyXWA', FALSE, FALSE, 'T3');
# INSERT INTO User (id, name, salt, secret, admin, judge, team_id) VALUES ('T3U1', 'user3.1', 'judgebean', '$argon2id$v=19$m=1024,t=2,p=2$B6Lw0JZ5b+QqOmOba0ossQ$oYHSH76XD/H94adXaAIWR+c9/vAE09XZ6dFtAsjyXWA', FALSE, FALSE, 'T3');
# INSERT INTO Team (id, name, flag, contest_id) VALUES ('T4', 'Totally Unrelated People', '123456', 'C2');
# INSERT INTO User (id, name, salt, secret, admin, judge, team_id) VALUES ('T4U0', 'user4.0', 'judgebean', '$argon2id$v=19$m=1024,t=2,p=2$B6Lw0JZ5b+QqOmOba0ossQ$oYHSH76XD/H94adXaAIWR+c9/vAE09XZ6dFtAsjyXWA', FALSE, FALSE, 'T4');
# INSERT INTO User (id, name, salt, secret, admin, judge, team_id) VALUES ('T4U1', 'user4.1', 'judgebean', '$argon2id$v=19$m=1024,t=2,p=2$B6Lw0JZ5b+QqOmOba0ossQ$oYHSH76XD/H94adXaAIWR+c9/vAE09XZ6dFtAsjyXWA', FALSE, FALSE, 'T4');
# INSERT INTO Team (id, name, flag, contest_id) VALUES ('T5', 'Totally Unrelated People, 2', '234567', 'C2');
# INSERT INTO User (id, name, salt, secret, admin, judge, team_id) VALUES ('T5U0', 'user5.0', 'judgebean', '$argon2id$v=19$m=1024,t=2,p=2$B6Lw0JZ5b+QqOmOba0ossQ$oYHSH76XD/H94adXaAIWR+c9/vAE09XZ6dFtAsjyXWA', FALSE, FALSE, 'T5');
# INSERT INTO User (id, name, salt, secret, admin, judge, team_id) VALUES ('T5U1', 'user5.1', 'judgebean', '$argon2id$v=19$m=1024,t=2,p=2$B6Lw0JZ5b+QqOmOba0ossQ$oYHSH76XD/H94adXaAIWR+c9/vAE09XZ6dFtAsjyXWA', FALSE, FALSE, 'T5');
# INSERT INTO Team (id, name, flag, contest_id) VALUES ('T6', 'Totally Unrelated People, 3', '345678', 'C0');
# INSERT INTO User (id, name, salt, secret, admin, judge, team_id) VALUES ('T6U0', 'user6.0', 'judgebean', '$argon2id$v=19$m=1024,t=2,p=2$B6Lw0JZ5b+QqOmOba0ossQ$oYHSH76XD/H94adXaAIWR+c9/vAE09XZ6dFtAsjyXWA', FALSE, FALSE, 'T6');
# INSERT INTO User (id, name, salt, secret, admin, judge, team_id) VALUES ('T6U1', 'user6.1', 'judgebean', '$argon2id$v=19$m=1024,t=2,p=2$B6Lw0JZ5b+QqOmOba0ossQ$oYHSH76XD/H94adXaAIWR+c9/vAE09XZ6dFtAsjyXWA', FALSE, FALSE, 'T6');
# INSERT INTO Team (id, name, flag) VALUES ('T7', 'Totally Unrelated People', '123456');
# INSERT INTO User (id, name, salt, secret, admin, judge, team_id) VALUES ('T7U0', 'user7.0', 'judgebean', '$argon2id$v=19$m=1024,t=2,p=2$B6Lw0JZ5b+QqOmOba0ossQ$oYHSH76XD/H94adXaAIWR+c9/vAE09XZ6dFtAsjyXWA', FALSE, FALSE, 'T7');
# INSERT INTO User (id, name, salt, secret, admin, judge, team_id) VALUES ('T7U1', 'user7.1', 'judgebean', '$argon2id$v=19$m=1024,t=2,p=2$B6Lw0JZ5b+QqOmOba0ossQ$oYHSH76XD/H94adXaAIWR+c9/vAE09XZ6dFtAsjyXWA', FALSE, FALSE, 'T7');
# INSERT INTO Resource (id, name, type, address, port, contest_id, scoring, weight) VALUES ('R0', 'Nameserver A0', 'DNS', 'ns1.baylor.edu', 53, 'C0', TRUE, 1);
# INSERT INTO ResourceTeams (resource_id, team_id) VALUES ('R0', 'T0');
# INSERT INTO Resource (id, name, type, address, port, contest_id, scoring, weight) VALUES ('R1', 'Nameserver B0', 'DNS', 'ns2.baylor.edu', 53, 'C0', TRUE, 1);
# INSERT INTO ResourceTeams (resource_id, team_id) VALUES ('R1', 'T1');
# INSERT INTO Resource (id, name, type, address, port, contest_id, scoring, weight) VALUES ('R2', 'Webserver A0', 'HTTP', 'baylor.edu', 80, 'C0', TRUE, 1);
# INSERT INTO ResourceTeams (resource_id, team_id) VALUES ('R2', 'T0');
# INSERT INTO Resource (id, name, type, address, port, contest_id, scoring, weight) VALUES ('R3', 'Webserver B0', 'HTTP', 'ecs.baylor.edu', 80, 'C0', TRUE, 1);
# INSERT INTO ResourceTeams (resource_id, team_id) VALUES ('R3', 'T1');
# INSERT INTO Resource (id, name, type, address, port, contest_id, scoring, weight) VALUES ('R4', 'Nameserver A1', 'DNS', '129.62.3.222', 53, 'C1', FALSE, 1);
# INSERT INTO ResourceTeams (resource_id, team_id) VALUES ('R4', 'T2');
# INSERT INTO Resource (id, name, type, address, port, contest_id, scoring, weight) VALUES ('R5', 'Nameserver B1', 'DNS', '129.62.3.212', 53, 'C1', FALSE, 1);
# INSERT INTO ResourceTeams (resource_id, team_id) VALUES ('R5', 'T3');
# INSERT INTO Resource (id, name, type, address, port, contest_id, scoring, weight) VALUES ('R6', 'Webserver A1', 'HTTP', 'infosec.baylor.edu', 80, 'C1', FALSE, 1);
# INSERT INTO Resource (id, name, type, address, port, contest_id, scoring, weight) VALUES ('R7', 'Webserver B1', 'HTTP', 'google.com', 80, 'C1', FALSE, 1);
# INSERT INTO Resource (id, name, type, address, port, contest_id, scoring, weight) VALUES ('R8', 'Nameserver A2', 'DNS', '208.67.222.222', 53, 'C2', FALSE, 1);
# INSERT INTO Resource (id, name, type, address, port, contest_id, scoring, weight) VALUES ('R9', 'Nameserver B2', 'DNS', '208.67.220.220', 53, 'C2', FALSE, 1);
# INSERT INTO Resource (id, name, type, address, port, contest_id, scoring, weight) VALUES ('R10', 'Webserver A2', 'HTTP', 'facebook.com', 80, 'C2', FALSE, 1);
# INSERT INTO Resource (id, name, type, address, port, contest_id, scoring, weight) VALUES ('R11', 'Webserver B2', 'HTTP', 'duckduckgo.com', 80, 'C2', FALSE, 1);
# INSERT INTO Resource (id, name, type, address, port, scoring, weight) VALUES ('R12', 'Webserver', 'HTTP', 'stma.is', 80, false, 1);
# INSERT INTO Task (id, description, name, pointValue, contest_id) VALUES ('I0', 'Do 0 thing(s).', 'An Inject', 100, 'C0');
# INSERT INTO TaskResponse (id, contents, task_id, team_id, score) VALUES ('TR0', 'We did 0 thing(s).', 'I0', 'T0', 0);
# INSERT INTO TaskResponse (id, contents, task_id, team_id, score) VALUES ('TR1', 'We did 0 thing(s).', 'I0', 'T1', 0);
# INSERT INTO Task (id, description, name, pointValue, contest_id) VALUES ('I1', 'Do 1 thing(s).', 'Another Inject', 101, 'C0');
# INSERT INTO TaskResponse (id, contents, task_id, team_id, score) VALUES ('TR2', 'We did 1 thing(s).', 'I1', 'T0', 0);
# INSERT INTO TaskResponse (id, contents, task_id, team_id, score) VALUES ('TR3', 'We did 1 thing(s).', 'I1', 'T1', 0);
# INSERT INTO Task (id, description, name, pointValue, contest_id) VALUES ('I2', 'Do 2 thing(s).', 'An Inject, Reconsidered', 102, 'C1');
# INSERT INTO TaskResponse (id, contents, task_id, team_id, score) VALUES ('TR4', 'We did 2 thing(s).', 'I2', 'T2', 0);
# INSERT INTO TaskResponse (id, contents, task_id, team_id, score) VALUES ('TR5', 'We did 2 thing(s).', 'I2', 'T3', 0);
# INSERT INTO Task (id, description, name, pointValue, contest_id) VALUES ('I3', 'Do 3 thing(s).', 'An Inject, Re-Examined', 103, 'C1');
# INSERT INTO TaskResponse (id, contents, task_id, team_id, score, comments) VALUES ('TR6', 'We did 3 thing(s).', 'I3', 'T2', 14, 'Terrible job doing 3 thing(s).');
# INSERT INTO TaskResponse (id, contents, task_id, team_id, score, comments) VALUES ('TR7', 'We did 3 thing(s).', 'I3', 'T3', 87, 'Good job doing 3 thing(s).');
# INSERT INTO Task (id, description, name, pointValue, contest_id) VALUES ('I4', 'Do 4 thing(s).', 'An Inject, Re-Imagined', 104, 'C2');
# INSERT INTO Task (id, description, name, pointValue, contest_id) VALUES ('I5', 'Do 5 thing(s).', 'Inject, Interrupted', 105, 'C2');
# INSERT INTO Task (id, description, name, pointValue, contest_id) VALUES ('I6', 'Do 6 thing(s).', 'Not My Inject, Not My Problem', 106, 'C3');