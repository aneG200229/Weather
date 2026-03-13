CREATE TABLE if not exists locations(
    id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL,
    user_id INTEGER NOT NULL REFERENCES users(id),
    latitude DECIMAL NOT NULL,
    longitude DECIMAL NOT NULL
);