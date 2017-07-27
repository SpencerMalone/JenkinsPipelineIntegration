#!/usr/bin/env groovy

def call(){
  sh "git checkout -B $BRANCH_NAME"
}