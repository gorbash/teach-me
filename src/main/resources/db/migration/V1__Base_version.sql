--
-- PostgreSQL database dump
--

-- Dumped from database version 9.6.8
-- Dumped by pg_dump version 9.6.8


--
-- Name: concept; Type: TABLE; Schema: public; Owner: teach-me
--

CREATE TABLE public.concept (
    id bigint NOT NULL,
    definition text,
    hits bigint NOT NULL,
    name text
);

--
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; Owner: teach-me
--

CREATE SEQUENCE public.hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: concept concept_pkey; Type: CONSTRAINT; Schema: public; Owner: teach-me
--

ALTER TABLE ONLY public.concept
    ADD CONSTRAINT concept_pkey PRIMARY KEY (id);


--
-- PostgreSQL database dump complete
--

