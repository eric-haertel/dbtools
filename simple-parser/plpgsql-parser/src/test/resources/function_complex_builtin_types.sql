SET search_path = std_api, pg_catalog;
CREATE OR REPLACE FUNCTION add_email_to_citydeal_newsletter_v6(p_city_id integer, p_email character varying, p_optin_done boolean, p_registration_origin text, p_is_logged_in boolean, p_ucd_postal_code character varying) RETURNS integer
    LANGUAGE plpgsql
    AS $$
	/* stores the subscription of an email address to specified city and the it's newsletter mappings.
	 * If no user with the given email exists in the system an unregisterd one is created.
	 * 
	 * The parameter p_p_ucd_postal_code, when not null, results into the creation of contact details for the user. 
	 * The parameter p_optin_done p_registration_origin, and p_is_logged_in are needed for the double opt-in logic.
	 * 
	 * @param p_city_id integer
	 * @param p_email character varying
	 * @param p_optin_done boolean
	 * @param p_registration_origin text
	 * @param p_is_logged_in boolean
	 * @param p_ucd_postal_code character varying
	 * 
	 * @returns the modification result contain a status code and a message text
	 * 
	 * changeset 20-12
	 */
DECLARE
	return_value integer;
	
	l_prepared_email character varying;

	l_appdomain smallint;
	l_u_id integer;
	l_u_state_id smallint;
	
	l_rp_reward_amount int;
	l_origin_parts text[];
	l_app_reward_expire_interval interval;
BEGIN
	-- set default value for return value
	return_value.result_status_id := 0; -- SUCCESS
	
	-- validation ------------------------------------------------------------------------------------------------------
	-- just lower case is allowed
	l_prepared_email := btrim( lower(p_email) );
	
	-- validate p_city_id
	SELECT ptc_appdomain 
	INTO l_appdomain 
	FROM	std_data.participant_cities
	WHERE	ptc_id = p_city_id;

	IF NOT FOUND	THEN
		return_value.result_status_id:=100; -- INVALID_CITY_ID
		return_value.result_status_msg:=std_data.scm(return_value.result_status_id);
		RETURN return_value;
	END IF;
	
	-- create user if necessary ----------------------------------------------------------------------------------------
	-- step 2: check if the p_email is already registered as a user
	SELECT
		/* 1*/ u_id, 
		/* 2*/ u_state_id
	INTO	
		/* 1*/ l_u_id,
		/* 2*/ l_u_state_id
	FROM
		std_data.users
	WHERE
		u_email = l_prepared_email
	AND	u_appdomain_id = l_appdomain;
	
	IF
		NOT FOUND
	THEN 
		-- create user
		l_u_id :=  std_data.user_create(
				/* 0 */ l_appdomain,		-- p_appdomain_id smallint
				/* 1 */ null,				-- p_first_name character varying
				/* 2 */ null,				-- p_last_name character varying
				/* 3 */ l_prepared_email,	-- p_email character varying
				/* 4 */ null,				-- p_password character varying, 
				/* 5 */ null,				-- p_birthday date, 
				/* 6 */ null,				-- p_personal_identifier character varying
				/* 7 */ null,				-- p_sex character
				/* 8 */ null,				-- p_recurring boolean, 
				/* 9 */ 1::smallint,		-- unregistered user p_u_ut_id smallint
				/*10 */ null,				-- p_facebook_id text
				/*11 */ null,				-- p_qq_connect_id text
				/*12 */ null,				-- p_qq_tracking_code text
				/*13 */ 'MD5',				-- p_encryption_method text
				/*14 */ ''					-- p_password_salt text
			);
		RAISE DEBUG E'INSERT NEW UNREGISTERED USER:\t%\t%', l_u_id, l_prepared_email;
		IF NOT p_ucd_postal_code is NULL THEN 
			INSERT INTO std_data.users_contact_details(
				    ucd_user_id,
				    ucd_postal_code
			) VALUES (
				 	l_u_id,
					p_ucd_postal_code
			);
		END IF;
		
	ELSEIF
		FOUND AND l_u_state_id = 1
	THEN
		UPDATE std_data.users SET u_state_id = 0 -- ACTIVE
		WHERE u_id = l_u_id;
		IF NOT p_ucd_postal_code IS NULL THEN
			UPDATE std_data.users_contact_details SET
				ucd_postal_code = p_ucd_postal_code
			WHERE ucd_user_id = l_u_id;
		END IF;
	END IF;
	
	return_value.result_status_id := std_api.newsletter_add_subscription(	
			l_u_id, 		-- p_u_id integer, 
			p_city_id, 		-- p_ptc_id integer, 
			p_optin_done, 	-- p_ncdr_optin_done boolean, 
			false, 			-- p_during_sale boolean, 
			p_registration_origin, -- p_ncdr_registration_origin text
			p_is_logged_in	-- p_is_user_known
		 );
	return_value.result_status_msg:=std_data.scm(return_value.result_status_id);
	
	IF return_value.result_status_id IN (0, 20) THEN
		SELECT 	ncdr_id
		INTO 	return_value.ncdr_id
		FROM std_data.newsletter_city_deals_registered
		WHERE 	ncdr_city_id = p_city_id 
			AND ncdr_user_id = l_u_id
		LIMIT 1;
	END IF;
		
	RETURN return_value;

EXCEPTION WHEN others THEN
	return_value.result_status_id:=999;
	return_value.result_status_msg:=SQLSTATE::text|| ' '  || SQLERRM;
	
	RAISE WARNING 'add_email_to_citydeal_newsletter: % - p_city_id=%, p_email=%, p_optin_done=%, p_registration_origin=%, p_is_logged_in=%, p_ucd_postal_code=%', 
						return_value.result_status_msg,  p_city_id,   p_email,   p_optin_done,   p_registration_origin,   p_is_logged_in,   p_ucd_postal_code;
	RETURN return_value;
END;
$$;
ALTER FUNCTION std_api.add_email_to_citydeal_newsletter_v6(p_city_id integer, p_email character varying, p_optin_done boolean, p_registration_origin text, p_is_logged_in boolean, p_ucd_postal_code character varying) OWNER TO stardeals;
