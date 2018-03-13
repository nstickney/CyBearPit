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

-- You can use this file to load seed data into the database using SQL statements
insert into User (id, name, salt, secret, admin) values ('UA', 'beanpoll', 'judgebean', '8def98492aa109f7809c63eb747d41f768c6635430fbf6bf3e0951730287608c', true);
insert into Contest (id, name, running) values ('C0', 'Cyber Patriot', false);
insert into Contest (id, name, running) values ('C1', 'CCDC', false);
insert into Contest (id, name, running) values ('C2', 'CDX', false);
insert into Contest (id, name, running) values ('C3', 'Panoply', false);
insert into Team (id, name, flag, contest_id) values ('T0', 'Blue Man Group', 'Ready, Go!', 'C0');
insert into User (id, name, salt, secret, admin, team_id) values ('T0U0', 'team0.0', 'judgebean', '8def98492aa109f7809c63eb747d41f768c6635430fbf6bf3e0951730287608c', false, 'T0');
insert into User (id, name, salt, secret, admin, team_id) values ('T0U1', 'team0.1', 'judgebean', '8def98492aa109f7809c63eb747d41f768c6635430fbf6bf3e0951730287608c', false, 'T0');
insert into Team (id, name, flag, contest_id) values ('T1', 'Bala Morgab', 'BMG', 'C0');
insert into User (id, name, salt, secret, admin, team_id) values ('T1U0', 'team1.0', 'judgebean', '8def98492aa109f7809c63eb747d41f768c6635430fbf6bf3e0951730287608c', false, 'T1');
insert into User (id, name, salt, secret, admin, team_id) values ('T1U1', 'team1.1', 'judgebean', '8def98492aa109f7809c63eb747d41f768c6635430fbf6bf3e0951730287608c', false, 'T1');
insert into Team (id, name, flag, contest_id) values ('T2', 'General Motors Bicyclists', 'GMb', 'C1');
insert into User (id, name, salt, secret, admin, team_id) values ('T2U0', 'team2.0', 'judgebean', '8def98492aa109f7809c63eb747d41f768c6635430fbf6bf3e0951730287608c', false, 'T2');
insert into User (id, name, salt, secret, admin, team_id) values ('T2U1', 'team2.1', 'judgebean', '8def98492aa109f7809c63eb747d41f768c6635430fbf6bf3e0951730287608c', false, 'T2');
insert into Team (id, name, flag, contest_id) values ('T3', 'Greater Midwest Baseball', 'GMB', 'C1');
insert into User (id, name, salt, secret, admin, team_id) values ('T3U0', 'team3.0', 'judgebean', '8def98492aa109f7809c63eb747d41f768c6635430fbf6bf3e0951730287608c', false, 'T3');
insert into User (id, name, salt, secret, admin, team_id) values ('T3U1', 'team3.1', 'judgebean', '8def98492aa109f7809c63eb747d41f768c6635430fbf6bf3e0951730287608c', false, 'T3');
insert into Team (id, name, flag, contest_id) values ('T4', 'Totally Unrelated People', '123456', 'C2');
insert into User (id, name, salt, secret, admin, team_id) values ('T4U0', 'team4.0', 'judgebean', '8def98492aa109f7809c63eb747d41f768c6635430fbf6bf3e0951730287608c', false, 'T4');
insert into User (id, name, salt, secret, admin, team_id) values ('T4U1', 'team4.1', 'judgebean', '8def98492aa109f7809c63eb747d41f768c6635430fbf6bf3e0951730287608c', false, 'T4');
insert into Team (id, name, flag, contest_id) values ('T5', 'Totally Unrelated People, 2', '234567', 'C2');
insert into User (id, name, salt, secret, admin, team_id) values ('T5U0', 'team5.0', 'judgebean', '8def98492aa109f7809c63eb747d41f768c6635430fbf6bf3e0951730287608c', false, 'T5');
insert into User (id, name, salt, secret, admin, team_id) values ('T5U1', 'team5.1', 'judgebean', '8def98492aa109f7809c63eb747d41f768c6635430fbf6bf3e0951730287608c', false, 'T5');
insert into Team (id, name, flag, contest_id) values ('T6', 'Totally Unrelated People, 3', '345678', 'C0');
insert into User (id, name, salt, secret, admin, team_id) values ('T6U0', 'team6.0', 'judgebean', '8def98492aa109f7809c63eb747d41f768c6635430fbf6bf3e0951730287608c', false, 'T6');
insert into User (id, name, salt, secret, admin, team_id) values ('T6U1', 'team6.1', 'judgebean', '8def98492aa109f7809c63eb747d41f768c6635430fbf6bf3e0951730287608c', false, 'T6');
insert into Team (id, name, flag) values ('T7', 'Totally Unrelated People', '123456');
insert into User (id, name, salt, secret, admin, team_id) values ('T7U0', 'team7.0', 'judgebean', '8def98492aa109f7809c63eb747d41f768c6635430fbf6bf3e0951730287608c', false, 'T7');
insert into User (id, name, salt, secret, admin, team_id) values ('T7U1', 'team7.1', 'judgebean', '8def98492aa109f7809c63eb747d41f768c6635430fbf6bf3e0951730287608c', false, 'T7');
insert into Resource (id, name, type, address, port, contest_id) values ('R0', 'Nameserver A0', 'DNS', 'ns1.baylor.edu', 53, 'C0');
insert into ResourceTeams (resource_id, team_id) values ('R0', 'T0');
insert into Resource (id, name, type, address, port, contest_id) values ('R1', 'Nameserver B0', 'DNS', 'ns2.baylor.edu', 53, 'C0');
insert into ResourceTeams (resource_id, team_id) values ('R1', 'T1');
insert into Resource (id, name, type, address, port, contest_id) values ('R2', 'Webserver A0', 'HTTP', 'baylor.edu', 80, 'C0');
insert into ResourceTeams (resource_id, team_id) values ('R2', 'T0');
insert into Resource (id, name, type, address, port, contest_id) values ('R3', 'Webserver B0', 'HTTP', 'ecs.baylor.edu', 80, 'C0');
insert into ResourceTeams (resource_id, team_id) values ('R3', 'T1');
insert into Resource (id, name, type, address, port, contest_id) values ('R4', 'Nameserver A1', 'DNS', '129.62.3.222', 53, 'C1');
insert into ResourceTeams (resource_id, team_id) values ('R4', 'T2');
insert into Resource (id, name, type, address, port, contest_id) values ('R5', 'Nameserver B1', 'DNS', '129.62.3.212', 53, 'C1');
insert into ResourceTeams (resource_id, team_id) values ('R5', 'T3');
insert into Resource (id, name, type, address, port, contest_id) values ('R6', 'Webserver A1', 'HTTP', 'infosec.baylor.edu', 80, 'C1');
insert into Resource (id, name, type, address, port, contest_id) values ('R7', 'Webserver B1', 'HTTP', 'google.com', 80, 'C1');
insert into Resource (id, name, type, address, port, contest_id) values ('R8', 'Nameserver A2', 'DNS', '208.67.222.222', 53, 'C2');
insert into Resource (id, name, type, address, port, contest_id) values ('R9', 'Nameserver B2', 'DNS', '208.67.220.220', 53, 'C2');
insert into Resource (id, name, type, address, port, contest_id) values ('R10', 'Webserver A2', 'HTTP', 'facebook.com', 80, 'C2');
insert into Resource (id, name, type, address, port, contest_id) values ('R11', 'Webserver B2', 'HTTP', 'duckduckgo.com', 80, 'C2');
insert into Resource (id, name, type, address, port) values ('R12', 'Webserver', 'HTTP', 'stma.is', 80);
insert into Task (id, description, name, pointValue, contest_id) values ('I0', 'Do 0 thing(s).', 'An Inject', 100, 'C0');
insert into TaskResponse (id, contents, task_id, team_id) values ('TR0', 'We did 0 thing(s).', 'I0', 'T0');
insert into TaskResponse (id, contents, task_id, team_id) values ('TR1', 'We did 0 thing(s).', 'I0', 'T1');
insert into Task (id, description, name, pointValue, contest_id) values ('I1', 'Do 1 thing(s).', 'Another Inject', 101, 'C0');
insert into TaskResponse (id, contents, task_id, team_id) values ('TR2', 'We did 1 thing(s).', 'I1', 'T0');
insert into TaskResponse (id, contents, task_id, team_id) values ('TR3', 'We did 1 thing(s).', 'I1', 'T1');
insert into Task (id, description, name, pointValue, contest_id) values ('I2', 'Do 2 thing(s).', 'An Inject, Reconsidered', 102, 'C1');
insert into TaskResponse (id, contents, task_id, team_id) values ('TR4', 'We did 2 thing(s).', 'I2', 'T2');
insert into TaskResponse (id, contents, task_id, team_id) values ('TR5', 'We did 2 thing(s).', 'I2', 'T3');
insert into Task (id, description, name, pointValue, contest_id) values ('I3', 'Do 3 thing(s).', 'An Inject, Re-Examined', 103, 'C1');
insert into TaskResponse (id, contents, points_id, task_id, team_id) values ('TR6', 'We did 3 thing(s).', 'P0', 'I3', 'T2');
insert into Points (id, information, score, taskResponse_id, team_id) values ('P0', 'Good job doing 3 things(s).', 87, 'TR6', 'T2');
insert into TaskResponse (id, contents, points_id, task_id, team_id) values ('TR7', 'We did 3 thing(s).', 'P1', 'I3', 'T3');
insert into Points (id, information, score, taskResponse_id, team_id) values ('P1', 'Good job doing 3 things(s).', 87, 'TR7', 'T3');
insert into Task (id, description, name, pointValue, contest_id) values ('I4', 'Do 4 thing(s).', 'An Inject, Re-Imagined', 104, 'C2');
insert into Task (id, description, name, pointValue, contest_id) values ('I5', 'Do 5 thing(s).', 'Inject, Interrupted', 105, 'C2');
insert into Task (id, description, name, pointValue, contest_id) values ('I6', 'Do 6 thing(s).', 'Not My Inject, Not My Problem', 106, 'C3');