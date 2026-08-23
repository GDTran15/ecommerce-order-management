ALTER TABLE orders
    RENAME COLUMN order_total_amount TO total_amount;

ALTER TABLE orders
    ALTER COLUMN total_amount SET DEFAULT 0;

ALTER TABLE orders
    ALTER COLUMN total_amount SET NOT NULL;