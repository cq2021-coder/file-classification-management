drop table if exists `file`;
drop table if exists `category`;

use cq_hwh;
create table `category`(
    `id` bigint not null primary key comment 'ID',
    `name` varchar(50) not null comment '分类名称',
    `sort` int not null
);

create table `file_up`(
    `id` bigint not null primary key comment '文件id',
    `name` varchar(50) not null comment '文件名',
    `file_path` varchar(50) not null comment '文件路径',
    `create_time` date not null comment '创建时间',
    `category_id` bigint references category(id)
)