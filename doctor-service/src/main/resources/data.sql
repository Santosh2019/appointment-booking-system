-- ==========================================================
-- Insert New Doctors Sample Data (Completely New Users)
-- ==========================================================

-- Cardiologist
INSERT IGNORE INTO doctors (
    doctor_id, doctor_name, mobile, email_id, aadhar_card, pan_card,
    date_of_birth, gender, year_of_experience, qualification, specialization,
    created_at, updated_at, created_by, updated_by
) VALUES (
    'DOC0101', 'Dr. Rahul Deshmukh', '9321456789', 'rahul.deshmukh@hospital.com',
    '112233445566', 'AAAPL1234A', '1976-02-10', 'Male', '15',
    'MBBS, MD Cardiology', 'Cardiologist',
    NOW(), NOW(), 'admin', 'admin'
);

-- Dermatologist
INSERT IGNORE INTO doctors (
    doctor_id, doctor_name, mobile, email_id, aadhar_card, pan_card,
    date_of_birth, gender, year_of_experience, qualification, specialization,
    created_at, updated_at, created_by, updated_by
) VALUES (
    'DOC0102', 'Dr. Sneha Kulkarni', '9345678901', 'sneha.kulkarni@hospital.com',
    '223344556677', 'BBAPL2345B', '1980-06-14', 'Female', '12',
    'MBBS, MD Dermatology', 'Dermatologist',
    NOW(), NOW(), 'admin', 'admin'
);

-- Neurologist
INSERT IGNORE INTO doctors (
    doctor_id, doctor_name, mobile, email_id, aadhar_card, pan_card,
    date_of_birth, gender, year_of_experience, qualification, specialization,
    created_at, updated_at, created_by, updated_by
) VALUES (
    'DOC0103', 'Dr. Arvind Nair', '9356789012', 'arvind.nair@hospital.com',
    '334455667788', 'CCAPL3456C', '1975-08-22', 'Male', '18',
    'MBBS, DM Neurology', 'Neurologist',
    NOW(), NOW(), 'admin', 'admin'
);

-- Pediatrician
INSERT IGNORE INTO doctors (
    doctor_id, doctor_name, mobile, email_id, aadhar_card, pan_card,
    date_of_birth, gender, year_of_experience, qualification, specialization,
    created_at, updated_at, created_by, updated_by
) VALUES (
    'DOC0104', 'Dr. Meera Iyer', '9367890123', 'meera.iyer@hospital.com',
    '445566778899', 'DDAPL4567D', '1983-09-05', 'Female', '10',
    'MBBS, MD Pediatrics', 'Pediatrician',
    NOW(), NOW(), 'admin', 'admin'
);

-- General Physician
INSERT IGNORE INTO doctors (
    doctor_id, doctor_name, mobile, email_id, aadhar_card, pan_card,
    date_of_birth, gender, year_of_experience, qualification, specialization,
    created_at, updated_at, created_by, updated_by
) VALUES (
    'DOC0105', 'Dr. Sandeep Patil', '9378901234', 'sandeep.patil@hospital.com',
    '556677889900', 'EEAPL5678E', '1984-11-12', 'Male', '9',
    'MBBS', 'General Physician',
    NOW(), NOW(), 'admin', 'admin'
);

-- Another General Physician
INSERT IGNORE INTO doctors (
    doctor_id, doctor_name, mobile, email_id, aadhar_card, pan_card,
    date_of_birth, gender, year_of_experience, qualification, specialization,
    created_at, updated_at, created_by, updated_by
) VALUES (
    'DOC0106', 'Dr. Kavita Sharma', '9389012345', 'kavita.sharma@hospital.com',
    '667788990011', 'FFAPL6789F', '1986-03-18', 'Female', '13',
    'MBBS, DNB General Medicine', 'General Physician',
    NOW(), NOW(), 'admin', 'admin'
);

-- Orthopedic
INSERT IGNORE INTO doctors (
    doctor_id, doctor_name, mobile, email_id, aadhar_card, pan_card,
    date_of_birth, gender, year_of_experience, qualification, specialization,
    created_at, updated_at, created_by, updated_by
) VALUES (
    'DOC0107', 'Dr. Rohit Verma', '9390123456', 'rohit.verma@hospital.com',
    '778899001122', 'GGAPL7890G', '1979-12-25', 'Male', '16',
    'MBBS, MS Orthopedics', 'Orthopedic',
    NOW(), NOW(), 'admin', 'admin'
);