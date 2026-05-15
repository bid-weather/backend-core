DELETE FROM category_subcategory a
WHERE EXISTS (
    SELECT 1
    FROM category_subcategory b
    WHERE a.id > b.id
      AND a.category_id = b.category_id
      AND a.subcategory_id = b.subcategory_id
);