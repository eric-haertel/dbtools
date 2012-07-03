create or replace function test_join()
returns void language plpgsql
as
$_$
begin
	SELECT 	a, b, c
	FROM		std_data.mytable
	JOIN		std_data.youtable ON a = b and c IN ( 2, 3 ,4)
	WHERE		c = 5;


	SELECT	created::TIMESTAMP WITH TIME ZONE AT TIME ZONE my_time_zone AS local_time
	FROM		std_data.users_billing_history;
	SELECT	my_column
	FROM 		my_table
	WHERE  	filter_column NOT IN (SELECT e FROM list_table );

   SELECT	my_column
	FROM 		( SELECT * FROM my_table ) sel;

	SELECT	my_column
	FROM 		unnest( my_array );

	SELECT	mvc_code, dm_merchant_company_name, 'NOT_ASSIGNED'
	FROM	std_data.merchant_voucher_codes,
		(
			SELECT 	dm_merchant_company_name
			FROM 	std_data.deal_merchant
			JOIN	std_data.city_deals ON cd_merchant_id = dm_id
			WHERE	cd_mvc_pool_id = 1
			LIMIT	1
			 ) sel
	WHERE	mvc_pool_id = 1

	UNION

	SELECT	ujd_part2_provider_customer, dm_merchant_company_name, ujd_usage_state
	FROM	std_data.users_joined_deals
	JOIN	std_data.city_deals ON cd_id = ujd_city_deal_id
	JOIN	std_data.deal_merchant ON cd_merchant_id = dm_id
	WHERE	cd_mvc_pool_id = 1;

	SELECT	*
	FROM		table_except
	WHERE		mvc_pool_id = 1
	EXCEPT
	(
		SELECT	*
		FROM		table_except_2
		WHERE		cd_mvc_pool_id = 1
	);

	PERFORM 	my_column
	FROM		my_table
	WHERE		condition;

	SELECT	*
	INTO		l_var
	FROM		my_table
	WHERE		id IN (
			SELECT 	col1
			FROM 		your_table
			UNION
			SELECT	col1
			FROM 		my_table
		);
end;
$_$;
