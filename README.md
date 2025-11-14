# Public Online Library - Java EE Application

A comprehensive online library management system built with Java EE, JSP, and Servlets.

## Features

### User Features
- User registration and authentication
- Browse books by category
- Search books by title, author, ISBN, or category
- View detailed book information
- Borrow books (14-day borrowing period)
- Return books
- View borrowing history
- Track overdue books

### Admin Features
- Admin dashboard with statistics
- Manage books (Create, Read, Update, Delete)
- Manage users
- View all borrowings
- Track overdue borrowings

## Technology Stack

- **Backend**: Java EE, Servlets, JSP
- **Database**: MySQL
- **Build Tool**: Maven
- **Server**: Apache Tomcat 9.0+ / GlassFish / WildFly
- **Libraries**: 
  - JSTL (JSP Standard Tag Library)
  - MySQL Connector
  - Apache Commons Codec (for password hashing)

## Prerequisites

- JDK 11 or higher
- Apache Tomcat 9.0+ (or any Java EE compatible server)
- MySQL 8.0+
- Maven 3.6+

## Installation & Setup

### 1. Clone or Download the Project

Download the project ZIP file and extract it to your desired location.

### 2. Database Setup

1. Start your MySQL server
2. Run the database schema script:
   \`\`\`bash
   mysql -u root -p < database/schema.sql
   \`\`\`
3. Run the seed data script:
   \`\`\`bash
   mysql -u root -p < database/seed.sql
   \`\`\`

### 3. Configure Database Connection

Edit the database connection settings in:
- `src/main/java/com/library/util/DatabaseConnection.java`
- `src/main/webapp/WEB-INF/web.xml`

Update the following values:
\`\`\`java
private static final String URL = "jdbc:mysql://localhost:3306/online_library";
private static final String USERNAME = "your_username";
private static final String PASSWORD = "your_password";
\`\`\`

### 4. Build the Project

\`\`\`bash
mvn clean package
\`\`\`

This will create a WAR file in the `target` directory.

### 5. Deploy to Tomcat

**Option 1: Manual Deployment**
1. Copy the generated WAR file from `target/online-library.war`
2. Paste it into your Tomcat's `webapps` directory
3. Start Tomcat
4. Access the application at `http://localhost:8080/online-library`

**Option 2: IDE Deployment**
1. Import the project into your IDE (Eclipse, IntelliJ IDEA, NetBeans)
2. Configure Tomcat server in your IDE
3. Deploy and run the project

## Default Credentials

### Admin Account
- **Username**: admin
- **Password**: admin123

### Test User Accounts
- **Username**: john_doe | **Password**: user123
- **Username**: jane_smith | **Password**: user123
- **Username**: bob_wilson | **Password**: user123

## Project Structure

\`\`\`
online-library/
├── src/main/java/com/library/
│   ├── dao/              # Data Access Objects
│   ├── filter/           # Servlet Filters (Auth, Admin)
│   ├── model/            # Entity classes
│   ├── servlet/          # Servlets
│   │   └── admin/        # Admin servlets
│   └── util/             # Utility classes
├── src/main/webapp/
│   ├── admin/            # Admin JSP pages
│   ├── css/              # Stylesheets
│   ├── error/            # Error pages
│   ├── includes/         # Reusable JSP components
│   └── WEB-INF/          # Configuration files
├── database/             # SQL scripts
└── pom.xml              # Maven configuration
\`\`\`

## Features Breakdown

### Authentication & Authorization
- Password hashing using SHA-256
- Session management
- Role-based access control (USER, ADMIN)
- Auth filters for protected routes

### Book Management
- Full CRUD operations
- Category filtering
- Search functionality
- Availability tracking
- Cover image support

### Borrowing System
- 14-day borrowing period
- Automatic overdue detection
- Prevent duplicate borrowings
- Return functionality
- Borrowing history

### Admin Dashboard
- Real-time statistics
- User management
- Book inventory management
- Borrowing oversight

## Database Schema

### Tables
- **users**: User accounts and authentication
- **books**: Book catalog
- **borrowings**: Borrowing records
- **reviews**: Book reviews (optional feature)

## Security Features

- Password hashing (SHA-256)
- SQL injection prevention (PreparedStatements)
- Session-based authentication
- Role-based authorization
- XSS protection (JSTL escaping)

## Customization

### Changing Borrowing Period
Edit `BorrowServlet.java`:
\`\`\`java
borrowing.setDueDate(Date.valueOf(LocalDate.now().plusDays(14))); // Change 14 to desired days
\`\`\`

### Adding New Categories
Categories are dynamically loaded from the database. Simply add books with new categories.

### Styling
Modify `src/main/webapp/css/style.css` to customize the appearance.

## Troubleshooting

### Database Connection Issues
- Verify MySQL is running
- Check database credentials in `DatabaseConnection.java`
- Ensure the database `online_library` exists

### 404 Errors
- Verify the context path matches your deployment
- Check servlet mappings in `web.xml`

### Login Issues
- Verify the database is seeded with default users
- Check password hashing implementation

## Future Enhancements

- Email notifications for due dates
- Book reservations
- Advanced search filters
- Book reviews and ratings
- Export reports (PDF, Excel)
- REST API for mobile apps
- OAuth integration

## License

This project is open-source and available for educational purposes.

## Support

For issues or questions, please refer to the documentation or contact the development team.
