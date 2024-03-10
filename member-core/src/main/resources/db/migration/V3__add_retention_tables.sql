CREATE TABLE member.withdraw_member
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
    migrated_at   DATETIME     NOT NULL,
    created_at    DATETIME     NOT NULL,
    updated_at    DATETIME
) DEFAULT CHARACTER SET = utf8mb4;

CREATE INDEX withdraw_member_idx_1 ON member.withdraw_member (created_at);
CREATE INDEX withdraw_member_idx_2 ON member.withdraw_member (updated_at);
CREATE INDEX withdraw_member_idx_3 ON member.withdraw_member (withdraw_at);
CREATE INDEX withdraw_member_idx_4 ON member.withdraw_member (migrated_at);
CREATE INDEX withdraw_member_idx_5 ON member.withdraw_member (name, birthday);

CREATE TABLE member.withdraw_business
(
    business_id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    owner_member_id     BIGINT       NOT NULL,
    business_number     VARCHAR(50)  NOT NULL,
    business_name       VARCHAR(255) NOT NULL,
    zip_code            VARCHAR(50)  NOT NULL,
    road_address        VARCHAR(255) NOT NULL,
    road_address_detail VARCHAR(255),
    is_main             BOOLEAN DEFAULT FALSE,
    migrated_at         DATETIME     NOT NULL,
    created_at          DATETIME     NOT NULL,
    updated_at          DATETIME
);

CREATE INDEX withdraw_business_idx_1 ON member.withdraw_business (created_at);
CREATE INDEX withdraw_business_idx_2 ON member.withdraw_business (updated_at);
CREATE INDEX withdraw_business_idx_3 ON member.withdraw_business (owner_member_id);
CREATE INDEX withdraw_business_idx_4 ON member.withdraw_business (migrated_at);
CREATE INDEX withdraw_business_idx_5 ON member.withdraw_business (business_number);
