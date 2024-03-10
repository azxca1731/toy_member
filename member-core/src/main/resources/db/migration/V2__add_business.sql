CREATE TABLE member.business
(
    business_id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    owner_member_id     BIGINT       NOT NULL,
    business_number     VARCHAR(50)  NOT NULL,
    business_name       VARCHAR(255) NOT NULL,
    zip_code            VARCHAR(50)  NOT NULL,
    road_address        VARCHAR(255) NOT NULL,
    road_address_detail VARCHAR(255),
    is_main             BOOLEAN DEFAULT FALSE,
    created_at          DATETIME     NOT NULL,
    updated_at          DATETIME
);

CREATE INDEX business_idx_1 ON member.business (created_at);
CREATE INDEX business_idx_2 ON member.business (updated_at);
CREATE INDEX business_idx_3 ON member.business (owner_member_id);
CREATE INDEX business_idx_4 ON member.business (zip_code);
CREATE INDEX business_idx_5 ON member.business (business_number);