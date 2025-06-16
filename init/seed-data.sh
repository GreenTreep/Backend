#!/bin/bash

echo "⏳ Attente de 20 secondes pour s'assurer que Spring Boot a créé les tables..."
sleep 20

echo "🚀 Exécution des requêtes d’initialisation dans MySQL..."

docker exec -i mysql-docker mysql -uroot -proot greentrip <<'EOF'
-- Produits
INSERT INTO product (name, description, price, image_url, stock)
SELECT * FROM (SELECT 'Sac à dos 50L', 'Sac de randonnée 50L imperméable, dos ventilé et sangles de compression.', 89.99, 'sac-a-dos-50l.jpg', 12) AS tmp
WHERE NOT EXISTS (SELECT 1 FROM product WHERE name = 'Sac à dos 50L') LIMIT 1;

INSERT INTO product (name, description, price, image_url, stock)
SELECT * FROM (SELECT 'Chaussures de marche', 'Chaussures de randonnée haute avec semelle anti-dérapante.', 74.90, 'chaussures-marche.jpg', 20) AS tmp
WHERE NOT EXISTS (SELECT 1 FROM product WHERE name = 'Chaussures de marche') LIMIT 1;

INSERT INTO product (name, description, price, image_url, stock)
SELECT * FROM (SELECT 'Bâtons de randonnée', 'Paire de bâtons télescopiques en aluminium, poignées ergonomiques.', 39.99, 'batons-randonnee.jpg', 30) AS tmp
WHERE NOT EXISTS (SELECT 1 FROM product WHERE name = 'Bâtons de randonnée') LIMIT 1;

INSERT INTO product (name, description, price, image_url, stock)
SELECT * FROM (SELECT 'Tente 2 places', 'Tente légère pour 2 personnes, montage facile, résistante au vent.', 129.00, 'tente-2-places.jpg', 8) AS tmp
WHERE NOT EXISTS (SELECT 1 FROM product WHERE name = 'Tente 2 places') LIMIT 1;

INSERT INTO product (name, description, price, image_url, stock)
SELECT * FROM (SELECT 'Lampe frontale LED', 'Lampe frontale rechargeable avec mode SOS et intensité réglable.', 24.50, 'lampe-frontale.jpg', 25) AS tmp
WHERE NOT EXISTS (SELECT 1 FROM product WHERE name = 'Lampe frontale LED') LIMIT 1;

INSERT INTO product (name, description, price, image_url, stock)
SELECT * FROM (SELECT 'Veste imperméable', 'Veste coupe-vent imperméable, respirante et compacte.', 59.95, 'veste-impermeable.jpg', 15) AS tmp
WHERE NOT EXISTS (SELECT 1 FROM product WHERE name = 'Veste imperméable') LIMIT 1;

INSERT INTO product (name, description, price, image_url, stock)
SELECT * FROM (SELECT 'Réchaud portable', 'Mini réchaud à gaz pliable, idéal pour bivouac et randonnée.', 22.99, 'rechaud-portable.jpg', 18) AS tmp
WHERE NOT EXISTS (SELECT 1 FROM product WHERE name = 'Réchaud portable') LIMIT 1;

INSERT INTO product (name, description, price, image_url, stock)
SELECT * FROM (SELECT 'Bouteille filtrante', 'Gourde avec filtre à eau intégré, 650ml, pour eau de source.', 34.90, 'bouteille-filtrante.jpg', 22) AS tmp
WHERE NOT EXISTS (SELECT 1 FROM product WHERE name = 'Bouteille filtrante') LIMIT 1;

INSERT INTO product (name, description, price, image_url, stock)
SELECT * FROM (SELECT 'Couteau multifonction', 'Outil 15-en-1 : couteau, tournevis, ouvre-boîte, ciseaux...', 19.90, 'couteau-multifonction.jpg', 40) AS tmp
WHERE NOT EXISTS (SELECT 1 FROM product WHERE name = 'Couteau multifonction') LIMIT 1;

INSERT INTO product (name, description, price, image_url, stock)
SELECT * FROM (SELECT 'Poncho de pluie', 'Poncho ultra-léger, pliable avec capuche, taille universelle.', 14.99, 'poncho-pluie.jpg', 35) AS tmp
WHERE NOT EXISTS (SELECT 1 FROM product WHERE name = 'Poncho de pluie') LIMIT 1;
EOF

echo "✅ Données initiales insérées avec succès !"
