--Users table
INSERT INTO users (username, password, email) VALUES ('user', '$2a$12$IzA1Ja1LH4PSMoro9PeITO1etDlknPjSX1nLusgt1vi9c1uaEXdEK','user@test.nl');
INSERT INTO users (username, password, email) VALUES ('admin', '$2a$12$IzA1Ja1LH4PSMoro9PeITO1etDlknPjSX1nLusgt1vi9c1uaEXdEK', 'admin@test.nl');
INSERT INTO authorities (username, authority) VALUES ('user', 'ROLE_USER');
INSERT INTO authorities (username, authority) VALUES ('admin', 'ROLE_USER');
INSERT INTO authorities (username, authority) VALUES ('admin', 'ROLE_ADMIN');

--service_category table
INSERT INTO service_category (category_name, description) VALUES ('Construction, Maintenance & Cleaning', 'Building, customizing, repairing and cleaning things as a service'),
('Professional Service', 'The services of individuals with professional qualifications such as an accountant or lawyer'),
('Financial Services', 'Services related to assets such as banks, investment funds and tax preparation'),
('Sports & Fitness', 'Gyms and other fitness and sports activities'),
('Event Services', 'Planning and delivery of events such as weddings'),
('Knowledge', 'Knowledge based services such as consulting');

--Services table
INSERT INTO services (price, category_id, service_name, description, state, user_name) VALUES (50.00 , 1, 'Cleaning Cars at your front door', 'Best cleaning car service in the city we will take care of your car and make it brand new','Available','admin'),
(100.00, 2, 'Legal Consultation', 'Professional legal services from experienced lawyers', 'Available','admin'),
(75.00, 3, 'Financial Planning', 'Comprehensive financial planning services to secure your future', 'Available','admin'),
(80.00, 4, 'Personal Training Sessions', 'Customized fitness training sessions with certified trainers', 'Available','admin'),
(150.00, 5, 'Wedding Planning Package', 'Full-service wedding planning for your special day', 'Available','admin'),
(120.00, 6, 'Business Consulting', 'Expert knowledge-based consulting for business growth', 'Available','admin');

--service_request table
INSERT INTO service_request (service_id, message, request_State,user_name) VALUES (1, 'Hallo there i have seen your service and i would like to book appointment with you', 'Pending', 'user'),
(2, 'I need assistance with financial planning. Can we discuss available options?', 'Pending', 'user'),
(3, 'Interested in personal training sessions. Please provide details and availability.', 'Pending', 'user'),
(4, 'We are planning a wedding and would like to inquire about your wedding planning services', 'Pending', 'user'),
(5, 'Looking for business consulting services to enhance our company strategies', 'Pending', 'user'),
(6, 'Require legal consultation for a real estate matter. Please advise on availability.', 'Pending', 'user');

--transaction table
INSERT INTO transaction (service_request_id, transaction_date, is_payed, invoice) VALUES (1, '2024-01-15', true, 'invoicesPdf\invoice-1.pdf'),
 (2, '2024-01-16', true, 'invoicesPdf\invoice-2.pdf'),
 (3, '2024-01-17', true, 'invoicesPdf\invoice-3.pdf'),
 (4, '2024-01-18', false, 'invoicesPdf\invoice-4.pdf'),
 (5, '2024-01-19', true, 'invoicesPdf\invoice-5.pdf'),
 (6, '2024-01-20', false, 'invoicesPdf\invoice-6.pdf');


