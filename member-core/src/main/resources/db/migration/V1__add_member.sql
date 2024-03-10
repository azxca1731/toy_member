CREATE TABLE member.member
(
    member_id     BIGINT AUTO_INCREMENT PRIMARY KEY,
    email         VARCHAR(255),
    password      VARCHAR(255),
    name          VARCHAR(100) NOT NULL,
    mobile        VARCHAR(50)  NOT NULL,
    birthday      VARCHAR(50)  NOT NULL,
    rrn7th        VARCHAR(10)  NOT NULL,
    provider      VARCHAR(50)  NOT NULL,
    member_status VARCHAR(50)  NOT NULL,
    withdraw_at   DATETIME,
    created_at    DATETIME     NOT NULL,
    updated_at    DATETIME
) DEFAULT CHARACTER SET = utf8mb4;

CREATE INDEX member_idx_1 ON member.member (created_at);
CREATE INDEX member_idx_2 ON member.member (updated_at);
CREATE INDEX member_idx_3 ON member.member (withdraw_at);
CREATE INDEX member_idx_4 ON member.member (name, birthday);