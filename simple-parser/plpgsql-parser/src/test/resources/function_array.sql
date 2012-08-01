create or replace function test()
returns void language plpgsql
as
$_$
	declare
		my_array_1 text[];
		my_array_2 text[] = array[ 'content1' ] ;
		my_array_3 text[] = array['content2', 'content3'];
	begin
		my_array[12] = 15;
	end;
$_$;


create or replace function test_multidimensional()
returns void language plpgsql
as
$BODY$
	declare
		my_array text[][] = array[['content11'],['content21','content22']];
	begin
	   my_array_1 = array( select row( col_1, col_2, col_3 ) from my_table );
	end;
$BODY$;
