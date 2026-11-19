# AGENT Instructions

# Security and Privacy Guidelines

## 1. Strict File Access Control
- **NEVER** read, inspect, open, modify, or transmit the contents of sensitive configuration files, including but not limited to:
  - Environment files: `.env`, `.env.*`, `*.env`
  - Private keys and certificates: `*.pem`, `*.key`, `*.p12`, `*.crt`, `id_rsa*`
  - Secret stores and credential files: `*credential*`, `*secret*`, `*token*`, `service-account*.json`
- If configuration information is required, **ONLY** inspect template files such as `.env.example`, `application.properties.example`, or similar mock files.

## 2. Command Execution Restrictions
- **DO NOT** execute shell or terminal commands that print, dump, or expose environment variables or secret files, including:
  - Commands displaying file contents: `cat .env`, `less .env`, `tail .env`, `head .env`, etc.
  - Environment inspection commands: `env`, `printenv`, `export -p`, `set`.
  - Echoing secrets: `echo $PASSWORD`, `echo $DATABASE_URL`, or similar.
- Do not run scripts or inline commands designed to extract secrets from memory or disk.

## 3. Safe Handling of Sensitive Data
- When writing code, test suites, or documentation:
  - Never hardcode real passwords, tokens, API keys, or credentials.
  - Always use placeholder values (e.g., `your_api_key_here`, `postgres_dev_password`, `dummy_secret`).
  - Instruct the user to populate actual values locally in their untracked `.env` file.
