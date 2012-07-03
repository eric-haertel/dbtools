SELECT term_a OR term_b, term_c is not null;

SELECT created::TIMESTAMP WITH TIME ZONE AT TIME ZONE timezone
FROM 	std_data.users_billing_history
WHERE ubh_citydeal_id = p_cd_id;