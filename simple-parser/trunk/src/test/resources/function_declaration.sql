CREATE OR REPLACE FUNCTION test() 
RETURNS void    
LANGUAGE plpgsql    
AS $$
	DECLARE
		l_simple_variable SMALLINT;
		l_with_default_1 text = 'not implemented yet';
		l_with_default_1 text := 'not implemented yet';
		l_with_default_2 boolean default 'not implemented yet';
	BEGIN
		RETURN;
	END
$$;