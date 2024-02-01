--service_category table
INSERT INTO service_category (category_name, description) VALUES ('Construction, Maintenance & Cleaning', 'Building, customizing, repairing and cleaning things as a service'),
('Professional Service', 'The services of individuals with professional qualifications such as an accountant or lawyer'),
('Financial Services', 'Services related to assets such as banks, investment funds and tax preparation'),
('Sports & Fitness', 'Gyms and other fitness and sports activities'),
('Event Services', 'Planning and delivery of events such as weddings'),
('Knowledge', 'Knowledge based services such as consulting');

--Services table
INSERT INTO services (price, category_id, services_name, description, state) VALUES (50.00 , 1, 'Cleaning Cars at your front door', 'Best cleaning car service in the city we will take care of your car and make it brand new','Available'),
(100.00, 2, 'Legal Consultation', 'Professional legal services from experienced lawyers', 'Available'),
(75.00, 3, 'Financial Planning', 'Comprehensive financial planning services to secure your future', 'Available'),
(80.00, 4, 'Personal Training Sessions', 'Customized fitness training sessions with certified trainers', 'Available'),
(150.00, 5, 'Wedding Planning Package', 'Full-service wedding planning for your special day', 'Available'),
(120.00, 6, 'Business Consulting', 'Expert knowledge-based consulting for business growth', 'Available');

--service_request table
INSERT INTO service_request (service_id, message, request_State) VALUES (1, 'Hallo there i have seen your service and i would like to book appointment with you', 'Pending'),
(2, 'I need assistance with financial planning. Can we discuss available options?', 'Pending'),
(3, 'Interested in personal training sessions. Please provide details and availability.', 'Pending'),
(4, 'We are planning a wedding and would like to inquire about your wedding planning services', 'Pending'),
(5, 'Looking for business consulting services to enhance our company strategies', 'Pending'),
(6, 'Require legal consultation for a real estate matter. Please advise on availability.', 'Pending');

--transaction table
INSERT INTO transaction (service_request_id, transaction_date, is_payed, invoice) VALUES (1, '2024-01-15', true, 'LesOpdrachten\\EindProject\\ServiceConnect\\invoicesPdf\\invoice_1706085619924.pdf'),
 (2, '2024-01-16', false, NULL), -- Transaction not yet paid
 (3, '2024-01-17', true, 'LesOpdrachten\\EindProject\\ServiceConnect\\invoicesPdf\\invoice_1706085623456.pdf'),
 (4, '2024-01-18', false, NULL),
 (5, '2024-01-19', true, 'LesOpdrachten\\EindProject\\ServiceConnect\\invoicesPdf\\invoice_1706085627890.pdf'),
 (6, '2024-01-20', true, 'LesOpdrachten\\EindProject\\ServiceConnect\\invoicesPdf\\invoice_1706085631234.pdf');

--10 unit test voor en 2 service testen, en 2 integration test
--proberen design pattern te implementeren


INSERT INTO users (username, password, email) VALUES ('user', '$2a$12$IzA1Ja1LH4PSMoro9PeITO1etDlknPjSX1nLusgt1vi9c1uaEXdEK','user@test.nl');
INSERT INTO users (username, password, email) VALUES ('admin', '$2a$12$IzA1Ja1LH4PSMoro9PeITO1etDlknPjSX1nLusgt1vi9c1uaEXdEK', 'admin@test.nl');
INSERT INTO authorities (username, authority) VALUES ('user', 'ROLE_USER');
INSERT INTO authorities (username, authority) VALUES ('admin', 'ROLE_USER');
INSERT INTO authorities (username, authority) VALUES ('admin', 'ROLE_ADMIN');