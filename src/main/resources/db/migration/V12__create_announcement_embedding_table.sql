CREATE EXTENSION IF NOT EXISTS vector;

CREATE TABLE announcement_embedding (
    id        BIGINT PRIMARY KEY REFERENCES announcement(id),
    embedding vector(768)
);