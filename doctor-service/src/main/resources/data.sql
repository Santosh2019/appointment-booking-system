-- Correct table name: doctors (as per @Table(name = "doctors"))
-- Correct column names as per @Column annotations
INSERT   INTO doctors (doctor_id, doctor_name, mobile, email_id, aadhar_card, pan_card, date_of_birth, gender, year_of_experience, qualification, specialization, created_at, updated_at, created_by, updated_by)
VALUES ('DOC0002', 'Dr. Priya Sharma', '8021234567', 'priya.sharma@hospital.com', '000210022002', 'ACEGI0002C', '1972-03-03', 'Female', '11', 'MBBS, MD', 'Cardiology', '2026-01-01 17:57:19', '2026-01-01 17:57:19', 'admin', 'admin');

INSERT   INTO doctors (doctor_id, doctor_name, mobile, email_id, aadhar_card, pan_card, date_of_birth, gender, year_of_experience, qualification, specialization, created_at, updated_at, created_by, updated_by)
VALUES ('DOC0003', 'Dr. Amit Patel', '8031234567', 'amit.patel@hospital.com', '000310032003', 'ADGJM0003D', '1973-04-04', 'Male', '14', 'MBBS, MD', 'Dermatology', '2026-01-01 17:57:19', '2026-01-01 17:57:19', 'admin', 'admin');

INSERT   INTO doctors (doctor_id, doctor_name, mobile, email_id, aadhar_card, pan_card, date_of_birth, gender, year_of_experience, qualification, specialization, created_at, updated_at, created_by, updated_by)
VALUES ('DOC0004', 'Dr. Neha Singh', '8041234567', 'neha.singh@hospital.com', '000410042004', 'AEIMQ0004E', '1974-05-05', 'Female', '17', 'MBBS, MD', 'Dermatology', '2026-01-01 17:57:19', '2026-01-01 17:57:19', 'admin', 'admin');

INSERT   INTO doctors (doctor_id, doctor_name, mobile, email_id, aadhar_card, pan_card, date_of_birth, gender, year_of_experience, qualification, specialization, created_at, updated_at, created_by, updated_by)
VALUES ('DOC0005', 'Dr. Vikram Rao', '8051234567', 'vikram.rao@hospital.com', '000510052005', 'AFKPU0005F', '1975-06-06', 'Male', '20', 'MBBS, MD', 'Neurology', '2026-01-01 17:57:19', '2026-01-01 17:57:19', 'admin', 'admin');

INSERT   INTO doctors (doctor_id, doctor_name, mobile, email_id, aadhar_card, pan_card, date_of_birth, gender, year_of_experience, qualification, specialization, created_at, updated_at, created_by, updated_by)
VALUES ('DOC0006', 'Dr. Anjali Mehta', '8061234567', 'anjali.mehta@hospital.com', '000610062006', 'AGMSY0006G', '1976-07-07', 'Female', '23', 'MBBS, MD', 'Neurology', '2026-01-01 17:57:19', '2026-01-01 17:57:19', 'admin', 'admin');

INSERT   INTO doctors (doctor_id, doctor_name, mobile, email_id, aadhar_card, pan_card, date_of_birth, gender, year_of_experience, qualification, specialization, created_at, updated_at, created_by, updated_by)
VALUES ('DOC0007', 'Dr. Sanjay Gupta', '8071234567', 'sanjay.gupta@hospital.com', '000710072007', 'AHOVC0007H', '1977-08-08', 'Male', '6', 'MBBS, MD', 'Pediatrics', '2026-01-01 17:57:19', '2026-01-01 17:57:19', 'admin', 'admin');

INSERT   INTO doctors (doctor_id, doctor_name, mobile, email_id, aadhar_card, pan_card, date_of_birth, gender, year_of_experience, qualification, specialization, created_at, updated_at, created_by, updated_by)
VALUES ('DOC0008', 'Dr. Pooja Verma', '8081234567', 'pooja.verma@hospital.com', '000810082008', 'AIQYG0008I', '1978-09-09', 'Female', '9', 'MBBS, MD', 'Pediatrics', '2026-01-01 17:57:19', '2026-01-01 17:57:19', 'admin', 'admin');

INSERT   INTO doctors (doctor_id, doctor_name, mobile, email_id, aadhar_card, pan_card, date_of_birth, gender, year_of_experience, qualification, specialization, created_at, updated_at, created_by, updated_by)
VALUES ('DOC0009', 'Dr. Arjun Desai', '8091234567', 'arjun.desai@hospital.com', '000910092009', 'AJSBK0009J', '1979-10-10', 'Male', '12', 'MBBS, MD', 'Orthopedics', '2026-01-01 17:57:19', '2026-01-01 17:57:19', 'admin', 'admin');

INSERT   INTO doctors (doctor_id, doctor_name, mobile, email_id, aadhar_card, pan_card, date_of_birth, gender, year_of_experience, qualification, specialization, created_at, updated_at, created_by, updated_by)
VALUES ('DOC0010', 'Dr. Kavita Nair', '8101234567', 'kavita.nair@hospital.com', '001010102010', 'AKUEO0010K', '1980-11-11', 'Female', '15', 'MBBS, MD', 'Orthopedics', '2026-01-01 17:57:19', '2026-01-01 17:57:19', 'admin', 'admin');

-- General Physicians (updated to match frontend dropdown value "General Physician")
INSERT IGNORE INTO doctors (doctor_id, doctor_name, mobile, email_id, aadhar_card, pan_card, date_of_birth, gender, year_of_experience, qualification, specialization, created_at, updated_at, created_by, updated_by)
VALUES ('DOC0011', 'Dr. Mohan Lal', '8111234567', 'mohan.lal@hospital.com', '001110112011', 'ALWHS0011L', '1981-12-12', 'Male', '18', 'MBBS', 'General Physician', '2026-01-01 17:57:19', '2026-01-01 17:57:19', 'admin', 'admin');

INSERT IGNORE INTO doctors (doctor_id, doctor_name, mobile, email_id, aadhar_card, pan_card, date_of_birth, gender, year_of_experience, qualification, specialization, created_at, updated_at, created_by, updated_by)
VALUES ('DOC0012', 'Dr. Sunita Reddy', '8121234567', 'sunita.reddy@hospital.com', '001210122012', 'AMYKW0012M', '1982-01-13', 'Female', '21', 'MBBS', 'General Physician', '2026-01-01 17:57:19', '2026-01-01 17:57:19', 'admin', 'admin');

INSERT IGNORE INTO doctors (doctor_id, doctor_name, mobile, email_id, aadhar_card, pan_card, date_of_birth, gender, year_of_experience, qualification, specialization, created_at, updated_at, created_by, updated_by)
VALUES ('DOC0013', 'Dr. Karan Joshi', '8131234567', 'karan.joshi@hospital.com', '001310132013', 'ANANA0013N', '1983-02-14', 'Male', '24', 'MBBS', 'General Physician', '2026-01-01 17:57:19', '2026-01-01 17:57:19', 'admin', 'admin');