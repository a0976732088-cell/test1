FROM caddy:latest

ENV TZ="Asia/Taipei"
COPY ./Caddyfile /etc/caddy/Caddyfile
