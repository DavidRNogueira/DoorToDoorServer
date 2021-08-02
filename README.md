# DoorToDoorService

## Development


1. `docker-compose up`
2. Get IP address of Keycloak server:
   - `docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' doortodoorserver_keycloak_1`
3. Update `auth-server-url` in `keycloak.json`
4. Build and star the server `yarn build && yarn start`
