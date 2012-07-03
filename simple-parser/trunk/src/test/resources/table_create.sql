CREATE TABLE deal_run_type (
    drt_id smallint NOT NULL,
    drt_info text NOT NULL,
    drt_created timestamp with time zone DEFAULT now() NOT NULL,
    drt_last_modified timestamp with time zone DEFAULT now() NOT NULL
);

ALTER TABLE std_data.deal_run_type OWNER TO stardeals;
ALTER TABLE ONLY deal_run_type ADD CONSTRAINT pk_deal_run_type PRIMARY KEY (drt_id);
CREATE INDEX idx_drt_created ON deal_run_type USING btree (drt_created);
CREATE INDEX idx_drt_last_modified ON deal_run_type USING btree (drt_last_modified);
CREATE TRIGGER trg_deal_run_type_change
    BEFORE INSERT OR UPDATE ON deal_run_type
    FOR EACH ROW
    EXECUTE PROCEDURE deal_run_type_change();

REVOKE ALL ON TABLE deal_run_type FROM PUBLIC;
REVOKE ALL ON TABLE deal_run_type FROM stardeals;
GRANT ALL ON TABLE deal_run_type TO stardeals;
GRANT SELECT ON TABLE deal_run_type TO std_reader;
