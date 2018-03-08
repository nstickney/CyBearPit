#!/bin/bash

IMAGE_NAME='judgebean-arqtest-wildfly'
CONTAINER_NAME="$IMAGE_NAME"

# Create the image if "build" is specified, or it doesn't exist
if [ "$1" == "build" ] || \
	[ "$(docker images -q $IMAGE_NAME 2> /dev/null)" == "" ]; then

	printf '%s\n' "Building $IMAGE_NAME..."

	# Build the image
	docker build --no-cache --force-rm -t "$IMAGE_NAME" .    
fi

# Create the container if "build" is specified, or it doesn't exist
if [ "$1" == "new" ] || [ "$1" == "build" ] || \
	! docker ps -a | grep -q "$CONTAINER_NAME"; then

	printf '%s\n' "Running $CONTAINER_NAME from $IMAGE_NAME..."

	# Delete the container, if it's already made
	docker stop "$CONTAINER_NAME" > /dev/null 2>&1
	docker rm "$CONTAINER_NAME" > /dev/null 2>&1

	# Run the container, forwarding ports 80 (to 8080) and 81 (to 9990)
	docker run --name="$CONTAINER_NAME" \
		-p 8080:8080 \
		-p 8787:8787 \
		-p 9990:9990 \
		-d "$IMAGE_NAME"

else
	# Start the existing container
	docker start "$CONTAINER_NAME"
fi
