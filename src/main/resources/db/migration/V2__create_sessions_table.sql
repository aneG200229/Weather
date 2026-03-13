CREATE TABLE if not exists sessions(
    id UUID PRIMARY KEY,
    user_id INTEGER NOT NULL references users(id),
    expires_at TIMESTAMP NOT NULL
);