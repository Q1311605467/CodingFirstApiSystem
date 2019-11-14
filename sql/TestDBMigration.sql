# 从 ojtest 迁移 到 cf_test 测试库 脚本文件

# 迁移 t_message 到 t_user_message
INSERT INTO cf_test.t_user_message
    (SELECT mid   as id,
            user  as username,
            statu as status,
            title as title,
            text  as text,
            time  as time
     FROM ojtest.t_message
    );

# 迁移 contest 到 t_contest_info
INSERT INTO cf_test.t_contest_info
    (SELECT null              as id,
            id                as contest_id,
            kind              as contest_kind,
            LEFT(name, 30)    as title,
            info              as description,
            createuser        as create_user,
            '2019-01-01'      as create_time,
            beginTime         as begin_time,
            endTime           as end_time,
            ctype             as permission_type,
            password          as password,
            registerstarttime as register_begin_time,
            registerendtime   as register_end_time,
            computerating     as computer_rating,
            rankType             rank_type,
            1                 as problem_put_tag,
            1                 as status_read_out,
            1                 as show_register_list,
            1                 as show_border_list,
            1                 as show_other_status
     from ojtest.contest
    );


# 迁移 statu 到 t_judge_status
INSERT INTO cf_test.t_judge_status
    (select id         as id,
            ruser      as username,
            pid        as problem_id,
            cid        as contest_id,
            lang       as language,
            submitTime as submit_time,
            result     as result,
            score      as score,
            timeUsed   as time_used,
            memoryUsed as memory_used,
            code       as code,
            codelen    as code_length
     FROM ojtest.statu
    );

# 迁移 ceinfo 到 t_judge_result
INSERT INTO cf_test.t_judge_result
    (SELECT null as id,
            rid  as judge_id,
            info as info
     FROM ojtest.ceinfo
    );


# 迁移 t_team_member_info 到 t_border_honor_rank
INSERT INTO cf_test.t_border_honor_rank
    (SELECT id            as id,
            username1     as username_one,
            name1         as real_name_one,
            username2     as username_two,
            name2         as real_name_two,
            username3     as username_three,
            name3         as real_name_three,
            time          as reward_date,
            '2019-01-01'  as register_time,
            contest_level as contest_level,
            awards_level  as award_level,
            text          as description
     FROM ojtest.t_team_member_info);

# 迁移 t_challenge_block 到 t_challenge_block
INSERT INTO cf_test.t_challenge_block
    (SELECT id   as id,
            name as name,
            gro  as block_type,
            text as description
     FROM ojtest.t_challenge_block);

# 迁移 t_challenge_condition 到 t_challenge_block_condition
INSERT INTO cf_test.t_challenge_block_condition
    (SELECT NULL          as id,
            belongBlockId as block_id,
            par           as precondition_block_id,
            num           as precondition_unlock_score
     FROM ojtest.t_challenge_condition);

# 迁移 t_challenge_problem 到 t_challenge_block_problem
INSERT INTO cf_test.t_challenge_block_problem
    (SELECT null  as id,
            id    as block_id,
            pid   as problem_order,
            tpid  as problem_id,
            score as score,
            0     as reward_acb
     FROM ojtest.t_challenge_problem);

# 迁移 t_challenge_openblock 到 t_challenge_user_open_block
INSERT INTO cf_test.t_challenge_user_open_block
    (select null  as id,
            username,
            block as block_id,
            NOW() as unlock_time
     from ojtest.t_challenge_openblock
    );

# 迁移 permission 到 t_permission_type
INSERT INTO cf_test.t_permission_type
    (SELECT id   as id,
            name as permission_name
     FROM ojtest.permission);

# 迁移 problem 到 t_problem_info
INSERT INTO cf_test.t_problem_info
    (SELECT NULL     as id,
            pid      as problem_id,
            title    as title,
            ojid     as belong_oj_id,
            ojspid   as belong_problem_id,
            'SYSTEM' as author,
            0        as total_submit,
            0        as total_ac,
            0        as total_submit_user,
            0        as total_ac_user,
            0        as visible,
            0        as judge_option
     FROM ojtest.problem
    );

# 迁移 t_problem_difficult 到 t_problem_difficult
INSERT INTO cf_test.t_problem_difficult
    (SELECT id             as id,
            pid            as problem_id,
            difficult_type as difficult_level
     FROM ojtest.t_problem_difficult);

# 迁移 t_problem_sample 到 t_problem_sample
INSERT INTO cf_test.t_problem_sample
    (SELECT NULL   as id,
            pid    as problem_id,
            id     as case_order,
            input  as input_case,
            output as output_case
     FROM ojtest.t_problem_sample);

# 迁移 t_problem_tag 到 t_problem_tag
INSERT INTO cf_test.t_problem_tag
    (SELECT null     as id,
            name     as name,
            ttype    as tag_type,
            'SYSTEM' as create_user,
            priority as priority
     FROM ojtest.t_problem_tag);

# 迁移 t_problemview 到 t_problem_view
INSERT INTO cf_test.t_problem_view
    (SELECT null        as id,
            pid         as problem_id,
            timelimit   as time_limit,
            MenoryLimit as memory_limit,
            Int64       as int_format,
            spj         as spj,
            Dis         as description,
            Input       as input,
            Output      as output
     FROM ojtest.t_problemview);

# 迁移 t_usersolve 到 t_user_problem_solved
INSERT INTO cf_test.t_user_problem_solved
    (SELECT null     as id,
            username as username,
            pid      as problem_id,
            status   as try_count,
            status   as solved_count,
            now()    as last_try_time,
            now()    as first_solved_time
     FROM ojtest.t_usersolve);

# 迁移 t_mall 到 t_mall_goods
INSERT INTO cf_test.t_mall_goods
    (SELECT id             as id,
            title          as name,
            acb            as cost,
            0              as goods_type,
            -1             as stock,
            des            as description,
            url            as picture_url,
            1              as visible,
            user           as shelf_user,
            time           as shelf_time,
            buyLimit       as buy_limit,
            buyVerifyLimit as buy_verify_limit
     FROM ojtest.t_mall);

# 迁移 t_problem_tag_record 到 t_problem_tag_record
INSERT INTO cf_test.t_problem_tag_record
    (SELECT null     as id,
            username as username,
            pid      as problem_id,
            tagid    as tag_id,
            now() as time,
            rating   as confidence
     FROM ojtest.t_problem_tag_record
    );