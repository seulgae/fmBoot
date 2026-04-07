INSERT INTO member (
    m_id, m_pw, m_name, m_phone, m_email, m_level, m_cname, m_zip, m_addr1, m_addr2,
    m_pname, m_account, m_bank, m_sysdate, m_check, m_thum
) VALUES
    ('user', '1234', '테스트 사용자', '010-1111-1111', 'user@fm.local', '1', '', '06236', '서울 강남구 테헤란로 1', '101호', 'teamDefault', '', '', CURRENT_TIMESTAMP, '0', 'logo.png'),
    ('manager', '1234', '테스트 구장관리자', '010-2222-2222', 'manager@fm.local', '2', 'FM Arena', '06237', '서울 강남구 테헤란로 2', '201호', 'managerGround', '123-456-7890', '국민은행', CURRENT_TIMESTAMP, '0', 'id.png'),
    ('admin', '1234', '테스트 관리자', '010-3333-3333', 'admin@fm.local', '0', 'FM Admin', '06238', '서울 강남구 테헤란로 3', '301호', 'adminDesk', '', '', CURRENT_TIMESTAMP, '0', 'logo.png');

INSERT INTO image (i_no, i_sort, i_board, i_fname, i_fsize)
SELECT
    NEXT VALUE FOR seq_image,
    'place',
    'place',
    CASE WHEN MOD(x, 2) = 0 THEN 'app' ELSE 'app' END,
    CAST(1024 + x AS VARCHAR)
FROM SYSTEM_RANGE(1, 60);

INSERT INTO place (
    p_no, p_code, p_manager, p_userid, p_pname, p_place, p_explain, p_book, p_price, i_no,
    p_max, p_area, p_post, p_plus, p_op1, p_op2, p_op3, p_op4, p_op5, p_op6, p_date
)
SELECT
    NEXT VALUE FOR place_seq,
    'P' || RIGHT('000' || CAST(x AS VARCHAR), 3),
    'manager',
    'manager',
    '테스트 구장 ' || x,
    '서울 테스트구 테스트로 ' || x,
    'H2 테스트용 구장 설명 ' || x || '번입니다.',
    '가능',
    CAST(50000 + (x * 1000) AS VARCHAR),
    CAST((x * 2) - 1 AS VARCHAR) || ' ' || CAST(x * 2 AS VARCHAR),
    CAST(10 + MOD(x, 6) AS VARCHAR),
    CAST(20 + x AS VARCHAR) || '평',
    '06' || RIGHT('000' || CAST(x AS VARCHAR), 3),
    '주차 가능',
    CASE WHEN MOD(x, 2) = 0 THEN '1' ELSE '0' END,
    CASE WHEN MOD(x, 3) = 0 THEN '1' ELSE '0' END,
    CASE WHEN MOD(x, 4) = 0 THEN '1' ELSE '0' END,
    CASE WHEN MOD(x, 5) = 0 THEN '1' ELSE '0' END,
    CASE WHEN MOD(x, 2) = 1 THEN '1' ELSE '0' END,
    '1',
    DATEADD('DAY', -x, CURRENT_TIMESTAMP)
FROM SYSTEM_RANGE(1, 30);

INSERT INTO team (
    t_no, t_date, t_id, t_name, t_region, t_state, t_age, t_skill, t_uniform, t_kind, t_introduce, m_id, t_thum
)
SELECT
    NEXT VALUE FOR team_seq,
    FORMATDATETIME(DATEADD('DAY', -x, CURRENT_TIMESTAMP), 'yyyy-MM-dd HH:mm:ss'),
    CASE
        WHEN MOD(x, 3) = 0 THEN 'admin'
        WHEN MOD(x, 2) = 0 THEN 'manager'
        ELSE 'user'
    END,
    '테스트 팀 ' || x,
    CASE MOD(x, 5)
        WHEN 0 THEN '서울'
        WHEN 1 THEN '경기'
        WHEN 2 THEN '인천'
        WHEN 3 THEN '부산'
        ELSE '대구'
    END,
    '정상',
    CASE MOD(x, 4)
        WHEN 0 THEN '20대'
        WHEN 1 THEN '30대'
        WHEN 2 THEN '40대'
        ELSE '혼합'
    END,
    CASE MOD(x, 3)
        WHEN 0 THEN '상'
        WHEN 1 THEN '중'
        ELSE '하'
    END,
    CASE MOD(x, 4)
        WHEN 0 THEN '레드'
        WHEN 1 THEN '블루'
        WHEN 2 THEN '화이트'
        ELSE '블랙'
    END,
    CASE WHEN MOD(x, 2) = 0 THEN '남성' ELSE '혼성' END,
    '테스트 팀 소개 ' || x || '번입니다.',
    'user manager',
    'KakaoTalk_20250817_155245456.png'
FROM SYSTEM_RANGE(1, 30);

INSERT INTO gmatch (
    g_mno, g_no, g_vsno, g_game, g_gf, g_ga, g_gamedate, g_wdate
)
SELECT
    NEXT VALUE FOR mno_seq,
    CAST(x AS VARCHAR),
    CAST(CASE WHEN x = 30 THEN 1 ELSE x + 1 END AS VARCHAR),
    CAST((MOD(x, 3) + 1) AS VARCHAR),
    CAST(MOD(x + 2, 5) AS VARCHAR),
    CAST(MOD(x + 1, 4) AS VARCHAR),
    FORMATDATETIME(DATEADD('DAY', -x, CURRENT_TIMESTAMP), 'yyyy-MM-dd'),
    DATEADD('DAY', -x, CURRENT_TIMESTAMP)
FROM SYSTEM_RANGE(1, 30);

INSERT INTO notice (
    n_no, n_title, n_id, n_content, n_date, n_count
)
SELECT
    NEXT VALUE FOR n_no_seq,
    '공지사항 테스트 제목 ' || x,
    'admin',
    '공지사항 테스트 내용 ' || x || '번입니다.',
    DATEADD('DAY', -x, CURRENT_TIMESTAMP),
    0
FROM SYSTEM_RANGE(1, 30);

INSERT INTO teamblog (
    tb_no, tb_tbno, tb_id, tb_title, tb_content, tb_date, tb_thum, tb_state
)
SELECT
    NEXT VALUE FOR tb_no,
    'teamblog',
    CASE
        WHEN MOD(x, 3) = 0 THEN 'admin'
        WHEN MOD(x, 2) = 0 THEN 'manager'
        ELSE 'user'
    END,
    '블로그 테스트 제목 ' || x,
    '블로그 테스트 본문 ' || x || '번입니다.',
    DATEADD('DAY', -x, CURRENT_TIMESTAMP),
    'banner2.jpg',
    '2'
FROM SYSTEM_RANGE(1, 30);

INSERT INTO cment (
    c_no, c_c_id, c_m_thum, c_tbno, c_tbset, c_content, c_date, c_dec
)
SELECT
    NEXT VALUE FOR cmt_seq,
    CASE WHEN MOD(x, 2) = 0 THEN 'manager' ELSE 'user' END,
    CASE WHEN MOD(x, 2) = 0 THEN 'id.png' ELSE 'logo.png' END,
    'teamblog',
    CAST(x AS VARCHAR),
    '블로그 댓글 테스트 ' || x,
    DATEADD('HOUR', -x, CURRENT_TIMESTAMP),
    0
FROM SYSTEM_RANGE(1, 30);

INSERT INTO cment (
    c_no, c_c_id, c_m_thum, c_tbno, c_tbset, c_content, c_date, c_dec
)
SELECT
    NEXT VALUE FOR cmt_seq,
    CASE WHEN MOD(x, 2) = 0 THEN 'user' ELSE 'admin' END,
    'logo.png',
    'team',
    CAST(x AS VARCHAR),
    '팀 댓글 테스트 ' || x,
    DATEADD('HOUR', -x, CURRENT_TIMESTAMP),
    0
FROM SYSTEM_RANGE(1, 30);

INSERT INTO reservation (
    r_no, r_m_id, r_p_no, r_time, r_date, r_wdate
)
SELECT
    NEXT VALUE FOR reservation_seq,
    'user',
    CAST(x AS VARCHAR),
    CASE MOD(x, 4)
        WHEN 0 THEN '09:00'
        WHEN 1 THEN '11:00'
        WHEN 2 THEN '14:00'
        ELSE '18:00'
    END,
    FORMATDATETIME(DATEADD('DAY', x, CURRENT_TIMESTAMP), 'yyyy-MM-dd'),
    DATEADD('DAY', -x, CURRENT_TIMESTAMP)
FROM SYSTEM_RANGE(1, 30);

INSERT INTO payment (
    pay_code, p_no, pay_id, pay_price, pay_date
)
SELECT
    'PAY-' || RIGHT('0000' || CAST(x AS VARCHAR), 4),
    CAST(x AS VARCHAR),
    'user',
    CAST(50000 + (x * 1000) AS VARCHAR),
    DATEADD('DAY', -x, CURRENT_TIMESTAMP)
FROM SYSTEM_RANGE(1, 30);
