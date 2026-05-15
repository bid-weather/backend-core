CREATE INDEX idx_announcement_date ON announcement (date);
CREATE INDEX idx_announcement_category_date ON announcement (category_id, date);
CREATE INDEX idx_announcement_subcategory_date ON announcement (subcategory_id, date);