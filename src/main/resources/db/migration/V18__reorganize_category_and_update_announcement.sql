UPDATE announcement SET category_id = 2 WHERE category_id IN (2, 3);
UPDATE announcement SET category_id = 3 WHERE category_id = 4;
DELETE FROM category WHERE id = 4;

UPDATE category SET category_name = '용역' WHERE id = 2;
UPDATE category SET category_name = '물품' WHERE id = 3;
