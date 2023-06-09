--
-- PostgreSQL database dump
--

-- Dumped from database version 15.3
-- Dumped by pg_dump version 15.3

-- Started on 2023-06-22 11:05:07

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 214 (class 1259 OID 16692)
-- Name: diet; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.diet (
    id integer NOT NULL,
    file_name character varying(256) NOT NULL,
    file_path character varying(256) NOT NULL,
    file_type character varying(32) NOT NULL,
    file_size bigint NOT NULL,
    title character varying(256) NOT NULL,
    description character varying(512) DEFAULT ''::character varying NOT NULL,
    duration integer DEFAULT 0 NOT NULL
);


ALTER TABLE public.diet OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 16787)
-- Name: personal_diet; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.personal_diet (
    id integer NOT NULL,
    user_id integer NOT NULL,
    meal text,
    start_time timestamp without time zone,
    days_duration integer NOT NULL,
    completion_reason character varying(7),
    status character varying(9) NOT NULL
);


ALTER TABLE public.personal_diet OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 16786)
-- Name: personal_diet_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.personal_diet ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.personal_diet_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 215 (class 1259 OID 16698)
-- Name: product_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.diet ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.product_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 216 (class 1259 OID 16699)
-- Name: user; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."user" (
    id integer NOT NULL,
    username character varying(256) NOT NULL,
    password character varying(60) NOT NULL,
    role character varying(10) NOT NULL,
    personal_diet_id integer
);


ALTER TABLE public."user" OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 16702)
-- Name: user_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public."user" ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.user_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 218 (class 1259 OID 16745)
-- Name: user_parameters; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_parameters (
    user_id integer NOT NULL,
    sex character varying(6) NOT NULL,
    age integer NOT NULL,
    height integer NOT NULL,
    weight integer NOT NULL,
    allergy character varying(256) DEFAULT ''::character varying NOT NULL,
    physical_activity character varying(6) DEFAULT 'high'::character varying NOT NULL
);


ALTER TABLE public.user_parameters OWNER TO postgres;

--
-- TOC entry 3350 (class 0 OID 16692)
-- Dependencies: 214
-- Data for Name: diet; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.diet (id, file_name, file_path, file_type, file_size, title, description, duration) VALUES (1, 'kd-1024x1024.jpg', 'C:\Users\minya\Documents\Code\Java\simplediet\uploads\images\kd-1024x1024.jpg', 'image/jpeg', 293038, 'Кетогенная', 'Эта диета основана на потреблении низкого количества углеводов и высокого содержания жиров. Она направлена на достижение состояния кетоза, при котором организм начинает использовать жиры в качестве основного источника энергии вместо углеводов. Кето-диета может помочь в снижении веса, контроле аппетита и улучшении общего здоровья.', 14);
INSERT INTO public.diet (id, file_name, file_path, file_type, file_size, title, description, duration) VALUES (2, 'milayaya-25111812150015_8.jpg', 'C:\Users\minya\Documents\Code\Java\simplediet\uploads\images\milayaya-25111812150015_8.jpg', 'image/jpeg', 66632, 'Палеолитская', 'Палео-диета основана на идее потребления пищи, которую наши предки охотились и собирали в период палеолита. Она подразумевает потребление мяса, рыбы, овощей, фруктов, орехов и отказ от обработанных продуктов, зерновых, молочных продуктов и сахара. Эта диета ставит упор на натуральные и необработанные продукты питания и может помочь в снижении веса и улучшении общего здоровья.', 7);
INSERT INTO public.diet (id, file_name, file_path, file_type, file_size, title, description, duration) VALUES (3, '1663677743_27-mykaleidoscope-ru-p-veganskaya-yeda-vkontakte-28.jpg', 'C:\Users\minya\Documents\Code\Java\simplediet\uploads\images\1663677743_27-mykaleidoscope-ru-p-veganskaya-yeda-vkontakte-28.jpg', 'image/jpeg', 262012, 'Веганская', 'Веганство - это образ жизни, и веганская диета является его составной частью. Веганы исключают из своего рациона все продукты животного происхождения, включая мясо, рыбу, молочные продукты, яйца и мед. Они полагаются на плоды, овощи, орехи, семена, зерна и растительные продукты для получения всех необходимых питательных веществ. Веганская диета может быть полезной для снижения риска сердечно-сосудистых заболеваний и ожирения, а также для борьбы с климатическими проблемами и защиты животных.', 10);


--
-- TOC entry 3356 (class 0 OID 16787)
-- Dependencies: 220
-- Data for Name: personal_diet; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.personal_diet (id, user_id, meal, start_time, days_duration, completion_reason, status) VALUES (1, 1, 'День 1: 
завтрак: яичница из двух яиц с кусочками помидоров и авокадо (250 г)
обед: жареная куринная грудка (150 г) с овощным салатом (сырые овощи, оливковое масло, зелень) (250 г)
полдник: мягкий сыр (50 г) с оливками (50 г)
ужин: жареный лосось (150 г) с овощным гарниром (брокколи, зеленый горошек, масло оливковое) (250 г)

День 2: 
завтрак: омлет с беконом (2 яйца, 30 г бекона) с авокадо (100 г)
обед: говядина тушеная (150 г) с овощным гарниром (шпинат, грибы, масло оливковое) (250 г)
полдник: мини-бейгл с крем-сыром (50 г)
ужин: жареный кролик (150 г) с овощным салатом (сырые овощи, оливковое масло, зелень) (250 г)

День 3: 
завтрак: гречневая каша с ореховым маслом (150 г)
обед: свинина жареная (150 г) с овощным гарниром (цветная капуста, морковь, масло оливковое) (250 г)
полдник: мини-сэндвич с курицей и сыром (50 г)
ужин: телятина жареная (150 г) с овощным гарниром (пекинская капуста, баклажаны, масло оливковое) (250 г)

День 4:
завтрак: омлет с сыром (2 яйца, 50 г сыра) с авокадо (100 г)
обед: лосось вареный с нарезанными овощами (250 г)
полдник: грецкие орехи (30 г)
ужин: куриное филе жареное (150 г) с овощным гарниром (шпинат, масло оливковое, зелень) (250 г)

День 5: 
завтрак: омлет с беконом (2 яйца, 30 г бекона) с авокадо (100 г)
обед: говядина тушеная (150 г) с овощным гарниром (шпинат, грибы, масло оливковое) (250 г)
полдник: мини-бейгл с крем-сыром (50 г)
ужин: жареный кролик (150 г) с овощным салатом (сырые овощи, оливковое масло, зелень) (250 г)

День 6: 
завтрак: яичница из трех яиц с кусочками помидоров и авокадо (250 г)
обед: жареная куринная грудка (150 г) с овощным салатом (сырые овощи, оливковое масло, зелень) (250 г)
полдник: мягкий сыр (50 г) с оливками (50 г)
ужин: жареный лосось (150 г) с овощным гарниром (брокколи, зеленый горошек, масло оливковое) (250 г)

День 7: 
завтрак: омлет с сыром (2 яйца, 50 г сыра) с авокадо (100 г)
обед: свинина жареная (150 г) с овощным гарниром (цветная капуста, морковь, масло оливковое) (250 г)
полдник: грецкие орехи (30 г)
ужин: куриное филе жареное (150 г) с овощным гарниром (шпинат, масло оливковое, зелень) (250 г)

День 8: 
завтрак: гречневая каша с ореховым маслом (150 г)
обед: лосось вареный с нарезанными овощами (250 г)
полдник: мини-сэндвич с курицей и сыром (50 г)
ужин: телятина жареная (150 г) с овощным гарниром (пекинская капуста, баклажаны, масло оливковое) (250 г)

День 9:
завтрак: омлет с беконом (2 яйца, 30 г бекона) с авокадо (100 г)
обед: говядина тушеная (150 г) с овощным гарниром (шпинат, грибы, масло оливковое) (250 г)
полдник: мини-бейгл с крем-сыром (50 г)
ужин: жареный кролик (150 г) с овощным салатом (сырые овощи, оливковое масло, зелень) (250 г)

День 10:
завтрак: яичница из двух яиц с кусочками помидоров и авокадо (250 г)
обед: жареная куринная грудка (150 г) с овощным салатом (сырые овощи, оливковое масло, зелень) (250 г)
полдник: мягкий сыр (50 г) с оливками (50 г)
ужин: жареный лосось (150 г) с овощным гарниром (брокколи, зеленый горошек, масло оливковое) (250 г)

День 11:
завтрак: омлет с сыром (2 яйца, 50 г сыра) с авокадо (100 г)
обед: свинина жареная (150 г) с овощным гарниром (цветная капуста, морковь, масло оливковое) (250 г)
полдник: грецкие орехи (30 г)
ужин: куриное филе жареное (150 г) с овощным гарниром (шпинат, масло оливковое, зелень) (250 г)

День 12:
завтрак: гречневая каша с ореховым маслом (150 г)
обед: лосось вареный с нарезанными овощами (250 г)
полдник: мини-сэндвич с курицей и сыром (50 г)
ужин: телятина жареная (150 г) с овощным гарниром (пекинская капуста, баклажаны, масло оливковое) (250 г)

День 13:
завтрак: омлет с беконом (2 яйца, 30 г бекона) с авокадо (100 г)
обед: говядина тушеная (150 г) с овощным гарниром (шпинат, грибы, масло оливковое) (250 г)
полдник: мини-бейгл с крем-сыром (50 г)
ужин: жареный кролик (150 г) с овощным салатом (сырые овощи, оливковое масло, зелень) (250 г)

День 14:
завтрак: яичница из трех яиц с кусочками помидоров и авокадо (250 г)
обед: жареная куринная грудка (150 г) с овощным салатом (сырые овощи, оливковое масло, зелень) (250 г)
полдник: мягкий сыр (50 г) с оливками (50 г)
ужин: жареный лосось (150 г) с овощным гарниром (брокколи, зеленый горошек, масло оливковое) (250 г)', NULL, 14, NULL, 'generated');


--
-- TOC entry 3352 (class 0 OID 16699)
-- Dependencies: 216
-- Data for Name: user; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public."user" (id, username, password, role, personal_diet_id) VALUES (1, 'admin', '$2a$12$mUFMrLoS8PXdE3LA26xqBesODAaZD7n/uW9.Mi8ISW5wYtGoQEv0y', 'ROLE_ADMIN', 1);


--
-- TOC entry 3354 (class 0 OID 16745)
-- Dependencies: 218
-- Data for Name: user_parameters; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.user_parameters (user_id, sex, age, height, weight, allergy, physical_activity) VALUES (1, 'male', 21, 184, 70, 'морепродукты, мёд, орехи', 'medium');


--
-- TOC entry 3362 (class 0 OID 0)
-- Dependencies: 219
-- Name: personal_diet_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.personal_diet_id_seq', 1, true);


--
-- TOC entry 3363 (class 0 OID 0)
-- Dependencies: 215
-- Name: product_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.product_id_seq', 3, true);


--
-- TOC entry 3364 (class 0 OID 0)
-- Dependencies: 217
-- Name: user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.user_id_seq', 2, true);


--
-- TOC entry 3204 (class 2606 OID 16793)
-- Name: personal_diet personal_diet_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.personal_diet
    ADD CONSTRAINT personal_diet_pkey PRIMARY KEY (id);


--
-- TOC entry 3192 (class 2606 OID 16710)
-- Name: diet product_file_name_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.diet
    ADD CONSTRAINT product_file_name_key UNIQUE (file_name);


--
-- TOC entry 3194 (class 2606 OID 16712)
-- Name: diet product_file_path_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.diet
    ADD CONSTRAINT product_file_path_key UNIQUE (file_path);


--
-- TOC entry 3196 (class 2606 OID 16714)
-- Name: diet product_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.diet
    ADD CONSTRAINT product_pkey PRIMARY KEY (id);


--
-- TOC entry 3202 (class 2606 OID 16749)
-- Name: user_parameters user_parameters_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_parameters
    ADD CONSTRAINT user_parameters_pkey PRIMARY KEY (user_id);


--
-- TOC entry 3198 (class 2606 OID 16716)
-- Name: user user_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."user"
    ADD CONSTRAINT user_pkey PRIMARY KEY (id);


--
-- TOC entry 3200 (class 2606 OID 16718)
-- Name: user user_username_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."user"
    ADD CONSTRAINT user_username_key UNIQUE (username);


--
-- TOC entry 3207 (class 2606 OID 16794)
-- Name: personal_diet personal_diet_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.personal_diet
    ADD CONSTRAINT personal_diet_user_id_fkey FOREIGN KEY (user_id) REFERENCES public."user"(id);


--
-- TOC entry 3206 (class 2606 OID 16750)
-- Name: user_parameters user_parameters_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_parameters
    ADD CONSTRAINT user_parameters_user_id_fkey FOREIGN KEY (user_id) REFERENCES public."user"(id);


--
-- TOC entry 3205 (class 2606 OID 16814)
-- Name: user user_personal_diet_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."user"
    ADD CONSTRAINT user_personal_diet_fkey FOREIGN KEY (personal_diet_id) REFERENCES public.personal_diet(id);


-- Completed on 2023-06-22 11:05:07

--
-- PostgreSQL database dump complete
--

