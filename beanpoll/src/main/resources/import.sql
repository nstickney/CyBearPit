-- noinspection SqlNoDataSourceInspectionForFile

--
-- JBoss, Home of Professional Open Source
-- Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
-- contributors by the @authors type. See the copyright.txt in the
-- distribution for a full listing of individual contributors.
--
-- Licensed under the Apache License, Version 2.0 (the "License");
-- you may not use this file except in compliance with the License.
-- You may obtain a copy of the License at
-- http://www.apache.org/licenses/LICENSE-2.0
-- Unless required by applicable law or agreed to in writing, software
-- distributed under the License is distributed on an "AS IS" BASIS,
-- WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
-- See the License for the specific language governing permissions and
-- limitations under the License.
--

-- User "beanpoll" is both an admin and a judge, and has password "beanpollpassword"
INSERT INTO User (id, admin, judge, name, salt, secret, team_id) VALUES ('UA', true, true, 'beanpoll', 'b97ebe9b-4db5-4dc4-8c31-c1d46df88d83', '$argon2id$v=19$m=1024,t=2,p=2$/bDvwgcKFx/SLUhz768+mQ$qdptwK3x7Z8u6/LBE9FjW0+IAZtVINmczJjEOGmhBUc', NULL);

-- User "beanadmin" is an admin but not a judge, and has password "beanadminpassword"
INSERT INTO User (id, admin, judge, name, salt, secret, team_id) VALUES ('UBA', true, false, 'beanadmin', 'fd981cdb-fe4e-42e6-893d-2935f21e95ab', '$argon2id$v=19$m=1024,t=2,p=2$5/afcBFwYdIw2Rof2wBTXg$KLlRoawxCr51fcesq+Q8YB65PRrHPqDQFX0WlO35b9s', NULL);

-- User "beanadmin" is a judge but not an admin, and has password "beanjudgepassword"
INSERT INTO User (id, admin, judge, name, salt, secret, team_id) VALUES ('UBJ', false, true, 'beanjudge', 'b7cb9d0b-bdd1-41f7-ba03-702462f129a3', '$argon2id$v=19$m=1024,t=2,p=2$6JmQTLgp2EwfJ/10htuLWw$vvn79tSS5RdaZSCZV9oaZybaDm5BcYXR3RDthUufH9U', NULL);

-- User "TestTeamUser" is a team user for TestTeam and has password "asdfasdfasdfasdf"
INSERT INTO User (id, admin, judge, name, salt, secret, team_id) VALUES ('9939c6da-a2be-4e56-be0e-6b48241dee53', false, false,'TestTeamUser', 'fa2e0ad8-2021-4a6c-8be1-0dbe72f54610', '$argon2id$v=19$m=1024,t=2,p=2$IANVy6Ninr3hxQaWFEnuzA$8p8M0xD3nvT9kRigf/uwYrZiImYQtNtrM6BYezGTQSs', '17cf9dc4-f82a-4c55-b927-c8804e76a36c');

-- TestContest
INSERT INTO Contest (id, name, running) VALUES ('873a7207-7fb2-4aa4-bf9b-562da607c3d7', 'TestContest', false);

-- TestTeam
INSERT INTO Team (id, flag, name, contest_id) VALUES ('17cf9dc4-f82a-4c55-b927-c8804e76a36c', 'BONUSPOINTS', 'TestTeam', '873a7207-7fb2-4aa4-bf9b-562da607c3d7');

-- TestResource
INSERT INTO Resource (id, address, name, port, scoring, type, weight, contest_id) VALUES ('ac1985ec-e5b2-4501-bffa-3a5d65826aad', 'http://httpbin.org', 'httpbin.org', 80, true, 'HTTP', 1, '873a7207-7fb2-4aa4-bf9b-562da607c3d7');

-- Parameters for TestResource
INSERT INTO `Parameter` VALUES ('927adb3e-3097-4fe4-8599-39fdfc730666', 'HTTP_RESOLVER', NULL, 'ac1985ec-e5b2-4501-bffa-3a5d65826aad');


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