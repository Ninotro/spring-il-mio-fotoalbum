INSERT INTO photos (title, description, url, visible) VALUES ('Mountain Landscape', 'Majestic mountain view', 'https://i.pinimg.com/736x/4c/ba/87/4cba8729a7bcc7bae92b7eb28063d4b3.jpg', true);

INSERT INTO photos (title, description, url, visible) VALUES ('Ocean Sunset', 'Beautiful sunset over the ocean', 'https://img.freepik.com/premium-photo/wooden-stairway-sunrise-style-tropical-landscapes_916626-8053.jpg', true);

INSERT INTO photos (title, description, url, visible) VALUES('City Skyline', 'Cityscape at night', 'https://i0.wp.com/fotografinviaggio.it/wp-content/uploads/2023/04/Immagine-copertina-per-blog-1-2-2.jpg?fit=1000%2C662&ssl=1', true);

INSERT INTO photos (title, description, url, visible) VALUES('Flower Garden', 'Colorful flowers in a garden', 'https://img.freepik.com/free-photo/nature-rock-objects-frame-tranquil-waterscape-generative-ai_188544-12636.jpg', true);

INSERT INTO photos (title, description, url, visible) VALUES('Snowy Forest', 'Scenic view of a snowy forest', 'https://thumbs.dreamstime.com/b/paesaggi-di-yosemite-46208063.jpg', true);
INSERT INTO photos (title, description, url, visible) VALUES('Flower Garden', 'Colorful flowers in a garden', 'https://img.freepik.com/free-photo/nature-rock-objects-frame-tranquil-waterscape-generative-ai_188544-12636.jpg', true);


INSERT INTO users (first_name, last_name, email, password) VALUES('Giovanni', 'Verdi', 'giovanniverdi@gmail.com', '{noop}giovanni');
INSERT INTO users (first_name, last_name, email, password) VALUES('Anna', 'Neri', 'annaneri@gmail.com', '{noop}anna');


INSERT INTO roles (id, name) VALUES(1, 'ADMIN');
INSERT INTO roles (id, name) VALUES(2, 'USER');


INSERT INTO users_roles (user_id, roles_id) VALUES(1, 2);
INSERT INTO users_roles (user_id, roles_id) VALUES(2, 1);
INSERT INTO users_roles (user_id, roles_id) VALUES(2, 2);


INSERT INTO categories (name) VALUES('travel');
INSERT INTO categories (name) VALUES('food');
INSERT INTO categories (name) VALUES('technology');


INSERT INTO photos_categories (photo_id, categories_id) VALUES(2, 3)
INSERT INTO photos_categories (photo_id, categories_id) VALUES(2, 2);
INSERT INTO photos_categories (photo_id, categories_id) VALUES(2, 1);
INSERT INTO photos_categories (photo_id, categories_id) VALUES(1, 2);
INSERT INTO photos_categories (photo_id, categories_id) VALUES(4, 3);


INSERT INTO messages (body, sender_email) VALUES ('Ciao, che bella foto!' , 'ninotroia96@gmail.com')