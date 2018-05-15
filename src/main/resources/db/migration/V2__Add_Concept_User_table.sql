
CREATE TABLE public.concept_user (
    id bigint NOT NULL,
    user_name text,
    password text
);


ALTER TABLE public.concept_user OWNER TO "teach-me";



ALTER TABLE ONLY public.concept_user
    ADD CONSTRAINT concept_user_pkey PRIMARY KEY (id);
