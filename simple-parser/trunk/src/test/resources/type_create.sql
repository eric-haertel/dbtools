SET search_path = my_schema, pg_catalog;
CREATE TYPE my_type AS (
	result_status_id smallint,
	result_status_msg text,
	max_available_count integer
);
ALTER TYPE my_schema.my_type OWNER TO my_user;
