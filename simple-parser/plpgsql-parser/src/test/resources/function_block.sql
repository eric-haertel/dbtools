create or replace function test()
returns void language plpgsql
as
$_$
begin
	declare
	begin
		raise info 'test';
	end;
end;
$_$;