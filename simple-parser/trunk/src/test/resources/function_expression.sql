CREATE OR REPLACE FUNCTION test_return_coalesce( )
RETURNS SMALLINT
LANGUAGE plpgsql
AS $$
	DECLARE
		l_simple_variable SMALLINT;
		l_with_default_1 text = 'not implemented yet';
		l_with_default_2 boolean default 'not implemented yet';
	BEGIN

		l_payload = coalesce( '"' || p_citydeal.cd_ptc_city_id::text, 'null');
	   l_payload = '{ '
			|| '"dealId" : "' 				|| 					p_citydeal.cd_id::text 										|| '", '
			|| '"cityId" :' 				|| coalesce('"' || 	p_citydeal.cd_ptc_city_id::text 	|| '"', 'null')			|| ', '
			|| '"appDomainId" :' 			|| coalesce('"' || 	p_citydeal.cd_appdomain_id::text 	|| '"', 'null') 		|| ', '
			|| '"voucherCodeId" :' 			|| coalesce('"' || 	p_citydeal.cd_mvc_pool_id::text 	|| '"', 'null') 		|| ', '
			|| '"dealLimitGroupId" :' 		|| coalesce('"' || 	p_citydeal.cd_dlg_id::text 			|| '"', 'null') 	    || ', '
			|| '"dealType" :' 				|| coalesce('"' || 	p_citydeal.cd_deal_type_id::text 	|| '"', 'null')			|| ', '
			|| '"merchantId" :' 			|| coalesce('"' || 	p_citydeal.cd_merchant_id::text 	|| '"', 'null')			|| ', '
			|| '"multidealParentId" :'	 	|| coalesce('"' || 	p_citydeal.cd_multideal_parent::text|| '"', 'null')  		|| ', '
			|| '"salesforceId" :' 			|| coalesce('"' || 	p_citydeal.cd_salesforce_id::text 	|| '"', 'null') 		|| ', '
			|| '"status" :' 				|| coalesce('"' || 	p_citydeal.cd_deal_status::text 	|| '"', 'null') 		|| ', '
			|| '"mulliganParentId" :'  		|| coalesce('"' || 	p_citydeal.cd_mulligan_parent::text || '"', 'null') 		|| ', '
			|| '"dealPurchaseControlId" :'	|| coalesce('"' || 	p_citydeal.cd_dpc_id::text 			|| '"', 'null')
			|| '}';
		RETURN l_simple_variable;
	END
$$;

CREATE OR REPLACE FUNCTION test_if()
RETURNS void
LANGUAGE plpgsql
AS $$
	DECLARE

	BEGIN
		IF l_test THEN
			RETURN false;
		ELSIF NOT l_test THEN
			RETURN true;
		END IF;

		IF l_test = 10 THEN
			RETURN false;
		END IF;

		IF l_test = 10 * 3 THEN
			RETURN false;
		END IF;

		RETURN;
	END
$$;

CREATE OR REPLACE FUNCTION test_raise()
RETURNS void
LANGUAGE plpgsql
AS $$
	DECLARE
		l_info_log text = 'not implemented yet';
	BEGIN
	    RAISE DEBUG 'test debug';

        RAISE LOG 'test log: %', l_info_log;

        RAISE INFO 'test info', l_info_log, l_info_log, l_info_log, l_info_log;

	    RAISE NOTICE 'test notice';

	    RAISE WARNING 'test ';

        RAISE EXCEPTION 'test';

		RETURN;
	END
$$;

CREATE OR REPLACE FUNCTION test_assignment()
RETURNS void    
LANGUAGE plpgsql    
AS $$
	DECLARE
		l_simple_variable SMALLINT;
		l_with_default_1 text = 'not implemented yet';
		l_with_default_2 boolean default 'not implemented yet';
	BEGIN
	    -- assignment
	    variable1 = 25;
	    variable2 = 15.46463;
	    variable3 = true;
	    variable4 = false;
	    variable5 = 'teatrses';

	    variable6 := substring( variable2 from 2 for 3 );

		RETURN;
	END
$$;

CREATE OR REPLACE FUNCTION test_return_bool_const()
RETURNS boolean
LANGUAGE plpgsql
AS $$
	DECLARE
		l_simple_variable SMALLINT;
		l_with_default_1 text = 'not implemented yet';
		l_with_default_2 boolean default 'not implemented yet';
	BEGIN

		RETURN TRUE;
	END
$$;

CREATE OR REPLACE FUNCTION test_return_variable()
RETURNS SMALLINT
LANGUAGE plpgsql
AS $$
	DECLARE
		l_simple_variable SMALLINT;
		l_with_default_1 text = 'not implemented yet';
		l_with_default_2 boolean default 'not implemented yet';
	BEGIN

		RETURN l_simple_variable;
	END
$$;

