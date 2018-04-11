
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

-- TestCapturable
INSERT INTO Capturable (id, flag, name, pointValue, contest_id) VALUES ('2fd71b73-6b7c-46d6-ab28-7f3363cac465', 'TEST_CAPTURABLE_FLAG', 'TestCapturable', 1234, 'a51c930e-ea6a-4c27-8764-06ee921dca3b');

-- TestResource
INSERT INTO Resource (id, address, name, port, scoring, type, weight, contest_id) VALUES ('d24ceab6-b371-42ee-9de5-9b94814300f3', 'httpbin.org', 'TestHTTP', 80, true, 'HTTP', 1, 'a51c930e-ea6a-4c27-8764-06ee921dca3b');

-- Parameters for TestResource
INSERT INTO Parameter VALUES ('b7e9aac3-990e-4f48-a513-6a49d9debcf8', 'HTTP_RESOLVER', NULL, 'd24ceab6-b371-42ee-9de5-9b94814300f3');

-- TestTask
INSERT INTO Task (id, starts, description, ends, name, pointValue, contest_id) VALUES ('d59f69a0-b789-40ca-a45d-d1c5bd8adceb', '2018-01-01 08:00:00', 'Upload a file.', '2018-05-31 18:00:00', 'TestTask', 100, 'a51c930e-ea6a-4c27-8764-06ee921dca3b');