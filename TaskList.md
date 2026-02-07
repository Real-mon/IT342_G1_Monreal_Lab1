# Task List

## ✅ Done
- [x] Create Spring Boot backend
- [x] Scaffold React + Vite frontend
- [x] Set PowerShell execution policy to allow npm scripts
- [x] Commit and push `frontend/` to `origin main`
- [x] Install frontend dependencies (`frontend/` -> `npm install`)
- [x] Start and verify frontend dev server (Vite) — http://localhost:5173/
- [x] Add `backend/package.json` with `dev` script to run frontend
- [x] Add repo-level `.gitignore`
- [x] Create and push `TaskList.md`

### Backend authentication (completed)
- [x] Add JWT + H2 dependencies and configuration
- [x] Implement `User` entity and `UserRepository`
- [x] Implement DTOs: `RegisterRequest`, `LoginRequest`, `AuthResponse`
- [x] Implement `JwtTokenProvider`
- [x] Implement `SecurityConfig` (basic stateless setup)
- [x] Implement `AuthService` (register, authenticate, logout)
- [x] Implement `AuthController` with `/api/auth/register`, `/login`, `/logout`
- [x] Commit and push backend auth changes

## 🔧 In Progress
- [ ] Run backend (Gradle `bootRun`) and verify it serves on http://localhost:8080/
- [ ] Run backend and frontend concurrently and verify integration
- [ ] Verify backend endpoints and frontend-backend connectivity

## 📌 To Do
- [ ] Add README section with frontend and backend run instructions
- [ ] Add integration tests for frontend-backend communication
- [ ] (Optional) Split frontend into its own repository

---
*Last updated: 2026-02-04*