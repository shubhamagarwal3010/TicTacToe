all: dependencies build test

build:
	gradle clean build

test:
	gradle clean test

clean:
	gradle clean

.PHONY: all build dependencies test clean