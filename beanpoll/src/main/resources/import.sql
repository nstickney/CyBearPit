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
INSERT INTO User (id, name, salt, secret, admin) VALUES ('UA', 'beanpoll', 'judgebean', '8def98492aa109f7809c63eb747d41f768c6635430fbf6bf3e0951730287608c', TRUE);
INSERT INTO Contest (id, name, running) VALUES ('C0', 'Cyber Patriot', FALSE);
INSERT INTO Contest (id, name, running) VALUES ('C1', 'CCDC', FALSE);
INSERT INTO Contest (id, name, running) VALUES ('C2', 'CDX', FALSE);
INSERT INTO Contest (id, name, running) VALUES ('C3', 'Panoply', FALSE);
INSERT INTO Team (id, name, flag, contest_id) VALUES ('T0', 'Blue Man Group', 'Ready, Go!', 'C0');
INSERT INTO User (id, name, salt, secret, admin, team_id) VALUES ('T0U0', 'team0.0', 'judgebean', '8def98492aa109f7809c63eb747d41f768c6635430fbf6bf3e0951730287608c', FALSE, 'T0');
INSERT INTO User (id, name, salt, secret, admin, team_id) VALUES ('T0U1', 'team0.1', 'judgebean', '8def98492aa109f7809c63eb747d41f768c6635430fbf6bf3e0951730287608c', FALSE, 'T0');
INSERT INTO Team (id, name, flag, contest_id) VALUES ('T1', 'Bala Morgab', 'BMG', 'C0');
INSERT INTO User (id, name, salt, secret, admin, team_id) VALUES ('T1U0', 'team1.0', 'judgebean', '8def98492aa109f7809c63eb747d41f768c6635430fbf6bf3e0951730287608c', FALSE, 'T1');
INSERT INTO User (id, name, salt, secret, admin, team_id) VALUES ('T1U1', 'team1.1', 'judgebean', '8def98492aa109f7809c63eb747d41f768c6635430fbf6bf3e0951730287608c', FALSE, 'T1');
INSERT INTO Team (id, name, flag, contest_id) VALUES ('T2', 'General Motors Bicyclists', 'GMb', 'C1');
INSERT INTO User (id, name, salt, secret, admin, team_id) VALUES ('T2U0', 'team2.0', 'judgebean', '8def98492aa109f7809c63eb747d41f768c6635430fbf6bf3e0951730287608c', FALSE, 'T2');
INSERT INTO User (id, name, salt, secret, admin, team_id) VALUES ('T2U1', 'team2.1', 'judgebean', '8def98492aa109f7809c63eb747d41f768c6635430fbf6bf3e0951730287608c', FALSE, 'T2');
INSERT INTO Team (id, name, flag, contest_id) VALUES ('T3', 'Greater Midwest Baseball', 'GMB', 'C1');
INSERT INTO User (id, name, salt, secret, admin, team_id) VALUES ('T3U0', 'team3.0', 'judgebean', '8def98492aa109f7809c63eb747d41f768c6635430fbf6bf3e0951730287608c', FALSE, 'T3');
INSERT INTO User (id, name, salt, secret, admin, team_id) VALUES ('T3U1', 'team3.1', 'judgebean', '8def98492aa109f7809c63eb747d41f768c6635430fbf6bf3e0951730287608c', FALSE, 'T3');
INSERT INTO Team (id, name, flag, contest_id) VALUES ('T4', 'Totally Unrelated People', '123456', 'C2');
INSERT INTO User (id, name, salt, secret, admin, team_id) VALUES ('T4U0', 'team4.0', 'judgebean', '8def98492aa109f7809c63eb747d41f768c6635430fbf6bf3e0951730287608c', FALSE, 'T4');
INSERT INTO User (id, name, salt, secret, admin, team_id) VALUES ('T4U1', 'team4.1', 'judgebean', '8def98492aa109f7809c63eb747d41f768c6635430fbf6bf3e0951730287608c', FALSE, 'T4');
INSERT INTO Team (id, name, flag, contest_id) VALUES ('T5', 'Totally Unrelated People, 2', '234567', 'C2');
INSERT INTO User (id, name, salt, secret, admin, team_id) VALUES ('T5U0', 'team5.0', 'judgebean', '8def98492aa109f7809c63eb747d41f768c6635430fbf6bf3e0951730287608c', FALSE, 'T5');
INSERT INTO User (id, name, salt, secret, admin, team_id) VALUES ('T5U1', 'team5.1', 'judgebean', '8def98492aa109f7809c63eb747d41f768c6635430fbf6bf3e0951730287608c', FALSE, 'T5');
INSERT INTO Team (id, name, flag, contest_id) VALUES ('T6', 'Totally Unrelated People, 3', '345678', 'C0');
INSERT INTO User (id, name, salt, secret, admin, team_id) VALUES ('T6U0', 'team6.0', 'judgebean', '8def98492aa109f7809c63eb747d41f768c6635430fbf6bf3e0951730287608c', FALSE, 'T6');
INSERT INTO User (id, name, salt, secret, admin, team_id) VALUES ('T6U1', 'team6.1', 'judgebean', '8def98492aa109f7809c63eb747d41f768c6635430fbf6bf3e0951730287608c', FALSE, 'T6');
INSERT INTO Team (id, name, flag) VALUES ('T7', 'Totally Unrelated People', '123456');
INSERT INTO User (id, name, salt, secret, admin, team_id) VALUES ('T7U0', 'team7.0', 'judgebean', '8def98492aa109f7809c63eb747d41f768c6635430fbf6bf3e0951730287608c', FALSE, 'T7');
INSERT INTO User (id, name, salt, secret, admin, team_id) VALUES ('T7U1', 'team7.1', 'judgebean', '8def98492aa109f7809c63eb747d41f768c6635430fbf6bf3e0951730287608c', FALSE, 'T7');
INSERT INTO Resource (id, name, type, address, port, contest_id) VALUES ('R0', 'Nameserver A0', 'DNS', 'ns1.baylor.edu', 53, 'C0');
INSERT INTO ResourceTeams (resource_id, team_id) VALUES ('R0', 'T0');
INSERT INTO Resource (id, name, type, address, port, contest_id) VALUES ('R1', 'Nameserver B0', 'DNS', 'ns2.baylor.edu', 53, 'C0');
INSERT INTO ResourceTeams (resource_id, team_id) VALUES ('R1', 'T1');
INSERT INTO Resource (id, name, type, address, port, contest_id) VALUES ('R2', 'Webserver A0', 'HTTP', 'baylor.edu', 80, 'C0');
INSERT INTO ResourceTeams (resource_id, team_id) VALUES ('R2', 'T0');
INSERT INTO Resource (id, name, type, address, port, contest_id) VALUES ('R3', 'Webserver B0', 'HTTP', 'ecs.baylor.edu', 80, 'C0');
INSERT INTO ResourceTeams (resource_id, team_id) VALUES ('R3', 'T1');
INSERT INTO Resource (id, name, type, address, port, contest_id) VALUES ('R4', 'Nameserver A1', 'DNS', '129.62.3.222', 53, 'C1');
INSERT INTO ResourceTeams (resource_id, team_id) VALUES ('R4', 'T2');
INSERT INTO Resource (id, name, type, address, port, contest_id) VALUES ('R5', 'Nameserver B1', 'DNS', '129.62.3.212', 53, 'C1');
INSERT INTO ResourceTeams (resource_id, team_id) VALUES ('R5', 'T3');
INSERT INTO Resource (id, name, type, address, port, contest_id) VALUES ('R6', 'Webserver A1', 'HTTP', 'infosec.baylor.edu', 80, 'C1');
INSERT INTO Resource (id, name, type, address, port, contest_id) VALUES ('R7', 'Webserver B1', 'HTTP', 'google.com', 80, 'C1');
INSERT INTO Resource (id, name, type, address, port, contest_id) VALUES ('R8', 'Nameserver A2', 'DNS', '208.67.222.222', 53, 'C2');
INSERT INTO Resource (id, name, type, address, port, contest_id) VALUES ('R9', 'Nameserver B2', 'DNS', '208.67.220.220', 53, 'C2');
INSERT INTO Resource (id, name, type, address, port, contest_id) VALUES ('R10', 'Webserver A2', 'HTTP', 'facebook.com', 80, 'C2');
INSERT INTO Resource (id, name, type, address, port, contest_id) VALUES ('R11', 'Webserver B2', 'HTTP', 'duckduckgo.com', 80, 'C2');
INSERT INTO Resource (id, name, type, address, port) VALUES ('R12', 'Webserver', 'HTTP', 'stma.is', 80);
INSERT INTO Task (id, description, name, pointValue, contest_id) VALUES ('I0', 'Do 0 thing(s).', 'An Inject', 100, 'C0');
INSERT INTO TaskResponse (id, contents, task_id, team_id) VALUES ('TR0', 'We did 0 thing(s).', 'I0', 'T0');
INSERT INTO TaskResponse (id, contents, task_id, team_id) VALUES ('TR1', 'We did 0 thing(s).', 'I0', 'T1');
INSERT INTO Task (id, description, name, pointValue, contest_id) VALUES ('I1', 'Do 1 thing(s).', 'Another Inject', 101, 'C0');
INSERT INTO TaskResponse (id, contents, task_id, team_id) VALUES ('TR2', 'We did 1 thing(s).', 'I1', 'T0');
INSERT INTO TaskResponse (id, contents, task_id, team_id) VALUES ('TR3', 'We did 1 thing(s).', 'I1', 'T1');
INSERT INTO Task (id, description, name, pointValue, contest_id) VALUES ('I2', 'Do 2 thing(s).', 'An Inject, Reconsidered', 102, 'C1');
INSERT INTO TaskResponse (id, contents, task_id, team_id) VALUES ('TR4', 'We did 2 thing(s).', 'I2', 'T2');
INSERT INTO TaskResponse (id, contents, task_id, team_id) VALUES ('TR5', 'We did 2 thing(s).', 'I2', 'T3');
INSERT INTO Task (id, description, name, pointValue, contest_id) VALUES ('I3', 'Do 3 thing(s).', 'An Inject, Re-Examined', 103, 'C1');
INSERT INTO TaskResponse (id, contents, task_id, team_id) VALUES ('TR6', 'We did 3 thing(s).', 'I3', 'T2');
INSERT INTO Points (id, information, score, taskResponse_id, team_id) VALUES ('P0', 'Good job doing 3 things(s).', 87, 'TR6', 'T2');
UPDATE TaskResponse SET points_id = 'P0' WHERE id = 'TR6';
INSERT INTO TaskResponse (id, contents, task_id, team_id) VALUES ('TR7', 'We did 3 thing(s).', 'I3', 'T3');
INSERT INTO Points (id, information, score, taskResponse_id, team_id) VALUES ('P1', 'Terrible job doing 3 things(s).', 14, 'TR7', 'T3');
UPDATE TaskResponse SET points_id = 'P1' WHERE id = 'TR7';
INSERT INTO Task (id, description, name, pointValue, contest_id) VALUES ('I4', 'Do 4 thing(s).', 'An Inject, Re-Imagined', 104, 'C2');
INSERT INTO Task (id, description, name, pointValue, contest_id) VALUES ('I5', 'Do 5 thing(s).', 'Inject, Interrupted', 105, 'C2');
INSERT INTO Task (id, description, name, pointValue, contest_id) VALUES ('I6', 'Do 6 thing(s).', 'Not My Inject, Not My Problem', 106, 'C3');