-- src/main/resources/data.sql  (हा फाइल replace कर)

INSERT INTO patients (
    patient_id, full_name, mobile, email, gender, date_of_birth,
    address, city, state, pincode, aadhar_card,
    is_active, created_at, updated_at, created_by, updated_by
) VALUES
('PAT-10001', 'Ramu Pawar', '9876541356', 'ramu.pawar92@gmail.com', 'Male', '1992-05-14',
 'Flat 203, Green Valley, Balewadi', 'Patna', 'Bihar', '411045', '546782239998',
 true, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),

('PAT-10002', 'Priya Sanjay Sharma', '9823451357', 'priya.sharma95@gmail.com', 'Female', '1995-08-22',
 'B-1204, Lodha Belmondo', 'Ujjain', 'Madhya Pradesh', '400070', '873456789012',
 true, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),

('PAT-10003', 'Amit Rajesh Patil', '9877712345', 'amit.patil88@gmail.com', 'Male', '1988-03-10',
 'Runwal Forests, Kanjurmarg', 'Chennai', 'Tamil Nadu', '400078', '456789123456',
 true, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),

('PAT-10004', 'Sneha Vijay Jadhav', '8669012345', 'sneha.jadhav93@gmail.com', 'Female', '1993-11-30',
 'Sai Residency, CIDCO', 'GandhiNagar', 'Gujrat', '431003', '789123456789',
 true, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),

('PAT-10005', 'Vikram Sunil Desai', '9004412345', 'vikram.desai90@gmail.com', 'Male', '1990-07-18',
 'Dhanori-Lohegaon Road', 'Pune', 'Maharashtra', '411032', '321654987321',
 true, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),

('PAT-10006', 'Pooja Mahesh More', '8877612345', 'pooja.more94@gmail.com', 'Female', '1994-02-25',
 'Prestige Lakeside Habitat, Whitefield', 'Bengaluru', 'Karnataka', '560066', '654321987654',
 true, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),

('PAT-10007', 'Rahul Dinesh Kadam', '9766412345', 'rahul.kadam89@gmail.com', 'Male', '1989-12-05',
 'Canada Corner', 'Nashik', 'Maharashtra', '422005', '147258369147',
 true, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),

('PAT-10008', 'Neha Santosh Gaikwad', '8999212345', 'neha.gaikwad96@gmail.com', 'Female', '1996-09-17',
 'Manik Bagh Road', 'Nagpur', 'Maharashtra', '440004', '963852741963',
 true, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),

('PAT-10009', 'Sachin Ramesh Bhosale', '9552512345', 'sachin.b91@gmail.com', 'Male', '1991-04-08',
 'Rajwada Area', 'Kolhapur', 'Maharashtra', '416002', '258963147258',
 true, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),

('PAT-10010', 'Ankita Pradeep Kulkarni', '7349012345', 'ankita.k97@gmail.com', 'Female', '1997-06-12',
 'Hauz Khas Village', 'Delhi', 'Delhi', '110016', '741852963741',
 true, NOW(), NOW(), 'SYSTEM', 'SYSTEM');