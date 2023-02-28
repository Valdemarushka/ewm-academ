CREATE TABLE IF NOT EXISTS users
(
    id     BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    "name" VARCHAR(255) NOT NULL,
    email  VARCHAR(512) NOT NULL UNIQUE
);
CREATE TABLE IF NOT EXISTS categories
(
    id     BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    "name" VARCHAR(255) NOT NULL UNIQUE
);
CREATE TABLE IF NOT EXISTS events
(
    id                 BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    annotation         VARCHAR                     NOT NULL,
    description        VARCHAR,
    title              VARCHAR(1024)               NOT NULL,
    state              VARCHAR(50),
    created_on         TIMESTAMP WITHOUT TIME ZONE,
    event_date         TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    published_on       TIMESTAMP WITHOUT TIME ZONE,
    is_paid            BOOL                        NOT NULL,
    request_moderation BOOL,
    participant_limit  BIGINT,
    location_lat       FLOAT                       NOT NULL,
    location_lon       FLOAT                       NOT NULL,
    views              BIGINT,
    initiator_id       BIGINT                      NOT NULL,
    category_id        BIGINT                      NOT NULL,
    CONSTRAINT fk_events_to_users FOREIGN KEY (initiator_id) REFERENCES users (id),
    CONSTRAINT fk_events_to_categories FOREIGN KEY (category_id) REFERENCES categories (id)
);
CREATE TABLE IF NOT EXISTS requests
(
    id           BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    event_id     BIGINT NOT NULL,
    requester_id BIGINT,
    created      TIMESTAMP WITHOUT TIME ZONE,
    status       VARCHAR(50),
    CONSTRAINT fk_requests_to_events FOREIGN KEY (event_id) REFERENCES events (id),
    CONSTRAINT fk_requests_to_users FOREIGN KEY (requester_id) REFERENCES users (id)
);
CREATE TABLE IF NOT EXISTS compilations
(
    id        BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    is_pinned BOOL         NOT NULL,
    title     VARCHAR(255) NOT NULL
);
CREATE TABLE IF NOT EXISTS events_compilations
(
    id             BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    event_id       BIGINT NOT NULL REFERENCES events,
    compilation_id BIGINT NOT NULL REFERENCES compilations,
    UNIQUE (event_id, compilation_id)
);