SET search_path = my_schema, pg_catalog;

-- simple one line comment

CREATE OR REPLACE FUNCTION test() 
RETURNS void    
LANGUAGE plpgsql    
AS $$
	/**
	 * comment for documentation
	 */
	DECLARE
	BEGIN
		/*
		 * standard comment
		 */
		RETURN;
	END
$$;