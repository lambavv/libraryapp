LibraryApp
========================

- This application simulates a REST API/terminal for a Library
- Two main entities: Customers, Books. Both support standard CRUD operations.
- Books can be checked out/returned using the Checkout service/resource
- H2 in-memory DB is used, with persistance to a local DB file located in "data" folder
- A Postman collection with all 12 available API endpoints is available in api_collection folder
- When app is launched, a terminal is opened in console (CommandLineRunner) where user can generate random entities (books/customers) or get
 existing ones, by providing input