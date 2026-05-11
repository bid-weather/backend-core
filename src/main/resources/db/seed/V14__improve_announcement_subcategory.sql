UPDATE announcement SET subcategory_id = NULL
WHERE subcategory_id = 6
  AND title LIKE '%동해%'
  AND title NOT LIKE '%동파%' AND title NOT LIKE '%혹한%' AND title NOT LIKE '%동결%'
  AND title NOT LIKE '%보온%' AND title NOT LIKE '%동절기%' AND title NOT LIKE '%한파%'
  AND title NOT LIKE '%방한%' AND title NOT LIKE '%보냉%';

UPDATE announcement SET subcategory_id = NULL
WHERE subcategory_id = 7
  AND (title LIKE '%낙석%' OR title LIKE '%보강공사%')
  AND title NOT LIKE '%사면%' AND title NOT LIKE '%지반%' AND title NOT LIKE '%옹벽%'
  AND title NOT LIKE '%절개지%' AND title NOT LIKE '%토사%' AND title NOT LIKE '%산사태%'
  AND title NOT LIKE '%급경사%' AND title NOT LIKE '%석축%' AND title NOT LIKE '%토석류%';

UPDATE announcement SET subcategory_id = NULL
WHERE subcategory_id = 8
  AND title LIKE '%조림%'
  AND title NOT LIKE '%산불%' AND title NOT LIKE '%수목%' AND title NOT LIKE '%벌목%'
  AND title NOT LIKE '%방화선%' AND title NOT LIKE '%임도%' AND title NOT LIKE '%숲가꾸%'
  AND title NOT LIKE '%가로수%' AND title NOT LIKE '%산림%' AND title NOT LIKE '%나무%';

UPDATE announcement SET subcategory_id = NULL
WHERE subcategory_id = 9
  AND (
    title LIKE '%설계%' OR title LIKE '%용역%' OR title LIKE '%연구%' OR
    title LIKE '%조사%' OR title LIKE '%분석%' OR title LIKE '%계획수립%' OR
    title LIKE '%타당성%' OR title LIKE '%모니터링%'
    )
  AND title NOT LIKE '%기상%' AND title NOT LIKE '%재해영향%';

UPDATE announcement SET subcategory_id = 9
WHERE subcategory_id IS NULL AND (
    title LIKE '%기상재해%' OR
    title LIKE '%재해영향평가%' OR
    title LIKE '%재해위험%' OR
    title LIKE '%방재계획%' OR
    title LIKE '%재해저감%' OR
    title LIKE '%위험성평가%' OR
    title LIKE '%안전진단%' OR
    title LIKE '%재난관리%' OR
    title LIKE '%방재시설%'
    );