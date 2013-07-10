--
-- PostgreSQL database dump
--

-- Dumped from database version 9.1.9
-- Dumped by pg_dump version 9.1.9
-- Started on 2013-07-10 16:05:23 CEST

SET statement_timeout = 0;
SET client_encoding = 'SQL_ASCII';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- TOC entry 162 (class 3079 OID 11677)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 1893 (class 0 OID 0)
-- Dependencies: 162
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 161 (class 1259 OID 16421)
-- Dependencies: 5
-- Name: USER_TOKEN; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE "USER_TOKEN" (
    uid character varying NOT NULL,
    token character varying
);


ALTER TABLE public."USER_TOKEN" OWNER TO postgres;

--
-- TOC entry 1885 (class 2606 OID 16429)
-- Dependencies: 161 161 1887
-- Name: uid; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY "USER_TOKEN"
    ADD CONSTRAINT uid PRIMARY KEY (uid);


--
-- TOC entry 1883 (class 1259 OID 16431)
-- Dependencies: 161 1887
-- Name: TOKEN_INDEX; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE UNIQUE INDEX "TOKEN_INDEX" ON "USER_TOKEN" USING btree (token);


--
-- TOC entry 1892 (class 0 OID 0)
-- Dependencies: 5
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2013-07-10 16:05:23 CEST

--
-- PostgreSQL database dump complete
--

