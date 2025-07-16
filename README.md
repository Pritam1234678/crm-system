# CRM System

A comprehensive Customer Relationship Management (CRM) system built with Spring Boot, featuring customer management, lead tracking, contract handling, and integrated communication tools.

## 🚀 Features

- **Customer Management**: Complete customer profile management with contact information
- **Lead Tracking**: Track leads from initial contact to conversion
- **Contract Management**: Handle contracts with automated workflows
- **Ticket System**: Customer support ticket management
- **Email Templates**: Customizable email templates for automated communications
- **Google Integration**: OAuth2 login and Google Drive integration
- **File Management**: Upload and manage files for leads and contracts
- **User Roles**: Manager, Employee, and Customer role-based access
- **Dashboard**: Comprehensive dashboard with analytics
- **Calendar Integration**: Schedule and manage meetings

## 🛠️ Technologies Used

- **Backend**: Spring Boot 3.1.0, Spring Security, Spring Data JPA
- **Database**: MySQL 8.0
- **Frontend**: Thymeleaf, Bootstrap, JavaScript
- **Authentication**: Spring Security with OAuth2 (Google)
- **Build Tool**: Maven
- **Java Version**: 17+

## 📋 Prerequisites

- Java 17 or higher
- MySQL 8.0 or higher
- Maven 3.6+
- Git

## ⚙️ Installation & Setup

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/crm-system.git
   cd crm-system
   ```

2. **Database Setup**
   - Create a MySQL database named `crm`
   - The application will automatically create tables on first run using `schema.sql`

3. **Environment Variables**
   Set the following environment variables:
   ```bash
   SQL_NAME=your_mysql_username
   SQL_PASSWORD=your_mysql_password
   ```

4. **Google OAuth2 Setup (Optional)**
   - Create a Google Cloud Console project
   - Enable Google+ API
   - Create OAuth2 credentials
   - Update `application.properties` with your client ID and secret

5. **Run the application**
   ```bash
   ./mvnw spring-boot:run
   ```

6. **Access the application**
   - Open your browser and navigate to `http://localhost:8080`

## 🏗️ Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── site/easy/to/build/crm/
│   │       ├── config/          # Configuration classes
│   │       ├── controller/      # REST controllers
│   │       ├── entity/          # JPA entities
│   │       ├── repository/      # Data repositories
│   │       ├── service/         # Business logic
│   │       └── CrmApplication.java
│   └── resources/
│       ├── static/              # CSS, JS, images
│       ├── templates/           # Thymeleaf templates
│       ├── application.properties
│       └── schema.sql           # Database schema
└── test/                        # Unit tests
```

## 📊 Database Schema

The system includes the following main entities:
- **Users**: System users (employees, managers)
- **Customers**: Customer information and profiles
- **Leads**: Lead tracking and management
- **Contracts**: Contract management
- **Tickets**: Support ticket system
- **Email Templates**: Customizable email templates
- **Files**: File attachments for leads and contracts

## 🔧 Configuration

### Database Configuration
Update `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/crm?createDatabaseIfNotExist=true
spring.datasource.username=@sql.name@
spring.datasource.password=@sql.password@
```

### Security Configuration
The application uses Spring Security with role-based authentication:
- **ROLE_MANAGER**: Full system access
- **ROLE_EMPLOYEE**: Employee-level access
- **ROLE_CUSTOMER**: Customer portal access

## 🚀 Deployment

### Local Development
```bash
./mvnw spring-boot:run
```

### Production Deployment
1. Build the application:
   ```bash
   ./mvnw clean package
   ```

2. Run the JAR file:
   ```bash
   java -jar target/crm.war
   ```

## 📝 API Documentation

The application provides REST APIs for:
- Customer management (`/api/customers`)
- Lead management (`/api/leads`)
- Contract management (`/api/contracts`)
- Ticket management (`/api/tickets`)

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/new-feature`)
3. Commit your changes (`git commit -am 'Add new feature'`)
4. Push to the branch (`git push origin feature/new-feature`)
5. Create a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👥 Authors

- **Your Name** - *Initial work* - [YourGitHub](https://github.com/yourusername)

## 🆘 Support

If you encounter any issues or have questions, please:
1. Check the [Issues](https://github.com/yourusername/crm-system/issues) page
2. Create a new issue if your problem isn't already documented
3. Provide detailed information about your environment and the issue

## 🔮 Future Enhancements

- [ ] Mobile app development
- [ ] Advanced analytics and reporting
- [ ] Integration with more third-party services
- [ ] Real-time notifications
- [ ] Advanced workflow automation
- [ ] Multi-language support

---

**Note**: Remember to set up your database credentials as environment variables and never commit sensitive information to the repository.
