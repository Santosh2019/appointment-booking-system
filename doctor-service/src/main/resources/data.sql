-- =====================================================================
-- Insert sample doctors (compatible with your current entity & frontend)
-- =====================================================================

-- Cardiologist
INSERT IGNORE INTO doctors (
    doctor_id, doctor_name, mobile, email_id, aadhar_card, pan_card,
    date_of_birth, gender, year_of_experience, qualification, specialization,
    created_at, updated_at, created_by, updated_by
) VALUES (
    'DOC0002', 'Dr. Priya Sharma', '9123456789', 'priya.sharma@hospital.com',
    '123456789012', 'ABCDE1234F', '1972-03-15', 'Female', '11',
    'MBBS, MD Cardiology', 'Cardiologist',
    '2026-01-01 17:57:19', '2026-01-01 17:57:19', 'admin', 'admin'
);

-- Dermatologist
INSERT IGNORE INTO doctors (
    doctor_id, doctor_name, mobile, email_id, aadhar_card, pan_card,
    date_of_birth, gender, year_of_experience, qualification, specialization,
    created_at, updated_at, created_by, updated_by
) VALUES (
    'DOC0003', 'Dr. Amit Patel', '9876543210', 'amit.patel@hospital.com',
    '234567890123', 'BCDEF2345G', '1973-04-20', 'Male', '14',
    'MBBS, MD Dermatology', 'Dermatologist',
    '2026-01-01 17:57:19', '2026-01-01 17:57:19', 'admin', 'admin'
);

-- Neurologist
INSERT IGNORE INTO doctors (
    doctor_id, doctor_name, mobile, email_id, aadhar_card, pan_card,
    date_of_birth, gender, year_of_experience, qualification, specialization,
    created_at, updated_at, created_by, updated_by
) VALUES (
    'DOC0004', 'Dr. Neha Singh', '9988776655', 'neha.singh@hospital.com',
    '345678901234', 'CDEFG3456H', '1974-05-25', 'Female', '17',
    'MBBS, DM Neurology', 'Neurologist',
    '2026-01-01 17:57:19', '2026-01-01 17:57:19', 'admin', 'admin'
);

-- Pediatrician
INSERT IGNORE INTO doctors (
    doctor_id, doctor_name, mobile, email_id, aadhar_card, pan_card,
    date_of_birth, gender, year_of_experience, qualification, specialization,
    created_at, updated_at, created_by, updated_by
) VALUES (
    'DOC0005', 'Dr. Vikram Rao', '9765432109', 'vikram.rao@hospital.com',
    '456789012345', 'DEFGH4567I', '1975-06-10', 'Male', '20',
    'MBBS, MD Pediatrics', 'Pediatrician',
    '2026-01-01 17:57:19', '2026-01-01 17:57:19', 'admin', 'admin'
);

-- General Physician (matches frontend dropdown value exactly)
INSERT IGNORE INTO doctors (
    doctor_id, doctor_name, mobile, email_id, aadhar_card, pan_card,
    date_of_birth, gender, year_of_experience, qualification, specialization,
    created_at, updated_at, created_by, updated_by
) VALUES (
    'DOC0011', 'Dr. Mohan Lal', '9012345678', 'mohan.lal@hospital.com',
    '567890123456', 'EFGHI5678J', '1981-12-05', 'Male', '18',
    'MBBS', 'General Physician',
    '2026-01-01 17:57:19', '2026-01-01 17:57:19', 'admin', 'admin'
);

-- Another General Physician
INSERT IGNORE INTO doctors (
    doctor_id, doctor_name, mobile, email_id, aadhar_card, pan_card,
    date_of_birth, gender, year_of_experience, qualification, specialization,
    created_at, updated_at, created_by, updated_by
) VALUES (
    'DOC0012', 'Dr. Sunita Reddy', '9123456780', 'sunita.reddy@hospital.com',
    '678901234567', 'FGHIJ6789K', '1982-01-15', 'Female', '21',
    'MBBS, DNB', 'General Physician',
    '2026-01-01 17:57:19', '2026-01-01 17:57:19', 'admin', 'admin'
);

-- Orthopedic
INSERT IGNORE INTO doctors (
    doctor_id, doctor_name, mobile, email_id, aadhar_card, pan_card,
    date_of_birth, gender, year_of_experience, qualification, specialization,
    created_at, updated_at, created_by, updated_by
) VALUES (
    'DOC0013', 'Dr. Karan Joshi', '9234567890', 'karan.joshi@hospital.com',
    '789012345678', 'GHIJK7890L', '1983-02-20', 'Male', '24',
    'MBBS, MS Orthopedics', 'Orthopedic',
    '2026-01-01 17:57:19', '2026-01-01 17:57:19', 'admin', 'admin'
);