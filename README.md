# Spring Security Level Up

## Introduction
This guide walks through progressively enhancing a basic Spring Security configuration. 
Starting with a minimal setup, we will incrementally add features such as custom authentication, authorization, JWT tokens, CSRF protection, and more.

## Step 1: Basic Spring Security Configuration
In this step, we will start with a simple Spring Security setup and create a basic welcome page. 
We will also test user authentication using Spring Security's default configurations.
1. Create a Welcome Page ('src/main/resources/index.html')
2. Check user authentication process in Login Form
3. Use the HTTP Post Method to verify the user authentication processc
---

## Step 2: Connecting Spring Security to the Database
In this step, we will replace the in-memory authentication with database authentication. 
We will store user credentials in the database and use it for login.
1. Set Up Database Connection (Using MySql)
2. Create User Entity
3. Create UserRepository
4. Update Security Configuration for Database Authentication
5. Modify Security Configuration
---

## Step 3: Implementing JWT for REST API Authentication
In this step, we will implement JWT-based authentication for a stateless REST API. 
This replaces session-based authentication and is better suited for RESTful APIs.
1. Add JWT Dependencies
2. Create JwtTokenProvider
3. Create JwtAuthenticationFilter
4. Update Security Configuration
5. Create Login API
6. Create Protected API
---
