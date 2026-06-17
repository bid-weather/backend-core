CREATE INDEX idx_prediction_lookup ON prediction_daily (date, category_id, subcategory_id, predicted_at);
CREATE INDEX idx_prediction_category_date ON prediction_daily (category_id, date);
