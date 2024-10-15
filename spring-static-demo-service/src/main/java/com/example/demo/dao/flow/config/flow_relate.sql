-- 字段解释
-- id: 主键，使用 UUID 类型，默认值为生成的 UUID。
-- name: 非空字符串，带有索引。
-- description: 可选文本字段，带有索引。
-- icon: 可选字符串字段，最长 255 个字符。
-- icon_bg_color: 可选字符串字段，必须是 7 个字符长的十六进制颜色值。
-- data: 可选的 JSONB 类型字段，用于存储 JSON 数据。
-- is_component: 可选布尔值字段，默认值为 FALSE。
-- updated_at: 可选的时间戳字段，默认值为当前时间。
-- webhook: 可选布尔值字段，默认值为 FALSE。
-- endpoint_name: 可选字符串字段，带有索引。
-- user_id: 可选 UUID 字段，用作外键，指向 user 表的 id 字段。
-- folder_id: 可选 UUID 字段，用作外键，指向 folder 表的 id 字段。
-- 约束和索引
-- unique_flow_name: 约束 user_id 和 name 的组合唯一。
-- unique_flow_endpoint_name: 约束 user_id 和 endpoint_name 的组合唯一。
-- 外键约束 user_id 引用 user 表的 id 字段。
-- 外键约束 folder_id 引用 folder 表的 id 字段。
-- 索引：在 name、description、endpoint_name、user_id 和 folder_id 字段上建立索引，以提高查询性能。
CREATE TABLE flow (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(), --CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
    name VARCHAR(255) NOT NULL,
    description TEXT,
    icon VARCHAR(255),
    icon_bg_color VARCHAR(7),
    data JSONB,
    is_component BOOLEAN DEFAULT FALSE,
    updated_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    webhook BOOLEAN DEFAULT FALSE,
    endpoint_name VARCHAR(255),
    user_id UUID,
    folder_id UUID,
--     CONSTRAINT unique_flow_name UNIQUE (user_id, name),
--     CONSTRAINT unique_flow_endpoint_name UNIQUE (user_id, endpoint_name),
--     FOREIGN KEY (user_id) REFERENCES user (id),
--     FOREIGN KEY (folder_id) REFERENCES folder (id),
--     INDEX (name),
--     INDEX (description),
--     INDEX (endpoint_name),
--     INDEX (user_id),
--     INDEX (folder_id)
);

CREATE UNIQUE INDEX unique_flow_name on flow (user_id, name);
CREATE UNIQUE INDEX unique_flow_endpoint_name on flow (user_id, endpoint_name);

CREATE INDEX index_name ON flow (name);
CREATE INDEX index_description ON flow (description);
CREATE INDEX index_endpoint_name ON flow (endpoint_name);
CREATE INDEX index_user_id ON flow (user_id);
CREATE INDEX index_folder_id ON flow (folder_id);


-- 字段解释
-- id: 主键，使用 UUID 类型，默认值为生成的 UUID。
-- name: 非空字符串，最长 255 个字符，带有索引。
-- description: 可选文本字段。
-- parent_id: 可选 UUID 字段，用作外键，指向 folder 表的 id 字段，用于实现文件夹的层级关系。
-- user_id: 可选 UUID 字段，用作外键，指向 user 表的 id 字段。
-- 约束和索引
-- unique_folder_name: 约束 user_id 和 name 的组合唯一。
-- 外键约束 parent_id 引用 folder 表的 id 字段，实现文件夹的层级关系。
-- 外键约束 user_id 引用 user 表的 id 字段。
-- 索引：在 name、parent_id 和 user_id 字段上建立索引，以提高查询性能。
CREATE TABLE folder (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(255) NOT NULL,
    description TEXT,
    parent_id UUID,
    user_id UUID,
--     CONSTRAINT unique_folder_name UNIQUE (user_id, name),
--     FOREIGN KEY (parent_id) REFERENCES folder(id),
--     FOREIGN KEY (user_id) REFERENCES "user" (id),
--     INDEX (name),
--     INDEX (parent_id),
--     INDEX (user_id)
);
CREATE UNIQUE INDEX unique_folder_name on folder (user_id, name);
CREATE INDEX index_folder_name ON folder (name);
CREATE INDEX index_folder_user_id ON folder (user_id);

-- 字段解释
-- id: 主键，使用 UUID 类型，默认值为生成的 UUID。
-- username: 非空字符串，最长 255 个字符，唯一。
-- password: 非空字符串，最长 255 个字符。
-- profile_image: 可选字符串字段，最长 255 个字符。
-- is_active: 布尔值字段，默认值为 FALSE。
-- is_superuser: 布尔值字段，默认值为 FALSE。
-- create_at: 带时区的时间戳字段，默认值为当前时间。
-- updated_at: 带时区的时间戳字段，默认值为当前时间。
-- last_login_at: 可选带时区的时间戳字段。
-- store_api_key: 可选字符串字段，最长 255 个字符。
-- 关系和级联删除
-- 在你的模型中，还定义了与 ApiKey、Flow、Variable 和 Folder 的关系，并包含级联删除（cascade delete）。要在数据库中实现这一点，需要在相关表中定义相应的外键约束。
-- 假设 api_key, flow, variable, folder 表已经存在
-- ALTER TABLE api_key
-- ADD CONSTRAINT fk_api_key_user_id
-- FOREIGN KEY (user_id)
-- REFERENCES "user" (id)
-- ON DELETE CASCADE;
--
-- ALTER TABLE flow
-- ADD CONSTRAINT fk_flow_user_id
-- FOREIGN KEY (user_id)
-- REFERENCES "user" (id)
-- ON DELETE CASCADE;
--
-- ALTER TABLE variable
-- ADD CONSTRAINT fk_variable_user_id
-- FOREIGN KEY (user_id)
-- REFERENCES "user" (id)
-- ON DELETE CASCADE;
--
-- ALTER TABLE folder
-- ADD CONSTRAINT fk_folder_user_id
-- FOREIGN KEY (user_id)
-- REFERENCES "user" (id)
-- ON DELETE CASCADE;
CREATE TABLE user_tb (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    profile_image VARCHAR(255),
    is_active BOOLEAN DEFAULT FALSE,
    is_superuser BOOLEAN DEFAULT FALSE,
    create_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    last_login_at TIMESTAMPTZ,
    store_api_key VARCHAR(255)
);
INSERT INTO public.user_tb(
   	username, password, is_active, is_superuser, store_api_key)
   	VALUES ('admin', 'admin', 'true', 'true', 'admin-api-key');

-- 字段解释
-- id: 主键，使用 UUID 类型，默认值为生成的 UUID。
-- name: 可选字符串，最长 255 个字符，带有索引。
-- last_used_at: 可选带时区的时间戳字段。
-- total_uses: 整型字段，默认值为 0。
-- is_active: 布尔值字段，默认值为 TRUE。
-- created_at: 带时区的时间戳字段，默认值为当前时间，并且不能为空。
-- api_key: 非空字符串，最长 255 个字符，唯一。
-- user_id: 非空 UUID 字段，用作外键，指向 user 表的 id 字段。
-- 关系和级联删除
-- 在你的模型中，你定义了与 User 的关系，并包含级联删除（cascade delete）。要在数据库中实现这一点，需要在 user_id 外键约束中添加 ON DELETE CASCADE。
CREATE TABLE api_key (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(255),
    last_used_at TIMESTAMPTZ,
    total_uses INT DEFAULT 0,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP NOT NULL,
    api_key VARCHAR(255) NOT NULL UNIQUE,
    user_id UUID NOT NULL,
    FOREIGN KEY (user_id) REFERENCES "user" (id) ON DELETE CASCADE,
    INDEX (name),
    INDEX (api_key),
    INDEX (user_id)
);


-- 字段解释
-- id: 主键，使用 UUID 类型，默认值为生成的 UUID。
-- name: 非空字符串，最长 255 个字符，带有索引。
-- value: 非空文本字段，用于存储加密的变量值。
-- default_fields: 可选 JSONB 类型字段，用于存储默认字段。
-- type: 可选字符串，最长 255 个字符，用于存储变量的类型。
-- created_at: 带时区的时间戳字段，默认值为当前时间。
-- updated_at: 可选带时区的时间戳字段。
-- user_id: 非空 UUID 字段，用作外键，指向 user 表的 id 字段。
-- 关系和级联删除
-- 在你的模型中，你定义了与 User 的关系，并包含级联删除（cascade delete）。要在数据库中实现这一点，需要在 user_id 外键约束中添加 ON DELETE CASCADE。
-- 唯一性约束
-- UNIQUE (user_id, name): 约束 user_id 和 name 的组合唯一，确保每个用户的变量名称唯一。
CREATE TABLE variable (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(255) NOT NULL,
    value TEXT NOT NULL,
    default_fields JSONB,
    type VARCHAR(255),
    created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ,
    user_id UUID NOT NULL,
    FOREIGN KEY (user_id) REFERENCES "user" (id) ON DELETE CASCADE,
    UNIQUE (user_id, name),
    INDEX (name),
    INDEX (user_id)
);