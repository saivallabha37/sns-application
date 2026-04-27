# SNS-application
A 3-layer Social Networking System built using Java Swing and Oracle DB

# Setup and Run Guide

## Prerequisites
1. Oracle Database installed and running
2. ojdbc6.jar driver file
3. Java JDK installed (Java 8+)

## Step 1: Database Setup

1. Open SQL Plus or your Oracle SQL client
2. Run the following command to execute the database setup script:
   ```
   @setup/database_setup.sql
   ```
3. This will add necessary columns and test data to your SNS database

## Step 2: Configure Database Connection

Edit `db.properties` file with your Oracle credentials:
```properties
db.url=jdbc:oracle:thin:@localhost:1521:xe
db.user=your_oracle_username
db.password=your_oracle_password
```

## Step 3: Download ojdbc6.jar

1. Download ojdbc6.jar from [Maven Central Repository](https://mvnrepository.com/artifact/com.oracle/ojdbc6/11.2.0.4)
2. Place it in the `sns-app/lib/` directory

## Step 4: Compile the Application

Navigate to `sns-app` folder and run:
```bash
compile.bat
```

This will compile all Java files and place .class files in the `bin/` directory.

## Step 5: Run the Application

Execute:
```bash
run.bat
```

The SNS application will launch with the login screen.

## Test Credentials

- Username: `testuser`
- Password: `password123`

## Features

✓ User Registration and Login  
✓ Create Posts  
✓ View Feed (All Posts)  
✓ Like Posts  
✓ Comment on Posts  
✓ View Comments  
✓ Logout  

---

**Enjoy your SNS Application!**
