# SwagLabs Selenide UI Automation

A comprehensive UI test automation suite for [SauceDemo](https://www.saucedemo.com/) built with Selenide, demonstrating best practices in automated testing with Page Object Model, CI/CD integration, and containerized execution.

## 🎯 Project Overview

This project provides hands-on experience with Selenide-based UI automation, covering the complete e-commerce user journey from login to checkout. It's designed for QA engineers looking to master modern test automation practices with a focus on maintainability, reliability, and CI/CD integration.

### Application Under Test
**SauceDemo** - A demo e-commerce application featuring:
- User authentication
- Product catalog browsing
- Shopping cart management
- Multi-step checkout process

## 🚀 Quick Start

### Prerequisites
- Java 17 or higher
- Maven 3.9+
- Docker (optional, for containerized execution)

### Run Tests Locally

```bash
# Clone the repository
git clone https://github.com/nksarps/swaglabs-selenide
cd swaglabs-selenide

# Run all tests (headed Chrome)
mvn clean test

# Run tests in headless mode
mvn clean test -Dheadless=true

# Run specific test suite
mvn clean test -Dgroups=smoke
mvn clean test -Dgroups=regression

# Run with Firefox
mvn clean test -Dbrowser=firefox -Dheadless=true

# Generate Allure report
mvn allure:report

# View report (opens in browser)
mvn allure:serve
```

### Run with Docker

```bash
# Build and run tests with report server
docker-compose up --build

# View the Allure report at http://localhost:8080
```

The Docker container will:
1. Execute all tests in headless Chrome
2. Generate the Allure HTML report
3. Serve the report on port 8080
4. Display a clickable link in the console

## 📁 Project Structure

```
swaglabs-selenide/
├── .github/
│   └── workflows/
│       └── ci.yml                    # GitHub Actions CI/CD pipeline
├── .allure/
│   └── allure-2.32.0/                # Bundled Allure CLI
├── src/
│   ├── main/java/com/automation/selenide/
│   │   └── pages/                    # Page Object Model classes
│   │       ├── LoginPage.java
│   │       ├── InventoryPage.java
│   │       ├── CartPage.java
│   │       ├── CheckoutInfoPage.java
│   │       ├── CheckoutOverviewPage.java
│   │       └── CheckoutCompletePage.java
│   └── test/
│       ├── java/com/automation/selenide/
│       │   ├── base/
│       │   │   └── SetUp.java        # Base test configuration
│       │   ├── data/                 # Test data constants
│       │   │   ├── LoginData.java
│       │   │   ├── ProductData.java
│       │   │   └── CheckoutData.java
│       │   └── tests/                # Test classes
│       │       ├── LoginTest.java    # @Tag("smoke")
│       │       ├── InventoryTest.java # @Tag("smoke")
│       │       ├── CartTest.java     # @Tag("regression")
│       │       └── CheckoutTest.java # @Tag("regression")
│       └── resources/
│           └── logging.properties    # Console logging configuration
├── Dockerfile                        # Docker image definition
├── docker-compose.yml                # Docker Compose configuration
└── pom.xml                           # Maven dependencies and plugins
```

## 🏗️ Architecture

### Page Object Model (POM)
Each page is represented by a dedicated class that encapsulates:
- Element locators using Selenide's `$()` and `$$()` syntax
- Page-specific interaction methods
- No WebDriver management (handled by Selenide globally)

Example:
```java
public class LoginPage {
    private final SelenideElement usernameField = $("#user-name");
    private final SelenideElement passwordField = $("#password");
    private final SelenideElement loginButton = $("#login-button");
    
    public void login(String username, String password) {
        usernameField.setValue(username);
        passwordField.setValue(password);
        loginButton.click();
    }
}
```

### Selenide Auto-Waits
No explicit waits needed. Selenide automatically waits for elements before every interaction:
- Default timeout: 10 seconds (configurable)
- Automatic retry on stale elements
- Built-in smart waiting for visibility, enabled state, etc.

### Test Data Management
All test data is centralized in dedicated data classes:
- `LoginData` - Credentials and error messages
- `ProductData` - Product identifiers and inventory metadata
- `CheckoutData` - Customer info and checkout flow constants

No magic strings in test methods.

## 🧪 Test Suites

### Smoke Suite (`@Tag("smoke")`)
Fast, critical path tests executed on every commit:
- **LoginTest** - Authentication scenarios (valid/invalid credentials)
- **InventoryTest** - Product page validation, cart badge behavior, logout

**Execution:** `mvn test -Dgroups=smoke`

### Regression Suite (`@Tag("regression")`)
Comprehensive end-to-end flow tests:
- **CartTest** - Cart operations (add, remove, navigation)
- **CheckoutTest** - Complete checkout flow with form validation

**Execution:** `mvn test -Dgroups=regression`

### Test Coverage
- **Total Test Methods:** 22
- **Parameterized Scenarios:** 7
- **Total Test Executions:** ~24
- **Coverage:** Login, inventory browsing, cart management, complete checkout flow

## 📊 Reporting

### Allure Reports
Comprehensive HTML reports with:
- Test execution timeline
- Pass/fail statistics
- Screenshots on failure (automatic)
- Page source capture on failure
- Step-by-step execution logs
- Historical trends (when run multiple times)

**Generate and view locally:**
```bash
mvn clean test
mvn allure:report
mvn allure:serve  # Opens report in browser
```

**Report location:** `target/site/allure-maven-plugin/index.html`

### Screenshot Capture
Automatic screenshot capture on test failure via:
1. `AllureSelenide` listener (registered in SetUp)
2. `ScreenshotOnFailureExtension` (custom JUnit 5 extension)

No manual screenshot code needed in tests.

### Console Logging
Clean, minimal console output:
- `[PASS]` - Test passed
- `[FAIL]` - Test failed (with error message)
- `[SKIP]` - Test skipped

Selenium/CDP noise suppressed via `logging.properties`.

## 🐳 Docker Support

### Build and Run
```bash
# Using Docker Compose (recommended)
docker-compose up --build

# Using Docker directly
docker build -t swaglabs-tests .
docker run --rm -p 8080:8080 swaglabs-tests
```

### What Happens in the Container
1. Tests execute in headless Chrome
2. Allure report is generated
3. Python HTTP server starts on port 8080
4. Report accessible at http://localhost:8080

### Docker Features
- Pre-cached Maven dependencies for faster rebuilds
- Chrome installed with proper dependencies
- Shared memory configured (--shm-size=2g in CI)
- Optimized .dockerignore for smaller image size

## 🔄 CI/CD Pipeline

### GitHub Actions Workflow
Triggers on:
- Push to `main`, `develop`, or `feature/*` branches
- Pull requests to `main`, `develop`, or `feature/*`
- Manual workflow dispatch

### Pipeline Steps
1. **Build** - Docker image creation
2. **Test** - Execute all tests in headless Chrome
3. **Report** - Generate Allure HTML report
4. **Artifact Upload** - Store reports and test results
5. **GitHub Pages Deploy** - Publish report (unique URL per run)
6. **Notifications** - Slack and email alerts with test summary

### Notification Features
- Test execution summary (total/passed/failed/skipped)
- List of failing tests (if any)
- Direct links to GitHub Actions run
- Direct link to Allure report on GitHub Pages
- Status indicators (✅/❌)

### Required GitHub Secrets
Configure these in your repository settings:

```
SLACK_WEBHOOK_URL       # Slack incoming webhook URL
SMTP_SERVER             # SMTP server address
SMTP_PORT               # SMTP port (e.g., 587)
SMTP_USERNAME           # SMTP authentication username
SMTP_PASSWORD           # SMTP authentication password
NOTIFY_EMAIL_TO         # Recipient email address
NOTIFY_EMAIL_FROM       # Sender email address
```

`GITHUB_TOKEN` is automatically provided by GitHub Actions.

## 🛠️ Technology Stack

| Component | Technology | Version |
|-----------|-----------|---------|
| Language | Java | 17 |
| Build Tool | Maven | 3.9.6 |
| Test Framework | JUnit 5 (Jupiter) | 5.10.2 |
| UI Automation | Selenide | 7.15.0 |
| Reporting | Allure | 2.32.0 |
| Containerization | Docker | - |
| CI/CD | GitHub Actions | - |

### Key Dependencies
- `selenide` - Browser automation with fluent API
- `junit-jupiter` - Test framework and assertions
- `allure-junit5` - Test lifecycle reporting
- `allure-selenide` - Automatic screenshot capture
- `aspectjweaver` - Allure bytecode instrumentation
- `slf4j-simple` - Logging facade

## 🎨 Design Patterns & Best Practices

### 1. Page Object Model
- Encapsulation of page elements and interactions
- No WebDriver in page classes
- Reusable, maintainable page methods

### 2. Test Data Separation
- Constants in dedicated data classes
- No hardcoded values in tests
- Easy to update test data centrally

### 3. Automatic Waits
- No explicit `Thread.sleep()` or `WebDriverWait`
- Selenide handles all synchronization
- Configurable global timeout

### 4. Parameterized Testing
- JUnit 5 `@ParameterizedTest` for validation scenarios
- Reduces code duplication
- Clear test case naming

### 5. Test Independence
- Each test gets a fresh browser instance
- No shared state between tests
- Parallel execution safe

### 6. Fail-Fast Reporting
- Immediate screenshot on failure
- Page source capture for debugging
- Console markers for quick scan

## 📝 Test Execution Examples

### Local Development
```bash
# Quick smoke test
mvn test -Dgroups=smoke -Dheadless=true

# Full regression with visible browser
mvn test -Dgroups=regression

# Single test class
mvn test -Dtest=LoginTest

# Single test method
mvn test -Dtest=LoginTest#standardUserCanLogin

# Firefox headless
mvn test -Dbrowser=firefox -Dheadless=true
```

### CI/CD Execution
```bash
# Simulate CI environment locally
docker run --rm \
  --shm-size=2g \
  -v $(pwd)/target:/app/target \
  swaglabs-tests \
  bash -c "mvn -B test -Dheadless=true"
```

## 🔍 Debugging Failed Tests

### 1. Check Console Output
Look for `[FAIL]` markers with error messages:
```
[FAIL] Invalid login shows error message [1] — Epic sadface: Sorry, this user has been locked out.
```

### 2. View Allure Report
```bash
mvn allure:serve
```
Navigate to failed test → View screenshot and page source

### 3. Run Single Test in Headed Mode
```bash
mvn test -Dtest=LoginTest#standardUserCanLogin
```
Watch the browser to see what's happening

### 4. Check Surefire Reports
Text reports: `target/surefire-reports/*.txt`
XML reports: `target/surefire-reports/TEST-*.xml`

## 🌐 Browser Configuration

### Supported Browsers
- **Chrome** (default) - Stable version
- **Firefox** - Latest version

### Headless Mode
Enabled via system property:
```bash
-Dheadless=true
```

### Docker Chrome Configuration
Special flags for container stability:
- `--no-sandbox` - Required for Chrome in Docker
- `--disable-dev-shm-usage` - Prevents shared memory issues

## 📦 Maven Commands Reference

```bash
# Clean and compile
mvn clean compile

# Run tests
mvn test

# Run specific suite
mvn test -Dgroups=smoke
mvn test -Dgroups=regression

# Skip tests
mvn clean install -DskipTests

# Generate Allure report
mvn allure:report

# Serve Allure report (opens browser)
mvn allure:serve

# Clean Allure results
mvn allure:clean

# Dependency tree
mvn dependency:tree

# Update dependencies
mvn versions:display-dependency-updates
```

## 🔧 Configuration

### Selenide Configuration (SetUp.java)
```java
Configuration.browser = "chrome";           // or "firefox"
Configuration.headless = false;             // or true
Configuration.baseUrl = "https://www.saucedemo.com";
Configuration.timeout = 10000;              // 10 seconds
```

### Browser Selection
Override via system property:
```bash
mvn test -Dbrowser=firefox
```

### Headless Mode
Override via system property:
```bash
mvn test -Dheadless=true
```

## 📈 CI/CD Integration

### GitHub Actions
The pipeline automatically:
1. Builds Docker image on every commit
2. Runs all tests in headless Chrome
3. Generates Allure report
4. Deploys report to GitHub Pages
5. Sends notifications to Slack and email
6. Uploads artifacts for download

### Viewing CI Reports
After each run, find the report URL in:
- GitHub Actions job summary
- Slack notification message
- Email notification
- Format: `https://<username>.github.io/<repo>/<run-number>/`

### Manual Trigger
Navigate to Actions tab → Select "SwagLabs CI" → Click "Run workflow"

## 🧩 Test Scenarios

### Login Tests (Smoke)
- ✅ Successful login with valid credentials
- ✅ Locked-out user error handling
- ✅ Invalid credentials error handling
- ✅ Empty username validation
- ✅ Empty password validation

### Inventory Tests (Smoke)
- ✅ Page title verification
- ✅ Product count validation (6 products)
- ✅ Cart badge appears when product added
- ✅ Cart badge count updates correctly
- ✅ Logout functionality
- ✅ Remove button state changes
- ✅ Initial cart badge state (hidden)

### Cart Tests (Regression)
- ✅ Cart page title verification
- ✅ Added product appears in cart
- ✅ Remove product from cart
- ✅ Proceed to checkout navigation
- ✅ Continue shopping navigation

### Checkout Tests (Regression)
- ✅ Checkout info page title
- ✅ Form validation (missing first name)
- ✅ Form validation (missing last name)
- ✅ Form validation (missing postal code)
- ✅ Valid info navigates to overview
- ✅ Overview displays ordered items
- ✅ Complete order flow with confirmation
- ✅ Cancel checkout navigation
- ✅ Back to products navigation

## 🎓 Key Features

### Selenide Advantages
- **Automatic Waits** - No explicit wait code needed
- **Fluent API** - Readable, chainable syntax
- **Auto-Screenshots** - Captures failures automatically
- **Smart Retries** - Handles stale elements gracefully
- **Concise Syntax** - Less boilerplate than raw Selenium

### Test Organization
- **Tagged Suites** - Smoke vs Regression separation
- **Parameterized Tests** - Data-driven validation scenarios
- **BeforeEach Setup** - Consistent test preconditions
- **Test Independence** - Fresh browser per test

### Reporting Features
- **Allure Integration** - Rich HTML reports with history
- **Screenshot Attachment** - Visual debugging on failure
- **Page Source Capture** - HTML snapshot for analysis
- **Console Markers** - Quick pass/fail scanning
- **CI Notifications** - Slack and email alerts

### Docker Benefits
- **Zero Setup** - No local browser/driver installation
- **Consistent Environment** - Same execution everywhere
- **Isolated Execution** - No conflicts with host system
- **Easy Sharing** - Anyone can run tests with one command

## 🔒 Security Notes

### Credentials
Test credentials are publicly available (SauceDemo demo accounts):
- Username: `standard_user`
- Password: `secret_sauce`

For real applications, use environment variables or secret management:
```java
String username = System.getenv("TEST_USERNAME");
String password = System.getenv("TEST_PASSWORD");
```

### CI/CD Secrets
Never commit secrets to the repository. Use GitHub Secrets for:
- Slack webhook URLs
- SMTP credentials
- API tokens

## 🐛 Troubleshooting

### Tests Fail Locally But Pass in CI
- Check Chrome/Firefox version compatibility
- Verify network connectivity to saucedemo.com
- Try headless mode: `mvn test -Dheadless=true`

### Chrome Crashes in Docker
- Ensure `--shm-size=2g` is set (already configured in ci.yml)
- Check available memory on host system

### Allure Report Not Generating
- Verify AspectJ weaver is configured in pom.xml
- Check `target/allure-results/` directory exists
- Ensure tests actually ran: `ls target/surefire-reports/`

### Element Not Found Errors
- Verify SauceDemo site is accessible
- Check if site HTML structure changed
- Increase timeout: `Configuration.timeout = 15000;`

### Parameterized Test Not Running
- Verify `@MethodSource` method is static
- Check method returns `Stream<Arguments>`
- Ensure method name matches annotation value

## 📚 Learning Resources

### Selenide Documentation
- Official Docs: https://selenide.org/documentation.html
- Quick Start: https://selenide.org/quick-start.html
- FAQ: https://selenide.org/faq.html

### Allure Documentation
- Allure Report: https://docs.qameta.io/allure/
- JUnit 5 Integration: https://docs.qameta.io/allure/#_junit_5

### JUnit 5 Documentation
- User Guide: https://junit.org/junit5/docs/current/user-guide/
- Parameterized Tests: https://junit.org/junit5/docs/current/user-guide/#writing-tests-parameterized-tests

## 🤝 Contributing

### Adding New Tests
1. Create test class extending `SetUp`
2. Add `@Tag("smoke")` or `@Tag("regression")`
3. Use page objects from protected fields
4. Add test data to appropriate data class
5. Run locally to verify
6. Commit and push (CI runs automatically)

### Adding New Pages
1. Create page class in `src/main/java/.../pages/`
2. Declare elements using `$()` and `$$()`
3. Add interaction methods
4. Instantiate in `SetUp.setUp()`
5. Add protected field in `SetUp`

### Code Style
- Use descriptive method names
- Add JavaDoc comments for public methods
- Follow existing naming conventions
- Keep methods focused and single-purpose

## 📄 License

This project is for educational purposes. SauceDemo is a publicly available demo application provided by Sauce Labs.

## 🙋 Support

For issues or questions:
1. Check the Troubleshooting section above
2. Review Selenide documentation
3. Check existing GitHub issues
4. Create a new issue with:
   - Test failure description
   - Console output
   - Screenshot (if available)
   - Steps to reproduce

---

**Built with ❤️ using Selenide** - Making UI automation simple and reliable.
