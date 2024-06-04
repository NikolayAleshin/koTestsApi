FROM gradle:7.6-jdk11 AS build

WORKDIR /home/gradle/project

COPY . .

RUN gradle clean test allureReport --no-daemon

FROM nginx:alpine

RUN mkdir -p /usr/share/nginx/html/allure-report

COPY --from=build /home/gradle/project/build/reports/allure-report/allureReport /usr/share/nginx/html/allure-report

RUN echo "<html><head><meta http-equiv='refresh' content='0; url=allure-report/' /></head><body></body></html>" > /usr/share/nginx/html/index.html

RUN sed -i 's|^http {|http {\n    autoindex on;|' /etc/nginx/nginx.conf

EXPOSE 80
