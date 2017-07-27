#!/usr/bin/env groovy

@NonCPS
def call(){
  try {
    return env.BRANCH_NAME.contains("PR-")
  } catch ( Exception e ) {
    return false 
  }
}