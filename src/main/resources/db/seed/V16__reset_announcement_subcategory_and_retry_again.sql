UPDATE announcement SET subcategory_id = NULL;

UPDATE announcement SET subcategory_id = 1  -- 수해/침수 예방
WHERE subcategory_id IS NULL AND (
    title LIKE '%침수%' OR title LIKE '%홍수%' OR title LIKE '%배수%' OR
    title LIKE '%우수관%' OR title LIKE '%빗물%' OR title LIKE '%방수%' OR
    title LIKE '%제방%' OR title LIKE '%하천정비%' OR title LIKE '%유수지%' OR
    title LIKE '%수방%' OR title LIKE '%저류지%' OR title LIKE '%펌프장%'
    );

UPDATE announcement SET subcategory_id = 2  -- 재해 긴급 복구
WHERE subcategory_id IS NULL AND (
    title LIKE '%긴급복구%' OR title LIKE '%재해복구%' OR title LIKE '%피해복구%' OR
    title LIKE '%응급복구%' OR title LIKE '%재난복구%' OR title LIKE '%수해복구%' OR
    title LIKE '%태풍복구%' OR title LIKE '%복구공사%' OR title LIKE '%재해대책%'
    );

UPDATE announcement SET subcategory_id = 3  -- 폭염 및 냉방 지원
WHERE subcategory_id IS NULL AND (
    title LIKE '%폭염%' OR title LIKE '%무더위%' OR title LIKE '%쿨링%' OR
    title LIKE '%그늘막%' OR title LIKE '%열사병%' OR title LIKE '%온열%'
    );

UPDATE announcement SET subcategory_id = 4  -- 가뭄 및 용수 확보
WHERE subcategory_id IS NULL AND (
    title LIKE '%가뭄%' OR title LIKE '%농업용수%' OR title LIKE '%공업용수%' OR
    title LIKE '%생활용수%' OR title LIKE '%저수지%' OR title LIKE '%취수%' OR
    title LIKE '%긴급급수%' OR title LIKE '%제한급수%' OR title LIKE '%물부족%' OR
    title LIKE '%관개%' OR title LIKE '%수리시설%' OR title LIKE '%양수장%'
    );

UPDATE announcement SET subcategory_id = 5  -- 대설 및 제설 작업
WHERE subcategory_id IS NULL AND (
    title LIKE '%제설%' OR title LIKE '%대설%' OR title LIKE '%염화칼슘%' OR
    title LIKE '%눈치우%' OR title LIKE '%적설%' OR title LIKE '%빙판%' OR
    title LIKE '%결빙%' OR title LIKE '%제빙%' OR title LIKE '%모래살포%'
    );

UPDATE announcement SET subcategory_id = 6  -- 혹한 및 동파 방지
WHERE subcategory_id IS NULL AND (
    title LIKE '%동파%' OR title LIKE '%혹한%' OR
    title LIKE '%보온%' OR title LIKE '%동절기%' OR title LIKE '%한파%' OR
    title LIKE '%방한%' OR title LIKE '%보냉%'
    );

UPDATE announcement SET subcategory_id = 7  -- 지반 및 사면 보강
WHERE subcategory_id IS NULL AND (
    title LIKE '%사면%' OR title LIKE '%지반%' OR title LIKE '%옹벽%' OR
    title LIKE '%절개지%' OR title LIKE '%토사%' OR title LIKE '%산사태%' OR
    title LIKE '%급경사%' OR title LIKE '%석축%' OR title LIKE '%토석류%'
    );

UPDATE announcement SET subcategory_id = 8  -- 수목 관리 및 산불 예방
WHERE subcategory_id IS NULL AND (
    title LIKE '%산불%' OR title LIKE '%수목관리%' OR title LIKE '%수목제거%' OR
    title LIKE '%수목전지%' OR title LIKE '%벌목%' OR title LIKE '%방화선%' OR
    title LIKE '%임도%' OR title LIKE '%숲가꾸%' OR title LIKE '%가로수%' OR
    title LIKE '%산림%'
    );

UPDATE announcement SET subcategory_id = 9  -- 기상 재해 연구 및 설계
WHERE subcategory_id IS NULL AND (
    title LIKE '%기상재해%' OR
    title LIKE '%재해영향평가%' OR
    title LIKE '%재해위험지구%' OR
    title LIKE '%풍수해저감%' OR
    title LIKE '%재해저감%' OR
    title LIKE '%자연재해대책%' OR
    title LIKE '%풍수해보험%'
    );

UPDATE announcement SET subcategory_id = 10  -- 재난 경보 및 통신
WHERE subcategory_id IS NULL AND (
    title LIKE '%재난경보%' OR title LIKE '%재난문자%' OR title LIKE '%사이렌%' OR
    title LIKE '%경보시스템%' OR title LIKE '%재난통신%'
    );

UPDATE announcement SET subcategory_id = 11  -- 해양 및 어업 재해
WHERE subcategory_id IS NULL AND (
    title LIKE '%어업%' OR title LIKE '%항만%' OR
    title LIKE '%방파제%' OR title LIKE '%어항%' OR title LIKE '%해일%' OR
    title LIKE '%어장%'
    );

UPDATE announcement SET subcategory_id = 12  -- 미세먼지 및 황사
WHERE subcategory_id IS NULL AND (
    title LIKE '%미세먼지%' OR title LIKE '%황사%' OR title LIKE '%대기오염%' OR
    title LIKE '%대기질%' OR title LIKE '%집진%'
    );

UPDATE announcement SET subcategory_id = 13  -- 감염병 방역 및 보건
WHERE subcategory_id IS NULL AND (
    title LIKE '%방역%' OR title LIKE '%감염병%' OR title LIKE '%소독%' OR
    title LIKE '%검역%' OR title LIKE '%해충%' OR title LIKE '%구제역%' OR
    title LIKE '%조류독감%'
    );

UPDATE announcement SET subcategory_id = 14  -- 농축산 보호 및 방제
WHERE subcategory_id IS NULL AND (
    title LIKE '%농작물%' OR title LIKE '%축산%' OR title LIKE '%방제%' OR
    title LIKE '%병충해%' OR title LIKE '%가축%'
    OR title LIKE '%살충%'
    );

UPDATE announcement SET subcategory_id = 15  -- 수질 악화 및 정수 처리
WHERE subcategory_id IS NULL AND (
    title LIKE '%수질%' OR title LIKE '%정수%' OR title LIKE '%하수%' OR
    title LIKE '%오수%' OR title LIKE '%폐수%' OR title LIKE '%수처리%' OR
    title LIKE '%정화조%' OR title LIKE '%하수관%' OR title LIKE '%오염수%'
    );

UPDATE announcement SET subcategory_id = 16  -- 포트홀 및 싱크홀
WHERE subcategory_id IS NULL AND (
    title LIKE '%포트홀%' OR title LIKE '%싱크홀%' OR title LIKE '%도로함몰%' OR
    title LIKE '%지하공동%' OR title LIKE '%도로파손%' OR title LIKE '%노면%' OR
    title LIKE '%아스팔트%' OR title LIKE '%포장보수%' OR title LIKE '%도로보수%'
    );