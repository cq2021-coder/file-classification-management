drop table if exists `file_up`;
drop table if exists `category`;

use cq_hwh;
create table `category`(
    `id` bigint not null primary key comment 'ID',
    `name` varchar(50) not null comment '分类名称'
);
insert into category (id, name) values (1,'视频');
insert into category (id, name) values (2,'图片');
insert into category (id, name) values (3,'Word文档');
insert into category (id, name) values (4,'Excel表格');

create table `file_up`(
    `id` bigint not null primary key comment '文件id',
    `name` varchar(50) not null comment '文件名',
    `file_path` varchar(500) not null comment '文件路径',
    `create_time` date not null comment '创建时间',
    `category_id` bigint references category(id)
);
insert into file_up (id, name, file_path , create_time , category_id) values (1,'测试1','D:\Tencent Files\QQ截图20210519192318.png','2021/6/21',2);
insert into file_up (id, name, file_path , create_time , category_id) values (2,'测试2','D:\Tencent Files\VID_20210519_195913.mp4','2021/6/21',1);
insert into file_up (id, name, file_path , create_time , category_id) values (3,'测试3','D:\Tencent Files\测试结果.docx','2021/6/21',3);
insert into file_up (id, name, file_path , create_time , category_id) values (4,'测试4','D:\Tencent Files\人员信息统计表.xlsx','2021/6/21',4);
