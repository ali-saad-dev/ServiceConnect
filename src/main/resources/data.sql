--service_category table
INSERT INTO service_category (id, category_name, description) VALUES (1, 'Construction, Maintenance & Cleaning', 'Building, customizing, repairing and cleaning things as a service'),
(2, 'Professional Service', 'The services of individuals with professional qualifications such as an accountant or lawyer'),
(3, 'Financial Services', 'Services related to assets such as banks, investment funds and tax preparation'),
(4, 'Sports & Fitness', 'Gyms and other fitness and sports activities'),
(5, 'Event Services', 'Planning and delivery of events such as weddings'),
(6, 'Knowledge', 'Knowledge based services such as consulting');

--Services table
INSERT INTO services (id, price, category_id, services_name, description, state) VALUES (1, 50.00 , 1, 'Cleaning Cars at your front door', 'Best cleaning car service in the city we will take care of your car and make it brand new','AVAILABLE'),
(2, 100.00, 2, 'Legal Consultation', 'Professional legal services from experienced lawyers', 'AVAILABLE'),
(3, 75.00, 3, 'Financial Planning', 'Comprehensive financial planning services to secure your future', 'AVAILABLE'),
(4, 80.00, 4, 'Personal Training Sessions', 'Customized fitness training sessions with certified trainers', 'AVAILABLE'),
(5, 150.00, 5, 'Wedding Planning Package', 'Full-service wedding planning for your special day', 'AVAILABLE'),
(6, 120.00, 6, 'Business Consulting', 'Expert knowledge-based consulting for business growth', 'AVAILABLE');

--service_request table
INSERT INTO service_request (id, service_id, message, state) VALUES (1, 1, 'Hallo there i have seen your service and i would like to book appointment with you', 'AVAILABLE'),
(2, 3, 'I need assistance with financial planning. Can we discuss available options?', 'AVAILABLE'),
(3, 4, 'Interested in personal training sessions. Please provide details and availability.', 'AVAILABLE'),
(4, 5, 'We are planning a wedding and would like to inquire about your wedding planning services', 'AVAILABLE'),
(5, 6, 'Looking for business consulting services to enhance our company strategies', 'AVAILABLE'),
(6, 2, 'Require legal consultation for a real estate matter. Please advise on availability.', 'AVAILABLE');

--transaction table
INSERT INTO transaction (id, service_request_id, transaction_date, is_payed, invoice) VALUES (1, 1, '2024-01-15', true, 'LesOpdrachten\\EindProject\\ServiceConnect\\invoicesPdf\\invoice_1706085619924.pdf'),
 (2, 2, '2024-01-16', false, NULL), -- Transaction not yet paid
 (3, 3, '2024-01-17', true, 'LesOpdrachten\\EindProject\\ServiceConnect\\invoicesPdf\\invoice_1706085623456.pdf'),
 (4, 4, '2024-01-18', false, NULL),
 (5, 5, '2024-01-19', true, 'LesOpdrachten\\EindProject\\ServiceConnect\\invoicesPdf\\invoice_1706085627890.pdf'),
 (6, 6, '2024-01-20', true, 'LesOpdrachten\\EindProject\\ServiceConnect\\invoicesPdf\\invoice_1706085631234.pdf');