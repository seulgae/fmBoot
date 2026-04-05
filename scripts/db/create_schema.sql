-- Run as user: fm
-- Target: Oracle XE PDB service xepdb1

SET DEFINE OFF;

BEGIN
    EXECUTE IMMEDIATE '
        CREATE TABLE member (
            m_id        VARCHAR2(50)   NOT NULL,
            m_pw        VARCHAR2(200)  NOT NULL,
            m_name      VARCHAR2(100)  NOT NULL,
            m_phone     VARCHAR2(30),
            m_email     VARCHAR2(200),
            m_level     VARCHAR2(10),
            m_cname     VARCHAR2(200),
            m_zip       VARCHAR2(20),
            m_addr1     VARCHAR2(300),
            m_addr2     VARCHAR2(300),
            m_pname     VARCHAR2(100),
            m_account   VARCHAR2(100),
            m_bank      VARCHAR2(100),
            m_sysdate   DATE DEFAULT SYSDATE,
            m_check     VARCHAR2(10) DEFAULT ''0'',
            m_thum      VARCHAR2(400),
            CONSTRAINT pk_member PRIMARY KEY (m_id)
        )
    ';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -955 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE '
        CREATE TABLE image (
            i_no        NUMBER         NOT NULL,
            i_sort      VARCHAR2(50),
            i_board     VARCHAR2(50),
            i_fname     VARCHAR2(400),
            i_fsize     VARCHAR2(50),
            CONSTRAINT pk_image PRIMARY KEY (i_no)
        )
    ';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -955 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE '
        CREATE TABLE place (
            p_no        NUMBER         NOT NULL,
            p_code      VARCHAR2(50),
            p_manager   VARCHAR2(50)   NOT NULL,
            p_userid    VARCHAR2(50),
            p_pname     VARCHAR2(200)  NOT NULL,
            p_place     VARCHAR2(500)  NOT NULL,
            p_explain   CLOB,
            p_book      VARCHAR2(50),
            p_price     VARCHAR2(50)   NOT NULL,
            i_no        NUMBER,
            p_max       VARCHAR2(50),
            p_area      VARCHAR2(100),
            p_post      VARCHAR2(50),
            p_plus      VARCHAR2(100),
            p_op1       VARCHAR2(10),
            p_op2       VARCHAR2(10),
            p_op3       VARCHAR2(10),
            p_op4       VARCHAR2(10),
            p_op5       VARCHAR2(10),
            p_op6       VARCHAR2(10),
            p_date      DATE DEFAULT SYSDATE,
            CONSTRAINT pk_place PRIMARY KEY (p_no),
            CONSTRAINT fk_place_manager FOREIGN KEY (p_manager) REFERENCES member (m_id),
            CONSTRAINT fk_place_image FOREIGN KEY (i_no) REFERENCES image (i_no)
        )
    ';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -955 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE '
        CREATE TABLE team (
            t_no          NUMBER         NOT NULL,
            t_date        VARCHAR2(30),
            t_id          VARCHAR2(50)   NOT NULL,
            t_name        VARCHAR2(200)  NOT NULL,
            t_region      VARCHAR2(100),
            t_state       VARCHAR2(20),
            t_age         VARCHAR2(50),
            t_skill       VARCHAR2(50),
            t_uniform     VARCHAR2(100),
            t_kind        VARCHAR2(50),
            t_introduce   CLOB,
            m_id          VARCHAR2(4000),
            t_thum        VARCHAR2(400),
            CONSTRAINT pk_team PRIMARY KEY (t_no),
            CONSTRAINT fk_team_owner FOREIGN KEY (t_id) REFERENCES member (m_id)
        )
    ';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -955 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE '
        CREATE TABLE gmatch (
            g_mno       NUMBER         NOT NULL,
            g_no        NUMBER         NOT NULL,
            g_vsno      NUMBER,
            g_game      VARCHAR2(10),
            g_gf        VARCHAR2(10),
            g_ga        VARCHAR2(10),
            g_gamedate  VARCHAR2(30),
            g_wdate     DATE DEFAULT SYSDATE,
            CONSTRAINT pk_gmatch PRIMARY KEY (g_mno),
            CONSTRAINT fk_gmatch_team FOREIGN KEY (g_no) REFERENCES team (t_no)
        )
    ';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -955 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE '
        CREATE TABLE teamblog (
            tb_no       NUMBER         NOT NULL,
            tb_tbno     VARCHAR2(50)   NOT NULL,
            tb_id       VARCHAR2(50)   NOT NULL,
            tb_title    VARCHAR2(300)  NOT NULL,
            tb_content  CLOB,
            tb_date     DATE DEFAULT SYSDATE,
            tb_thum     VARCHAR2(400),
            tb_state    VARCHAR2(20),
            CONSTRAINT pk_teamblog PRIMARY KEY (tb_no),
            CONSTRAINT fk_teamblog_writer FOREIGN KEY (tb_id) REFERENCES member (m_id)
        )
    ';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -955 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE '
        CREATE TABLE cment (
            c_no        NUMBER         NOT NULL,
            c_c_id      VARCHAR2(50)   NOT NULL,
            c_m_thum    VARCHAR2(400),
            c_tbno      VARCHAR2(50)   NOT NULL,
            c_tbset     VARCHAR2(50)   NOT NULL,
            c_content   CLOB,
            c_date      DATE DEFAULT SYSDATE,
            c_dec       NUMBER DEFAULT 0,
            CONSTRAINT pk_cment PRIMARY KEY (c_no),
            CONSTRAINT fk_cment_writer FOREIGN KEY (c_c_id) REFERENCES member (m_id)
        )
    ';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -955 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE '
        CREATE TABLE notice (
            n_no        NUMBER         NOT NULL,
            n_title     VARCHAR2(300)  NOT NULL,
            n_id        VARCHAR2(50)   NOT NULL,
            n_content   CLOB,
            n_date      DATE DEFAULT SYSDATE,
            n_count     NUMBER DEFAULT 0,
            CONSTRAINT pk_notice PRIMARY KEY (n_no),
            CONSTRAINT fk_notice_writer FOREIGN KEY (n_id) REFERENCES member (m_id)
        )
    ';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -955 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE '
        CREATE TABLE payment (
            pay_code      VARCHAR2(100) NOT NULL,
            p_no          NUMBER        NOT NULL,
            pay_id        VARCHAR2(50)  NOT NULL,
            pay_price     VARCHAR2(50)  NOT NULL,
            pay_date      DATE DEFAULT SYSDATE,
            CONSTRAINT pk_payment PRIMARY KEY (pay_code),
            CONSTRAINT fk_payment_place FOREIGN KEY (p_no) REFERENCES place (p_no),
            CONSTRAINT fk_payment_member FOREIGN KEY (pay_id) REFERENCES member (m_id)
        )
    ';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -955 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE '
        CREATE TABLE reservation (
            r_no        NUMBER         NOT NULL,
            r_m_id      VARCHAR2(50)   NOT NULL,
            r_p_no      NUMBER         NOT NULL,
            r_time      VARCHAR2(20)   NOT NULL,
            r_date      VARCHAR2(30)   NOT NULL,
            r_wdate     DATE DEFAULT SYSDATE,
            CONSTRAINT pk_reservation PRIMARY KEY (r_no),
            CONSTRAINT fk_reservation_member FOREIGN KEY (r_m_id) REFERENCES member (m_id),
            CONSTRAINT fk_reservation_place FOREIGN KEY (r_p_no) REFERENCES place (p_no)
        )
    ';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -955 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'CREATE SEQUENCE seq_image START WITH 1 INCREMENT BY 1 NOCACHE';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -955 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'CREATE SEQUENCE place_seq START WITH 1 INCREMENT BY 1 NOCACHE';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -955 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'CREATE SEQUENCE team_seq START WITH 1 INCREMENT BY 1 NOCACHE';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -955 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'CREATE SEQUENCE mno_seq START WITH 1 INCREMENT BY 1 NOCACHE';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -955 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'CREATE SEQUENCE tb_no START WITH 1 INCREMENT BY 1 NOCACHE';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -955 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'CREATE SEQUENCE cmt_seq START WITH 1 INCREMENT BY 1 NOCACHE';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -955 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'CREATE SEQUENCE n_no_seq START WITH 1 INCREMENT BY 1 NOCACHE';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -955 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'CREATE SEQUENCE reservation_seq START WITH 1 INCREMENT BY 1 NOCACHE';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -955 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'CREATE INDEX idx_team_name ON team (t_name)';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -955 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'CREATE INDEX idx_place_name ON place (p_pname)';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -955 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'CREATE INDEX idx_teamblog_title ON teamblog (tb_title)';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -955 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'CREATE INDEX idx_cment_tb ON cment (c_tbno, c_tbset)';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -955 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'CREATE INDEX idx_reservation_date_place ON reservation (r_date, r_p_no)';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -955 THEN
            RAISE;
        END IF;
END;
/

COMMIT;
