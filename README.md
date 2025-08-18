# Little Neighbors

Little Neighbors is an application designed to help families with children connect with other families in their neighborhood, facilitating meetups in parks and local activities.

---

## 📁 Project Structure

- `/docs/diagrams/` — Design and architecture diagrams  
- `/frontend/` — HTML and CSS frontend files  
- `/src/main/java/` — Java source code with Spring Boot  
- `/src/main/resources/` — Configuration and resources  

---

## 📊 Diagrams

- [Flow Chart](docs/diagrams/FlowChartLittles.drawio.png)  
- [Conceptual Diagram](docs/diagrams/littleconceptualultimo.png)  

---

## 🚀 How to Run the Project

1. Clone the repository  
```bash
git clone https://github.com/Alexandracoder/Little_Neighbors.git
cd Little_Neighbors
Configure the .env file (use .env.example as a template)

Start the backend with:

./mvnw spring-boot:run
Open frontend/index.html in your browser to test the UI.

📝 Commit Conventions
This project follows the Conventional Commits standard.

Examples:

feat: add user entity

fix: correct relationship between family and child

docs: add initial diagrams

User, Family, and Child Flow

The application organizes user data hierarchically, linking users to families and children. The UserService is internal and does not expose its own controller, keeping user management within meaningful contexts.

Flow:

User Registration – A new user signs up through the web app (handled by the authentication module).

Family Profile Creation – Once registered, the user can create a family profile.

Child Profile Creation – After creating the family profile, the user can add one or more child profiles, specifying name, age, gender, and interests.

Advantages:

Simplifies the API by avoiding unnecessary user-specific endpoints.

Ensures all user actions happen in the context of a family.

Encapsulates user logic, working seamlessly with authentication and security modules.

Example usage:

// Step 1: User registration

User user = authService.registerUser(userRequest);

// Step 2: Family profile creation

Family family = familyService.createFamily(familyRequest, user);

// Step 3: Child profile creation

Child child = childService.createChild(childRequest, family);

🤝 Contribution
If you want to contribute, please create a feature/ or fix/ branch and open a Pull Request.

