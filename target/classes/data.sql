INSERT INTO usuario (nombre_completo, nombre_corto, image ,username, password,resetear_password) values
    ('Juan Sebastian Gonzalez Sanchez','Juan', 'jsgs.png', 'jsgs', '$2a$11$TckEYxY/0DPf6OfpQhXKP./hl45UlgXYs0jQFsZZCwBEjUCo7bUKy',0),
    ('Luis Orlando Gonzalez Martinez', 'Orlando', 'ogm.png', 'ogm', '$2a$11$TckEYxY/0DPf6OfpQhXKP./hl45UlgXYs0jQFsZZCwBEjUCo7bUKy',0),
    ('Luis David Sevillano Yucra', 'David', 'ogm.png', 'dei', '$2a$11$TckEYxY/0DPf6OfpQhXKP./hl45UlgXYs0jQFsZZCwBEjUCo7bUKy',0)
    ;

INSERT INTO cuenta (iban, saldo,usuario_principal) values
    ('ES00 0000 2000 00 0000000000', 3000.0,1),
    ('ES00 0000 2000 00 0000000001', 600.0,1),
    ('ES00 0000 2000 00 0000000002', 20.0,3)
    ;

INSERT INTO categoria (nombre) values
    ('Ocio'),
    ('Restaurantes'),
    ('Compras Internet'),
    ('Viajes'),
    ('SuperMercados'),
    ('Otros')
    ;

INSERT INTO tarjeta (numero,cuenta_id) VALUES
    ("4599847915444147", 1)
	;

INSERT INTO movimiento (cantidad, tipo, concepto, saldo_actual, usuario_id, categoria_id, cuenta_id, tarjeta_id, fecha) values
    (35.04, 1, 'Clases', 3000-35.04, 1 , 6, 1, 1, '2021-06-09'),
    (20.04, 1, 'Clases', 3000-35.04-20.04, 2 , 6, 1, 1, '2021-06-02')
    ;

-- actualizo saldo de las cuentas según los movimientos realizados
UPDATE cuenta SET saldo = 3000-35.04-20.04 WHERE id=1;


-- relaciones usuario_cuenta
INSERT INTO usuario_cuenta (saldo_en_cuenta, usuario_id, cuenta_id) values
    (344.52,1, 1),
    (344.52,2, 1),
    (344.52,1, 2),
    (344.52,3, 3),
    (344.52,2, 3)
    ;