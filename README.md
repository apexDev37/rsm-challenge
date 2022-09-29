# Retail Store Manager

## Introduction
This is a retail store manager CLI (command-line) Java application.
This application was written in response to a challenge posed by my Object-Oriented Programming lecturer to develop a Retail Store manager application. This CLI Java application seeks to demonstrate learned object-oriented principles and practices such as:

- Abstraction
- Encapsulation
- Inheritance
- Polymorphism

**Target application users**:
1. Store Manager: Responsible for managing the entire retail store.
2. Cashier: Responsible for interacting with the POS (Point of sale).

**Application Features**:
1. Models diverse retail store components: Products, Brands, Vendors, Categories, Contacts, and Purchase orders.
2. Provides support and implements a SQL database for persistent storage.
3. Models various types of retail staff: Cashiers, Employees, and Sales attendants.
4. Provides a terminal interface for POS transactions.
5. Enables cashiers to record customer transactions and output receipts.
6. CRUD operations to manage: Inventory, Staff, and an interactive POS CLI interface.

## Technologies
Associated and leveraged in Project development:
- Java 11
- Maven
- SQL
- IntelliJ IDEA (Ultimate)
- MariaDB
- XAMP

## Installing/Getting Started
After cloning the project, generate sources and update folders with Maven in your preferred IDE.
This project contains 4 packages in the project base package: `com.store.manager`
The following 3 each have their own `Main.class` to isolate and run package-related features.

- inventory
- pos
- staff

## Setup
To start running the application, some minimal Database configuration is required.
The class: `DatabaseConnector` provides details for the following String variables to establish a database connection:

- url (your JDBC URL)
- driverName
- userName
- password

> Note: This application uses MariaDB. Modify the `pom.xml` and `DatabaseConnector` class in the `database` package to define your own data source and connection.