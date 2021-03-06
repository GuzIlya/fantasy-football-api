CREATE TYPE PLAYER_POSITION AS ENUM ('UNKNOWN', 'GOALKEEPER', 'DEFENDER', 'MIDFIELDER', 'ATTACKER');

ALTER TABLE player
    ADD COLUMN position PLAYER_POSITION DEFAULT CAST('UNKNOWN' AS PLAYER_POSITION) NOT NULL;