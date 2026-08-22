## **auth-service**

Responsibilities:
- user registration;
- user storage;
- password hash;
- login;
- JWT issuing;
- refresh token;

Owns:
- user;
- credentials;
- token lifecycle;
- authentication;

## **api-gateway**

Responsibilities:
- request routing;
- JWT validation;
- authentication enforcement;
- extracting user id from JWT;
- rejecting unauthenticated requests;

Owns:
- external API entry point;
- routing rules;
- authentication enforcement;

## **monitor-service**
Produces:
- endpoint check result;

Responsibilities:
- monitor creation and management;
- endpoint creation and management;
- endpoint scheduling and health checks;
- monitor access control based on roles (OWNER, EDIT, VIEW);
- producing endpoint check results;
- monitor member management;

Owns:
- monitors;
- endpoints;
- monitor membership;
- monitor roles and permissions;
- monitoring configuration;
- check scheduling;

## **incident-service**
Consumes:
- endpoint check results;

Produces:
- incident opened;
- incident resolved;

Responsibilities:
- processing endpoint check results;
- detecting incident start;
- detecting incident recovery;
- incident lifecycle management;
- incident history;

Owns:
- incidents;
- incident state;
- consecutive failure/recovery state;
- incident history;