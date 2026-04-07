DROP TABLE IF EXISTS gmatch;
DROP TABLE IF EXISTS payment;
DROP TABLE IF EXISTS reservation;
DROP TABLE IF EXISTS cment;
DROP TABLE IF EXISTS teamblog;
DROP TABLE IF EXISTS notice;
DROP TABLE IF EXISTS team;
DROP TABLE IF EXISTS place;
DROP TABLE IF EXISTS image;
DROP TABLE IF EXISTS member;

DROP SEQUENCE IF EXISTS mno_seq;
DROP SEQUENCE IF EXISTS team_seq;
DROP SEQUENCE IF EXISTS reservation_seq;
DROP SEQUENCE IF EXISTS n_no_seq;
DROP SEQUENCE IF EXISTS place_seq;
DROP SEQUENCE IF EXISTS cmt_seq;
DROP SEQUENCE IF EXISTS tb_no;
DROP SEQUENCE IF EXISTS seq_image;

CREATE SEQUENCE tb_no START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE cmt_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE place_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE n_no_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE reservation_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_image START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE team_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE mno_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE member (
    m_id VARCHAR(50) PRIMARY KEY,
    m_pw VARCHAR(255) NOT NULL,
    m_name VARCHAR(100) NOT NULL,
    m_phone VARCHAR(30),
    m_email VARCHAR(200),
    m_level VARCHAR(10) NOT NULL,
    m_cname VARCHAR(100),
    m_zip VARCHAR(20),
    m_addr1 VARCHAR(255),
    m_addr2 VARCHAR(255),
    m_pname VARCHAR(100),
    m_account VARCHAR(100),
    m_bank VARCHAR(100),
    m_sysdate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    m_check VARCHAR(10) DEFAULT '0',
    m_thum VARCHAR(255) DEFAULT 'logo.png'
);

CREATE TABLE image (
    i_no BIGINT PRIMARY KEY,
    i_sort VARCHAR(50),
    i_board VARCHAR(50),
    i_fname VARCHAR(255),
    i_fsize VARCHAR(50)
);

CREATE TABLE place (
    p_no BIGINT PRIMARY KEY,
    p_code VARCHAR(50),
    p_manager VARCHAR(50) NOT NULL,
    p_userid VARCHAR(50),
    p_pname VARCHAR(150) NOT NULL,
    p_place VARCHAR(255),
    p_explain CLOB,
    p_book VARCHAR(50),
    p_price VARCHAR(50),
    i_no VARCHAR(255),
    p_max VARCHAR(50),
    p_area VARCHAR(50),
    p_post VARCHAR(20),
    p_plus VARCHAR(255),
    p_op1 VARCHAR(10),
    p_op2 VARCHAR(10),
    p_op3 VARCHAR(10),
    p_op4 VARCHAR(10),
    p_op5 VARCHAR(10),
    p_op6 VARCHAR(10),
    p_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE notice (
    n_no BIGINT PRIMARY KEY,
    n_title VARCHAR(200) NOT NULL,
    n_id VARCHAR(50) NOT NULL,
    n_content CLOB NOT NULL,
    n_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    n_count DECIMAL(10, 1) DEFAULT 0
);

CREATE TABLE teamblog (
    tb_no BIGINT PRIMARY KEY,
    tb_tbno VARCHAR(50),
    tb_id VARCHAR(50) NOT NULL,
    tb_title VARCHAR(200) NOT NULL,
    tb_content CLOB NOT NULL,
    tb_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    tb_thum VARCHAR(255),
    tb_state VARCHAR(10)
);

CREATE TABLE cment (
    c_no BIGINT PRIMARY KEY,
    c_c_id VARCHAR(50) NOT NULL,
    c_m_thum VARCHAR(255),
    c_tbno VARCHAR(50) NOT NULL,
    c_tbset VARCHAR(50) NOT NULL,
    c_content CLOB NOT NULL,
    c_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    c_dec INT DEFAULT 0
);

CREATE TABLE reservation (
    r_no BIGINT PRIMARY KEY,
    r_m_id VARCHAR(50) NOT NULL,
    r_p_no VARCHAR(50) NOT NULL,
    r_time VARCHAR(50) NOT NULL,
    r_date VARCHAR(50) NOT NULL,
    r_wdate TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE payment (
    pay_code VARCHAR(100) PRIMARY KEY,
    p_no VARCHAR(50) NOT NULL,
    pay_id VARCHAR(50) NOT NULL,
    pay_price VARCHAR(50),
    pay_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE team (
    t_no BIGINT PRIMARY KEY,
    t_date VARCHAR(30),
    t_id VARCHAR(50) NOT NULL,
    t_name VARCHAR(150) NOT NULL,
    t_region VARCHAR(100),
    t_state VARCHAR(20),
    t_age VARCHAR(50),
    t_skill VARCHAR(50),
    t_uniform VARCHAR(100),
    t_kind VARCHAR(50),
    t_introduce CLOB,
    m_id VARCHAR(255),
    t_thum VARCHAR(255)
);

CREATE TABLE gmatch (
    g_mno BIGINT PRIMARY KEY,
    g_no VARCHAR(50) NOT NULL,
    g_vsno VARCHAR(50) NOT NULL,
    g_game VARCHAR(10),
    g_gf VARCHAR(10),
    g_ga VARCHAR(10),
    g_gamedate VARCHAR(50),
    g_wdate TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
