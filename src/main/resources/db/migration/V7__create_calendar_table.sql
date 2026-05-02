CREATE TABLE calendar_date (
    date            DATE            PRIMARY KEY,
    year            INTEGER         NOT NULL,
    month           INTEGER         NOT NULL,
    day             INTEGER         NOT NULL,
    day_of_week     INTEGER         NOT NULL,
    is_weekend      BOOLEAN         NOT NULL,
    is_holiday      BOOLEAN,
    day_of_year     INTEGER         NOT NULL
);