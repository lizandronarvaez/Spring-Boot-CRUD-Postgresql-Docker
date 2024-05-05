INSERT INTO categories (name) 
VALUES ('proteinas'),
       ('carbohidratos'),
       ('creatinas'),
       ('sin_lactosa');

-- Insertar más productos de proteínas
INSERT INTO products (id, fullname, description, price, quantity, category_id)
VALUES 
    (14, 'Proteína de Caseína en Polvo', 'Suplemento de proteínas de caseína para la recuperación muscular nocturna.', 39.99, 80, (SELECT id FROM categories WHERE name = 'proteinas')),
    (15, 'Barra de Proteínas de Cookies y Crema', 'Barra de proteínas con sabor a cookies y crema, perfecta como merienda.', 3.49, 120, (SELECT id FROM categories WHERE name = 'proteinas')),
    (16, 'Batido de Proteínas de Chocolate Blanco', 'Batido de proteínas con sabor a chocolate blanco, delicioso y nutritivo.', 29.99, 100, (SELECT id FROM categories WHERE name = 'proteinas')),
    (17, 'Proteína de Huevo en Polvo', 'Suplemento de proteínas de huevo en polvo, ideal para dietas altas en proteínas.', 36.99, 90, (SELECT id FROM categories WHERE name = 'proteinas')),
    (18, 'Aislado de Proteína de Suero', 'Aislado de proteína de suero de leche, de rápida absorción y bajo en carbohidratos.', 44.99, 70, (SELECT id FROM categories WHERE name = 'proteinas'));

-- Insertar más productos de carbohidratos
INSERT INTO products (id, fullname, description, price, quantity, category_id)
VALUES 
    (14, 'Galletas de Avena con Pasas', 'Galletas de avena con pasas, ideales para una merienda energética.', 2.99, 150, (SELECT id FROM categories WHERE name = 'carbohidratos')),
    (15, 'Pan Integral de Trigo', 'Pan integral de trigo, rico en fibra y perfecto para un desayuno saludable.', 3.99, 120, (SELECT id FROM categories WHERE name = 'carbohidratos')),
    (16, 'Granola con Frutas Secas', 'Mezcla de granola con frutas secas, perfecta para acompañar el yogur.', 5.49, 100, (SELECT id FROM categories WHERE name = 'carbohidratos')),
    (17, 'Cereal de Avena Multigrano', 'Cereal de avena multigrano, bajo en grasas y alto en fibra.', 4.49, 130, (SELECT id FROM categories WHERE name = 'carbohidratos'));

-- Insertar más productos de creatina
INSERT INTO products (id, fullname, description, price, quantity, category_id)
VALUES 
    (11, 'Monohidrato de Creatina', 'Monohidrato de creatina micronizada para aumentar la fuerza y la resistencia.', 19.99, 75, (SELECT id FROM categories WHERE name = 'creatinas')),
    (12, 'Creatina Kre-Alkalyn', 'Fórmula de creatina alcalina para una mejor absorción y menos retención de agua.', 24.99, 60, (SELECT id FROM categories WHERE name = 'creatinas')),
    (13, 'Creatina HCL', 'Creatina hidrocloruro para una mayor solubilidad y absorción.', 29.99, 50, (SELECT id FROM categories WHERE name = 'creatinas'));

-- Insertar más productos sin lactosa
INSERT INTO products (id, fullname, description, price, quantity, category_id)
VALUES 
    (14, 'Batido de Proteínas de Soja', 'Batido de proteínas a base de soja, sin lactosa y bajo en calorías.', 4.99, 100, (SELECT id FROM categories WHERE name = 'sin_lactosa')),
    (15, 'Queso Vegano de Almendras', 'Queso vegano elaborado con almendras, sin lactosa ni ingredientes de origen animal.', 6.99, 80, (SELECT id FROM categories WHERE name = 'sin_lactosa')),
    (16, 'Yogur de Coco', 'Yogur de coco sin lactosa, cremoso y delicioso.', 3.49, 120, (SELECT id FROM categories WHERE name = 'sin_lactosa'));
