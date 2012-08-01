create or replace function test_if() returns void language plpgsql
as
$_$
	declare
		c_test smallint = 3;
	begin
	
		if c_test not in (2,3,4,5) then
			raise notice 'test';
		end if;

	end;
$_$;

create or replace function test_case() returns void language plpgsql
as
$_$
	begin

		case  c_test
			when 2 then
				SELECT some_thing
				INTO var
				FROM my_table;
		end case;


		case  c_test[1][2]
			when 2 then
				SELECT some_thing
				INTO var
				FROM my_table;
		end case;

	end;
$_$;