# The Demo-App for SSO-auth in telegram chatbots

This demo serves to demonstrate the ability to identify, authenticate, and fulfill authorized requests through a chatbot 
channel.  
Specifically, in this example, a Telegram chatbot is used as a communication channel. However, this principle can be used for absolutely any type of chat.  \
  

Below are brief instructions for running this demo app locally:

## Components 

The project is implemented based on the following components:

- Spring modules:
  - [chat-backend](./chat-backend)
  - [resource-server](./resource-server)
- keycloak
- nginx

## Preparing to launch the application

### Setting up a domain name

In the project, the address `mydomain.com` is used as an arbitrary domain name. And additionally the `auth.mydomain.com` 
 subdomain is used for SSO purposes.  
You will need to add both domain names to the `hosts` file and point to the local ip-address ``127.0.0.1``.  
Rooting to services is performed using nginx.

### Launching the infrastructure

Run the [docker-compose.yaml](docker-compose.yaml) file with the following command `docker compose up -d`.  

Make sure that the required realm has been imported into keycloak. If not, create the realm and the required client manually.

Launch both spring boot modules in a way convenient for you: in your IDE or get the jar files and execute them.

### Configuration parameters

The [chat-backend](./chat-backend) module requires the following configuration parameters:

- `APP_SERVER_URL` - (Optional) the base URL of the service.
- `TG_BOT_TOKEN *` - the token of your telegram bot.
- `TG_BOT_ID *` - the id of the bot provided by telegram (to build the redirect link).
- `TG_BOT_NAME` - the service name of the bot.
- `OAUTH2_CLIENT_SECRET` - (Optional) the client secret if the client is not public.