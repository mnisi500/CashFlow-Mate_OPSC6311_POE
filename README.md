# CashFlow-Mate_OPSC6311_POE

ST10265120-Nompilo Mnisi
ST10328803-Promise Lushaba
ST10260739-Tshiamo Maredi


Functionalities
OTP Login via Email
This security feature sends a One-Time Password (OTP) to the user’s email when they log in. The user must enter the correct OTP to access the app.

How it works:
When the user enters their email and password, the app sends an OTP to their email address. The user must enter the OTP on the next screen. If the code is correct, they are granted access; otherwise, access is denied.

 Why it’s useful:
This adds a layer of security to protect user information and prevent unauthorized access to personal budgeting data.

CashFlowMate Demonstration videolink:
https://youtu.be/v_gDpjUSsNg?si=3Hw20fpm2JzHE1mF

PURPOSE OF APP 


The Expense Tracker is a mobile application designed to help users manage their personal finances effectively. Its primary objectives are:

Budget Management: Allows users to set monthly budgets and track spending against these budgets

Expense Tracking: Enables recording of daily expenses with categorization

Financial Insights: Provides visual feedback on spending patterns and budget utilization

User-Friendly Experience: Simple interface for quick expense logging and financial overview

Key features include:

User authentication (sign-up/login)

Category management

Expense recording with optional receipt photos

Budget setting and monitoring

Spending overview with progress visualization

Design Considerations
UI/UX Design
Minimalist Interface: Clean layout with focused functionality to reduce cognitive load

Consistent Styling: Uniform button styles, color schemes, and typography throughout the app

Responsive Layouts: Adaptive designs that work across various screen sizes

Visual Feedback: Progress bars and percentage indicators for budget tracking

Technical Architecture
Modular Structure: Separate activities for each major function (signup, categories, expenses, budgets)

Database Integration: Firebase database for persistent data storage

Image Handling: Capability to attach and store receipt photos with expenses

Date Management: Built-in date picker for easy expense date selection

Performance Optimization
Coroutine Implementation: Background thread operations for database access

Efficient Queries: Optimized database operations for quick data retrieval

Image Compression: Potential for future implementation to reduce storage needs

GitHub Utilization
Repository Organization
Branch Strategy: Main branch for stable releases, feature branches for development

Commit Convention: Descriptive commit messages following conventional commits

Issue Tracking: GitHub Issues used for bug reports and feature requests

Project Board: Kanban-style project management for tracking progress

Collaboration Features
Pull Requests: Code reviews before merging to main branch

Wiki: Documentation for setup and architecture decisions

Discussions: Forum for team communication and planning

GitHub Actions Implementation
CI/CD Pipeline
Automated Testing: Runs unit tests on every push/pull request

Build Verification: Ensures app builds successfully across configurations

Linting Checks: Code style validation using Android Lint

Personal Feature: OTP Authentication
We've enhanced security by implementing OTP (One-Time Password) authentication during the sign-up process. This ensures that only verified users can access the app and helps prevent fraudulent account creation.


Workflow Examples
Test Suite Runner:

Triggered on push to any branch

Runs all unit tests

Fails build if any tests fail

Release Preparation:

Triggered on tag creation

Builds release APK

Generates release notes

Uploads artifacts

Code Quality Check:

Scheduled nightly run

Static code analysis

Complexity metrics reporting
