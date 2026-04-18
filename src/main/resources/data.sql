-- 인코딩 주의:
-- 이 파일은 UTF-8 기준의 개발용 시드 데이터다.
-- 관리자/일반 사용자/구장 관리자 계정과 각 게시판의 기본 데이터를 함께 초기화한다.

INSERT INTO member (
    m_id, m_pw, m_name, m_phone, m_email, m_level, m_cname, m_zip, m_addr1, m_addr2,
    m_pname, m_account, m_bank, m_sysdate, m_check, m_thum
) VALUES
    ('admin', '12341234', '관리자 계정', '010-1000-1000', 'admin@fm.local', '0', 'FM 운영팀', '04524', '서울특별시 중구 세종대로 110', '12층 운영실', 'FM Admin', '', '', CURRENT_TIMESTAMP, '0', 'logo.png'),
    ('manager1', '12341234', '강민수', '010-2000-1001', 'manager1@fm.local', '2', '레드풋살파크', '06236', '서울특별시 강남구 테헤란로 101', '레드타워 2층', '레드풋살파크', '110-234-567890', '국민은행', CURRENT_TIMESTAMP, '0', 'id.png'),
    ('manager2', '12341234', '이서현', '010-2000-1002', 'manager2@fm.local', '2', '어반사커센터', '21394', '인천광역시 부평구 경원대로 220', 'A동 5층', '어반사커센터', '221-778-990011', '신한은행', CURRENT_TIMESTAMP, '0', 'logo.png'),
    ('player1', '12341234', '박준호', '010-3000-1001', 'player1@fm.local', '1', '', '08389', '서울특별시 구로구 디지털로 300', '801호', '준호FC', '', '', CURRENT_TIMESTAMP, '0', 'logo.png'),
    ('player2', '12341234', '최유진', '010-3000-1002', 'player2@fm.local', '1', '', '13529', '경기도 성남시 분당구 판교역로 235', '판교오피스 9층', '유진풋살', '', '', CURRENT_TIMESTAMP, '0', 'id.png'),
    ('player3', '12341234', '정하늘', '010-3000-1003', 'player3@fm.local', '1', '', '48058', '부산광역시 해운대구 센텀동로 45', '센텀빌딩 6층', '하늘킥', '', '', CURRENT_TIMESTAMP, '0', 'logo.png');

INSERT INTO image (i_no, i_sort, i_board, i_fname, i_fsize) VALUES
    (1, 'place', 'place', 'app.jpg', '1024'),
    (2, 'place', 'place', 'app.jpg', '1025'),
    (3, 'place', 'place', 'app.jpg', '1026'),
    (4, 'place', 'place', 'app.jpg', '1027'),
    (5, 'place', 'place', 'app.jpg', '1028'),
    (6, 'place', 'place', 'app.jpg', '1029'),
    (7, 'place', 'place', 'app.jpg', '1030'),
    (8, 'place', 'place', 'app.jpg', '1031'),
    (9, 'place', 'place', 'app.jpg', '1032'),
    (10, 'place', 'place', 'app.jpg', '1033'),
    (11, 'place', 'place', 'app.jpg', '1034'),
    (12, 'place', 'place', 'app.jpg', '1035'),
    (13, 'place', 'place', 'app.jpg', '1036'),
    (14, 'place', 'place', 'app.jpg', '1037'),
    (15, 'place', 'place', 'app.jpg', '1038'),
    (16, 'place', 'place', 'app.jpg', '1039'),
    (17, 'place', 'place', 'app.jpg', '1040'),
    (18, 'place', 'place', 'app.jpg', '1041'),
    (19, 'place', 'place', 'app.jpg', '1042'),
    (20, 'place', 'place', 'app.jpg', '1043');

INSERT INTO place (
    p_no, p_code, p_manager, p_userid, p_pname, p_place, p_explain, p_book, p_price, i_no,
    p_max, p_area, p_post, p_plus, p_op1, p_op2, p_op3, p_op4, p_op5, p_op6, p_date
) VALUES
    (1, 'P001', 'manager1', 'manager1', '레드아레나 강남점', '서울 강남구 테헤란로 101', '퇴근 후 가볍게 매치하기 좋은 실내 풋살장입니다.', '가능', '55000', '1 2', '10', '36x18', '06236', '주차 2시간 무료', '1', '1', '0', '1', '1', '1', DATEADD('DAY', -1, CURRENT_TIMESTAMP)),
    (2, 'P002', 'manager1', 'manager1', '레드아레나 송파점', '서울 송파구 올림픽로 240', '야간 조명이 밝고 샤워실이 깔끔한 구장입니다.', '가능', '60000', '3 4', '12', '40x20', '05554', '지하 주차장 이용 가능', '1', '1', '1', '0', '1', '1', DATEADD('DAY', -2, CURRENT_TIMESTAMP)),
    (3, 'P003', 'manager2', 'manager2', '어반사커 인천점', '인천 부평구 경원대로 220', '인천권 주말 매치 수요가 많은 실내 구장입니다.', '가능', '48000', '5 6', '10', '32x18', '21394', '대관 전 음료 제공', '1', '0', '1', '1', '0', '1', DATEADD('DAY', -3, CURRENT_TIMESTAMP)),
    (4, 'P004', 'manager2', 'manager2', '어반사커 부천점', '경기 부천시 길주로 300', '초보 팀도 부담 없이 사용할 수 있는 합리적 가격대입니다.', '가능', '50000', '7 8', '10', '34x18', '14543', '무료 공 대여', '1', '1', '0', '0', '1', '1', DATEADD('DAY', -4, CURRENT_TIMESTAMP)),
    (5, 'P005', 'manager1', 'manager1', '챔프필드 마포점', '서울 마포구 월드컵북로 402', '대중교통 접근성이 좋은 루프탑 풋살장입니다.', '가능', '65000', '9 10', '12', '42x20', '03925', '라커룸 제공', '1', '1', '1', '1', '0', '1', DATEADD('DAY', -5, CURRENT_TIMESTAMP)),
    (6, 'P006', 'manager2', 'manager2', '챔프필드 수원점', '경기 수원시 영통구 광교중앙로 140', '동호회 리그전 테스트용으로 쓰기 좋은 구장입니다.', '가능', '53000', '11 12', '10', '35x19', '16514', '야간 추가 조명', '1', '0', '1', '1', '1', '1', DATEADD('DAY', -6, CURRENT_TIMESTAMP)),
    (7, 'P007', 'manager1', 'manager1', '사커돔 구로점', '서울 구로구 디지털로 300', '회사 팀빌딩용 예약이 자주 들어오는 실내 구장입니다.', '가능', '57000', '13 14', '10', '33x17', '08389', '단체 예약 문의 가능', '1', '1', '0', '1', '1', '1', DATEADD('DAY', -7, CURRENT_TIMESTAMP)),
    (8, 'P008', 'manager2', 'manager2', '사커돔 성남점', '경기 성남시 분당구 판교역로 235', '판교권 저녁 경기 테스트에 적합한 구장입니다.', '가능', '62000', '15 16', '12', '38x19', '13529', '음향 장비 제공', '1', '1', '1', '0', '1', '1', DATEADD('DAY', -8, CURRENT_TIMESTAMP)),
    (9, 'P009', 'manager1', 'manager1', '더풋 라운지 강서점', '서울 강서구 공항대로 247', '주차가 편하고 가족 단위 방문도 많은 복합 스포츠 공간입니다.', '가능', '54000', '17 18', '10', '34x18', '07803', '카페 할인 쿠폰 제공', '1', '0', '0', '1', '1', '1', DATEADD('DAY', -9, CURRENT_TIMESTAMP)),
    (10, 'P010', 'manager2', 'manager2', '더풋 라운지 해운대점', '부산 해운대구 센텀동로 45', '부산 지역 원정팀 테스트용으로 무난한 실내 구장입니다.', '가능', '59000', '19 20', '10', '36x18', '48058', '장비 보관함 제공', '1', '1', '1', '1', '1', '1', DATEADD('DAY', -10, CURRENT_TIMESTAMP));

INSERT INTO team (
    t_no, t_date, t_id, t_name, t_region, t_state, t_age, t_skill, t_uniform, t_kind, t_introduce, m_id, t_thum
) VALUES
    (1, FORMATDATETIME(DATEADD('DAY', -2, CURRENT_TIMESTAMP), 'yyyy-MM-dd HH:mm:ss'), 'player1', '레드스타 FC', '서울', '정상', '20대', '중', '레드', '남성', '주 1회 정기전을 뛰는 직장인 팀입니다.', 'player1 player2 ', 'KakaoTalk_20250817_155245456.png'),
    (2, FORMATDATETIME(DATEADD('DAY', -4, CURRENT_TIMESTAMP), 'yyyy-MM-dd HH:mm:ss'), 'player2', '어반킥스', '경기', '정상', '30대', '중상', '화이트', '혼성', '판교와 분당 위주로 활동하는 매너 중심 팀입니다.', 'player2 player3 ', 'KakaoTalk_20250817_155245456.png'),
    (3, FORMATDATETIME(DATEADD('DAY', -6, CURRENT_TIMESTAMP), 'yyyy-MM-dd HH:mm:ss'), 'player3', '부산웨이브', '부산', '정상', '20대', '중', '네이비', '남성', '원정 경기 테스트를 자주 잡는 부산 팀입니다.', 'player3 ', 'KakaoTalk_20250817_155245456.png'),
    (4, FORMATDATETIME(DATEADD('DAY', -8, CURRENT_TIMESTAMP), 'yyyy-MM-dd HH:mm:ss'), 'manager1', '강남이글스', '서울', '정상', '30대', '상', '블랙', '남성', '강남권 실내 구장을 주로 이용하는 팀입니다.', 'manager1 player1 ', 'KakaoTalk_20250817_155245456.png'),
    (5, FORMATDATETIME(DATEADD('DAY', -10, CURRENT_TIMESTAMP), 'yyyy-MM-dd HH:mm:ss'), 'manager2', '인천스트라이커', '인천', '정상', '40대', '중', '그린', '혼성', '주말 오전 친선전에 맞춘 가벼운 매치 팀입니다.', 'manager2 player2 ', 'KakaoTalk_20250817_155245456.png'),
    (6, FORMATDATETIME(DATEADD('DAY', -12, CURRENT_TIMESTAMP), 'yyyy-MM-dd HH:mm:ss'), 'admin', 'FM 테스트팀', '전국', '정상', '혼합', '중', '옐로', '혼성', '관리자 화면 점검용으로 만들어 둔 기본 테스트 팀입니다.', 'admin player1 player3 ', 'KakaoTalk_20250817_155245456.png');

INSERT INTO gmatch (
    g_mno, g_no, g_vsno, g_game, g_gf, g_ga, g_gamedate, g_wdate
) VALUES
    (1, '1', '2', '1', '3', '1', FORMATDATETIME(DATEADD('DAY', -7, CURRENT_TIMESTAMP), 'yyyy-MM-dd'), DATEADD('DAY', -7, CURRENT_TIMESTAMP)),
    (2, '2', '3', '2', '2', '2', FORMATDATETIME(DATEADD('DAY', -6, CURRENT_TIMESTAMP), 'yyyy-MM-dd'), DATEADD('DAY', -6, CURRENT_TIMESTAMP)),
    (3, '3', '4', '3', '1', '3', FORMATDATETIME(DATEADD('DAY', -5, CURRENT_TIMESTAMP), 'yyyy-MM-dd'), DATEADD('DAY', -5, CURRENT_TIMESTAMP)),
    (4, '4', '5', '1', '4', '2', FORMATDATETIME(DATEADD('DAY', -4, CURRENT_TIMESTAMP), 'yyyy-MM-dd'), DATEADD('DAY', -4, CURRENT_TIMESTAMP)),
    (5, '5', '6', '2', '2', '2', FORMATDATETIME(DATEADD('DAY', -3, CURRENT_TIMESTAMP), 'yyyy-MM-dd'), DATEADD('DAY', -3, CURRENT_TIMESTAMP)),
    (6, '6', '1', '3', '0', '1', FORMATDATETIME(DATEADD('DAY', -2, CURRENT_TIMESTAMP), 'yyyy-MM-dd'), DATEADD('DAY', -2, CURRENT_TIMESTAMP));

INSERT INTO notice (
    n_no, n_title, n_id, n_content, n_date, n_count
) VALUES
    (1, '4월 서비스 점검 안내', 'admin', '4월 셋째 주 새벽 시간대에 서버 점검이 예정되어 있습니다.', DATEADD('DAY', -1, CURRENT_TIMESTAMP), 0),
    (2, '주말 매치 예약 오픈', 'admin', '주말 저녁 시간대 구장 예약 슬롯을 추가로 열었습니다.', DATEADD('DAY', -2, CURRENT_TIMESTAMP), 0),
    (3, '커뮤니티 이용 가이드', 'admin', '비방성 글이나 광고성 게시물은 운영 기준에 따라 삭제됩니다.', DATEADD('DAY', -3, CURRENT_TIMESTAMP), 0),
    (4, '신규 구장 등록 안내', 'admin', '구장 관리자는 마이페이지에서 직접 구장 정보를 등록할 수 있습니다.', DATEADD('DAY', -4, CURRENT_TIMESTAMP), 0),
    (5, '결제 테스트 공지', 'admin', '개발 환경에서는 테스트 결제 데이터가 함께 저장될 수 있습니다.', DATEADD('DAY', -5, CURRENT_TIMESTAMP), 0),
    (6, '매너 매치 캠페인', 'admin', '노쇼 방지와 경기 예절 준수를 위한 캠페인을 진행합니다.', DATEADD('DAY', -6, CURRENT_TIMESTAMP), 0),
    (7, '모바일 UI 개선 안내', 'admin', '모바일 화면에서 예약 흐름을 더 쉽게 보이도록 일부 UI를 조정했습니다.', DATEADD('DAY', -7, CURRENT_TIMESTAMP), 0),
    (8, '팀 생성 기능 점검', 'admin', '팀 소개와 프로필 이미지 등록 기능을 순차적으로 점검하고 있습니다.', DATEADD('DAY', -8, CURRENT_TIMESTAMP), 0),
    (9, '이벤트 매치 모집', 'admin', '테스트용 이벤트 매치를 위해 서울/경기권 팀을 모집합니다.', DATEADD('DAY', -9, CURRENT_TIMESTAMP), 0),
    (10, '공지사항 샘플 데이터', 'admin', '현재 화면 점검을 위해 공지사항 샘플 데이터를 적용해 두었습니다.', DATEADD('DAY', -10, CURRENT_TIMESTAMP), 0);

INSERT INTO teamblog (
    tb_no, tb_tbno, tb_id, tb_title, tb_content, tb_date, tb_thum, tb_state
) VALUES
    (1, 'teamblog', 'player1', '강남 평일 저녁 매치팀 구합니다', '강남권에서 평일 저녁 8시 이후 맞춰서 경기 가능한 팀 찾고 있습니다.', DATEADD('DAY', -1, CURRENT_TIMESTAMP), 'banner2.jpg', '2'),
    (2, 'teamblog', 'player2', '판교 근처 초보 환영 팀 모집', '가볍게 운동하실 분들 중심으로 판교 인근에서 주말 팀원을 모집합니다.', DATEADD('DAY', -2, CURRENT_TIMESTAMP), 'banner2.jpg', '2'),
    (3, 'teamblog', 'manager1', '레드아레나 송파점 후기 남깁니다', '조명과 샤워실 상태가 좋아서 재방문하기 괜찮았습니다.', DATEADD('DAY', -3, CURRENT_TIMESTAMP), 'banner2.jpg', '2'),
    (4, 'teamblog', 'player3', '부산 원정 매치 가능 팀 있나요', '해운대 쪽에서 다음 주 원정 경기 가능한 팀 있으면 댓글 부탁드립니다.', DATEADD('DAY', -4, CURRENT_TIMESTAMP), 'banner2.jpg', '2'),
    (5, 'teamblog', 'admin', '테스트용 커뮤니티 게시글 1', '커뮤니티 레이아웃과 댓글 기능을 확인하기 위한 샘플 글입니다.', DATEADD('DAY', -5, CURRENT_TIMESTAMP), 'banner2.jpg', '2'),
    (6, 'teamblog', 'manager2', '인천 야간 매치 추천 구장 공유', '인천 부평 쪽은 실내 구장이 많아서 우천 시에도 잡기 수월합니다.', DATEADD('DAY', -6, CURRENT_TIMESTAMP), 'banner2.jpg', '2'),
    (7, 'teamblog', 'player1', '풋살화 추천 부탁드립니다', '실내 구장 위주로 쓰기 편한 풋살화 추천 있으면 공유 부탁드립니다.', DATEADD('DAY', -7, CURRENT_TIMESTAMP), 'banner2.jpg', '2'),
    (8, 'teamblog', 'player2', '혼성팀 친선전 상대 구합니다', '분위기 좋은 혼성팀끼리 가볍게 친선전 진행하고 싶습니다.', DATEADD('DAY', -8, CURRENT_TIMESTAMP), 'banner2.jpg', '2'),
    (9, 'teamblog', 'manager1', '구장 예약 시간 추천', '주말 오전보다 평일 늦은 저녁이 가격대비 만족도가 높았습니다.', DATEADD('DAY', -9, CURRENT_TIMESTAMP), 'banner2.jpg', '2'),
    (10, 'teamblog', 'player3', '팀 프로필 사진 어디서 찍나요', '다른 팀들처럼 간단한 단체 사진을 찍으려면 어떤 방식이 좋을까요.', DATEADD('DAY', -10, CURRENT_TIMESTAMP), 'banner2.jpg', '2');

INSERT INTO cment (
    c_no, c_c_id, c_m_thum, c_tbno, c_tbset, c_content, c_date, c_dec
) VALUES
    (1, 'manager1', 'id.png', 'teamblog', '1', '시간 맞으면 저희 팀도 가능합니다.', DATEADD('HOUR', -2, CURRENT_TIMESTAMP), 0),
    (2, 'player2', 'id.png', 'teamblog', '1', '강남이면 금요일 저녁이 제일 편합니다.', DATEADD('HOUR', -3, CURRENT_TIMESTAMP), 0),
    (3, 'player1', 'logo.png', 'teamblog', '2', '초보 위주면 관심 있습니다.', DATEADD('HOUR', -4, CURRENT_TIMESTAMP), 0),
    (4, 'admin', 'logo.png', 'teamblog', '3', '후기 감사합니다. 계속 데이터 보강 중입니다.', DATEADD('HOUR', -5, CURRENT_TIMESTAMP), 0),
    (5, 'manager2', 'logo.png', 'teamblog', '4', '부산 쪽 팀도 몇 군데 더 들어올 예정입니다.', DATEADD('HOUR', -6, CURRENT_TIMESTAMP), 0),
    (6, 'player3', 'logo.png', 'teamblog', '5', '테스트용이어도 내용은 무난하네요.', DATEADD('HOUR', -7, CURRENT_TIMESTAMP), 0),
    (7, 'player1', 'logo.png', 'team', '1', '팀 소개 문구가 깔끔합니다.', DATEADD('HOUR', -8, CURRENT_TIMESTAMP), 0),
    (8, 'player2', 'id.png', 'team', '2', '판교 쪽이면 일정 맞춰보고 싶습니다.', DATEADD('HOUR', -9, CURRENT_TIMESTAMP), 0),
    (9, 'manager1', 'id.png', 'team', '4', '강남권 팀과 스크림 잡으셔도 좋겠습니다.', DATEADD('HOUR', -10, CURRENT_TIMESTAMP), 0),
    (10, 'admin', 'logo.png', 'team', '6', '관리자 테스트 팀 코멘트입니다.', DATEADD('HOUR', -11, CURRENT_TIMESTAMP), 0);

INSERT INTO reservation (
    r_no, r_m_id, r_p_no, r_time, r_date, r_wdate
) VALUES
    (1, 'player1', '1', '19:00', FORMATDATETIME(DATEADD('DAY', 1, CURRENT_TIMESTAMP), 'yyyy-MM-dd'), DATEADD('DAY', -1, CURRENT_TIMESTAMP)),
    (2, 'player2', '2', '20:00', FORMATDATETIME(DATEADD('DAY', 2, CURRENT_TIMESTAMP), 'yyyy-MM-dd'), DATEADD('DAY', -2, CURRENT_TIMESTAMP)),
    (3, 'player3', '3', '18:00', FORMATDATETIME(DATEADD('DAY', 3, CURRENT_TIMESTAMP), 'yyyy-MM-dd'), DATEADD('DAY', -3, CURRENT_TIMESTAMP)),
    (4, 'player1', '5', '21:00', FORMATDATETIME(DATEADD('DAY', 4, CURRENT_TIMESTAMP), 'yyyy-MM-dd'), DATEADD('DAY', -4, CURRENT_TIMESTAMP)),
    (5, 'player2', '8', '11:00', FORMATDATETIME(DATEADD('DAY', 5, CURRENT_TIMESTAMP), 'yyyy-MM-dd'), DATEADD('DAY', -5, CURRENT_TIMESTAMP));

INSERT INTO payment (
    pay_code, p_no, pay_id, pay_price, pay_date
) VALUES
    ('PAY-0001', '1', 'player1', '55000', DATEADD('DAY', -1, CURRENT_TIMESTAMP)),
    ('PAY-0002', '2', 'player2', '60000', DATEADD('DAY', -2, CURRENT_TIMESTAMP)),
    ('PAY-0003', '3', 'player3', '48000', DATEADD('DAY', -3, CURRENT_TIMESTAMP)),
    ('PAY-0004', '5', 'player1', '65000', DATEADD('DAY', -4, CURRENT_TIMESTAMP)),
    ('PAY-0005', '8', 'player2', '62000', DATEADD('DAY', -5, CURRENT_TIMESTAMP));

ALTER SEQUENCE seq_image RESTART WITH 21;
ALTER SEQUENCE place_seq RESTART WITH 11;
ALTER SEQUENCE team_seq RESTART WITH 7;
ALTER SEQUENCE mno_seq RESTART WITH 7;
ALTER SEQUENCE n_no_seq RESTART WITH 11;
ALTER SEQUENCE tb_no RESTART WITH 11;
ALTER SEQUENCE cmt_seq RESTART WITH 11;
ALTER SEQUENCE reservation_seq RESTART WITH 6;
