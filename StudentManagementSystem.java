<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Student Management & Grading System</title>
    <!-- Load Tailwind CSS -->
    <script src="https://cdn.tailwindcss.com"></script>
    <style>
        /* Custom scrollbar for main content area */
        .custom-scrollbar::-webkit-scrollbar {
            width: 8px; /* Slightly wider, more visible scrollbar */
        }
        .custom-scrollbar::-webkit-scrollbar-thumb {
            background-color: #6ee7b7; /* Emerald-300 for a softer look */
            border-radius: 4px;
        }
        .custom-scrollbar::-webkit-scrollbar-track {
            background-color: #f1f5f9; /* Slate-100 */
        }
        body {
            font-family: 'Inter', sans-serif;
            background-color: #e5e7eb; /* Lighter background for better contrast */
        }
    </style>
</head>
<body class="min-h-screen flex items-center justify-center p-4">

    <!-- Main Container - Now uses a softer shadow and increased padding -->
    <div id="app-container" class="bg-white shadow-xl rounded-2xl w-full max-w-6xl transition-all duration-300">

        <!-- Authentication Screen -->
        <div id="auth-screen" class="p-8 md:p-16">
            <h1 id="auth-title" class="text-4xl font-extrabold text-emerald-800 mb-8 text-center">Student System Access</h1>
            <div class="space-y-5 max-w-sm mx-auto">
                
                <!-- Sign-up specific fields -->
                <div id="signup-fields" class="space-y-4 hidden">
                    <input type="text" id="auth-firstname" placeholder="First Name" class="w-full p-3 border border-gray-300 rounded-xl focus:ring-emerald-500 focus:border-emerald-500 shadow-sm transition duration-150">
                    <input type="text" id="auth-lastname" placeholder="Last Name" class="w-full p-3 border border-gray-300 rounded-xl focus:ring-emerald-500 focus:border-emerald-500 shadow-sm transition duration-150">
                </div>

                <input type="text" id="auth-username" placeholder="Username" class="w-full p-3 border border-gray-300 rounded-xl focus:ring-emerald-500 focus:border-emerald-500 shadow-sm transition duration-150">
                
                <input type="password" id="auth-password" placeholder="Password" class="w-full p-3 border border-gray-300 rounded-xl focus:ring-emerald-500 focus:border-emerald-500 shadow-sm transition duration-150">
                
                <!-- Re-enter password field -->
                <input type="password" id="auth-confirm-password" placeholder="Re-enter Password" class="w-full p-3 border border-gray-300 rounded-xl focus:ring-emerald-500 focus:border-emerald-500 shadow-sm hidden transition duration-150">
                
                <button onclick="handleSignIn()" id="sign-in-btn" class="w-full bg-emerald-600 text-white p-3 rounded-xl font-bold text-lg hover:bg-emerald-700 transition duration-200 shadow-md hover:shadow-lg transform hover:scale-[1.01]">Sign In</button>
                <button onclick="handleSignUp()" id="create-account-btn" class="w-full bg-gray-200 text-emerald-700 p-3 rounded-xl font-semibold hover:bg-gray-300 transition duration-200 shadow-sm">Create Account</button>
            </div>
            <p id="auth-message" class="mt-6 text-center text-sm font-medium text-red-500"></p>
        </div>

        <!-- Management System Screen -->
        <div id="manager-screen" class="hidden p-6 md:p-10">
            <header class="flex flex-col sm:flex-row justify-between items-start sm:items-center pb-6 border-b border-gray-200">
                <h1 class="text-3xl font-bold text-gray-800 mb-2 sm:mb-0">Student Grading Dashboard</h1>
                <button onclick="signOut()" class="bg-red-500 text-white py-2 px-6 rounded-xl text-sm font-medium hover:bg-red-600 transition duration-200 shadow-md">Sign Out</button>
            </header>

            <div class="grid grid-cols-1 lg:grid-cols-3 gap-8 mt-8">
                <!-- Student Form (Column 1) - Card-style container -->
                <div class="lg:col-span-1 bg-white p-6 rounded-2xl shadow-lg border border-emerald-100 h-fit">
                    <h2 id="form-title" class="text-2xl font-semibold text-emerald-700 mb-6 border-b pb-3">Add New Student</h2>
                    <form id="student-form" class="space-y-5">
                        <input type="hidden" id="student-id-input">
                        
                        <div>
                            <label for="name" class="block text-sm font-medium text-gray-700">Student Name</label>
                            <input type="text" id="name" required class="w-full p-3 border border-gray-300 rounded-xl focus:ring-emerald-500 focus:border-emerald-500 shadow-sm">
                        </div>

                        <div class="flex space-x-4">
                            <div class="flex-1">
                                <label for="age" class="block text-sm font-medium text-gray-700">Age</label>
                                <input type="number" id="age" required min="5" max="99" class="w-full p-3 border border-gray-300 rounded-xl focus:ring-emerald-500 focus:border-emerald-500 shadow-sm">
                            </div>
                            <div class="flex-1">
                                <label for="studentID" class="block text-sm font-medium text-gray-700">Student ID</label>
                                <input type="text" id="studentID" required pattern="[A-Z0-9]+" title="Only alphanumeric characters allowed" class="w-full p-3 border border-gray-300 rounded-xl focus:ring-emerald-500 focus:border-emerald-500 shadow-sm">
                            </div>
                        </div>

                        <!-- Total Subjects in Curriculum Input -->
                        <div>
                            <label for="totalCurriculumSubjects" class="block text-sm font-medium text-gray-700">Total Subjects in Curriculum (for 100% calculation)</label>
                            <input type="number" id="totalCurriculumSubjects" value="0" min="0" class="w-full p-3 border border-gray-300 rounded-xl focus:ring-emerald-500 focus:border-emerald-500 shadow-sm">
                            <p class="text-xs text-gray-500 mt-1">If > 0, the overall percentage will be calculated against this many subjects (assuming 100 marks each).</p>
                        </div>
                        <!-- END NEW INPUT -->

                        <!-- Grades Input Section -->
                        <div id="grades-input-section" class="pt-2">
                            <div class="flex justify-between items-center mb-3 border-b pb-2">
                                <label class="block text-base font-semibold text-gray-800">Subject Grades (Mark is out of 100)</label>
                                <button type="button" onclick="toggleBulkInput()" id="bulk-toggle-btn" class="text-xs text-emerald-600 hover:text-emerald-800 font-medium py-1 px-3 rounded-full bg-emerald-100 hover:bg-emerald-200 transition">
                                    Bulk Add Subjects
                                </button>
                            </div>
                            
                            <div id="subjects-container" class="space-y-3 mb-4 p-3 bg-emerald-50 rounded-xl border border-emerald-200 min-h-[4rem]">
                                <!-- Dynamically added subjects will go here -->
                                <p id="no-subjects-msg" class="text-emerald-400 text-sm italic py-2">No subjects added.</p>
                            </div>

                            <!-- Single Subject Input Row (Default) -->
                            <div id="single-input-mode" class="space-y-4">
                                <div class="flex space-x-3">
                                    <input type="text" id="new-subject-name" placeholder="Subject Name" class="flex-1 p-3 border border-emerald-300 rounded-xl focus:ring-emerald-500 focus:border-emerald-500 text-sm">
                                    <input type="number" id="new-subject-mark" placeholder="Mark" min="0" max="100" class="w-20 p-3 border border-emerald-300 rounded-xl focus:ring-emerald-500 focus:border-emerald-500 text-sm text-center">
                                    <button type="button" onclick="addSubjectGrade()" class="bg-emerald-600 text-white p-3 rounded-xl hover:bg-emerald-700 transition shadow-md text-sm whitespace-nowrap font-medium">Add</button>
                                </div>
                                <!-- Inline error message for subject input validation -->
                                <p id="subject-input-error" class="text-red-500 text-xs mt-2 font-medium h-4"></p> 
                            </div>

                            <!-- Bulk Subject Input Area (Hidden by default) -->
                            <div id="bulk-input-mode" class="hidden space-y-4">
                                <textarea id="bulk-subjects-textarea" rows="5" placeholder="Enter subjects and marks, one pair per line or separated by commas.&#10;Format: Subject Name, Mark&#10;Example: Chemistry, 85&#10;Data Structures: 92" class="w-full p-3 border border-emerald-300 rounded-xl focus:ring-emerald-500 focus:border-emerald-500 text-sm"></textarea>
                                <button type="button" onclick="addBulkSubjectGrades()" class="w-full bg-emerald-500 text-white p-3 rounded-xl hover:bg-emerald-600 transition shadow-md font-medium">Process Bulk Grades</button>
                                <p id="bulk-input-error" class="text-red-500 text-xs mt-2 font-medium h-4"></p>
                            </div>


                            <p id="grade-error" class="text-red-500 text-xs mt-2 hidden font-medium">Please add at least one subject with a valid mark.</p>
                        </div>
                        <!-- End Grades Input Section -->


                        <button type="submit" id="form-submit-btn" class="w-full bg-emerald-600 text-white p-3 rounded-xl font-bold text-lg hover:bg-emerald-700 transition duration-200 shadow-lg mt-4">Add Student</button>
                        <button type="button" id="form-cancel-btn" onclick="clearForm()" class="w-full bg-gray-400 text-white p-3 rounded-xl font-semibold hover:bg-gray-500 transition duration-200 shadow-md hidden">Cancel Update</button>
                    </form>
                </div>

                <!-- Student List/Table (Columns 2 & 3) -->
                <div class="lg:col-span-2">
                    <h2 class="text-2xl font-semibold text-gray-800 mb-4">Current Students Records</h2>
                    <div class="custom-scrollbar overflow-y-auto max-h-[70vh] bg-white rounded-2xl shadow-lg border border-gray-200">
                        <table class="min-w-full divide-y divide-gray-200">
                            <thead class="bg-gray-50 sticky top-0 shadow-sm">
                                <tr>
                                    <th class="px-4 py-3 text-left text-xs font-bold text-gray-600 uppercase tracking-wider">Name (ID)</th>
                                    <th class="px-4 py-3 text-center text-xs font-bold text-gray-600 uppercase tracking-wider">Age</th>
                                    <th class="px-4 py-3 text-center text-xs font-bold text-gray-600 uppercase tracking-wider">Avg. (%)</th>
                                    <th class="px-4 py-3 text-center text-xs font-bold text-gray-600 uppercase tracking-wider">Grade</th>
                                    <th class="px-4 py-3 text-center text-xs font-bold text-gray-600 uppercase tracking-wider">Actions</th>
                                </tr>
                            </thead>
                            <tbody id="student-list-body" class="bg-white divide-y divide-gray-100">
                                <!-- Student rows will be inserted here -->
                            </tbody>
                        </table>
                    </div>
                    <p id="empty-message" class="mt-6 text-center text-gray-500 font-medium hidden">No students added yet. Start by filling out the form!</p>
                </div>
            </div>
        </div>
    </div>

    <script>
        // Data storage for the current form's subject/mark entries
        let currentSubjectGrades = [];
        let currentEditingId = null;

        // =========================================================
        // 1. Core Classes (Conceptual Java/JavaScript Mapping) 
        // The classes below mirror the business logic defined in StudentModel.java
        // (the Java backend), allowing the front-end to function independently.
        // =========================================================
        
        /**
         * CONCEPTUAL MAPPING TO JAVA:
         * This JavaScript object is equivalent to the Java 'Grade' class.
         * {
         * subject: String,
         * mark: Number // int
         * }
         */

        /**
         * Represents a single student.
         * CONCEPTUAL MAPPING TO JAVA: This class is equivalent to the Java 'Student' class.
         * The methods 'calculateAverage' and 'getLetterGrade' contain the core business logic
         * that would typically run on the Java server.
         */
        class Student {
            // Updated constructor to accept totalCurriculumSubjects
            constructor(name, age, studentID, grades, totalCurriculumSubjects, id = crypto.randomUUID()) {
                this.id = id;
                this.name = name;
                this.age = parseInt(age);
                this.studentID = studentID;
                this.grades = Array.isArray(grades) ? grades : [];
                // Store the override count. Ensure it's a number, defaults to 0.
                this.totalCurriculumSubjects = Math.max(0, parseInt(totalCurriculumSubjects) || 0); 
            }

            /** * Calculates the average grade (percentage).
             * It uses the specified totalCurriculumSubjects for the denominator if > 0,
             * otherwise it uses the number of subjects actually entered.
             * This logic mirrors the Java calculateAverage method.
             */
            calculateAverage() {
                // Total marks earned from entered subjects
                const totalEarnedMarks = this.grades.reduce((sum, item) => sum + item.mark, 0);
                
                // Determine the denominator (Total possible marks):
                let denominatorSubjects = 0;
                if (this.totalCurriculumSubjects > 0) {
                    denominatorSubjects = this.totalCurriculumSubjects;
                } else if (this.grades.length > 0) {
                    denominatorSubjects = this.grades.length;
                } else {
                    return 0; // No grades and no total subjects defined
                }

                // Total possible marks based on 100 marks per subject
                const totalPossibleMarks = denominatorSubjects * 100;
                
                if (totalPossibleMarks === 0) return 0;
                
                return ((totalEarnedMarks / totalPossibleMarks) * 100).toFixed(1);
            }

            /** Assigns a letter grade based on the average. This logic mirrors the Java getLetterGrade method. */
            getLetterGrade() {
                const avg = parseFloat(this.calculateAverage());
                if (avg >= 90) return 'A';
                if (avg >= 80) return 'B';
                if (avg >= 70) return 'C';
                if (avg >= 60) return 'D';
                return 'F';
            }
        }

        /**
         * Manages the collection of students (simulated persistence via localStorage).
         * In a real mixed-Java app, this class would primarily use 'fetch' to communicate
         * with the Java REST API endpoints.
         */
        class StudentManager {
            constructor() {
                this.students = this.loadStudents();
            }

            /** Loads student data from localStorage. */
            loadStudents() {
                try {
                    const data = JSON.parse(localStorage.getItem('studentData')) || [];
                    // Map plain objects back to Student class instances, passing all properties
                    return data.map(s => new Student(s.name, s.age, s.studentID, s.grades, s.totalCurriculumSubjects, s.id));
                } catch (e) {
                    console.error("Error loading students:", e);
                    return [];
                }
            }

            /** Saves student data to localStorage (Simulated Database Persistence). */
            saveStudents() {
                localStorage.setItem('studentData', JSON.stringify(this.students));
            }

            /** Adds a new student instance. */
            addStudent(student) {
                this.students.push(student);
                this.saveStudents();
            }

            /** Finds a student by ID. */
            getStudent(id) {
                return this.students.find(s => s.id === id);
            }

            /** Updates an existing student. */
            updateStudent(id, updatedData) {
                const index = this.students.findIndex(s => s.id === id);
                if (index !== -1) {
                    const student = this.students[index];
                    student.name = updatedData.name;
                    student.age = parseInt(updatedData.age);
                    student.studentID = updatedData.studentID;
                    student.grades = updatedData.grades; 
                    student.totalCurriculumSubjects = Math.max(0, parseInt(updatedData.totalCurriculumSubjects) || 0);
                    this.saveStudents();
                    return true;
                }
                return false;
            }

            /** Deletes a student by ID. */
            deleteStudent(id) {
                const initialLength = this.students.length;
                this.students = this.students.filter(s => s.id !== id);
                this.saveStudents();
                return this.students.length < initialLength;
            }

            /** Returns all students for viewing. */
            getStudents() {
                return this.students;
            }
        }

        const manager = new StudentManager();

        // =========================================================
        // 2. Dynamic Subject Input Handlers
        // =========================================================

        /** Renders the list of subjects currently added to the form. */
        function renderSubjectInputs() {
            const container = document.getElementById('subjects-container');
            const msg = document.getElementById('no-subjects-msg');
            
            // Robust null check for stability
            if (!container || !msg) {
                console.warn("Attempted to render subject inputs before elements were available.");
                return;
            }
            
            container.innerHTML = '';
            
            if (currentSubjectGrades.length === 0) {
                msg.classList.remove('hidden');
                container.appendChild(msg);
                return;
            }
            msg.classList.add('hidden');

            currentSubjectGrades.forEach((item, index) => {
                const div = document.createElement('div');
                // Refined styling for a cleaner list item
                div.className = 'flex justify-between items-center bg-white p-3 rounded-lg border border-gray-300 shadow-sm transition duration-150 hover:border-emerald-500';
                div.innerHTML = `
                    <div class="flex-1 min-w-0 pr-4">
                        <span class="text-sm font-semibold text-gray-800 block truncate">${item.subject.toUpperCase()}</span>
                    </div>
                    <span class="text-sm font-bold text-emerald-800 bg-emerald-200 px-3 py-1 rounded-full whitespace-nowrap">${item.mark} / 100</span>
                    <button type="button" onclick="removeSubjectGrade(${index})" class="text-red-500 hover:text-red-700 ml-4 transition duration-150 p-1 rounded-full hover:bg-red-50">
                        <!-- Icon: X -->
                        <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12"></path></svg>
                    </button>
                `;
                container.appendChild(div);
            });
        }

        /** Adds a new subject/mark to the temporary list via single-input mode. */
        function addSubjectGrade() {
            const nameInput = document.getElementById('new-subject-name');
            const markInput = document.getElementById('new-subject-mark');
            const subjectErrorEl = document.getElementById('subject-input-error'); 
            
            const name = nameInput.value.trim();
            const mark = parseInt(markInput.value);

            if (!nameInput || !markInput) return; 

            // Clear previous errors
            subjectErrorEl.textContent = ''; 
            
            if (!name || isNaN(mark) || mark < 0 || mark > 100) {
                subjectErrorEl.textContent = 'Please enter a valid subject name (e.g., MATHS) and a mark (0-100).';
                if (!name) { nameInput.focus(); } else { markInput.focus(); }
                return;
            }

            currentSubjectGrades.push({ subject: name, mark: mark });
            nameInput.value = '';
            markInput.value = '';
            
            nameInput.focus(); 
            renderSubjectInputs();
        }

        /** Removes a subject/mark from the temporary list. */
        function removeSubjectGrade(index) {
            currentSubjectGrades.splice(index, 1);
            renderSubjectInputs();
        }

        /** Toggles between single subject input and bulk input text area. */
        let isBulkMode = false;
        function toggleBulkInput() {
            isBulkMode = !isBulkMode;
            const singleMode = document.getElementById('single-input-mode');
            const bulkMode = document.getElementById('bulk-input-mode');
            const toggleBtn = document.getElementById('bulk-toggle-btn');

            if (isBulkMode) {
                singleMode.classList.add('hidden');
                bulkMode.classList.remove('hidden');
                toggleBtn.textContent = 'Use Single Input';
                document.getElementById('bulk-subjects-textarea').focus();
            } else {
                singleMode.classList.remove('hidden');
                bulkMode.classList.add('hidden');
                toggleBtn.textContent = 'Bulk Add Subjects';
                document.getElementById('new-subject-name').focus();
            }
            document.getElementById('subject-input-error').textContent = '';
            document.getElementById('bulk-input-error').textContent = '';
        }

        /** Parses the text content from the bulk input textarea. */
        function parseBulkInput(text) {
            const lines = text.trim().split('\n').filter(line => line.trim() !== '');
            const parsedGrades = [];
            const errors = [];
            
            const gradeRegex = /^(.*?)\s*[:,\s]\s*(\d+)$/i; // Matches: Subject Name, Mark OR Subject Name: Mark

            lines.forEach((line, index) => {
                const match = line.match(gradeRegex);

                if (match && match.length === 3) {
                    const subject = match[1].trim();
                    const mark = parseInt(match[2].trim());
                    
                    if (subject.length > 0 && !isNaN(mark) && mark >= 0 && mark <= 100) {
                        parsedGrades.push({ subject, mark });
                    } else {
                        errors.push(`Line ${index + 1}: Mark must be between 0 and 100.`);
                    }
                } else {
                    errors.push(`Line ${index + 1}: Invalid format. Use "Subject Name, Mark".`);
                }
            });

            return { grades: parsedGrades, errors: errors };
        }

        /** Processes grades from the bulk textarea and adds them to the list. */
        function addBulkSubjectGrades() {
            const textarea = document.getElementById('bulk-subjects-textarea');
            const bulkErrorEl = document.getElementById('bulk-input-error');
            const text = textarea.value;

            if (!text.trim()) {
                bulkErrorEl.textContent = 'Please enter subjects before processing.';
                return;
            }
            
            const { grades, errors } = parseBulkInput(text);
            
            if (errors.length > 0) {
                bulkErrorEl.textContent = `Error(s) found. Check formatting: ${errors.join('; ')}.`;
                return;
            }

            // Append parsed grades to the current list
            currentSubjectGrades = currentSubjectGrades.concat(grades);
            
            // Clear the textarea upon successful parsing
            textarea.value = '';
            bulkErrorEl.textContent = '';
            
            renderSubjectInputs();
            // Switch back to single input mode after successful bulk add for simplicity
            toggleBulkInput(); 
            showToast(`${grades.length} subjects added from bulk input.`, 'success');
        }

        // =========================================================
        // 3. UI Rendering and Event Handlers
        // =========================================================

        /** Renders the student list table. */
        function renderStudentList() {
            const tableBody = document.getElementById('student-list-body');
            const emptyMessage = document.getElementById('empty-message');
            tableBody.innerHTML = '';
            const students = manager.getStudents();

            if (students.length === 0) {
                emptyMessage.classList.remove('hidden');
                return;
            }
            emptyMessage.classList.add('hidden');

            students.forEach(student => {
                const avg = student.calculateAverage();
                const letter = student.getLetterGrade();
                
                // Refined color scheme for grades
                let colorClass = 'bg-gray-200 text-gray-800';
                if (letter === 'A') colorClass = 'bg-green-100 text-green-700 ring-1 ring-green-300';
                else if (letter === 'B') colorClass = 'bg-blue-100 text-blue-700 ring-1 ring-blue-300';
                else if (letter === 'C') colorClass = 'bg-yellow-100 text-yellow-700 ring-1 ring-yellow-300';
                else if (letter === 'D') colorClass = 'bg-orange-100 text-orange-700 ring-1 ring-orange-300';
                else if (letter === 'F') colorClass = 'bg-red-100 text-red-700 ring-1 ring-red-300';

                const row = `
                    <tr class="hover:bg-emerald-50 transition-colors">
                        <td class="px-4 py-3 text-sm text-gray-900 font-medium whitespace-nowrap">
                            ${student.name} <span class="text-xs text-emerald-500 block font-normal">ID: ${student.studentID}</span>
                        </td>
                        <td class="px-4 py-3 text-sm text-center text-gray-600">${student.age}</td>
                        <td class="px-4 py-3 text-sm text-center text-gray-900 font-bold">${avg}%</td>
                        <td class="px-4 py-3 text-sm text-center">
                            <span class="px-4 py-1 inline-flex text-xs leading-5 font-bold rounded-full ${colorClass}">
                                ${letter}
                            </span>
                        </td>
                        <td class="px-4 py-3 text-center whitespace-nowrap space-x-3">
                            <button onclick="viewSubjects('${student.id}')" class="text-emerald-600 hover:text-emerald-800 text-sm font-semibold transition duration-150">View Grades</button>
                            <button onclick="editStudent('${student.id}')" class="text-gray-600 hover:text-gray-800 text-sm font-semibold transition duration-150">Edit</button>
                            <button onclick="deleteStudentHandler('${student.id}', '${student.name}')" class="text-red-600 hover:text-red-800 text-sm font-semibold transition duration-150">Delete</button>
                        </td>
                    </tr>
                `;
                tableBody.innerHTML += row;
            });
        }
        
        /** Shows a detailed subject list in a temporary modal-like overlay. */
        function viewSubjects(id) {
            const student = manager.getStudent(id);
            if (!student) return;

            const totalMarksEarned = student.grades.reduce((sum, item) => sum + item.mark, 0);
            const totalSubjects = student.grades.length;
            const avg = student.calculateAverage();

            let gradesHtml = student.grades.map(g => `
                <div class="flex justify-between py-2 border-b border-gray-100">
                    <span class="text-gray-700 font-medium">${g.subject}</span>
                    <span class="font-bold text-emerald-700">${g.mark} / 100</span>
                </div>
            `).join('');

            if (totalSubjects === 0) {
                gradesHtml = '<p class="text-center text-gray-500 italic py-4">No grades recorded for this student.</p>';
            }

            const modalHtml = `
                <div id="subject-modal-overlay" class="fixed inset-0 bg-gray-900 bg-opacity-70 z-50 flex items-center justify-center p-4" onclick="document.getElementById('subject-modal-overlay').remove()">
                    <div class="bg-white p-6 rounded-2xl shadow-2xl max-w-lg w-full transition-all duration-300 transform scale-100" onclick="event.stopPropagation()">
                        <h3 class="text-2xl font-bold text-emerald-700 mb-4 border-b pb-2">Grades for ${student.name}</h3>
                        <div class="max-h-80 overflow-y-auto custom-scrollbar pr-2">
                            ${gradesHtml}
                        </div>
                        <div class="mt-4 pt-4 border-t border-gray-200 space-y-2">
                            <p class="text-base font-semibold text-gray-800">Total Marks Earned: <span class="text-emerald-600">${totalMarksEarned}</span></p>
                            <p class="text-base font-semibold text-gray-800">Total Subjects Recorded: <span class="text-emerald-600">${totalSubjects}</span></p>
                            <p class="text-lg font-bold text-gray-900">Overall Average: <span class="text-emerald-700">${avg}%</span></p>
                        </div>
                        <button onclick="document.getElementById('subject-modal-overlay').remove()" class="mt-6 w-full bg-gray-300 text-gray-800 p-3 rounded-xl font-semibold hover:bg-gray-400 transition duration-200">Close</button>
                    </div>
                </div>
            `;
            document.body.insertAdjacentHTML('beforeend', modalHtml);
        }

        /** Handles form submission for both Add and Update operations. */
        document.getElementById('student-form').addEventListener('submit', function(e) {
            e.preventDefault();

            const gradeError = document.getElementById('grade-error');
            if (currentSubjectGrades.length === 0) {
                gradeError.classList.remove('hidden');
                return;
            }
            gradeError.classList.add('hidden');

            const name = document.getElementById('name').value;
            const age = document.getElementById('age').value;
            const studentID = document.getElementById('studentID').value;
            const totalCurriculumSubjects = document.getElementById('totalCurriculumSubjects').value; // Get new field

            // Pass the structured subject array and total curriculum subjects
            const studentData = { 
                name, 
                age, 
                studentID, 
                grades: currentSubjectGrades, 
                totalCurriculumSubjects: totalCurriculumSubjects 
            };

            if (currentEditingId) {
                manager.updateStudent(currentEditingId, studentData);
                showToast('Student updated successfully!', 'success');
            } else {
                const newStudent = new Student(
                    name, 
                    age, 
                    studentID, 
                    studentData.grades, 
                    studentData.totalCurriculumSubjects // Pass to constructor
                );
                manager.addStudent(newStudent);
                showToast('Student added successfully!', 'success');
            }

            clearForm();
            renderStudentList();
        });

        /** Populates the form for editing a student. */
        function editStudent(id) {
            const student = manager.getStudent(id);
            if (!student) return;

            currentEditingId = id;
            document.getElementById('form-title').textContent = `Update Student: ${student.name}`;
            document.getElementById('form-submit-btn').textContent = 'Save Changes';
            document.getElementById('form-cancel-btn').classList.remove('hidden');

            document.getElementById('name').value = student.name;
            document.getElementById('age').value = student.age;
            document.getElementById('studentID').value = student.studentID;
            // Populate the new field
            document.getElementById('totalCurriculumSubjects').value = student.totalCurriculumSubjects || 0;
            
            // Set the current SubjectGrades array to the student's grades for editing
            currentSubjectGrades = [...student.grades];
            renderSubjectInputs();
            // Scroll to the top of the form for better UX
            document.getElementById('app-container').scrollIntoView({ behavior: 'smooth' });
            
            // Ensure we are in single input mode when editing starts
            if (isBulkMode) toggleBulkInput();
        }

        /** Clears the form and resets it to 'Add New Student' mode. */
        function clearForm() {
            currentEditingId = null;
            document.getElementById('student-form').reset();
            document.getElementById('form-title').textContent = 'Add New Student';
            document.getElementById('form-submit-btn').textContent = 'Add Student';
            document.getElementById('form-cancel-btn').classList.add('hidden');
            document.getElementById('grade-error').classList.add('hidden');
            
            // Clear inline subject error messages
            document.getElementById('subject-input-error').textContent = '';
            document.getElementById('bulk-input-error').textContent = '';
            
            // Reset the new field
            document.getElementById('totalCurriculumSubjects').value = 0;

            // Clear the subject grades array and re-render the empty list
            currentSubjectGrades = [];
            renderSubjectInputs();
            
            // Ensure we are in single input mode after clearing
            if (isBulkMode) toggleBulkInput();
        }

        /** Handles the deletion of a student. */
        function deleteStudentHandler(id, name) {
            // Using a simple confirm dialog for this example
            if (confirm(`Are you sure you want to delete student: ${name}?`)) {
                manager.deleteStudent(id);
                renderStudentList();
                showToast(`Student ${name} deleted.`, 'error');
            }
        }

        // =========================================================
        // 4. Authentication Logic (Simulated)
        // =========================================================

        const AUTH_KEY = 'mockUserAuth';
        let isSignUpMode = false; 

        /** Toggles the visibility of sign-up specific fields. */
        function toggleSignUpMode(show) {
            isSignUpMode = show;
            const fieldsContainer = document.getElementById('signup-fields');
            const confirmPasswordInput = document.getElementById('auth-confirm-password');
            const createButton = document.getElementById('create-account-btn');
            const signInButton = document.getElementById('sign-in-btn');
            const authTitle = document.getElementById('auth-title');

            if (show) {
                fieldsContainer.classList.remove('hidden');
                confirmPasswordInput.classList.remove('hidden');
                signInButton.classList.add('hidden');
                
                createButton.textContent = 'Register Now';
                createButton.onclick = handleCreateAccount;
                
                authTitle.textContent = 'Create New Account';
                createButton.className = 'w-full bg-green-500 text-white p-3 rounded-xl font-bold text-lg hover:bg-green-600 transition duration-200 shadow-md hover:shadow-lg transform hover:scale-[1.01]';
                
            } else {
                fieldsContainer.classList.add('hidden');
                confirmPasswordInput.classList.add('hidden');
                signInButton.classList.remove('hidden');
                
                createButton.textContent = 'Create Account';
                createButton.onclick = handleSignUp; 
                
                authTitle.textContent = 'Student System Access';
                createButton.className = 'w-full bg-gray-200 text-emerald-700 p-3 rounded-xl font-semibold hover:bg-gray-300 transition duration-200 shadow-sm';
            }
            // Clear inputs and messages when mode changes
            document.getElementById('auth-screen').querySelectorAll('input').forEach(input => input.value = '');
            document.getElementById('auth-message').textContent = '';
        }

        /** Checks if user data exists in localStorage (i.e., "signed in"). */
        function checkAuthStatus() {
            return localStorage.getItem(AUTH_KEY) !== null;
        }

        /** Switches the UI view between auth and manager screens. */
        function toggleScreens(isAuth = false) {
            const authScreen = document.getElementById('auth-screen');
            const managerScreen = document.getElementById('manager-screen');

            if (isAuth) {
                authScreen.classList.add('hidden');
                managerScreen.classList.remove('hidden');
                document.getElementById('app-container').classList.remove('max-w-sm');
                document.getElementById('app-container').classList.add('max-w-6xl');
                renderStudentList();
                // Clear subject form state when switching screens
                clearForm(); 
            } else {
                authScreen.classList.remove('hidden');
                managerScreen.classList.add('hidden');
                document.getElementById('app-container').classList.add('max-w-sm');
                document.getElementById('app-container').classList.remove('max-w-6xl');
                toggleSignUpMode(false); 
            }
        }

        /** Simulates signing in (only uses username and password). */
        function handleSignIn() {
            if (isSignUpMode) {
                toggleSignUpMode(false);
                return;
            }

            const usernameInput = document.getElementById('auth-username').value;
            const passwordInput = document.getElementById('auth-password').value;
            const messageEl = document.getElementById('auth-message');
            
            messageEl.classList.remove('text-green-500');
            messageEl.classList.add('text-red-500');

            try {
                const storedUser = JSON.parse(localStorage.getItem(AUTH_KEY));

                if (!storedUser) {
                    messageEl.textContent = 'No account found. Please create an account first.';
                    return;
                }

                if (storedUser.username === usernameInput && storedUser.password === passwordInput) {
                    messageEl.textContent = '';
                    toggleScreens(true);
                } else {
                    messageEl.textContent = 'Invalid username or password.';
                }
            } catch (e) {
                messageEl.textContent = 'An error occurred during sign in.';
            }
        }
        
        /** Toggles the UI to Sign Up mode (first click). */
        function handleSignUp() {
            toggleSignUpMode(true);
        }

        /** Simulates creating a new account (second click after toggling fields) and auto sign-in. */
        function handleCreateAccount() {
            const firstNameInput = document.getElementById('auth-firstname').value.trim();
            const lastNameInput = document.getElementById('auth-lastname').value.trim();
            const usernameInput = document.getElementById('auth-username').value.trim();
            const passwordInput = document.getElementById('auth-password').value;
            const confirmPasswordInput = document.getElementById('auth-confirm-password').value;
            const messageEl = document.getElementById('auth-message');

            messageEl.textContent = '';
            messageEl.classList.remove('text-green-500', 'text-red-500');
            messageEl.classList.add('text-red-500');

            if (!firstNameInput || !lastNameInput || !usernameInput || !passwordInput || !confirmPasswordInput) {
                messageEl.textContent = 'All fields are required to create an account.';
                return;
            }

            if (passwordInput !== confirmPasswordInput) {
                messageEl.textContent = 'Passwords do not match.';
                return;
            }

            const newUser = { 
                firstName: firstNameInput,
                lastName: lastNameInput,
                username: usernameInput, 
                password: passwordInput 
            };
            localStorage.setItem(AUTH_KEY, JSON.stringify(newUser));
            
            // --- Auto Sign-in after successful creation ---
            showToast(`Welcome, ${firstNameInput}! Account created and signed in.`, 'success');
            
            toggleScreens(true); // Automatically switch to the manager screen
            // ---------------------------------------------
        }

        /** Logs the user out. */
        function signOut() {
            toggleScreens(false);
            showToast('Signed out successfully.', 'info');
        }

        /** Displays temporary messages (Toast). */
        function showToast(message, type = 'info') {
            const container = document.getElementById('app-container');
            let color = 'bg-emerald-600';
            if (type === 'success') color = 'bg-green-600';
            if (type === 'error') color = 'bg-red-600';
            if (type === 'info') color = 'bg-gray-700';

            const msgBox = document.createElement('div');
            msgBox.className = `fixed bottom-6 right-6 p-4 rounded-xl text-white shadow-2xl ${color} transition-opacity duration-300 z-50`;
            msgBox.textContent = message;

            document.body.appendChild(msgBox); 

            setTimeout(() => {
                msgBox.style.opacity = '0';
                msgBox.addEventListener('transitionend', () => msgBox.remove());
            }, 3500);
        }

        // Initialize the app on page load
        window.onload = function() {
            if (checkAuthStatus()) {
                toggleScreens(true);
            } else {
                toggleScreens(false);
            }
            renderSubjectInputs(); // Initialize the empty subject list
        }
    </script>
</body>
</html>
