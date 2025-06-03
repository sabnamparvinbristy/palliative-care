CREATE DATABASE palliative_hms;
USE palliative_hms;
SHOW DATABASES;



CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role ENUM('Admin', 'Doctor') NOT NULL,
    full_name VARCHAR(100),
    contact VARCHAR(20),
    email VARCHAR(100)
);
DROP TABLE users;


INSERT INTO users (username, password, role, full_name, contact, email)
VALUES 
('admin1', 'adminpass', 'Admin', 'Admin One', '01714955001', 'admin1@hms.com'),
('dr.joy', 'joypass', 'Doctor', 'Dr. Joy Smith', '01914955003', 'joy@hms.com'),
('dr.salma', 'salmapass', 'Doctor', 'Dr. Salma Karim', '01715855001', 'salma@hms.com');

-- patients table to store All patient data
CREATE TABLE patients (
    patient_id INT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    dob DATE,
    gender ENUM('Male', 'Female', 'Other'),
    contact VARCHAR(20),
    address TEXT,
    category ENUM('Critical', 'Hospice', 'Long-Term') NOT NULL,
    emergency_contact VARCHAR(100),
    registered_by INT,
    FOREIGN KEY (registered_by) REFERENCES users(user_id)
);

CREATE TABLE doctors (
    doc_id VARCHAR(20) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    joining_date DATE,
    education VARCHAR(100),
    specialization VARCHAR(100),
    age INT,
    gender VARCHAR(10),
    email VARCHAR(100),
    department VARCHAR(100),
    contact_no VARCHAR(20),
    address VARCHAR(200),
    nid VARCHAR(20),
    salary DECIMAL(10, 2),
    shift VARCHAR(50)
    -- week_days VARCHAR(100)  (commented out as per your request)
);


drop table doctors
select* from doctors



select * from patients
INSERT INTO patients (full_name, dob, gender, contact, address, category, emergency_contact, registered_by)
VALUES
('Ayesha Rahman', '1950-03-15', 'Female', '01700000011', 'Dhaka, Bangladesh', 'Hospice', '01900000001', 1),
('Rahim Uddin', '1945-11-25', 'Male', '01700000012', 'Chittagong, Bangladesh', 'Critical', '01900000002', 1);


-- medical_records table to Diagnosis, progress notes
CREATE TABLE medical_records (
    record_id INT AUTO_INCREMENT PRIMARY KEY,
    patient_id INT,
    diagnosis TEXT,
    notes TEXT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    recorded_by INT,
    FOREIGN KEY (patient_id) REFERENCES patients(patient_id),
    FOREIGN KEY (recorded_by) REFERENCES users(user_id)
);

INSERT INTO medical_records (patient_id, diagnosis, notes, recorded_by)
VALUES 
(1, 'Stage 4 Cancer', 'Requires daily pain management and palliative support.', 2),
(2, 'Advanced Kidney Failure', 'Scheduled dialysis twice a week.', 3);

CREATE TABLE appointments (
    appointment_id INT AUTO_INCREMENT PRIMARY KEY,
    patient_id INT,
    doctor_id VARCHAR(20),
    appointment_date DATETIME,
    appointment_time VARCHAR(20),
    status VARCHAR(20),
    purpose VARCHAR(100),
    notes TEXT,
    FOREIGN KEY (patient_id) REFERENCES patients(patient_id),
    FOREIGN KEY (doctor_id) REFERENCES doctors(doc_id)
);

DROP TABLE appointments;
SELECT patient_id, full_name FROM patients;
 DROP TABLE appointments ;

INSERT INTO appointments (patient_id, doctor_id, appointment_date, appointment_time, status, purpose, notes)
VALUES (8, 20003, '2025-05-07 10:00:00', '10:00 AM', 'Confirmed', 'Follow-up Visit', 'Patient is showing improvement.');




CREATE TABLE medications (
    medication_id INT AUTO_INCREMENT PRIMARY KEY,
    patient_id INT,
    medicine_name VARCHAR(100),
    dosage VARCHAR(50),
    schedule_time TIME,
    start_date DATE,
    end_date DATE,
    prescribed_by INT,
    FOREIGN KEY (patient_id) REFERENCES patients(patient_id),
    FOREIGN KEY (prescribed_by) REFERENCES users(user_id)
);

INSERT INTO medications (patient_id, medicine_name, dosage, schedule_time, start_date, end_date, prescribed_by)
VALUES
(1, 'Morphine', '10mg', '09:00:00', '2025-04-10', '2025-05-10', 2),
(2, 'Dialysis Solution', '500ml', '08:00:00', '2025-04-10', '2025-05-10', 3);



CREATE TABLE pharmacy (
    med_id INT AUTO_INCREMENT PRIMARY KEY,
    medicine_name VARCHAR(100),
    quantity INT,
    unit_price DECIMAL(10,2),
    expiry_date DATE
);






ALTER TABLE appointments
ADD COLUMN appointment_time VARCHAR(20),
ADD COLUMN status VARCHAR(20);

DESCRIBE patients;

ALTER TABLE patients ADD COLUMN allergies VARCHAR(255);

ALTER TABLE patients AUTO_INCREMENT = 30001;
ALTER TABLE patients ADD COLUMN age INT;

UPDATE patients
SET age = TIMESTAMPDIFF(YEAR, dob, CURDATE())
WHERE patient_id IS NOT NULL;


SELECT user_id FROM users;



CREATE TABLE emergency_details (
    id INT AUTO_INCREMENT PRIMARY KEY,
    patient_id INT NOT NULL,
    critical_status VARCHAR(50),
    ventilator_status VARCHAR(50),
    life_support_status VARCHAR(50),
    emergency_contact VARCHAR(50),
    patient_name VARCHAR(255),
    FOREIGN KEY (patient_id) REFERENCES patients(patient_id)
);
INSERT INTO emergency_details (patient_id, critical_status, ventilator_status, life_support_status, emergency_contact, patient_name)
SELECT patient_id, 'Critical', 'On', 'Required', '01900000001', full_name
FROM patients;

SELECT * FROM emergency_details;

CREATE TABLE billing (
    billing_id INT PRIMARY KEY,
    patient_id INT NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    discount DECIMAL(5,2) DEFAULT 0, -- discount percentage, e.g., 10.00 means 10%
    insurance_coverage VARCHAR(100),
    payment_reference VARCHAR(100),
    billing_date DATE,
    payment_date DATE,
    payment_method VARCHAR(50),
    billing_status VARCHAR(50),
    billing_notes TEXT,
    final_amount DECIMAL(10,2),
    outstanding_amount DECIMAL(10,2),
    FOREIGN KEY (patient_id) REFERENCES patients(patient_id)
);

INSERT INTO billing (
    billing_id, patient_id, amount, discount, insurance_coverage, 
    payment_reference, billing_date, payment_date, payment_method, 
    billing_status, billing_notes, final_amount, outstanding_amount
) VALUES (
    14280001,   
    8,      -- Ensure this ID exists in `patients`
    1000.00,    
    10.00,      
    'Insurance XYZ', 
    'REF123456',     
    '2025-05-16',    
    '2025-05-16',    
    'Cash',          
    'Paid',          
    'Regular billing',
    900.00,          -- final_amount = amount - (amount * discount/100)
    0.00             -- outstanding_amount (set to 0 if paid)
);
SELECT doc_id FROM doctors;

CREATE TABLE medicine (
    medicine_id INT AUTO_INCREMENT PRIMARY KEY,
    medi_name VARCHAR(100) NOT NULL,
    manufacturer VARCHAR(100),
    strength VARCHAR(50),
    price DECIMAL(10, 2),
    quantity_in_stock INT,
    batch_number VARCHAR(50),
    medicine_type VARCHAR(50),
    dosage_form VARCHAR(50),
    drug_category VARCHAR(50),
    side_effects TEXT,
    storage_conditions TEXT,
    prescription_required BOOLEAN
);

INSERT INTO medicine (
    medi_name, manufacturer, strength, price, quantity_in_stock,
    batch_number, medicine_type, dosage_form, drug_category,
    side_effects, storage_conditions, prescription_required
) VALUES
('Amoxicillin', 'Square Pharmaceuticals Ltd.', '250mg', 2.75, 200, 'AMX2025B', 'Tablet', 'Oral', 'Antibiotic', 'Diarrhea, nausea', 'Store below 25°C in a dry place', TRUE),
('Metformin', 'Beximco Pharma', '850mg', 3.20, 300, 'MET2025C', 'Tablet', 'Oral', 'Antidiabetic', 'Nausea, stomach upset', 'Store in a cool, dry place', TRUE),
('Cetirizine', 'ACI Limited', '10mg', 1.10, 150, 'CTZ2025D', 'Tablet', 'Oral', 'Antihistamine', 'Drowsiness, dry mouth', 'Store at room temperature', FALSE),
('Ibuprofen', 'Renata Limited', '400mg', 2.00, 100, 'IBU2025E', 'Tablet', 'Oral', 'NSAID', 'Stomach upset, dizziness', 'Store in a cool, dry place', FALSE),
('Hydrocortisone', 'Eskayef Pharmaceuticals', '1%', 4.50, 80, 'HDC2025O', 'Ointment', 'Topical', 'Steroid', 'Skin irritation (rare)', 'Keep in a dry, cool place', TRUE);



CREATE TABLE treatment (
    treatment_id INT AUTO_INCREMENT PRIMARY KEY,
    patient_id INT NOT NULL,
    doctor_id VARCHAR(20) NOT NULL,  
    diagnosis TEXT,
    treatment_type VARCHAR(100),
    medications TEXT,
    start_date DATE,
    end_date DATE,
    notes TEXT,
    side_effects TEXT,
    outcome VARCHAR(255),

    FOREIGN KEY (patient_id) REFERENCES patients(patient_id),
    FOREIGN KEY (doctor_id) REFERENCES doctors(doc_id)
);




DROP TABLE treatment;

SELECT * FROM treatment;
INSERT INTO treatment (
    treatment_id, patient_id, doctor_id, diagnosis, treatment_type, medications,
    start_date, end_date, notes, side_effects, outcome
) VALUES
(101, 8, '20003', 'Coronary artery blockage', 'surgery', 'Aspirin, Beta blockers, Statins', '2025-04-01', '2025-04-10',
 'Angioplasty with stent placement performed successfully.', 'Mild chest pain post-surgery', 'Recovery in progress');
 
 CREATE TABLE reports (
    report_id INT PRIMARY KEY,
    patient_id INT NOT NULL,
    doctor_id VARCHAR(20) NOT NULL,
    report_title VARCHAR(100),
    test_date DATE,
    test_result VARCHAR(50),
    report_type VARCHAR(50),
    follow_up_required VARCHAR(10),
    report_description TEXT,
    FOREIGN KEY (patient_id) REFERENCES patients(patient_id),
    FOREIGN KEY (doctor_id) REFERENCES doctors(doc_id)
);

INSERT INTO reports (
    report_id, patient_id, doctor_id, report_title, test_date,
    test_result, report_type, follow_up_required, report_description
) VALUES (
    10001, 9, '20003', 'Routine Checkup', CURDATE(),
    'Negative', 'Blood Test', 'No', 'good'
);

 
 -- Drop the old foreign key constraints
ALTER TABLE reports DROP FOREIGN KEY reports_ibfk_2;
ALTER TABLE treatment DROP FOREIGN KEY treatment_ibfk_2;
ALTER TABLE appointments DROP FOREIGN KEY appointments_ibfk_2;

-- Recreate them with ON DELETE CASCADE
ALTER TABLE reports 
ADD CONSTRAINT reports_ibfk_2 
FOREIGN KEY (doctor_id) REFERENCES doctors(doc_id) 
ON DELETE CASCADE;

ALTER TABLE treatment 
ADD CONSTRAINT treatment_ibfk_2 
FOREIGN KEY (doctor_id) REFERENCES doctors(doc_id) 
ON DELETE CASCADE;

ALTER TABLE appointments 
ADD CONSTRAINT appointments_ibfk_2 
FOREIGN KEY (doctor_id) REFERENCES doctors(doc_id) 
ON DELETE CASCADE;
