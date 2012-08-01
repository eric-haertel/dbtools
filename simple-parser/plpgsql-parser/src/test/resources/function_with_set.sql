SET search_path = std_api, pg_catalog;

CREATE OR REPLACE FUNCTION test() 
RETURNS void    
LANGUAGE plpgsql    
AS $$
	DECLARE
	BEGIN
		RETURN;
	END;
$$;