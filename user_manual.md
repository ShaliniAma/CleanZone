# CleanZone User Manual & Step-by-Step Flow Guide

Welcome to the **CleanZone** User Manual! This document provides a complete walkthrough of all flows and features inside the CleanZone Android application. Use the placeholders to add screenshots for your user documentation.

---

## Table of Contents
1. [Onboarding & Authentication Flow](#1-onboarding--authentication-flow)
2. [Security & Biometric Lock](#2-security--biometric-lock)
3. [App Navigation & Dashboard](#3-app-navigation--dashboard)
4. [Pickup Locations & Schedules](#4-pickup-locations--schedules)
5. [Filing & Viewing Complaints](#5-filing--viewing-complaints)
6. [Learning & Guide (Video Slider)](#6-learning--guide-video-slider)
7. [Contacting Support](#7-contacting-support)
8. [User Profile & Logout](#8-user-profile--logout)

---

## 1. Onboarding & Authentication Flow

### A. Welcome Screen
When the user launches CleanZone for the first time, they are greeted by the Welcome screen.
* **Steps:**
  1. Read the app description.
  2. Tap the green **Get Started** button to proceed to the Login screen.
* **Screenshot Placeholder:**
  `![Welcome Screen](placeholder_welcome_screen.png)`

### B. Login Screen
If the user already has an account, they can log in using their credentials.
* **Steps:**
  1. Enter registered **Email Address** and **Password**.
  2. Tap **Log In** to access the Dashboard.
  3. (Optional) If you do not have an account, tap **Sign Up** to create one.
  4. (Optional) If you forgot your password, tap **Forgot Password?**.
* **Screenshot Placeholder:**
  `![Login Screen](placeholder_login_screen.png)`

### C. Registration (Sign Up) Screen
New users can register by entering their profile details.
* **Steps:**
  1. Fill in **First Name**, **Last Name**, **Email Address**, **Mobile Number**, **Password**, and **Confirm Password**.
  2. Check the box to agree to the **Terms and Conditions**.
  3. Tap **Sign Up** to save your profile (details will be stored securely in `SharedPreferences`) and automatically return to the Login screen.
* **Screenshot Placeholder:**
  `![Registration Screen](placeholder_registration_screen.png)`

### D. Forgot Password Screen
If a user forgets their password, they can trigger a reset simulation.
* **Steps:**
  1. Type your registered **Email Address**.
  2. Tap **Reset Password**.
  3. A notification will appear showing that a reset link has been simulated.
* **Screenshot Placeholder:**
  `![Forgot Password Screen](placeholder_forgot_password_screen.png)`

---

## 2. Security & Biometric Lock

CleanZone includes automatic security locking to protect user data.

### A. Flip-to-Lock Gesture
* **Steps:**
  1. While using the app, physically turn your Android device flat, face-down (e.g. on a table).
  2. When you pick the device back up, you will see the **CleanZone Locked** screen.
  3. Tap **UNLOCK** and use your Fingerprint, Face ID, or Device PIN to resume your session.
* **Screenshot Placeholder:**
  `![Biometric Lock Screen](placeholder_biometric_lock_screen.png)`

### B. Background Return Lock
* **Steps:**
  1. Minimize the app or press the Home button.
  2. Wait for more than **3 seconds**.
  3. Re-open the app from the Recents menu.
  4. The app will immediately trigger the biometric authentication lock screen.

---

## 3. App Navigation & Dashboard

### A. Dashboard Overview
The main screen of the application contains direct shortcuts to the key features of the app.
* **Steps:**
  1. View overall recycling stats (Weekly Goals, Carbon Footprint split).
  2. Tap on grid icons (Complaints, Reports, Feedback) to navigate directly to those sections.
* **Screenshot Placeholder:**
  `![Dashboard Screen](placeholder_dashboard_screen.png)`

### B. Sidebar Drawer Navigation
Tap the hamburger menu button in the top left corner of the header toolbar to reveal the navigation drawer.
* **List of Sections:**
  * Dashboard
  * Schedule (Pickups)
  * Notices (Notifications)
  * Complaints
  * Reports
  * Guide (Activities)
  * Contact Us
  * User Profile
  * Settings
  * Logout
* **Screenshot Placeholder:**
  `![Navigation Drawer](placeholder_nav_drawer.png)`

---

## 4. Pickup Locations & Schedules

View waste collection points around the city.
* **Steps:**
  1. Select **Schedule** or **Pickups** from the navigation bar.
  2. View the **Google Map** on the top half showing color-coded pins for collection types (Organic, Recyclable, General, E-Waste).
  3. Scroll through the **Pickup Schedule** card list below the map.
  4. **Map interaction:** Tap a card in the list to automatically animate the map camera to that marker.
  5. **List interaction:** Tap a marker pin on the map to automatically highlight and center the corresponding schedule details in the list below.
* **Screenshot Placeholder:**
  `![Pickup Locations Map & List](placeholder_pickups_screen.png)`

---

## 5. Filing & Viewing Complaints

### A. Filing a New Complaint
If you notice illegal waste dumping or missed collection:
* **Steps:**
  1. Open **Complaints** from the drawer or dashboard.
  2. Fill in the form: **Title**, **Category** (e.g. Illegal Dumping, Missed Collection), **Description**, and **Location**.
  3. Tap **Submit Complaint**. A success toast will confirm submission.
* **Screenshot Placeholder:**
  `![Filing a Complaint](placeholder_complaint_form.png)`

### B. Viewing Submission History
All complaints are persisted across app restarts.
* **Steps:**
  1. Go to the **User Profile** page.
  2. Scroll down below the profile card to the **My Complaints** section.
  3. You will see a list of all your submitted complaints with titles, categories, dates, and locations.
* **Screenshot Placeholder:**
  `![My Complaints List on Profile](placeholder_complaints_history.png)`

---

## 6. Learning & Guide (Video Slider)

Educate yourself on environmental safety and proper waste separation.
* **Steps:**
  1. Open the **Guide** (Activities) page from the navigation drawer.
  2. The video player container on top will automatically load and stream the first video.
  3. Tap the video area to **Pause** or **Play** the video.
  4. Tap the **Left/Right Arrow buttons** (or swipe horizontally) to slide to other topics.
  5. The previous video will automatically pause when a new slide becomes active.
* **Screenshot Placeholder:**
  `![Video Player Slider Guide](placeholder_video_slider.png)`

---

## 7. Contacting Support

If you need help or want to send feedback:
* **Steps:**
  1. Open the **Contact Us** page.
  2. View hotline contacts and support email addresses.
  3. Use the message form to write suggestions/messages and tap **Submit Message**.
* **Screenshot Placeholder:**
  `![Contact Us Support Screen](placeholder_contact_us.png)`

---

## 8. User Profile & Logout

Manage your account profile and log out securely.
* **Steps:**
  1. Open the **User Profile** page.
  2. View your profile card containing your Name, Email, and Registered Mobile number.
  3. Tap the red **Logout** button.
  4. Confirm logout to finish the session and return to the Welcome onboarding screen.
* **Screenshot Placeholder:**
  `![User Profile & Logout](placeholder_user_profile.png)`
