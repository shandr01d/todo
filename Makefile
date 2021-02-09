include .env
DOCKER_COMPOSE = docker-compose
RUN_APP = $(DOCKER_COMPOSE) run --no-deps app
EXECUTE_APP ?= $(DOCKER_COMPOSE) exec app
EXECUTE_DB ?= $(DOCKER_COMPOSE) exec database
EXECUTE_PSQL ?= $(EXECUTE_DB) psql -U $(DATABASE_USER)
RUN_MVN = $(EXECUTE_APP) ./mvnw

all: setup mvn-run
.PHONY: all

#
# Setup
#
setup: up mvn-install
.PHONY: setup

start: up mvn-run
.PHONY: start

ssh:
	$(EXECUTE_APP) sh
.PHONY: ssh

#
# Docker Compose
#
ps:
	$(DOCKER_COMPOSE) ps
.PHONY: ps

restart:
	$(DOCKER_COMPOSE) restart
.PHONY: restart

logs:
	$(DOCKER_COMPOSE) logs -f
.PHONY: logs

up:
	$(DOCKER_COMPOSE) up --remove-orphans -d
.PHONY: up

down:
	$(DOCKER_COMPOSE) down --remove-orphans
.PHONY: down

rebuild-app-container:
	$(DOCKER_COMPOSE) up --remove-orphans -d --no-deps --build app
.PHONY: down

#
# Maven
#
mvn-run:
	$(RUN_MVN) spring-boot:run
.PHONY: mvn-run

mvn-install:
	$(RUN_MVN) clean install
.PHONY: mvn-install

mvn-debug:
	$(RUN_MVN) spring-boot:run -Dspring-boot.run.jvmArguments="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5009"
.PHONY: mvn-debug

#
# Tests
#
test:
	$(RUN_MVN) test -Dspring.profiles.active=test
.PHONY: test

test-x:
	$(RUN_MVN) test -Dspring.profiles.active=test -X
.PHONY: test-x

#test-init-db:
#	$(EXECUTE_PSQL) -tc "SELECT 1 FROM pg_database WHERE datname = 'test'" | grep -q 1 || \
#	$(EXECUTE_PSQL) -c "CREATE DATABASE test"
#.PHONY: test-init-db