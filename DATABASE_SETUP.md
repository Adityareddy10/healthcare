# Database Setup Instructions

## Problem
The application is trying to connect to MySQL but failing with:
```
Access denied for user 'root'@'localhost' (using password: YES)
```

## Solution

### Step 1: Ensure MySQL is Running
- Check if MySQL Service is running on Windows
- Or start MySQL from command line

### Step 2: Create Database & User

Run these commands in MySQL:

```sql
-- Create the database
CREATE DATABASE IF NOT EXISTS healthcare;

-- Create a root user if not exists (or verify existing)
-- Option A: Using 'password' as password (for development)
CREATE USER IF NOT EXISTS 'root'@'localhost' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON healthcare.* TO 'root'@'localhost';
FLUSH PRIVILEGES;

-- Option B: For production, use a secure password
-- CREATE USER IF NOT EXISTS 'prod_user'@'localhost' IDENTIFIED BY 'your_secure_password';
-- GRANT ALL PRIVILEGES ON healthcare.* TO 'prod_user'@'localhost';
-- FLUSH PRIVILEGES;
```

### Step 3: Set Environment Variables (Recommended)

**For development**, use the defaults in `application.properties`:
- `DB_USERNAME=root`
- `DB_PASSWORD=password`

**For production**, set custom environment variables in PowerShell:
```powershell
$env:DB_USERNAME="prod_user"
$env:DB_PASSWORD="your_secure_password"
$env:DB_URL="jdbc:mysql://your-prod-server:3306/healthcare"
```

### Step 4: Start the Application

```powershell
cd c:\Users\reddy\Downloads\backend\backend
mvn spring-boot:run
```

## Success Indicators
- Application starts without database connection errors
- All tables are created automatically via Hibernate (ddl-auto=update)
- API endpoints are accessible at `http://localhost:8081`

## Troubleshooting

**If still getting "Access denied":**
1. Verify MySQL is running: `mysql -u root -p`
2. Check user exists: `SELECT user FROM mysql.user;`
3. Verify permissions: `SHOW GRANTS FOR 'root'@'localhost';`

**To reset MySQL root password (if needed):**
- Windows: Stop MySQL service, restart with --skip-grant-tables flag
- Or use MySQL command line with admin privileges

## Security Note
- **Never** use 'password' as password in production
- Always use strong, unique passwords
- Use environment variables for sensitive data (already configured in code)
