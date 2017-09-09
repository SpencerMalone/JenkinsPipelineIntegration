#!/bin/bash

integrationTests() {
    ./gradlew clean build test --info
}

docCheck() {
    FILES="vars/*.groovy"
    SUCCESS=true
    for f in $FILES
    do
      filename="${f%.*}"
      if [ ! -f "$filename.txt" ]; then
        echo "$f is missing a .txt documentation file!"
        SUCCESS=false
      fi
    done

    if [ "$SUCCESS" == false ]; then
        exit 1
    fi
}

unitTests() {
    echo "TODO: this."
}

setupCopyOfRepo() {
    DIRECTORY=".copy-of-repo"
    oldPWD=$(pwd)
    
    if [ -d "$DIRECTORY" ]; then
        echo "Deleting old copy of repo"
        rm -rf $DIRECTORY
    fi

    mkdir $DIRECTORY
    cd $DIRECTORY

    git init
    cp -R $oldPWD/* .
    git add .
    git commit -m "Initial commit for copy of library repo"

    cd "$oldPWD"
}

# Default to running the integrationTests.
if [ -z "$1" ]; then
    integrationTests
else
    "$@"
fi