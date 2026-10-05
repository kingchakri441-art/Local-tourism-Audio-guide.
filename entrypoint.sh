#!/bin/sh
set -eu
if [ -n "${DATABASE_URL:-}" ] && [ -z "${DB_URL:-}" ]; then
  case "$DATABASE_URL" in
    jdbc:*) export DB_URL="$DATABASE_URL" ;;
    postgresql://*) export DB_URL="jdbc:${DATABASE_URL}" ;;
    postgres://*) export DB_URL="jdbc:postgresql://${DATABASE_URL#postgres://}" ;;
    mysql://*) export DB_URL="jdbc:${DATABASE_URL}" ;;
    *) export DB_URL="$DATABASE_URL" ;;
  esac
fi
exec java -jar /app/app.jar
