FROM nginx:1.30.4

ENV TZ="Asia/Taipei"
ARG PUBLISH_DIR=xxx
COPY ./${PUBLISH_DIR} /usr/share/nginx/html

CMD ["nginx", "-g", "daemon off;"]
