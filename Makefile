DOCKER_COMPOSE = docker-compose
RUN_APP = $(DOCKER_COMPOSE) run --no-deps app
EXECUTE_APP ?= $(DOCKER_COMPOSE) exec app
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

up: mvn-debug
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
	$(RUN_MVN) test
.PHONY: test