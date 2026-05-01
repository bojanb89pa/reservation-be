# Security & Authentication

## Overview

The system uses **OAuth2 with JWT** split across two services:

| Service | OAuth2 Role | URL |
|---|---|---|
| `auth-service` | Authorization Server — issues and signs JWTs | `http://localhost:8081` |
| `resource-service` | Resource Server — validates JWTs, enforces access | `http://localhost:8080` |

No runtime communication between services. The resource-service fetches the auth-service's public key once at startup via the JWK endpoint and validates all subsequent tokens locally.

---

## OAuth2 Endpoints

All endpoints are provided by Spring Authorization Server on `auth-service`:

| Endpoint | URL |
|---|---|
| Authorization | `http://localhost:8081/oauth2/authorize` |
| Token | `http://localhost:8081/oauth2/token` |
| Token revocation | `http://localhost:8081/oauth2/revoke` |
| JWK Set (public key) | `http://localhost:8081/oauth2/jwks` |
| OIDC Discovery | `http://localhost:8081/.well-known/openid-configuration` |
| OIDC User Info | `http://localhost:8081/userinfo` |

---

## Registered Client

A single client is registered in-memory for local development:

| Field | Value |
|---|---|
| Client ID | `reservation` |
| Client Secret | `secret` |
| Supported grant types | Authorization Code, Client Credentials, Refresh Token |
| Scopes | `openid`, `profile`, `read`, `write` |
| Redirect URIs | `https://oauth.pstmn.io/v1/callback` (Postman) |
| | `http://localhost:8080/swagger-ui/oauth2-redirect.html` (resource-service Swagger) |
| | `http://localhost:8081/swagger-ui/oauth2-redirect.html` (auth-service Swagger) |
| PKCE required | No |

> **Note:** The client secret is stored as `{noop}secret` (plain text, no encoding). For production, replace with a properly hashed secret and move client configuration to a database-backed `RegisteredClientRepository`.

---

## JWT Token Structure

Every access token issued by auth-service contains the following custom claims (added by `JwtTokenCustomizer`):

| Claim | Key | Example value |
|---|---|---|
| Subject (user public ID) | `sub` | `"a1b2c3d4-..."` |
| Email | `email` | `"user@example.com"` |
| Roles | `roles` | `["ROLE_USER"]` |

The resource-service's `JwtToUserConverter` reads these claims and creates an `AuthenticatedUser` object, which is injected into controllers via `@AuthenticationPrincipal`.

---

## Roles

| Role | Description |
|---|---|
| `ROLE_USER` | Standard authenticated user — can make and view reservations |
| `ROLE_ADMIN` | Administrative user — can manage businesses |

Roles are assigned during registration and embedded in every JWT. Role-based endpoint restrictions are configured in `ResourceServerSecurityConfig`.

---

## Endpoint Access Rules

### auth-service (port 8081)

| Endpoint | Access |
|---|---|
| `POST /api/users` | Public — no token required |
| `GET /api/users` | Requires valid JWT |
| `POST /oauth2/token` | Requires client credentials |
| `/swagger-ui/**`, `/v3/api-docs/**` | Public |

### resource-service (port 8080)

| Endpoint | Access |
|---|---|
| `POST /api/businesses` | Requires valid JWT |
| `GET /api/businesses/{id}` | Requires valid JWT |
| `POST /api/businesses/{id}/reservations` | Requires valid JWT — `userId` extracted from token |
| `GET /api/businesses/{id}/reservations/{id}` | Requires valid JWT |
| `/swagger-ui/**`, `/v3/api-docs/**` | Public |

---

## Getting a Token

### Authorization Code Flow (browser-based / Swagger / Postman)

This is the primary flow for interactive use. It redirects the user to a login form.

**Step 1** — Request authorization:
```
GET http://localhost:8081/oauth2/authorize
  ?response_type=code
  &client_id=reservation
  &redirect_uri=https://oauth.pstmn.io/v1/callback
  &scope=openid profile read write
```

**Step 2** — Log in on the auth-service form. You must first register a user via `POST /api/users` (see below).

**Step 3** — Exchange the authorization code for a token:
```bash
curl -X POST http://localhost:8081/oauth2/token \
  -u reservation:secret \
  -d "grant_type=authorization_code" \
  -d "code=<code_from_redirect>" \
  -d "redirect_uri=https://oauth.pstmn.io/v1/callback"
```

---

### Client Credentials Flow (machine-to-machine / quick testing)

No user login required. Returns a token scoped to the client, not a specific user. The `sub` claim will be the client ID, not a user UUID, so `@AuthenticationPrincipal` will not resolve to an `AuthenticatedUser` on the resource-service — use this flow only for endpoints that do not depend on the authenticated user's identity.

```bash
curl -X POST http://localhost:8081/oauth2/token \
  -u reservation:secret \
  -d "grant_type=client_credentials" \
  -d "scope=read write"
```

---

### Using the Token

Include the access token as a Bearer header in every request to resource-service:

```bash
curl -H "Authorization: Bearer <access_token>" \
  http://localhost:8080/api/businesses
```

---

## Registering a User

Before using the Authorization Code flow, register a user account:

```bash
curl -X POST http://localhost:8081/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "email": "user@example.com",
    "password": "yourpassword",
    "firstName": "Jane",
    "lastName": "Doe",
    "roles": ["ROLE_USER"],
    "enabled": true
  }'
```

Then use the same email and password on the auth-service login form.

---

## Using Swagger UI

Both services expose a Swagger UI with OAuth2 authorization built in.

### resource-service Swagger (`http://localhost:8080/swagger-ui.html`)

1. Open `http://localhost:8080/swagger-ui.html`.
2. Click **Authorize** (top right).
3. Under **oauth2**, select scopes: `openid`, `profile`, `read`, `write`.
4. Click **Authorize** — the browser redirects to the auth-service login form.
5. Log in with a registered user's credentials.
6. After redirect, Swagger stores the token and attaches it to all subsequent requests.

### auth-service Swagger (`http://localhost:8081/swagger-ui.html`)

The auth-service Swagger documents the `/api/users` endpoints. `POST /api/users` is public — no authorization needed. For `GET /api/users`, follow the same Authorize flow.

---

## Using Postman

The Postman OAuth2 callback URI (`https://oauth.pstmn.io/v1/callback`) is pre-registered.

1. Create a new request in Postman.
2. Go to **Authorization** → Type: **OAuth 2.0**.
3. Click **Get New Access Token** and fill in:

| Field | Value |
|---|---|
| Token Name | `reservation-token` |
| Grant Type | `Authorization Code` |
| Callback URL | `https://oauth.pstmn.io/v1/callback` |
| Auth URL | `http://localhost:8081/oauth2/authorize` |
| Access Token URL | `http://localhost:8081/oauth2/token` |
| Client ID | `reservation` |
| Client Secret | `secret` |
| Scope | `openid profile read write` |
| Client Authentication | `Send as Basic Auth header` |

4. Click **Request Token** — a browser window opens for login.
5. Log in and Postman captures the token automatically.
6. Click **Use Token**.

---

## Known Limitations (Development Only)

| Limitation | Details |
|---|---|
| In-memory client store | Registered client is lost on restart. Move to DB-backed `RegisteredClientRepository` for production. |
| Plain-text client secret | `{noop}secret` — no hashing. Use BCrypt-encoded secrets in production. |
| In-memory RSA key | `JwtKeyConfig` generates a new RSA key pair on each startup. All issued tokens are invalidated on restart. Use a persistent key store in production. |
| Role extraction bug | `JwtToUserConverter` attempts to cast JWT role strings to `Role` enum values, which always fails — `AuthenticatedUser.roles` is always empty. Role-based authorization on resource-service endpoints does not work until this is fixed. |
| No token persistence | Spring Authorization Server is using the default in-memory token store. Restart clears all active sessions. |
