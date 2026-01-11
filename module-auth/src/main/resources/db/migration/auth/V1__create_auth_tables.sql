CREATE SCHEMA IF NOT EXISTS auth;

CREATE TABLE auth.accounts (
    id UUID PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    hashed_password VARCHAR(255) NOT NULL,
    
    login_attempts INTEGER NOT NULL DEFAULT 0,
    is_locked_until TIMESTAMP WITH TIME ZONE,
    
    is_enabled BOOLEAN NOT NULL DEFAULT FALSE,
    activation_token VARCHAR(255) NOT NULL,
    activation_token_expire_at TIMESTAMP WITH TIME ZONE NOT NULL,
    
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
    last_login_at TIMESTAMP WITH TIME ZONE,
    last_successful_login_ip_address VARCHAR(45)
);

CREATE INDEX idx_accounts_email ON auth.accounts(email);