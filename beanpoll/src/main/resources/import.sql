-- noinspection SqlNoDataSourceInspectionForFile

--
-- JBoss, Home of Professional Open Source
-- Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
-- contributors by the @authors tag. See the copyright.txt in the
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
insert into Team (id, name, flag, contest_id) values ('T0', 'Blue Man Group', 'Ready, Go!', 'C0');
insert into Team (id, name, flag, contest_id) values ('T1', 'Bala Morgab', 'BMG', 'C0');
insert into Team (id, name, flag, contest_id) values ('T2', 'General Motors Bicyclists', 'GMb', 'C1');
insert into Team (id, name, flag, contest_id) values ('T3', 'Greater Midwest Baseball', 'GMB', 'C1');
insert into Team (id, name, flag, contest_id) values ('T4', 'Totally Unrelated People', '123456', 'C2');
insert into Team (id, name, flag, contest_id) values ('T5', 'Totally Unrelated People, 2', '234567', 'C2');
insert into Team (id, name, flag, contest_id) values ('T6', 'Totally Unrelated People, 3', '345678', 'C0');
insert into Team (id, name, flag) values ('T7', 'Totally Unrelated People', '123456');
insert into Resource (id, name, tag, contest_id) values ('R0', 'Nameserver A0', 'DNS', 'C0');
insert into Resource (id, name, tag, contest_id) values ('R1', 'Nameserver B0', 'DNS', 'C0');
insert into Resource (id, name, tag, contest_id) values ('R2', 'Webserver A0', 'HTTP', 'C0');
insert into Resource (id, name, tag, contest_id) values ('R3', 'Webserver B0', 'HTTP', 'C0');
insert into Resource (id, name, tag, contest_id) values ('R4', 'Nameserver A1', 'DNS', 'C1');
insert into Resource (id, name, tag, contest_id) values ('R5', 'Nameserver B1', 'DNS', 'C1');
insert into Resource (id, name, tag, contest_id) values ('R6', 'Webserver A1', 'HTTP', 'C1');
insert into Resource (id, name, tag, contest_id) values ('R7', 'Webserver B1', 'HTTP', 'C1');
insert into Resource (id, name, tag, contest_id) values ('R8', 'Nameserver A2', 'DNS', 'C2');
insert into Resource (id, name, tag, contest_id) values ('R9', 'Nameserver B2', 'DNS', 'C2');
insert into Resource (id, name, tag, contest_id) values ('R10', 'Webserver A2', 'HTTP', 'C2');
insert into Resource (id, name, tag, contest_id) values ('R11', 'Webserver B2', 'HTTP', 'C2');
insert into Resource (id, name, tag) values ('R12', 'Webserver', 'HTTP');