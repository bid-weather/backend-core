DO $$
DECLARE max_id BIGINT;
BEGIN
    SELECT COALESCE(MAX(id), 0) + 1 INTO max_id FROM announcement;
    EXECUTE 'ALTER TABLE announcement ALTER COLUMN id RESTART WITH ' || max_id;

    SELECT COALESCE(MAX(id), 0) + 1 INTO max_id FROM weather_daily;
    EXECUTE 'ALTER TABLE weather_daily ALTER COLUMN id RESTART WITH ' || max_id;

    SELECT COALESCE(MAX(id), 0) + 1 INTO max_id FROM prediction_daily;
    EXECUTE 'ALTER TABLE prediction_daily ALTER COLUMN id RESTART WITH ' || max_id;
END $$;
