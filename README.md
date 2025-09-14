# 🌍 Little Neighbors

**Little Neighbors** is an application designed to help families with children connect with other families in their neighborhood, facilitating meetups in parks and local activities.

---

## 📁 Project Structure

/docs/diagrams/ → Design and architecture diagrams
/src/main/java/ → Java source code (Spring Boot)
/src/main/resources/ → Configuration and resources

---

## 📊 Diagrams

- 🌀 [Flow Chart](docs/diagrams/FlowChartLittles.drawio.png)
- 🏗️ [Conceptual Diagram](docs/diagrams/littleconceptualultimo.png)

---

## 🚀 How to Run the Project

1. **Clone the repository**
```bash
git clone https://github.com/Alexandracoder/Little_Neighbors.git
cd Little_Neighbors
Configure environment
Create a .env file (use .env.example as a template).

Start the backend


./mvnw spring-boot:run
Test the UI
Open frontend/index.html in your browser.

📝 Commit Conventions
This project follows the Conventional Commits standard.

✅ Examples:

feat: add user entity

fix: correct relationship between family and child

docs: add initial diagrams

👨‍👩‍👧 User, Family, and Child Flow
The app organizes user data hierarchically, linking Users → Families → Children.
The UserService is internal only, keeping user logic tied to families.

Flow
1️⃣ User Registration → via authentication module
2️⃣ Family Profile Creation → user creates a family profile
3️⃣ Child Profile Creation → add children with age, gender & interests

Advantages
✔️ Cleaner API (no standalone user endpoints)
✔️ Contextual actions (always inside a family)
✔️ Secure & encapsulated logic

Code Example

// Step 1: User registration
User user = authService.registerUser(userRequest);

// Step 2: Family profile creation
Family family = familyService.createFamily(familyRequest, user);

// Step 3: Child profile creation
Child child = childService.createChild(childRequest, family);
🧩 Use of Generics
To keep the code reusable, consistent, and type-safe, the project uses Java Generics.

🔹 Generic Service

public interface GenericService<E, ID, RES, REQ> {
    RES create(REQ request);
    RES update(ID id, REQ request);
    void delete(ID id);
    RES getById(ID id);
    Page<RES> getAll(Pageable pageable);
}
Extended by FamilyService:


public interface FamilyService extends GenericService<
        Family, Long, FamilyResponse, FamilyRequest> {

    FamilyResponse getFamilyByUserId(Long userId);
}
🔹 Generic Mapper

public interface GenericMapper<E, REQ, RES> {
    E toEntity(REQ request);
    RES toResponse(E entity);
    List<RES> toResponseList(List<E> entities);
}
Extended by FamilyMapper:


@Mapper(componentModel = "spring")
public interface FamilyMapper extends GenericMapper<
        Family, FamilyRequest, FamilyResponse> {
}
📌 Visual Representation
mermaid

graph TD
    A[GenericService<E,ID,RES,REQ>] --> B[FamilyService]
    A --> C[ChildService]
    A --> D[NeighborhoodService]

    E[GenericMapper<E,REQ,RES>] --> F[FamilyMapper]
    E --> G[ChildMapper]
    E --> H[NeighborhoodMapper]
✨ Benefits of Generics

♻️ Reusability – common CRUD logic is centralized

🛡️ Type Safety – compile-time checks prevent mismatches

📐 Consistency – same structure across all entities

⚡ Maintainability – less boilerplate when adding new features

🛠️ Git Recovery Process (Fixing develop)
At some point, the develop branch became inconsistent.
We restored it to a perfect state using Git.

Steps:
🔍 1. Find last good commit

git reflog
👀 2. Inspect commit details

git show <commit-id>
🌱 3. Create backup branch

git checkout -b restore-perfect-state <commit-id>
♻️ 4. Reset develop to that commit


git checkout develop
git reset --hard restore-perfect-state
⬆️ 5. Push restored branch


git push origin develop --force
🤝 Contribution
Want to contribute? 🎉
Please create a branch (feature/ or fix/) and open a Pull Request.


---


