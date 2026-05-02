INSERT INTO calendar_date (date, year, month, day, day_of_week, is_weekend, day_of_year)
SELECT
    d::date,
    EXTRACT(YEAR FROM d)::int,
    EXTRACT(MONTH FROM d)::int,
    EXTRACT(DAY FROM d)::int,
    EXTRACT(DOW FROM d)::int, -- 0(일) ~ 6(토)
    CASE WHEN EXTRACT(DOW FROM d) IN (0, 6) THEN TRUE ELSE FALSE END,
    EXTRACT(DOY FROM d)::int
FROM generate_series('2024-01-01'::timestamp, '2026-12-31'::timestamp, '1 day') AS d;