set -eo pipefail # what this?

./gradlew clean build

docker compose up --build -d