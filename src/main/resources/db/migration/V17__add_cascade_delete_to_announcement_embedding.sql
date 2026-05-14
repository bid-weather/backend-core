ALTER TABLE announcement_embedding
DROP CONSTRAINT announcement_embedding_pkey;

ALTER TABLE announcement_embedding
    ADD CONSTRAINT announcement_embedding_pkey
        PRIMARY KEY (id);

ALTER TABLE announcement_embedding
    ADD FOREIGN KEY (id) REFERENCES announcement(id) ON DELETE CASCADE;