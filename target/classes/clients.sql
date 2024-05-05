INSERT INTO clients (id, fullname, email, password, phone, address, postal_code, city, country) VALUES
(2, 'Ana López', 'analopez@example.com', 'contraseña2', '+123456702', 'Calle 2 Numero 2', '54321', 'Barcelona', 'España'),
(3, 'David Martínez', 'davidmartinez@example.com', 'contraseña3', '+123456703', 'Calle 3 Numero 3', '23456', 'Valencia', 'España'),
(4, 'Laura García', 'lauragarcia@example.com', 'contraseña4', '+123456704', 'Calle 4 Numero 4', '65432', 'Sevilla', 'España'),
(5, 'Carlos Sánchez', 'carlossanchez@example.com', 'contraseña5', '+123456705', 'Calle 5 Numero 5', '87654', 'Zaragoza', 'España'),
(6, 'María Rodríguez', 'mariarodriguez@example.com', 'contraseña6', '+123456706', 'Calle 6 Numero 6', '34567', 'Málaga', 'España'),
(7, 'Miguel López', 'miguellopez@example.com', 'contraseña7', '+123456707', 'Calle 7 Numero 7', '76543', 'Murcia', 'España'),
(8, 'Lucía Martínez', 'luciamartinez@example.com', 'contraseña8', '+123456708', 'Calle 8 Numero 8', '45678', 'Palma', 'España'),
(9, 'Alejandro Sánchez', 'alejandrosanchez@example.com', 'contraseña9', '+123456709', 'Calle 9 Numero 9', '98765', 'Bilbao', 'España'),
(10, 'Sofía Pérez', 'sofiaperez@example.com', 'contraseña10', '+123456710', 'Calle 10 Numero 10', '56789', 'Alicante', 'España'),
(11, 'Diego Rodríguez', 'diegorodriguez@example.com', 'contraseña11', '+123456711', 'Calle 11 Numero 11', '45632', 'Córdoba', 'España'),
(12, 'Paula Martínez', 'paulamartinez@example.com', 'contraseña12', '+123456712', 'Calle 12 Numero 12', '76543', 'Valladolid', 'España'),
(13, 'Jorge López', 'jorgelopez@example.com', 'contraseña13', '+123456713', 'Calle 13 Numero 13', '87654', 'Vigo', 'España'),
(14, 'Laura Pérez', 'lauraperez@example.com', 'contraseña14', '+123456714', 'Calle 14 Numero 14', '98765', 'Gijón', 'España'),
(15, 'Pablo Rodríguez', 'pablorodriguez@example.com', 'contraseña15', '+123456715', 'Calle 15 Numero 15', '23456', 'Granada', 'España'),
(16, 'Isabel García', 'isabelgarcia@example.com', 'contraseña16', '+123456716', 'Calle 16 Numero 16', '12345', 'Santander', 'España'),
(17, 'Pedro Sánchez', 'pedrosanchez@example.com', 'contraseña17', '+123456717', 'Calle 17 Numero 17', '23456', 'Toledo', 'España');
(18, 'Juan Pérez', 'juanperez@example.com', 'contraseña1', '+123456701', 'Calle 1 Numero 1', '12345', 'Madrid', 'España'),

UPDATE clients 
SET create_at = DATE '2023-01-01' + CAST(FLOOR(random() * (CURRENT_DATE - DATE '2023-01-01')) AS INTEGER)
WHERE create_at IS NULL;