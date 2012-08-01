CREATE OR REPLACE FUNCTION test_function_call()
RETURNS void
LANGUAGE plpgsql
AS $$
	DECLARE

	BEGIN
		-- PERFORM my_function();

		l_email = btrim( p_email );
      l_email = btrim( lower(p_email) );

      l_var.part_1 = my_schema.my_function( l_var.part_2 );
	END
$$;

CREATE OR REPLACE FUNCTION test_function_call()
RETURNS void
LANGUAGE plpgsql
AS $$
	DECLARE

	BEGIN
		-- INSERT INTO my_schema.my_table DEFAULT VALUES;

		INSERT INTO my_schema.my_table VALUES ( val_1, val_2 );

		INSERT INTO my_schema.my_table( col_1, col_2 ) VALUES ( val_1, val_2 );

		INSERT INTO my_schema.my_table( col_1, col_2 ) VALUES ( DEFAULT, val_2 );

		INSERT INTO my_schema.my_table( col_1, col_2 ) (SELECT col_a, col_b FROM my_schema.your_table WHERE col_c = col_d);

	END
$$;

CREATE OR REPLACE FUNCTION test_function_call()
RETURNS void
LANGUAGE plpgsql
AS $$
	DECLARE

	BEGIN
UPDATE my_schema.my_table SET val_1 = val_2 FROM (SELECT  val2 FROM your_table ) as sel;
	END
$$;

CREATE OR REPLACE FUNCTION test_selects()
RETURNS void
LANGUAGE plpgsql
AS $$
	DECLARE

	BEGIN
		SELECT col1, lower(col2)
		INTO l_1, l_2
		FROM my_schema.mytable
		WHERE column_where = 5;

		SELECT col1, lower(col2)
		INTO l_1, l_2
		FROM my_schema.mytable
		WHERE column_where = 5
		GROUP BY group_column
		ORDER BY order_column;

		SELECT col1, lower(col2)
		INTO l_1, l_2
		FROM my_schema.mytable
		WHERE column_where = 5
		GROUP BY group_column
		HAVING lower(col2) = 'test';
	END
$$;