-- users
INSERT INTO public.users (email, password, username) VALUES ('admin@todo.com', '$2a$10$uj.bVdrQXdbdbte3DZ.Jc.SPqhEymDKJvpUwI3qe/KivnGpqTcbIm', 'admin');
INSERT INTO public.users (email, password, username) VALUES ('user@todo.com', '$2a$10$4XhyLcGTf6iB6zaOO8t9lOF3/yIMuGQlIUPf2x1/VaILGg.tjxIkO', 'user');
-- roles
INSERT INTO public.roles (id, name) VALUES (1, 'ROLE_ADMIN');
INSERT INTO public.roles (id, name) VALUES (2, 'ROLE_USER');
-- user roles
INSERT INTO public.user_roles (user_id, role_id) VALUES (1, 1);
INSERT INTO public.user_roles (user_id, role_id) VALUES (2, 2);
-- list
INSERT INTO public.todos_records (due_date, owner_id) VALUES ('2021-01-11 00:00:00.000000', 2);
INSERT INTO public.todos_records (due_date, owner_id) VALUES ('2021-01-12 00:00:00.000000', 2);
INSERT INTO public.todos_records (due_date, owner_id) VALUES ('2021-01-01 00:00:00.000000', 2);
INSERT INTO public.todos_records (due_date, owner_id) VALUES ('2021-01-01 00:00:00.000000', 1);
INSERT INTO public.todos_records (due_date, owner_id) VALUES ('2021-01-11 00:00:00.000000', 1);
INSERT INTO public.todos_records (due_date, owner_id) VALUES ('2021-01-12 00:00:00.000000', 1);
-- todos
INSERT INTO public.todos (status, title, todos_record_id, updated_at, created_at) VALUES ('CREATED', 'important task',  1, '2021-01-13 05:05:54.753299', '2021-01-13 05:03:32.247807');
INSERT INTO public.todos (status, title, todos_record_id, updated_at, created_at) VALUES ('DONE', 'not so important task',  1, '2021-01-13 05:05:54.755104', '2021-01-13 05:03:38.470188');
INSERT INTO public.todos (status, title, todos_record_id, updated_at, created_at) VALUES ('CANCELED', 'something else',  1, '2021-01-13 05:05:54.756360', '2021-01-13 05:04:26.199250');
INSERT INTO public.todos (status, title, todos_record_id, updated_at, created_at) VALUES ('FAILED', 'today task',  2, '2021-01-13 05:05:54.762710', '2021-01-13 05:05:13.786473');
INSERT INTO public.todos (status, title, todos_record_id, updated_at, created_at) VALUES ('DONE', 'it should be done',  2, '2021-01-13 05:07:34.719900', '2021-01-13 05:06:03.289248');
INSERT INTO public.todos (status, title, todos_record_id, updated_at, created_at) VALUES ('DONE', 'some old and completed task',  3, '2021-01-13 05:07:34.721246', '2021-01-13 05:06:42.845118');
INSERT INTO public.todos (status, title, todos_record_id, updated_at, created_at) VALUES ('DONE', 'some old and completed task',  4, '2021-01-13 05:10:54.712636', '2021-01-13 05:08:41.465923');
INSERT INTO public.todos (status, title, todos_record_id, updated_at, created_at) VALUES ('CREATED', 'important task',  5, '2021-01-13 05:10:54.713235', '2021-01-13 05:09:08.156979');
INSERT INTO public.todos (status, title, todos_record_id, updated_at, created_at) VALUES ('DONE', 'not so important task',  5, '2021-01-13 05:10:54.713776', '2021-01-13 05:09:15.257195');
INSERT INTO public.todos (status, title, todos_record_id, updated_at, created_at) VALUES ('FAILED', 'complete today', 6, '2021-01-13 05:10:54.714247', '2021-01-13 05:09:57.205203');
