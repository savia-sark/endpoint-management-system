# Enterprise Endpoint Management System

A full-stack web-based system for managing organizational departments, sub-departments, agencies, and endpoint devices through a hierarchical structure.

The project provides secure REST APIs using Spring Boot and PostgreSQL, along with a web-based frontend for performing endpoint and organizational management operations.

## Overview

The system is designed around the following organizational hierarchy:

Department → Sub-Department → Agency → Endpoint

It supports centralized endpoint management while restricting users to the data and operations permitted by their assigned role and organizational scope.

## Key Features

- Department, sub-department, agency, and endpoint management
- Agency type management
- User and role management
- JWT-based authentication
- Role-based authorization
- Scope-based access control
- Hierarchical organizational structure
- CRUD REST APIs
- DTO-based request and response handling
- Entity-to-DTO mapping
- Request validation
- Centralized exception handling
- PostgreSQL database integration
- Web-based frontend
- Current-user profile and scope information

## User Roles

The system implements different levels of access:

| Role | Access |
|---|---|
| SUPER_ADMIN | Full system access and administrator management |
| ADMIN | Full organizational and endpoint management |
| DEPT_HEAD | Operations within an assigned department |
| SUBDEPT_HEAD | Operations within an assigned sub-department |
| AGENCY_HEAD | Endpoint operations within an assigned agency |

Access is enforced on the backend based on both the user's role and organizational scope.

## Technology Stack

### Backend
- Java
- Spring Boot
- Spring Data JPA
- Spring Security
- JWT
- REST APIs
- Jakarta Bean Validation
- Maven

### Database
- PostgreSQL

### Frontend
- HTML
- JavaScript
- CSS

## Architecture

The backend follows a layered architecture:

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
PostgreSQL