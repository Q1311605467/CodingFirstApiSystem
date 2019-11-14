# 从 ojtest 迁移 到 coding_first 生产库 脚本文件

# 迁移 t_team_member_info 到 t_border_honor_rank
INSERT INTO coding_first.t_border_honor_rank
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
INSERT INTO coding_first.t_challenge_block
    (SELECT id   as id,
            name as name,
            gro  as block_type,
            text as description
     FROM ojtest.t_challenge_block);

# 迁移 t_challenge_condition 到 t_challenge_block_condition
INSERT INTO coding_first.t_challenge_block_condition
    (SELECT NULL          as id,
            belongBlockId as block_id,
            par           as precondition_block_id,
            num           as precondition_unlock_score
     FROM ojtest.t_challenge_condition);

# 迁移 t_challenge_problem 到 t_challenge_block_problem
INSERT INTO coding_first.t_challenge_block_problem
    (SELECT null  as id,
            id    as block_id,
            pid   as problem_order,
            tpid  as problem_id,
            score as score,
            0     as reward_acb
     FROM ojtest.t_challenge_problem);

# 迁移 permission 到 t_permission_type
INSERT INTO coding_first.t_permission_type
    (SELECT id   as id,
            name as permission_name
     FROM ojtest.permission);

# 迁移 problem 到 t_problem_info
INSERT INTO coding_first.t_problem_info
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
INSERT INTO coding_first.t_problem_difficult
    (SELECT id             as id,
            pid            as problem_id,
            0              as problem_type,
            difficult_type as difficult_level
     FROM ojtest.t_problem_difficult);

# 迁移 t_problem_sample 到 t_problem_sample
INSERT INTO coding_first.t_problem_sample
    (SELECT NULL   as id,
            pid    as problem_id,
            id     as case_order,
            input  as input_case,
            output as output_case
     FROM ojtest.t_problem_sample);

# 迁移 t_problem_tag 到 t_problem_tag
INSERT INTO coding_first.t_problem_tag
    (SELECT null     as id,
            name     as name,
            ttype    as tag_type,
            'SYSTEM' as create_user,
            priority as priority
     FROM ojtest.t_problem_tag);

# 迁移 t_problemview 到 t_problem_view
INSERT INTO coding_first.t_problem_view
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
