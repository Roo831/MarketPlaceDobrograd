FROM ubuntu:latest
LABEL authors="user2"

ENTRYPOINT ["top", "-b"]